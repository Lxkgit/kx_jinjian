package com.kx.system.domain.customer;

import lombok.Data;

/**
 * @author kangxu
 * @date 2024/4/8
 */
@Data
public class CustomInfoDTO {
    /** 锚点Id */
    private Long anchorPointId;
    /** 客户Id */
    private Long customerId;
    /** 客户身份证 */
    private String customerIdentity;
    /** 客户类型 1-绩效客户 2-授信客户 3-收单客户 4-建档客户 */
    private String type;
    /** 客户经理工号 */
    private String managerNum;
    /** 所属部门 */
    private Long deptId;
    /** 客户编码 */
    private String customerNm;
    /** 所属部门名称 */
    private String deptName;
}
