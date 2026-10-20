package com.kx.system.config.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 通用常量信息
 *
 * @author xiatian
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String SYSTEM_USER = "1";

    /**
     * UTF-8 字符集
     */
    public static final String WXAPP_USER = "2";
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";
    /**
     * 小程序登录前缀
     */
    public static final String WX_USER_KEY = "wx_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";
    /**任务点位 cache key*/
    public static final String TASK_POSITION_KEY = "task_position:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    public static final String win_default_upload_path ="D:/file/bg-dataserver-group/resource";

    public static final String linux_default_upload_path ="/file/bg-dataserver-group/resource";

    /**
     * 文件类型 图片
     */
    public static final String PICTURE="picture";

    public static final int successCode = 200;

    /**
     * http远程调用状态码
     */
    public static final String HTTP_CODE = "code";

    /**
     * 酷雷曼状态码
     */
    public static final String KLM_CODE = "error";
    public static final int KLM_SUCCESS_CODE = 0;
    public static final String KLM_DATA = "data";

    /**
     * 调动开关日志Title
     */
    public static final String TRANSFER_SWITCH = "调动开关";

    /**
     * 调动开关字典类型
     */
    public static final String TRANSFER_SWITCH_DICT_TYPE = "transfer_permission";

    public static final String CLIENT_MANAGER = "客户经理";
    public static final String BRANCH_BANK_MANAGER = "支行行长";

    /**
     * 外部excel导入类型
     */
    public static final String EXCELIMPORTTYPE = "外部导入";

    /**
     * 任务新建类型
     */
    public static final String taskAddType = "任务新建";

    /**
     * 客户补录类型
     */
    public static final String customerRepair = "客户补录";

    /**
     * 关注产品/指标类型名称
     */
    public static final String ProductQuotaTypeName = "productQuota";

    /**
     * 日均基准统计维度
     */
    public static final String AvgDailyTotalType = "1";

    /**
     * 时点基准统计维度
     */
    public static final String timePointTotalType = "2";

    /**
     * 考核完成类型：比例
     */
    public static final String finishRadio = "1";

    /**
     * 考核完成类型：相对值
     */
    public static final String finishRelative = "2";

    /**
     * 考核完成类型：绝对值
     */
    public static final String finishAbsolute = "3";

    /**
     * 贷款增量
     */
    public static final String daikuanType = "1";

    /**
     * 存款增量
     */
    public static final String cunkuanType = "2";

    /**
     * 待走访状态
     */
    public static final String WaitInterviewStatus = "0";

    /**
     * 已走访状态
     */
    public static final String interviewEdStatus = "1";

    /**
     * 任务已结束状态
     */
    public static final String taskEndStatus = "2";
    /**
     * 酷雷曼打开指定热点地址url前缀
     */
    public static final String KLM_URL = "http://192.168.1.80/";

    public static final String KLM_EDIT_URL = "index.php?s=/member/edit/index/";

    /**
     * 任务进行中状态
     */
    public static final String taskingStatus = "1";

    /**
     * 任务已结束状态
     */
    public static final String CustomerManager = "KHJL";

    /**
     * 已预约状态
     */
    public static final String subscribed = "1";

    /**
     * 不预约状态
     */
    public static final String notSubscribe = "2";

    /**
     * 两天的毫秒值
     */
    public static final long twoDaysTime = 2*24*60*60*1000;

    /**
     * 一天的毫秒值
     */
    public static final long oneDayTime = 24*60*60*1000;

    /**
     * 总行
     */
    public static final String HeadOffice = "总行";

    /**
     * 支行
     */
    public static final String subBranch = "支行";

    /**
     * 泰顺农商行非营业部编码前缀
     */
    public static final String TS_HEADER_PREFIX = "8630";

    public static final List<String> sql_regs = Arrays.asList("\\\n", "\\,", "\\;", "\\(", "\\)");

    public static final String[] keyWords = {"alter", "create", "delete", "drop", "insert", "update", "truncate"};

    /**
     * 分配规则config_key
     */
    public static final String CLIENT_DISTRIBUTION = "distribute_sql";
    public static final String PLACE_HOLDER = "##nm";
    public static final String REPLACE_NM = "378472893";

    /**
     * 虚拟网格特色网格数据存入Redis的key
     */
    public static final String PA_CLIENT_ANCHOR_LIST = "PaClientAnchorInfoList";
    /**
     *  登录地址
     */
    public static final String LOGIN_ADDR ="/prod-api/usercenter/loginByUser";

    /**
     * 个人客户客群
     */
    public static final Long CUSTOM = 1L;

    /**
     * 公司客户客群
     */
    public static final Long COMPANY = 2L;

    /**
     * 商户客户客群
     */
    public static final Long BUSINESS = 3L;
}
