package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 客户基本信息
 */
@Data
public class CustomInfo extends BaseEntity {
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
     * 跑批时间
     */
    private Date etlDt;
    /**
     * etl标记
     */
    private String etlFlg;


    private String fc;

    private static final long serialVersionUID = 1L;
}
