package com.kx.system.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.config.enums.*;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.DeptLocationInfo;
import com.kx.system.domain.JobCodeRelation;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.anchor.Location;
import com.kx.system.domain.customer.CustomerLabel;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.enums.FtpSyncFileEnum;
import com.kx.system.domain.es.EsAnchorPointDto;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.mapper.*;
import com.kx.system.service.ISysUserService;
import com.kx.system.service.es.EsAnchorPointService;
import com.kx.system.util.BeanUtils;
import com.kx.system.util.FtpUtil;
import com.kx.system.util.GPSUtil;
import com.kx.system.util.GeocodingUtil;
import com.kx.system.util.vgrid.GridUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户以及锚点信息跑批任务
 */
@Slf4j
@Component
public class CustomInfoSyncJob extends BaseJob {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    @Resource
    private AnchorPointMapper anchorPointMapper;

    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;

    @Resource
    private GeocodingUtil geocodingUtil;

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;

    @Resource
    private DeptLocationInfoMapper deptLocationInfoMapper;

    @Resource
    private JobCodeRelationMapper jobCodeRelationMapper;

    @Resource
    private CustomerLabelMapper customerLabelMapper;
    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private OpeningCardRecordMapper openingCardRecordMapper;

    @Resource
    private CreditRecordMapper creditRecordMapper;

//    @Value("${data-runtime.CustomInfoSyncJob:-}")
    private String cron;

    @Resource
    private EsAnchorPointService esAnchorPointService;

    /**
     * 每日跑批客户数据，新增/更新
     */
    @Override
    protected void execute(Date date) {
        Date timeFlag = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String now = sdf.format(timeFlag);
        log.info("==========" + now + "客户数据开始跑批==========");

        //获取所有部门
        List<SysDept> depts = sysDeptMapper.selectAll();
        //将部门编码和名称存放到map中
        Map<String, String> deptMap = depts.stream().collect(Collectors.toMap(SysDept::getOrgNum, SysDept::getDeptName));
        //将部门id和编码存放到map中
        Map<String, Long> deptIdCodeMap = depts.stream().collect(Collectors.toMap(SysDept::getOrgNum, SysDept::getDeptId));

        //获取所有机构经纬度
        List<DeptLocationInfo> deptLocationInfos = deptLocationInfoMapper.listAll();
        //将部门编码及其坐标存入map中，若有经纬度为空的客户，则将所属机构的经纬度填入
        Map<String, String> deptLocation = deptLocationInfos.stream().collect(Collectors.toMap(DeptLocationInfo::getOrgNum, DeptLocationInfo::getPoint));

        //获取所有职业代码以及职业
        List<JobCodeRelation> jobCodeRelations = jobCodeRelationMapper.listAll();
        Map<String, String> jobCode = jobCodeRelations.stream().collect(Collectors.toMap(JobCodeRelation::getJobCode, JobCodeRelation::getJobName));

        //划定区域
        List<String> codes = defineArea();

        //本级以及下级区域列表
        HashMap<String, List<PaAddrInfoType>> parentChildAreaMap = getChild();

        //设定批次大小
        int size = 500;

        //新增绩效客户列表
        List<PerformanceCustomerInfo> performanceCustomerInfos = new ArrayList<>();
        //新增信贷客户列表
        List<CreditCustomerInfo> creditCustomerInfos = new ArrayList<>();
        //新增绩效锚点列表
        List<AnchorPoint> perAnchorPoints = new ArrayList<>();
        //新增授信锚点列表
        List<AnchorPoint> creAnchorPoints = new ArrayList<>();
        //新增绩效客户标签关联关系
        List<CustomerLabel> perLabels = new ArrayList<>();
        //新增授信客户标签关联关系
        List<CustomerLabel> creLabels = new ArrayList<>();


        FtpUtil.getFileInputStream(FtpSyncFileEnum.CUSTOMER_FILE_PATH, date, inputStream -> {

            InputStreamReader fileReader = new InputStreamReader(inputStream);
            CSVParser parser = null;
            try {
                parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withDelimiter('|').withQuote(null));
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (CSVRecord record : parser) {
                //新增/更新标志
                String flag = record.get(51);
                //新增
                if ("0".equals(flag)) {
                    //绩效客户
                    if ("1".equals(record.get(49))) {
                        //构建绩效客户
                        PerformanceCustomerInfo performanceCustomerInfo = createPerformance(sdf, deptMap, deptIdCodeMap, jobCode, record, perLabels);
                        //构建绩效锚点
                        AnchorPoint anchorPoint = createPerAnchor(performanceCustomerInfo);

                        //解析地址获取经纬度
                        getPerLonAndLat(deptLocation, performanceCustomerInfo, anchorPoint);

                        //绩效锚点落格
                        initFullAnchor(anchorPoint, codes, parentChildAreaMap);

                        performanceCustomerInfo.setBelongGridCode(anchorPoint.getBelongGrid());
                        performanceCustomerInfo.setBelongGridName(anchorPoint.getBelongGridName());
                        performanceCustomerInfo.setGridManager(anchorPoint.getGridManager());

                        perAnchorPoints.add(anchorPoint);
                        performanceCustomerInfos.add(performanceCustomerInfo);

                        if (performanceCustomerInfos.size() == size) {
                            //插入绩效客户
                            performanceCustomerInfoMapper.insertBatch(performanceCustomerInfos);
                            performanceCustomerInfos.clear();
                            //插入绩效锚点
                            anchorPointMapper.insertBatch(perAnchorPoints);
                            perAnchorPoints.clear();
                            //插入绩效标签
                            customerLabelMapper.insertBatch(perLabels);
                            perLabels.clear();
                        }
                    }
                    if ("1".equals(record.get(48))) {
                        //构建授信客户
                        CreditCustomerInfo creditCustomerInfo = createCredit(sdf, deptMap, deptIdCodeMap, jobCode, record, creLabels);

                        //构建授信锚点
                        AnchorPoint anchorPoint = createCreAnchor(creditCustomerInfo);

                        //解析地址获取经纬度
                        getLonAndLat(deptLocation, creditCustomerInfo, anchorPoint);

                        //授信锚点落格
                        initFullAnchor(anchorPoint, codes, parentChildAreaMap);

                        creditCustomerInfo.setBelongGridCode(anchorPoint.getBelongGrid());
                        creditCustomerInfo.setBelongGridName(anchorPoint.getBelongGridName());
                        creditCustomerInfo.setGridManager(anchorPoint.getGridManager());

                        creditCustomerInfos.add(creditCustomerInfo);
                        creAnchorPoints.add(anchorPoint);

                        if (creditCustomerInfos.size() == size) {
                            creditCustomerInfoMapper.insertBatch(creditCustomerInfos);
                            creditCustomerInfos.clear();

                            anchorPointMapper.insertBatch(creAnchorPoints);
                            creAnchorPoints.clear();

                            customerLabelMapper.insertBatch(creLabels);
                            creLabels.clear();
                        }
                    }
                }
            }
            performanceCustomerInfoMapper.insertBatch(performanceCustomerInfos);
            anchorPointMapper.insertBatch(perAnchorPoints);
            creditCustomerInfoMapper.insertBatch(creditCustomerInfos);
            anchorPointMapper.insertBatch(creAnchorPoints);
            customerLabelMapper.insertBatch(perLabels);
            customerLabelMapper.insertBatch(creLabels);
            //更新绩效锚点客户id字段
            anchorPointMapper.updateSameDayPerCustomerId();
            //更新授信锚点客户id字段
            anchorPointMapper.updateSameDayCreCustomerId();
            //更新授信客户是否乡贷字段
            creditCustomerInfoMapper.updateIsProduct();
            //更新绩效客户标签关联表客户id
            customerLabelMapper.updatePerCustomerIdByNmAndType();
            //更新授信客户标签关联表客户id
            customerLabelMapper.updateCreCustomerIdByNmAndType();
            openingCardRecordMapper.updateIdentity();
            creditRecordMapper.updateIdentity();
        });
        log.info("==========" + now + "客户数据跑批结束==========");
    }

    @Override
    public String getJobDesc() {
        return "客户以及锚点信息跑批任务";
    }

    @Override
    public String getCron() {
        return cron;
    }

    /**
     * 确定绩效锚点坐标
     *
     * @param deptLocation
     * @param performanceCustomerInfo
     * @param anchorPoint
     */
    private void getPerLonAndLat(Map<String, String> deptLocation, PerformanceCustomerInfo performanceCustomerInfo, AnchorPoint anchorPoint) {
        String address = performanceCustomerInfo.getCustomerAddress();
        if (StringUtils.isNotEmpty(address)) {
            JSONObject jsonObject = geocodingUtil.geoCode(address);
            Location location = JSON.parseObject(jsonObject.getString("location"), Location.class);
            if (location != null) {
                double[] locationArr = GPSUtil.bd09_To_gps84(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLng()));
                anchorPoint.setLon(BigDecimal.valueOf(locationArr[1]));
                anchorPoint.setLat(BigDecimal.valueOf(locationArr[0]));
            } else {
                //未解析成功则落入当前所属机构
                if (StringUtils.isNotEmpty(deptLocation.get(performanceCustomerInfo.getOrgNum()))) {
                    String orgLocation = deptLocation.get(performanceCustomerInfo.getOrgNum());
                    String[] lonAndLat = orgLocation.split(",");
                    anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(lonAndLat[0])));
                    anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(lonAndLat[1])));
                } else if (StringUtils.isNotEmpty(deptLocation.get(performanceCustomerInfo.getSeparateBankId()))) {
                    String orgLocation = deptLocation.get(performanceCustomerInfo.getSeparateBankId());
                    String[] lonAndLat = orgLocation.split(",");
                    anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(lonAndLat[0])));
                    anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(lonAndLat[1])));
                }
            }
        }
    }

    /**
     * 确定授信锚点坐标
     *
     * @param deptLocation
     * @param creditCustomerInfo
     * @param anchorPoint
     */
    private void getLonAndLat(Map<String, String> deptLocation, CreditCustomerInfo creditCustomerInfo, AnchorPoint anchorPoint) {
        String address = creditCustomerInfo.getCustomerAddress();
        if (StringUtils.isNotEmpty(address)) {
            JSONObject jsonObject = geocodingUtil.geoCode(address);
            Location location = JSON.parseObject(jsonObject.getString("location"), Location.class);
            if (location != null) {
                double[] locationArr = GPSUtil.bd09_To_gps84(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLng()));
                anchorPoint.setLon(BigDecimal.valueOf(locationArr[1]));
                anchorPoint.setLat(BigDecimal.valueOf(locationArr[0]));
            } else {
                //未解析成功则落入当前所属机构
                if (StringUtils.isNotEmpty(deptLocation.get(creditCustomerInfo.getOrgNum()))) {
                    String orgLocation = deptLocation.get(creditCustomerInfo.getOrgNum());
                    String[] lonAndLat = orgLocation.split(",");
                    anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(lonAndLat[0])));
                    anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(lonAndLat[1])));
                } else if (StringUtils.isNotEmpty(deptLocation.get(creditCustomerInfo.getSeparateBankId()))) {
                    String orgLocation = deptLocation.get(creditCustomerInfo.getSeparateBankId());
                    String[] lonAndLat = orgLocation.split(",");
                    anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(lonAndLat[0])));
                    anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(lonAndLat[1])));
                }
            }
        }
    }

    /**
     * 划定客户所在区域
     *
     * @return
     */
    private List<String> defineArea() {
        List<String> codes = new ArrayList<>();
        codes.add("330000");
        codes.add("320100");
        codes.add("310000");
        codes.add("350100");
        codes.add("500233");
        codes.add("320981");
        codes.add("510112");
        codes.add("530181");
        codes.add("532326");
        codes.add("532322");
        codes.add("440114");
        codes.add("360821");
        codes.add("520000");
        codes.add("500000");
        return codes;
    }

    /**
     * 获取所有父区域的下级区域集合，边界处理
     *
     * @return
     */
    private HashMap<String, List<PaAddrInfoType>> getChild() {
        HashMap<String, List<PaAddrInfoType>> map = new HashMap<>();
        List<String> parents = paAddrInfoTypeMapper.selectParentsByCode();
        for (String parent : parents) {
            List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.selectSelfAndChildByParentCode(parent);
            for (PaAddrInfoType paAddrInfoType : paAddrInfoTypes) {
                double[][][][] coor = JSON.parseObject(paAddrInfoType.getCoordinates(), double[][][][].class);
                paAddrInfoType.setCoor(coor);
            }
            map.put(parent, paAddrInfoTypes);
        }
        return map;

    }

    /**
     * 锚点落格，客户更新归属网格信息
     *
     * @param anchorPoints 待处理锚点
     */
    public void initAnchor(List<AnchorPoint> anchorPoints) {
        List<String> codes = defineArea();
        //本级以及下级区域列表
        HashMap<String, List<PaAddrInfoType>> parentChildAreaMap = getChild();

        for (AnchorPoint anchorPoint : anchorPoints) {
            if (anchorPoint.getLon() != null && anchorPoint.getLat() != null) {

                for (String code : codes) {
                    selectGridByLocation(code, anchorPoint, parentChildAreaMap);
                    if (StringUtils.isNotEmpty(anchorPoint.getBelongGrid())) {
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(anchorPoint.getBelongGrid()) && anchorPoint.getId() != null) {
                    anchorPointMapper.updateAnchorPoint(anchorPoint);
                    //更新锚点信息
                    EsAnchorPointDto esAnchorPoint = new EsAnchorPointDto();
                    BeanUtils.copyBeanProperties(anchorPoint, esAnchorPoint);
                    esAnchorPointService.updateAnchorPoint(esAnchorPoint, "es_anchor_point");
                }
                if (anchorPoint.getId() == null) {
                    anchorPointMapper.insertAnchorPoint(anchorPoint);
                    //新增锚点信息
                    EsAnchorPointDto esAnchorPointDomain = new EsAnchorPointDto();
                    BeanUtils.copyBeanProperties(anchorPoint, esAnchorPointDomain);
                    List<SysUser> sysUsers = iSysUserService.selectUserByManagerNum(anchorPoint.getCreditManagerJobNumber());

                    if (sysUsers != null && sysUsers.size() > 0) {
                        SysUser sysUser = sysUsers.get(0);
                        //管护用户id
                        esAnchorPointDomain.setUserId(sysUser.getUserId());
                        //管护机构ID
                        esAnchorPointDomain.setDeptId(sysUser.getDeptId());
                        esAnchorPointDomain.setAncestorsDeptIds(sysUser.getDept().getAncestors());
                    }

                    esAnchorPointService.insertAnchorPoint("es_anchor_point", esAnchorPointDomain);

                }
                //更新建档客户所属网格
                if (String.valueOf(CustomTypeNewEnum.FILING.getCode()).equals(anchorPoint.getType())) {
                    FilingCustomerInfo filingCustomerInfo = new FilingCustomerInfo();
                    filingCustomerInfo.setId(anchorPoint.getCustomerId());
                    filingCustomerInfo.setBelongGridCode(anchorPoint.getBelongGrid());
                    filingCustomerInfo.setBelongGridName(anchorPoint.getBelongGridName());
                    filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
                    //更新绩效客户所属网格
                } else if (String.valueOf(CustomTypeNewEnum.PERFORMANCE.getCode()).equals(anchorPoint.getType())) {
                    PerformanceCustomerInfo performanceCustomerInfo = new PerformanceCustomerInfo();
                    performanceCustomerInfo.setId(anchorPoint.getCustomerId());
                    performanceCustomerInfo.setBelongGridCode(anchorPoint.getBelongGrid());
                    performanceCustomerInfo.setBelongGridName(anchorPoint.getBelongGridName());
                    performanceCustomerInfo.setGridManager(anchorPoint.getGridManager());
                    performanceCustomerInfoMapper.updatePerformanceCustomerInfo(performanceCustomerInfo);
                    //更新授信客户所属网格
                } else if (String.valueOf(CustomTypeNewEnum.CREDIT.getCode()).equals(anchorPoint.getType())) {
                    CreditCustomerInfo creditCustomerInfo = new CreditCustomerInfo();
                    creditCustomerInfo.setId(anchorPoint.getCustomerId());
                    creditCustomerInfo.setBelongGridCode(anchorPoint.getBelongGrid());
                    creditCustomerInfo.setBelongGridName(anchorPoint.getBelongGridName());
                    creditCustomerInfo.setGridManager(anchorPoint.getGridManager());
                    creditCustomerInfoMapper.updateCreditCustomerInfo(creditCustomerInfo);
                }
            }

        }
    }


    /**
     * 全量锚点落格使用
     *
     * @param anchorPoint
     */
    public void initFullAnchor(AnchorPoint anchorPoint, List<String> codes, HashMap<String, List<PaAddrInfoType>> parentChildAreaMap) {
        if (anchorPoint.getLon() != null && anchorPoint.getLat() != null) {
            for (String code : codes) {
                selectGridByLocation(code, anchorPoint, parentChildAreaMap);
                if (StringUtils.isNotEmpty(anchorPoint.getBelongGrid())) {
                    break;
                }
            }
        }
    }

    /**
     * 递归确定点在哪个网格中
     *initAnchor
     * @param code
     * @param anchorPoint
     * @param bianjieMap
     */
    public void selectGridByLocation(String code, AnchorPoint anchorPoint, HashMap<String, List<PaAddrInfoType>> bianjieMap) {
        List<PaAddrInfoType> addrInfoTypes = bianjieMap.get(code);
        if (CollectionUtils.isEmpty(addrInfoTypes)) {
            return;
        }

        for (PaAddrInfoType addrInfoType : addrInfoTypes) {
            if (StringUtils.isNotEmpty(addrInfoType.getCoordinates())) {
                double[][][][] coordinates = addrInfoType.getCoor();
                if (GridUtil.isPointInMultiPolygon(anchorPoint.getLon().doubleValue(), anchorPoint.getLat().doubleValue(), coordinates)) {
                    anchorPoint.setBelongGrid(addrInfoType.getCode());
                    anchorPoint.setBelongGridName(addrInfoType.getName());
                    anchorPoint.setGridManager(addrInfoType.getUserManagerNum());
                    anchorPoint.setAreaCode(addrInfoType.getCode());
                    anchorPoint.setUpdateTime(new Date());
                    anchorPoint.setAncestorCodes(addrInfoType.getAncestorCodes());
                    selectGridByLocation(addrInfoType.getCode(), anchorPoint, bianjieMap);
                }
            }
        }
    }


    //全量客户加锚点跑批
    public void importFullCustom(Date date) {
        Date timeFlag = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String now = sdf.format(timeFlag);
        log.info("==========" + now + "客户数据开始跑批==========");

        //获取所有部门
        List<SysDept> depts = sysDeptMapper.selectAll();
        //将部门编码和名称存放到map中
        Map<String, String> deptMap = depts.stream().collect(Collectors.toMap(SysDept::getOrgNum, SysDept::getDeptName));
        //将部门id和编码存放到map中
        Map<String, Long> deptIdCodeMap = depts.stream().collect(Collectors.toMap(SysDept::getOrgNum, SysDept::getDeptId));

        //获取所有机构经纬度
        List<DeptLocationInfo> deptLocationInfos = deptLocationInfoMapper.listAll();
        //将部门编码及其坐标存入map中，若有经纬度为空的客户，则将所属机构的经纬度填入
        Map<String, String> deptLocation = deptLocationInfos.stream().collect(Collectors.toMap(DeptLocationInfo::getOrgNum, DeptLocationInfo::getPoint));

        //获取所有职业代码以及职业
        List<JobCodeRelation> jobCodeRelations = jobCodeRelationMapper.listAll();
        Map<String, String> jobCode = jobCodeRelations.stream().collect(Collectors.toMap(JobCodeRelation::getJobCode, JobCodeRelation::getJobName));

        //划定区域
        List<String> codes = defineArea();

        //本级以及下级区域列表
        HashMap<String, List<PaAddrInfoType>> parentChildAreaMap = getChild();

        //设定批次大小
        int size = 500;

        //绩效客户列表
        List<PerformanceCustomerInfo> performanceCustomerInfos = new ArrayList<>();
        //信贷客户列表
        List<CreditCustomerInfo> creditCustomerInfos = new ArrayList<>();
        //绩效锚点列表
        List<AnchorPoint> perAnchorPoints = new ArrayList<>();
        //授信锚点列表
        List<AnchorPoint> creAnchorPoints = new ArrayList<>();
        //新增绩效客户标签关联关系
        List<CustomerLabel> perLabels = new ArrayList<>();
        //新增授信客户标签关联关系
        List<CustomerLabel> creLabels = new ArrayList<>();

        FtpUtil.getFileInputStream(FtpSyncFileEnum.FULL_CUSTOMER_FILE_PATH, date, inputStream -> {
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            CSVParser parser = null;
            try {
                parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withDelimiter('|').withQuote(null));
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (CSVRecord record : parser) {
                //绩效客户
                if ("1".equals(record.get(49))) {
                    PerformanceCustomerInfo performanceCustomerInfo = new PerformanceCustomerInfo();
                    //客户号
                    performanceCustomerInfo.setCustomerNm(record.get(1));
                    //客户名称也用客户号，要换成真实姓名则改为2
                    performanceCustomerInfo.setCustomerName(record.get(2));
                    //电话
                    performanceCustomerInfo.setCustomerPhone(record.get(5));
                    if (StringUtils.isNotEmpty(record.get(45))) {
                        //统一信用代码
                        performanceCustomerInfo.setCustomerIdentity(record.get(45));
                    } else {
                        //身份证
                        performanceCustomerInfo.setCustomerIdentity(record.get(4));
                    }
                    if ("T28010".equals(record.get(13))) {
                        //个人
                        performanceCustomerInfo.setCustomerType(1);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(1);
                        customerLabel.setCustomerNm(performanceCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CustomerTypeLabelEnum.PERSON.getPerId());
                        customerLabel.setLabelType(1);
                        perLabels.add(customerLabel);
                    } else if ("T28050".equals(record.get(13))) {
                        //个体
                        performanceCustomerInfo.setCustomerType(3);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(1);
                        customerLabel.setCustomerNm(performanceCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CustomerTypeLabelEnum.BUSINESS.getPerId());
                        customerLabel.setLabelType(1);
                        perLabels.add(customerLabel);
                    } else {
                        //企业客户
                        performanceCustomerInfo.setCustomerType(2);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(1);
                        customerLabel.setCustomerNm(performanceCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CustomerTypeLabelEnum.COMPANY.getPerId());
                        customerLabel.setLabelType(1);
                        perLabels.add(customerLabel);
                    }

                    //客户等级
                    if (StringUtils.isNotEmpty(record.get(10))) {
                        String level = record.get(10);
                        performanceCustomerInfo.setCustomerLevel(level);
                        //同步系统标签
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(1);
                        customerLabel.setLabelType(1);
                        customerLabel.setCustomerNm(performanceCustomerInfo.getCustomerNm());
                        if (level.equals("0")) {
                            customerLabel.setLabelId(CustomerLevelLabelEnum.ZERO.getId());
                        } else if (level.equals("1")) {
                            customerLabel.setLabelId(CustomerLevelLabelEnum.ONE.getId());
                        } else if (level.equals("2")) {
                            customerLabel.setLabelId(CustomerLevelLabelEnum.TWO.getId());
                        } else if (level.equals("3")) {
                            customerLabel.setLabelId(CustomerLevelLabelEnum.THREE.getId());
                        } else if (level.equals("4")) {
                            customerLabel.setLabelId(CustomerLevelLabelEnum.FOUR.getId());
                        } else if (level.equals("5")) {
                            customerLabel.setLabelId(CustomerLevelLabelEnum.FIVE.getId());
                        }
                        perLabels.add(customerLabel);
                    }
                    //客户地址
                    performanceCustomerInfo.setCustomerAddress(record.get(14));
                    //地址类型
                    String addrType = record.get(15);
                    if (StringUtils.isNotEmpty(addrType)) {
                        performanceCustomerInfo.setAddrType(addrType);
                    }
                    //工作单位
                    performanceCustomerInfo.setWorkUnit(record.get(11));
                    //职业代码
                    if (StringUtils.isNotEmpty(record.get(12))) {
                        performanceCustomerInfo.setJob(jobCode.get(record.get(12)));
                    }
                    if (StringUtils.isNotEmpty(record.get(18))) {
                        performanceCustomerInfo.setDeposit(BigDecimal.valueOf(Double.parseDouble(record.get(18))));
                    }
                    if (StringUtils.isNotEmpty(record.get(19))) {
                        performanceCustomerInfo.setLoan(BigDecimal.valueOf(Double.parseDouble(record.get(19))));
                    }
                    if (StringUtils.isNotEmpty(record.get(20))) {
                        performanceCustomerInfo.setFinance(BigDecimal.valueOf(Double.parseDouble(record.get(20))));
                    }
                    if (StringUtils.isNotEmpty(record.get(21))) {
                        performanceCustomerInfo.setProperty(BigDecimal.valueOf(Double.parseDouble(record.get(21))));
                    }
                    if (StringUtils.isNotEmpty(record.get(40))) {
                        performanceCustomerInfo.setOrgNum(record.get(40));
                        //查询支行名称
                        String branchBank = deptMap.get(record.get(40));
                        performanceCustomerInfo.setBranchBank(branchBank);
                        //设置部门id
                        performanceCustomerInfo.setDeptId(deptIdCodeMap.get(record.get(40)));
                    }
                    if (StringUtils.isNotEmpty(record.get(41))) {
                        performanceCustomerInfo.setSeparateBankId(record.get(41));
                        //查询分行名称
                        String separateBank = deptMap.get(record.get(41));
                        performanceCustomerInfo.setSeparateBank(separateBank);
                    }
                    performanceCustomerInfo.setCreditManager(record.get(38));
                    performanceCustomerInfo.setCreditManagerJobNumber(record.get(39));
                    performanceCustomerInfo.setGender(record.get(6));
                    String birthday = record.get(8);
                    if (StringUtils.isNotEmpty(birthday)) {
                        try {
                            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            birthday = sdf.format(dateTimeFormat.parse(birthday));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    performanceCustomerInfo.setBirthday(birthday);
                    if (StringUtils.isNotEmpty(record.get(7))) {
                        performanceCustomerInfo.setAge(Integer.parseInt(record.get(7)));
                    }
                    performanceCustomerInfo.setMaritalStatus(record.get(9));
                    performanceCustomerInfo.setLegalPersonIdentityNum(record.get(44));
                    performanceCustomerInfo.setLegalPersonName(record.get(42));
                    performanceCustomerInfo.setLegalPersonPhone(record.get(43));
                    performanceCustomerInfo.setBusinessAddress(record.get(46));
                    performanceCustomerInfo.setNewLegalPerson(record.get(52));
                    performanceCustomerInfo.setCreateTime(new Date());

//                    AnchorPoint anchorPoint = new AnchorPoint();
//                    anchorPoint.setCustomerNm(performanceCustomerInfo.getCustomerNm());
//                    anchorPoint.setIdentity(performanceCustomerInfo.getCustomerIdentity());
//                    anchorPoint.setType(String.valueOf(CustomTypeNewEnum.PERFORMANCE.getCode()));
//                    anchorPoint.setAnchorName(performanceCustomerInfo.getCustomerName());
//                    anchorPoint.setAnchorTypeCode(performanceCustomerInfo.getCustomerType());
//                    anchorPoint.setAnchorType(AnchorTypeEnum.getNameByCode(performanceCustomerInfo.getCustomerType()));
//                    anchorPoint.setAddressDesc(performanceCustomerInfo.getCustomerAddress());
//                    anchorPoint.setUpdateTime(new Date());
//                    //经度
//                    String lon = record.get(53);
//                    //纬度
//                    String lat = record.get(54);
//                    //有经纬度直接入库
//                    if (StringUtils.isNotEmpty(lon) && StringUtils.isNotEmpty(lat)) {
//                        //坐标系转换 百度转84
//                        double[] location = GPSUtil.bd09_To_gps84(Double.valueOf(lat), Double.valueOf(lon));
//                        anchorPoint.setLon(BigDecimal.valueOf(location[1]));
//                        anchorPoint.setLat(BigDecimal.valueOf(location[0]));
//                        //没有经纬度填入机构经纬度
//                    } else {
//                        if (StringUtils.isNotEmpty(deptLocation.get(performanceCustomerInfo.getOrgNum()))) {
//                            String location = deptLocation.get(performanceCustomerInfo.getOrgNum());
//                            String[] lonAndLat = location.split(",");
//                            anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(lonAndLat[0])));
//                            anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(lonAndLat[1])));
//                            //部门也没有坐标挂到支行下
//                        }else if(StringUtils.isNotEmpty(deptLocation.get(performanceCustomerInfo.getSeparateBankId()))){
//                            String location = deptLocation.get(performanceCustomerInfo.getOrgNum());
//                            String[] lonAndLat = location.split(",");
//                            anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(lonAndLat[0])));
//                            anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(lonAndLat[1])));
//                        }
//                    }
//                    if (StringUtils.isNotEmpty(performanceCustomerInfo.getAddrType())) {
//                        anchorPoint.setAddressType(Integer.valueOf(performanceCustomerInfo.getAddrType()));
//                    }
//                    anchorPoint.setBelongGridType(32);
//                    anchorPoint.setBelongOrgCode(performanceCustomerInfo.getOrgNum());
//                    anchorPoint.setBelongOrgName(performanceCustomerInfo.getBranchBank());
//                    anchorPoint.setCustomerPhone(performanceCustomerInfo.getCustomerPhone());
//                    anchorPoint.setCreateTime(new Date());
//                    anchorPoint.setBelongManagerCode(performanceCustomerInfo.getCreditManagerJobNumber());
//                    anchorPoint.setBelongManagerName(performanceCustomerInfo.getCreditManager());
//
//                    //绩效锚点落格
//                    initFullAnchor(anchorPoint, codes, parentChildAreaMap);
//
//                    performanceCustomerInfo.setBelongGridCode(anchorPoint.getBelongGrid());
//                    performanceCustomerInfo.setBelongGridName(anchorPoint.getBelongGridName());
//                    performanceCustomerInfo.setGridManager(anchorPoint.getGridManager());
//
//                    perAnchorPoints.add(anchorPoint);
                    performanceCustomerInfos.add(performanceCustomerInfo);

                    if (performanceCustomerInfos.size() == size) {
                        //插入绩效客户
                        performanceCustomerInfoMapper.insertBatch(performanceCustomerInfos);
                        performanceCustomerInfos.clear();
//                        //插入绩效锚点
//                        anchorPointMapper.insertBatch(perAnchorPoints);
//                        for (int i = 0; i < perAnchorPoints.size(); i++) {
//                            perAnchorPoints.remove(i);
//                            i--;
//                        }
                        //插入绩效标签
                        customerLabelMapper.insertBatch(perLabels);
                        perLabels.clear();
                    }
                }
                if ("1".equals(record.get(48))) {
                    CreditCustomerInfo creditCustomerInfo = new CreditCustomerInfo();
                    creditCustomerInfo.setCustomerNm(record.get(1));
                    creditCustomerInfo.setCustomerName(record.get(2));
                    creditCustomerInfo.setCustomerPhone(record.get(5));

                    if ("T28010".equals(record.get(13))) {
                        //个人
                        creditCustomerInfo.setCustomerType("1");
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CustomerTypeLabelEnum.PERSON.getCreId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    } else if ("T28050".equals(record.get(13))) {
                        //个体
                        creditCustomerInfo.setCustomerType("3");
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CustomerTypeLabelEnum.BUSINESS.getCreId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    } else {
                        //企业客户
                        creditCustomerInfo.setCustomerType("2");
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CustomerTypeLabelEnum.COMPANY.getCreId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }

                    creditCustomerInfo.setCustomerAddress(record.get(14));
                    String addrType = record.get(15);
                    if (StringUtils.isNotEmpty(addrType)) {
                        creditCustomerInfo.setAddrType(addrType);
                    }
                    creditCustomerInfo.setAddrType(record.get(15));
                    if (StringUtils.isNotEmpty(record.get(45))) {
                        creditCustomerInfo.setCustomerIdentity(record.get(45));
                    } else {
                        creditCustomerInfo.setCustomerIdentity(record.get(4));
                    }
                    creditCustomerInfo.setCustomerLevel(record.get(10));
                    if (StringUtils.isNotEmpty(record.get(22))) {
                        creditCustomerInfo.setCreditAmount(BigDecimal.valueOf(Double.parseDouble(record.get(22))));
                    }
                    if (StringUtils.isNotEmpty(record.get(19))) {
                        creditCustomerInfo.setUsingCreditAmount(BigDecimal.valueOf(Double.parseDouble(record.get(19))));
                    }

                    List<Integer> level = new ArrayList<>();
                    if ("是".equals(record.get(32))) {
                        level.add(0);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.NORMAL.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("是".equals(record.get(33))) {
                        level.add(1);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.ATTENTION.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("是".equals(record.get(34))) {
                        level.add(2);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.SECONDARY.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("是".equals(record.get(35))) {
                        level.add(3);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.DOUBT.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("是".equals(record.get(36))) {
                        level.add(4);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.LOST.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    creditCustomerInfo.setFiveLevel(String.join(",", level.toString()));
                    creditCustomerInfo.setCreditManagerJobNumber(record.get(23));
                    creditCustomerInfo.setCreditManager(record.get(24));
                    if (StringUtils.isNotEmpty(record.get(25))) {
                        creditCustomerInfo.setOrgNum(record.get(25));
                        //查询支行名称
                        String branchBank = deptMap.get(record.get(25));
                        creditCustomerInfo.setBranchBank(branchBank);
                        creditCustomerInfo.setDeptId(deptIdCodeMap.get(record.get(25)));
                    }

                    if (StringUtils.isNotEmpty(record.get(26))) {
                        creditCustomerInfo.setSeparateBankId(record.get(26));
                        //查询分行名称
                        String separateBank = deptMap.get(record.get(26));
                        creditCustomerInfo.setSeparateBank(separateBank);
                    }

                    List<Integer> guaranteeMethod = new ArrayList<>();
                    if ("是".equals(record.get(27))) {
                        guaranteeMethod.add(0);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.DY.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("是".equals(record.get(28))) {
                        guaranteeMethod.add(1);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.ZY.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("是".equals(record.get(29))) {
                        guaranteeMethod.add(2);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.BZ.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("是".equals(record.get(30))) {
                        guaranteeMethod.add(3);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.XY.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    creditCustomerInfo.setGuaranteeMethod(String.join(",", guaranteeMethod.toString()));

                    if ("消费".equals(record.get(31))) {
                        creditCustomerInfo.setUsingType(1);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.XF.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("经营".equals(record.get(31))) {
                        creditCustomerInfo.setUsingType(0);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.JY.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("是".equals(record.get(37))) {
                        creditCustomerInfo.setIsInArrears(1);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.YQ.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    if ("否".equals(record.get(37))) {
                        creditCustomerInfo.setIsInArrears(0);
                        CustomerLabel customerLabel = new CustomerLabel();
                        customerLabel.setType(2);
                        customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
                        customerLabel.setLabelId(CreditCustomerLabelEnum.NOT_YQ.getId());
                        customerLabel.setLabelType(1);
                        creLabels.add(customerLabel);
                    }
                    creditCustomerInfo.setGender(record.get(6));
                    String birthday = record.get(8);
                    if (StringUtils.isNotEmpty(birthday)) {
                        try {
                            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            birthday = sdf.format(dateTimeFormat.parse(birthday));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    creditCustomerInfo.setBirthday(birthday);
                    if (StringUtils.isNotEmpty(record.get(7))) {
                        creditCustomerInfo.setAge(Integer.parseInt(record.get(7)));
                    }
                    creditCustomerInfo.setMaritalStatus(record.get(9));
                    creditCustomerInfo.setWorkUnit(record.get(11));
                    //职业代码
                    if (StringUtils.isNotEmpty(record.get(12))) {
                        creditCustomerInfo.setJob(jobCode.get(record.get(12)));
                    }
                    creditCustomerInfo.setLegalPersonIdentityNum(record.get(44));
                    creditCustomerInfo.setLegalPersonName(record.get(42));
                    creditCustomerInfo.setLegalPersonPhone(record.get(43));
                    creditCustomerInfo.setBusinessAddress(record.get(46));
                    creditCustomerInfo.setNewLegalPerson(record.get(52));
                    creditCustomerInfo.setCreateTime(new Date());
                    //是否乡贷默认无
                    creditCustomerInfo.setIsProduct(0L);

//                    AnchorPoint anchorPoint = new AnchorPoint();
//                    anchorPoint.setCustomerNm(creditCustomerInfo.getCustomerNm());
//                    anchorPoint.setIdentity(creditCustomerInfo.getCustomerIdentity());
//                    anchorPoint.setType(String.valueOf(CustomTypeNewEnum.CREDIT.getCode()));
//                    anchorPoint.setAnchorName(creditCustomerInfo.getCustomerName());
//                    anchorPoint.setAnchorTypeCode(Integer.valueOf(creditCustomerInfo.getCustomerType()));
//                    anchorPoint.setAnchorType(AnchorTypeEnum.getNameByCode(Integer.valueOf(creditCustomerInfo.getCustomerType())));
//                    anchorPoint.setAddressDesc(creditCustomerInfo.getCustomerAddress());
//                    anchorPoint.setUpdateTime(new Date());
//                    //经度
//                    String lon = record.get(53);
//                    //纬度
//                    String lat = record.get(54);
//                    //有经纬度直接入库
//                    if (StringUtils.isNotEmpty(lon) && StringUtils.isNotEmpty(lat)) {
//                        //坐标系转换 百度转84
//                        double[] location = GPSUtil.bd09_To_gps84(Double.valueOf(lat), Double.valueOf(lon));
//                        anchorPoint.setLon(BigDecimal.valueOf(location[1]));
//                        anchorPoint.setLat(BigDecimal.valueOf(location[0]));
//                        //没有经纬度填入机构经纬度
//                    } else {
//                        if (StringUtils.isNotEmpty(deptLocation.get(creditCustomerInfo.getOrgNum()))) {
//                            String location = deptLocation.get(creditCustomerInfo.getOrgNum());
//                            String[] lonAndLat = location.split(",");
//                            anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(lonAndLat[0])));
//                            anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(lonAndLat[1])));
//                        }else if(StringUtils.isNotEmpty(deptLocation.get(creditCustomerInfo.getSeparateBankId()))){
//                            String location = deptLocation.get(creditCustomerInfo.getOrgNum());
//                            String[] lonAndLat = location.split(",");
//                            anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(lonAndLat[0])));
//                            anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(lonAndLat[1])));
//                        }
//                    }
//                    if (StringUtils.isNotEmpty(creditCustomerInfo.getAddrType())) {
//                        anchorPoint.setAddressType(Integer.valueOf(creditCustomerInfo.getAddrType()));
//                    }
//                    anchorPoint.setBelongGridType(32);
//                    anchorPoint.setBelongOrgCode(creditCustomerInfo.getOrgNum());
//                    anchorPoint.setBelongOrgName(creditCustomerInfo.getBranchBank());
//                    anchorPoint.setCustomerPhone(creditCustomerInfo.getCustomerPhone());
//                    anchorPoint.setCreateTime(new Date());
//                    anchorPoint.setBelongManagerCode(creditCustomerInfo.getCreditManagerJobNumber());
//                    anchorPoint.setBelongManagerName(creditCustomerInfo.getCreditManager());

                    //执行落格
//                    initFullAnchor(anchorPoint, codes, parentChildAreaMap);

//                    creditCustomerInfo.setBelongGridCode(anchorPoint.getBelongGrid());
//                    creditCustomerInfo.setBelongGridName(anchorPoint.getBelongGridName());
//                    creditCustomerInfo.setGridManager(anchorPoint.getGridManager());

                    creditCustomerInfos.add(creditCustomerInfo);
//                    creAnchorPoints.add(anchorPoint);

                    if (creditCustomerInfos.size() == size) {
                        creditCustomerInfoMapper.insertBatch(creditCustomerInfos);
                        creditCustomerInfos.clear();
//                        anchorPointMapper.insertBatch(creAnchorPoints);
//                        for (int i = 0; i < creAnchorPoints.size(); i++) {
//                            creAnchorPoints.remove(i);
//                            i--;
//                        }
                        //插入绩效标签
                        customerLabelMapper.insertBatch(creLabels);
                        creLabels.clear();
                    }
                }
            }
            performanceCustomerInfoMapper.insertBatch(performanceCustomerInfos);
//            anchorPointMapper.insertBatch(perAnchorPoints);
            creditCustomerInfoMapper.insertBatch(creditCustomerInfos);
//            anchorPointMapper.insertBatch(creAnchorPoints);
            customerLabelMapper.insertBatch(creLabels);
            customerLabelMapper.insertBatch(perLabels);

            //更新绩效锚点客户id字段
            anchorPointMapper.updateFullPerCustomerId();
            //更新授信锚点客户id字段
            anchorPointMapper.updateFullCreCustomerId();
            //更新授信客户是否乡贷字段
            creditCustomerInfoMapper.updateIsProduct();
            //更新绩效客户标签关联表客户id
            customerLabelMapper.updatePerCustomerIdByNmAndType();
            //更新授信客户标签关联表客户id
            customerLabelMapper.updateCreCustomerIdByNmAndType();
            //更新绩效客户所属网格信息
            performanceCustomerInfoMapper.updatePerBelongGrid();
            //更新授信客户所属网格信息
            creditCustomerInfoMapper.updateCreBelongGrid();
            //更新开卡表身份证
            openingCardRecordMapper.updateIdentity();
            //更新授信表身份证
            creditRecordMapper.updateIdentity();
        });
        log.info("==========" + now + "客户数据跑批结束==========");
    }

    @NotNull
    private AnchorPoint createCreAnchor(CreditCustomerInfo creditCustomerInfo) {
        AnchorPoint anchorPoint = new AnchorPoint();
        anchorPoint.setCustomerNm(creditCustomerInfo.getCustomerNm());
        anchorPoint.setIdentity(creditCustomerInfo.getCustomerIdentity());
        anchorPoint.setType(String.valueOf(CustomTypeNewEnum.CREDIT.getCode()));
        anchorPoint.setAnchorName(creditCustomerInfo.getCustomerName());
        anchorPoint.setAnchorTypeCode(Integer.valueOf(creditCustomerInfo.getCustomerType()));
        anchorPoint.setAnchorType(AnchorTypeEnum.getNameByCode(Integer.valueOf(creditCustomerInfo.getCustomerType())));
        anchorPoint.setAddressDesc(creditCustomerInfo.getCustomerAddress());
        anchorPoint.setUpdateTime(new Date());
        if (StringUtils.isNotEmpty(creditCustomerInfo.getAddrType())) {
            anchorPoint.setAddressType(Integer.valueOf(creditCustomerInfo.getAddrType()));
        }
        anchorPoint.setBelongGridType(32);
        anchorPoint.setBelongOrgCode(creditCustomerInfo.getOrgNum());
        anchorPoint.setBelongOrgName(creditCustomerInfo.getBranchBank());
        anchorPoint.setCustomerPhone(creditCustomerInfo.getCustomerPhone());
        anchorPoint.setCreateTime(new Date());
        anchorPoint.setBelongManagerCode(creditCustomerInfo.getCreditManagerJobNumber());
        anchorPoint.setBelongManagerName(creditCustomerInfo.getCreditManager());
        return anchorPoint;
    }

    @NotNull
    public CreditCustomerInfo createCredit(SimpleDateFormat sdf, Map<String, String> deptMap, Map<String, Long> deptIdCodeMap, Map<String, String> jobCode, CSVRecord record, List<CustomerLabel> customerLabels) {
        CreditCustomerInfo creditCustomerInfo = new CreditCustomerInfo();
        creditCustomerInfo.setCustomerNm(record.get(1));
        creditCustomerInfo.setCustomerName(record.get(2));
        creditCustomerInfo.setCustomerPhone(record.get(5));

        if ("T28010".equals(record.get(13))) {
            //个人
            creditCustomerInfo.setCustomerType("1");
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CustomerTypeLabelEnum.PERSON.getCreId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        } else if ("T28050".equals(record.get(13))) {
            //个体
            creditCustomerInfo.setCustomerType("3");
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CustomerTypeLabelEnum.BUSINESS.getCreId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        } else {
            //企业客户
            creditCustomerInfo.setCustomerType("2");
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CustomerTypeLabelEnum.COMPANY.getCreId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }

        creditCustomerInfo.setCustomerAddress(record.get(14));
        String addrType = record.get(15);
        if (StringUtils.isNotEmpty(addrType)) {
            creditCustomerInfo.setAddrType(addrType);
        }
        creditCustomerInfo.setAddrType(record.get(15));
        if (StringUtils.isNotEmpty(record.get(45))) {
            creditCustomerInfo.setCustomerIdentity(record.get(45));
        } else {
            creditCustomerInfo.setCustomerIdentity(record.get(4));
        }
        creditCustomerInfo.setCustomerLevel(record.get(10));
        if (StringUtils.isNotEmpty(record.get(22))) {
            creditCustomerInfo.setCreditAmount(BigDecimal.valueOf(Double.parseDouble(record.get(22))));
        }
        if (StringUtils.isNotEmpty(record.get(19))) {
            creditCustomerInfo.setUsingCreditAmount(BigDecimal.valueOf(Double.parseDouble(record.get(19))));
        }

        List<Integer> level = new ArrayList<>();
        if ("是".equals(record.get(32))) {
            level.add(0);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.NORMAL.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("是".equals(record.get(33))) {
            level.add(1);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.ATTENTION.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("是".equals(record.get(34))) {
            level.add(2);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.SECONDARY.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("是".equals(record.get(35))) {
            level.add(3);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.DOUBT.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("是".equals(record.get(36))) {
            level.add(4);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.LOST.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        creditCustomerInfo.setFiveLevel(String.join(",", level.toString()));
        creditCustomerInfo.setCreditManagerJobNumber(record.get(23));
        creditCustomerInfo.setCreditManager(record.get(24));
        if (StringUtils.isNotEmpty(record.get(25))) {
            creditCustomerInfo.setOrgNum(record.get(25));
            //查询支行名称
            String branchBank = deptMap.get(record.get(25));
            creditCustomerInfo.setBranchBank(branchBank);
            creditCustomerInfo.setDeptId(deptIdCodeMap.get(record.get(25)));
        }

        if (StringUtils.isNotEmpty(record.get(26))) {
            creditCustomerInfo.setSeparateBankId(record.get(26));
            //查询分行名称
            String separateBank = deptMap.get(record.get(26));
            creditCustomerInfo.setSeparateBank(separateBank);
        }

        List<Integer> guaranteeMethod = new ArrayList<>();
        if ("是".equals(record.get(27))) {
            guaranteeMethod.add(0);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.DY.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("是".equals(record.get(28))) {
            guaranteeMethod.add(1);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.ZY.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("是".equals(record.get(29))) {
            guaranteeMethod.add(2);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.BZ.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("是".equals(record.get(30))) {
            guaranteeMethod.add(3);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.XY.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        creditCustomerInfo.setGuaranteeMethod(String.join(",", guaranteeMethod.toString()));

        if ("消费".equals(record.get(31))) {
            creditCustomerInfo.setUsingType(1);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.XF.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("经营".equals(record.get(31))) {
            creditCustomerInfo.setUsingType(0);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.JY.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        if ("是".equals(record.get(37))) {
            creditCustomerInfo.setIsInArrears(1);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.YQ.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        } else {
            creditCustomerInfo.setIsInArrears(0);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(2);
            customerLabel.setCustomerNm(creditCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CreditCustomerLabelEnum.NOT_YQ.getId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        creditCustomerInfo.setGender(record.get(6));
        String birthday = record.get(8);
        if (StringUtils.isNotEmpty(birthday)) {
            try {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                birthday = sdf.format(dateTimeFormat.parse(birthday));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        creditCustomerInfo.setBirthday(birthday);
        if (StringUtils.isNotEmpty(record.get(7))) {
            creditCustomerInfo.setAge(Integer.parseInt(record.get(7)));
        }
        creditCustomerInfo.setMaritalStatus(record.get(9));
        creditCustomerInfo.setWorkUnit(record.get(11));
        //职业代码
        if (StringUtils.isNotEmpty(record.get(12))) {
            creditCustomerInfo.setJob(jobCode.get(record.get(12)));
        }
        creditCustomerInfo.setLegalPersonIdentityNum(record.get(44));
        creditCustomerInfo.setLegalPersonName(record.get(42));
        creditCustomerInfo.setLegalPersonPhone(record.get(43));
        creditCustomerInfo.setBusinessAddress(record.get(46));
        creditCustomerInfo.setNewLegalPerson(record.get(52));
        creditCustomerInfo.setCreateTime(new Date());
        //是否乡贷默认无
        creditCustomerInfo.setIsProduct(0L);
        return creditCustomerInfo;
    }

    @NotNull
    private AnchorPoint createPerAnchor(PerformanceCustomerInfo performanceCustomerInfo) {
        AnchorPoint anchorPoint = new AnchorPoint();
        anchorPoint.setCustomerNm(performanceCustomerInfo.getCustomerNm());
        anchorPoint.setIdentity(performanceCustomerInfo.getCustomerIdentity());
        anchorPoint.setType(String.valueOf(CustomTypeNewEnum.PERFORMANCE.getCode()));
        anchorPoint.setAnchorName(performanceCustomerInfo.getCustomerName());
        anchorPoint.setAnchorTypeCode(performanceCustomerInfo.getCustomerType());
        anchorPoint.setAnchorType(AnchorTypeEnum.getNameByCode(performanceCustomerInfo.getCustomerType()));
        anchorPoint.setAddressDesc(performanceCustomerInfo.getCustomerAddress());
        anchorPoint.setUpdateTime(new Date());
        if (StringUtils.isNotEmpty(performanceCustomerInfo.getAddrType())) {
            anchorPoint.setAddressType(Integer.valueOf(performanceCustomerInfo.getAddrType()));
        }
        anchorPoint.setBelongGridType(32);
        anchorPoint.setBelongOrgCode(performanceCustomerInfo.getOrgNum());
        anchorPoint.setBelongOrgName(performanceCustomerInfo.getBranchBank());
        anchorPoint.setCustomerPhone(performanceCustomerInfo.getCustomerPhone());
        anchorPoint.setCreateTime(new Date());
        anchorPoint.setBelongManagerCode(performanceCustomerInfo.getCreditManagerJobNumber());
        anchorPoint.setBelongManagerName(performanceCustomerInfo.getCreditManager());
        return anchorPoint;
    }

    @NotNull
    public PerformanceCustomerInfo createPerformance(SimpleDateFormat sdf, Map<String, String> deptMap, Map<String, Long> deptIdCodeMap, Map<String, String> jobCode, CSVRecord record, List<CustomerLabel> customerLabels) {
        PerformanceCustomerInfo performanceCustomerInfo = new PerformanceCustomerInfo();
        //客户号
        performanceCustomerInfo.setCustomerNm(record.get(1));
        //客户名称也用客户号，要换成真实姓名则改为2
        performanceCustomerInfo.setCustomerName(record.get(2));
        //电话
        performanceCustomerInfo.setCustomerPhone(record.get(5));
        if (StringUtils.isNotEmpty(record.get(45))) {
            //统一信用代码
            performanceCustomerInfo.setCustomerIdentity(record.get(45));
        } else {
            //身份证
            performanceCustomerInfo.setCustomerIdentity(record.get(4));
        }
        if ("T28010".equals(record.get(13))) {
            //个人
            performanceCustomerInfo.setCustomerType(1);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(1);
            customerLabel.setCustomerNm(performanceCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CustomerTypeLabelEnum.PERSON.getPerId());
            //系统标签
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        } else if ("T28050".equals(record.get(13))) {
            //个体
            performanceCustomerInfo.setCustomerType(3);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(1);
            customerLabel.setCustomerNm(performanceCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CustomerTypeLabelEnum.BUSINESS.getPerId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        } else {
            //企业客户
            performanceCustomerInfo.setCustomerType(2);
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(1);
            customerLabel.setCustomerNm(performanceCustomerInfo.getCustomerNm());
            customerLabel.setLabelId(CustomerTypeLabelEnum.COMPANY.getPerId());
            customerLabel.setLabelType(1);
            customerLabels.add(customerLabel);
        }
        //客户等级
        if (StringUtils.isNotEmpty(record.get(10))) {
            String level = record.get(10);
            performanceCustomerInfo.setCustomerLevel(level);
            //同步系统标签
            CustomerLabel customerLabel = new CustomerLabel();
            customerLabel.setType(1);
            customerLabel.setLabelType(1);
            customerLabel.setCustomerNm(performanceCustomerInfo.getCustomerNm());
            if (level.equals("0")) {
                customerLabel.setLabelId(CustomerLevelLabelEnum.ZERO.getId());
            } else if (level.equals("1")) {
                customerLabel.setLabelId(CustomerLevelLabelEnum.ONE.getId());
            } else if (level.equals("2")) {
                customerLabel.setLabelId(CustomerLevelLabelEnum.TWO.getId());
            } else if (level.equals("3")) {
                customerLabel.setLabelId(CustomerLevelLabelEnum.THREE.getId());
            } else if (level.equals("4")) {
                customerLabel.setLabelId(CustomerLevelLabelEnum.FOUR.getId());
            } else if (level.equals("5")) {
                customerLabel.setLabelId(CustomerLevelLabelEnum.FIVE.getId());
            }
            customerLabels.add(customerLabel);
        }

        //客户地址
        performanceCustomerInfo.setCustomerAddress(record.get(14));
        //地址类型
        String addrType = record.get(15);
        if (StringUtils.isNotEmpty(addrType)) {
            performanceCustomerInfo.setAddrType(addrType);
        }
        //工作单位
        performanceCustomerInfo.setWorkUnit(record.get(11));
        //职业代码
        if (StringUtils.isNotEmpty(record.get(12))) {
            performanceCustomerInfo.setJob(jobCode.get(record.get(12)));
        }
        if (StringUtils.isNotEmpty(record.get(18))) {
            performanceCustomerInfo.setDeposit(BigDecimal.valueOf(Double.parseDouble(record.get(18))));
        }
        if (StringUtils.isNotEmpty(record.get(19))) {
            performanceCustomerInfo.setLoan(BigDecimal.valueOf(Double.parseDouble(record.get(19))));
        }
        if (StringUtils.isNotEmpty(record.get(20))) {
            performanceCustomerInfo.setFinance(BigDecimal.valueOf(Double.parseDouble(record.get(20))));
        }
        if (StringUtils.isNotEmpty(record.get(21))) {
            performanceCustomerInfo.setProperty(BigDecimal.valueOf(Double.parseDouble(record.get(21))));
        }
        if (StringUtils.isNotEmpty(record.get(40))) {
            performanceCustomerInfo.setOrgNum(record.get(40));
            //查询支行名称
            String branchBank = deptMap.get(record.get(40));
            performanceCustomerInfo.setBranchBank(branchBank);
            //设置部门id
            performanceCustomerInfo.setDeptId(deptIdCodeMap.get(record.get(40)));
        }
        if (StringUtils.isNotEmpty(record.get(41))) {
            performanceCustomerInfo.setSeparateBankId(record.get(41));
            //查询分行名称
            String separateBank = deptMap.get(record.get(41));
            performanceCustomerInfo.setSeparateBank(separateBank);
        }
        performanceCustomerInfo.setCreditManager(record.get(38));
        performanceCustomerInfo.setCreditManagerJobNumber(record.get(39));
        performanceCustomerInfo.setGender(record.get(6));
        String birthday = record.get(8);
        if (StringUtils.isNotEmpty(birthday)) {
            try {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                birthday = sdf.format(dateTimeFormat.parse(birthday));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        performanceCustomerInfo.setBirthday(birthday);
        if (StringUtils.isNotEmpty(record.get(7))) {
            performanceCustomerInfo.setAge(Integer.parseInt(record.get(7)));
        }
        performanceCustomerInfo.setMaritalStatus(record.get(9));
        performanceCustomerInfo.setLegalPersonIdentityNum(record.get(44));
        performanceCustomerInfo.setLegalPersonName(record.get(42));
        performanceCustomerInfo.setLegalPersonPhone(record.get(43));
        performanceCustomerInfo.setBusinessAddress(record.get(46));
        performanceCustomerInfo.setNewLegalPerson(record.get(52));
        performanceCustomerInfo.setCreateTime(new Date());
        return performanceCustomerInfo;
    }



}
