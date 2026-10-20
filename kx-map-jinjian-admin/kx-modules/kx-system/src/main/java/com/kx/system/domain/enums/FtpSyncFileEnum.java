package com.kx.system.domain.enums;

import com.amazonaws.services.dynamodbv2.xspec.NULL;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.CreditRecord;
import com.kx.system.domain.OpeningCardRecord;
import com.kx.system.domain.PaTaskPosition;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.sync.*;
import com.kx.system.domain.sync.view.*;

import java.util.Date;
import java.util.Map;

public enum FtpSyncFileEnum {

    ORG_FILE_PATH(
            "/home/rmms/orginfo/{date}/D_XW_MAP_ORG_INFO.TXT",
            "/home/rmms/orginfo/{date}/D_XW_MAP_ORG_INFO.FLG",
            '|',
            false,
            true,
            SyncDetpVo.class),

    USER_FILE_PATH(
            "/home/rmms/userinfo/{date}/D_XW_MAP_MGR_INFO.TXT",
            "/home/rmms/userinfo/{date}/D_XW_MAP_MGR_INFO.FLG",
            '|',
            false,
            false,
            SyncUserVo.class),

    CUSTOMER_FILE_PATH(
            "/home/rmms/customerinfo/{date}/D_XW_MAP_CUST_INFO.TXT",
            "/home/rmms/customerinfo/{date}/D_XW_MAP_CUST_INFO.FLG",
            '|',
            false,
            true,
            PerformanceCustomerInfo.class),

    FULL_CUSTOMER_FILE_PATH(
            "/home/rmms/customerinfo/{date}/D_XW_MAP_CUST_INFO.TXT",
            "/home/rmms/customerinfo/{date}/D_XW_MAP_CUST_INFO.FLG",
            '|',
            false,
            true,
            PerformanceCustomerInfo.class),

    OPEN_CARD_FILE_PATH(
            "/home/rmms/cardopen/{date}/D_XW_MAP_CARD.TXT",
            "/home/rmms/cardopen/{date}/D_XW_MAP_CARD.FLG",
            '|',
            false,
            true,
            OpeningCardRecord.class),

    CREDIT_FILE_PATH(
            "/home/rmms/creidt/{date}/D_XW_MAP_CREDIT.TXT",
            "/home/rmms/creidt/{date}/D_XW_MAP_CREDIT.FLG",
            '|',
            false,
            true,
            Map.class),

    CREDIT_PRODUCT_FILE_PATH(
            "/home/rmms/creditproduct/{date}/D_XW_MAP_PRODUCT.TXT",
            "/home/rmms/creditproduct/{date}/D_XW_MAP_PRODUCT.FLG",
            '|',
            false,
            true,
            CreditRecord.class),

    /**
     * 指标文件
     **/
    PA_VIEW_DEPOSIT_MODULE_MANAGER(
            "/home/rmms/view/{date}/D_PA_VIEW_DEPOSIT_MODULE_MANAGER.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_DEPOSIT_MODULE_MANAGER.FLG",
            '|',
            false,
            true,
            PaViewDepositModuleManagerVo.class
    ),

    PA_VIEW_DEPOSIT_MODULE_MANAGER_GRID(
            "/home/rmms/view/{date}/D_PA_VIEW_DEPOSIT_MODULE_MANAGER_GRID.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_DEPOSIT_MODULE_MANAGER_GRID.FLG",
            '|',
            false,
            true,
            PaViewDepositModuleManagerGridVo.class
    ),

    PA_VIEW_DEPOSIT_MODULE_ORG(
            "/home/rmms/view/{date}/D_PA_VIEW_DEPOSIT_MODULE_ORG.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_DEPOSIT_MODULE_ORG.FLG",
            '|',
            false,
            true,
            PaViewDepositModuleOrgVo.class
    ),

    PA_VIEW_DEPOSIT_MODULE_ORG_GRID(
            "/home/rmms/view/{date}/D_PA_VIEW_DEPOSIT_MODULE_ORG_GRID.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_DEPOSIT_MODULE_ORG_GRID.FLG",
            '|',
            false,
            true,
            PaViewDepositModuleOrgGridVo.class
    ),

    PA_VIEW_GRID_OVERVIEW_MANAGER(
            "/home/rmms/view/{date}/D_PA_VIEW_GRID_OVERVIEW_MANAGER.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_GRID_OVERVIEW_MANAGER.FLG",
            '|',
            false,
            true,
            PaViewGridOverviewManagerVo.class
    ),

    PA_VIEW_GRID_OVERVIEW_MANAGER_GRID(
            "/home/rmms/view/{date}/D_PA_VIEW_GRID_OVERVIEW_MANAGER_GRID.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_GRID_OVERVIEW_MANAGER_GRID.FLG",
            '|',
            false,
            true,
            PaViewGridOverviewManagerGridVo.class
    ),

    PA_VIEW_GRID_OVERVIEW_ORG(
            "/home/rmms/view/{date}/D_PA_VIEW_GRID_OVERVIEW_ORG.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_GRID_OVERVIEW_ORG.FLG",
            '|',
            false,
            true,
            PaViewGridOverviewOrgVo.class
    ),

    PA_VIEW_GRID_OVERVIEW_ORG_GRID(
            "/home/rmms/view/{date}/D_PA_VIEW_GRID_OVERVIEW_ORG_GRID.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_GRID_OVERVIEW_ORG_GRID.FLG",
            '|',
            false,
            true,
            PaViewGridOverviewOrgGridVo.class
    ),

    PA_VIEW_LOAN_MANAGER(
            "/home/rmms/view/{date}/D_PA_VIEW_LOAN_MANAGER.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_LOAN_MANAGER.FLG",
            '|',
            false,
            true,
            PaViewLoanManagerVo.class
    ),

    PA_VIEW_LOAN_MANAGER_GRID(
            "/home/rmms/view/{date}/D_PA_VIEW_LOAN_MANAGER_GRID.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_LOAN_MANAGER_GRID.FLG",
            '|',
            false,
            true,
            PaViewLoanManagerGridVo.class
    ),

    PA_VIEW_LOAN_ORG(
            "/home/rmms/view/{date}/D_PA_VIEW_LOAN_ORG.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_LOAN_ORG.FLG",
            '|',
            false,
            true,
            PaViewLoanOrgVo.class
    ),

    PA_VIEW_LOAN_ORG_GRID(
            "/home/rmms/view/{date}/D_PA_VIEW_LOAN_ORG_GRID.TXT",
            "/home/rmms/view/{date}/D_PA_VIEW_LOAN_ORG_GRID.FLG",
            '|',
            false,
            true,
            PaViewLoanOrgGridVo.class
    ),

    /**
     * 时空轨迹
     */
    PA_TASK_POSITION(
            "/home/rmms/location/{date}/gis_{date}.txt",
            "/home/rmms/location/{date}/gis_{date}.FLG",
            '|',
            false,
            false,
            PaTaskPosition.class
    ),

    /**
     * 团队信息
     */
    SYS_TEAM(
            "/home/rmms/manager/{date}/F_OCRM_F_CM_MKT_TEAM.TXT",
            "/home/rmms/manager/{date}/F_OCRM_F_CM_MKT_TEAM.FLG",
            '|',
            false,
            true,
            SysTeamVo.class
    ),

    SYS_TEAM_MEMBER(
            "/home/rmms/manager/{date}/F_OCRM_F_CM_TEAM_CUST_MANAGER.TXT",
            "/home/rmms/manager/{date}/F_OCRM_F_CM_TEAM_CUST_MANAGER.FLG",
            '|',
            false,
            true,
            SysTeamMemberVo.class
    ),

    MAANAGER_TRANSFER_REFER(
            "/home/rmms/transfer/{date}/D_XW_MAP_MGR_TRANS.TXT",
            "/home/rmms/transfer/{date}/D_XW_MAP_MGR_TRANS.FLG",
            '|',
            false,
            true,
            SysManagerTransferReferIndex.class
    );


    /**
     * 文件路径
     */
    private final String path;
    /**
     * 文件路径
     */
    private final String checkFilePath;
    /**
     * 字段分隔符
     */
    private final char separator;
    /**
     * 是否包含标题行
     */
    private final boolean includeTitle;
    /**
     * 是否是全量
     */
    private final boolean isFullData;
    /**
     * 对应的JAVABEAN类型
     */
    private final Class beanType;

    FtpSyncFileEnum(String path, String checkFilePath, char separator, boolean includeTitle, boolean isFullData, Class beanType) {
        this.path = path;
        this.checkFilePath = checkFilePath;
        this.separator = separator;
        this.includeTitle = includeTitle;
        this.isFullData = isFullData;
        this.beanType = beanType;
    }

    public String getPath(Date date) {
        return path.replaceAll("\\{date\\}", DateUtils.parseDateToStr("yyyyMMdd", date));
    }

    public String getCheckFilePath(Date date){
        if (StringUtils.isEmpty(checkFilePath)) {
            return null;
        }
        return checkFilePath.replaceAll("\\{date\\}", DateUtils.parseDateToStr("yyyyMMdd", date));
    }

    public String getPath(String date) {
        return path.replaceAll("\\{date\\}", date);
    }

    public String getPath() {
        return getPath(new Date());
    }

    public char getSeparator() {
        return separator;
    }

    public boolean isIncludeTitle() {
        return includeTitle;
    }

    public Class getBeanType() {
        return beanType;
    }

    public boolean isFullData() {
        return isFullData;
    }
}
