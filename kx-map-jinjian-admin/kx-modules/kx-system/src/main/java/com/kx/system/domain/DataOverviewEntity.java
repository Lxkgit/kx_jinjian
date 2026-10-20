package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 数据概览对象
 *
 * @author gary
 * @date 2024-01-30
 */
@Data
public class DataOverviewEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 基础网格数量 */
    private Integer baseGridNumber;

    /** 特色网格数量 */
    private Integer featureGridNumber;

    /** 客户数量 */
    private Integer customNumber;

    /** 个人客户数量 */
    private Integer personCustomNumber;

    /** 商户客户数量 */
    private Integer businessCustomNumber;

    /** 公司客户数量 */
    private Integer companyCustomNumber;

    /** 任务数量 */
    private Integer taskNumber;

    /** 已完成任务数量 */
    private Integer finishTaskNumber;

    /** 未完成任务数量 */
    private Integer unFinishTaskNumber;


}
