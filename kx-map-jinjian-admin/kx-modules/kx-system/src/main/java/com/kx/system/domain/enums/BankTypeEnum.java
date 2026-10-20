package com.kx.system.domain.enums;

/**
 * 银行类型枚举
 */
public enum BankTypeEnum {

    // 政策性银行
    GUOJIA_KAIFA(1, "国家开发银行"),
    JINCHUKO(2, "中国进出口银行"),
    NONGYE_FAZHAN(3, "中国农业发展银行"),
    // 国有大型银行
    GONGSHANG(4, "中国工商银行"),
    NONGYE(5, "中国农业银行"),
    ZHONGGUO(6, "中国银行"),
    JIANSHE(7, "中国建设银行"),
    JIAOTONG(8, "交通银行"),
    YOUZHENG(9, "邮政储蓄银行"),
    // 股份制银行
    ZHAOSHANG(10, "招商银行"),
    PUFA(11, "浦发银行"),
    ZHONGXIN(12, "中信银行"),
    GUANGDA(13, "光大银行"),
    HUAXIA(14, "华夏银行"),
    MINSHENG(15, "民生银行"),
    GUANGFA(16, "广发银行"),
    PINGAN(17, "平安银行"),
    XINGYE(18, "兴业银行"),
    ZHESHANG(19, "浙商银行"),
    BOHAI(20, "渤海银行"),
    // 城市商业银行
    BEIJING(21, "北京银行"),
    SHANGHAI(22, "上海银行"),
    NINGBO(23, "宁波银行"),
    HANGZHOU(24, "杭州银行"),
    NANJING(25, "南京银行"),
    JIANGSU(26, "江苏银行"),
    WENZHOU(27, "温州银行"),
    TAIZHOU(28, "台州银行"),
    SHAOXING(29, "绍兴银行"),
    JINHUA(30, "金华银行"),
    JIAXING(31, "嘉兴银行"),
    CHOUZHOU(32, "稠州商业银行"),
    TAILONG(33, "泰隆商业银行"),
    WENZHOU_MINSHANG(34, "温州民商银行"),
    // 农村中小银行
    NONGCUN_SHANGYE(35, "农村商业银行"),
    NONGCUN_HEZUO(36, "农村合作银行"),
    NONGCUN_XINYONG(37, "农村信用社"),
    CUNZHEN(38, "村镇银行"),
    // 外资银行
    WAIZI(39, "外资银行"),
    // 其他
    OTHER(99, "其他");

    private final Integer code;
    private final String name;

    BankTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BankTypeEnum ofCode(Integer code) {
        if (code == null) return null;
        for (BankTypeEnum e : values()) {
            if (e.code.equals(code)) return e;
        }
        return null;
    }
}