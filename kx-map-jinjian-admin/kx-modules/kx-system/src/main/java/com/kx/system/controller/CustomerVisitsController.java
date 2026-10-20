package com.kx.system.controller;

import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.system.domain.customer.CustomerVisits;
import com.kx.system.domain.customer.VisitsManager;
import com.kx.system.domain.param.ListVisitsManagerParam;
import com.kx.system.service.CustomerVisitsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户走访信息Controller
 *
 * @author kangxu
 * @date 2024-04-09
 */
@RestController
@RequestMapping("/customerVisit")
@Api(tags = "客户走访")
public class CustomerVisitsController extends BaseController {
    @Autowired
    private CustomerVisitsService customerVisitsService;

    /**
     * 新增客户走访信息
     */
    @Log(title = "客户走访信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增客户走访信息")
    public AjaxResult add(@RequestBody CustomerVisits customerVisits) {
        return toAjax(customerVisitsService.insertCustomerVisits(customerVisits));
    }

    @GetMapping(value = "/listByManagerCode")
    @ApiOperation(value = "分页查询走访详情", notes = "传入走访人员编码")
    public TableDataInfo listByManagerCode(ListVisitsManagerParam param) {
        startPage();
        return getDataTable(customerVisitsService.listByMangerCode(param));
    }

    @PostMapping(value = "/exportManagerCode")
    @ApiOperation(value = "导出走访详情")
    public void exportManagerCode(HttpServletResponse response, ListVisitsManagerParam param) {
        List<CustomerVisits> customerVisits = customerVisitsService.listExportByMangerCode(param);
        ExcelUtil<CustomerVisits> util = new ExcelUtil(CustomerVisits.class);
        util.exportExcel(response, customerVisits, "走访信息");
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "分页查询统计数据", notes = "传入查询走访信息参数")
    public TableDataInfo list(ListVisitsManagerParam param) {
        startPage();
        return getDataTable(customerVisitsService.listGroupByManager(param));
    }

    @PostMapping("/export")
    @ApiOperation(value = "导出统计数据")
    public void list(HttpServletResponse response, ListVisitsManagerParam param) {
        List<VisitsManager> visitsManagers = customerVisitsService.listExportGroupByManager(param);
        ExcelUtil<VisitsManager> util = new ExcelUtil(VisitsManager.class);
        util.exportExcel(response, visitsManagers, "走访信息");
    }
}
