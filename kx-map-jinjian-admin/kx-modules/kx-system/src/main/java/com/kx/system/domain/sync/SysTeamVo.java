package com.kx.system.domain.sync;

import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;

import java.util.Date;

@Data
public class SysTeamVo {
    //营销团队编号
    @CsvBindByPosition(position = 0)
    private Long mktTeamId;
    //营销团队名称
    @CsvBindByPosition(position = 1)
    private String mktTeamName;
    //所属机构
    @CsvBindByPosition(position = 2)
    private String orgId;
    //营销团队负责人
    @CsvBindByPosition(position = 3)
    private String teamLeader;
    //创建日期
    @CsvCustomBindByPosition(position = 4, converter = CsvJavaUtilDateConvert.class)
    private Date createDate;
    //负责人手机号
    @CsvBindByPosition(position = 5)
    private String leadTelephone;
    //营销团队状态
    @CsvBindByPosition(position = 6)
    private String teamStatus;
    //创建人ID
    @CsvBindByPosition(position = 7)
    private String createUserId;
    //创建人名称
    @CsvBindByPosition(position = 8)
    private String createUserName;
    //创建人所属机构
    @CsvBindByPosition(position = 9)
    private String createUserOrgId;
    //营销团队负责人ID
    @CsvBindByPosition(position = 10)
    private String teamLeaderId;
    //三级机构
    @CsvBindByPosition(position = 11)
    private String threeOrgId;
    //所属机构名称
    @CsvBindByPosition(position = 12)
    private String orgName;
    //一级部门
    @CsvBindByPosition(position = 13)
    private String deptfirst;
    //二级部门
    @CsvBindByPosition(position = 14)
    private String deptsecond;
    //最近一次修改日期
    @CsvBindByPosition(position = 15)
    private String lastModifyDate;
    //最近一次修改人
    @CsvBindByPosition(position = 16)
    private String lastModify_user;
    //是否考核
    @CsvBindByPosition(position = 17)
    private String ifCheck;
}