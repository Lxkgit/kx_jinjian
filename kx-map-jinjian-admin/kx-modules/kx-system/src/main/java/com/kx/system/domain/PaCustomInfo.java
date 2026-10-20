package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 业务表客户基本信息
 */
@Data
public class PaCustomInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 客户内码
     */
    private String nm;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 客户身份证号
     */
    private String identify;

    /**
     * 户籍地址
     */
    private String homeAddress;

    /**
     * 联系地址
     */
    private String contractAddress;

    /**
     * 所在网点
     */
    private String outlet;

    /**
     * 所在网点机构码
     */
    private String outletCode;

    /**
     * 所属乡镇
     */
    private String township;

    /**
     * 所属村/社区
     */
    private String area;
    /**
     * 所属村/社区代码
     */
    private String areaCode;

    /**
     * 客户经理工号
     */
    private String managerId;

    /**
     * 工作单位
     */
    private String work;

    /**
     * 职业
     */
    private String profession;

    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 性别
     */
    private String gender;

    /**
     * 婚姻状况
     */
    private String maritalStatus;
    /**
     * 客户类型，00 大客户、01 滞留客户
     */
    private String type;
    /**
     * 分配时间
     */
    private Date allocationDate;
    /**
     * 客户经度
     */
    private double lon;
    /**
     * 客户维度
     */
    private double lat;
    /**
     * 跑批时间
     */
    private Date etlDt;

    /**
     * 删除标记
     */
    private int delFlag;

    /**
     * 客户经理名称
     */
    private String managerName;

    private String star;

    private String oldTarget;

    private String newTarget;

    @Override
    public String toString() {
        return "PaCustomInfo{" +
                "nm='" + nm + '\'' +
                ", name='" + name + '\'' +
                ", identify='" + identify + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", outlet='" + outlet + '\'' +
                ", outletCode='" + outletCode + '\'' +
                ", township='" + township + '\'' +
                ", area='" + area + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", managerId='" + managerId + '\'' +
                ", work='" + work + '\'' +
                ", profession='" + profession + '\'' +
                ", telephone='" + telephone + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", type='" + type + '\'' +
                ", allocationDate=" + allocationDate +
                ", lon=" + lon +
                ", lat=" + lat +
                ", etlDt=" + etlDt +
                ", delFlag=" + delFlag +
                ", managerName='" + managerName + '\'' +
                ", star='" + star + '\'' +
                '}';
    }
}
