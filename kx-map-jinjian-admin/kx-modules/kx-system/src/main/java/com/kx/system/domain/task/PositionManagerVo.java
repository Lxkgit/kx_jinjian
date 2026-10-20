//package com.kx.system.domain.task;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * <p>
// * 任务轨迹客户经理集
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-07-11
// */
//@Data
//@ApiModel(value = "任务轨迹客户经理集", description = "任务轨迹客户经理集")
//public class PositionManagerVo implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @ApiModelProperty(value = "客户经理号")
//    private String managerId;
//
//
//    @ApiModelProperty(value = "客户经理名称")
//    private String managerName;
//
//
//    @ApiModelProperty(value = "网点code")
//    private String networkCode;
//
//
//    @ApiModelProperty(value = "网点名称")
//    private String networkName;
//    /**
//     *  任务id查询
//     */
//    private String taskId;
//
//    /**
//     * 登录用户客户经理号
//     */
//    private String userManagerCode;
//    /**
//     * 登陆用户机构及下辖
//     */
//    private List<String> userList;
//}
