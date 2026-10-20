package com.kx.system.domain.common;

import com.github.pagehelper.PageInfo;
import com.kx.common.core.constant.HttpStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 包含泛型结构的表格分页数据对象
 *
 * @author xifan_h
 */
@Data
@NoArgsConstructor
public class TableDataInfoModel<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    @ApiModelProperty("总记录数")
    private long total;

    /** 列表数据 */
    @ApiModelProperty("列表数据")
    private T rows;

    /** 消息状态码 */
    @ApiModelProperty("消息状态码")
    private int code;

    /** 消息内容 */
    @ApiModelProperty("消息内容")
    private String msg;

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfoModel(T list, int total)
    {
        this.rows = list;
        this.total = total;
    }


    public static <T> TableDataInfoModel<T> getDataTable(T list) {
        TableDataInfoModel rspData = new TableDataInfoModel();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(list==null?0:new PageInfo((List) list).getTotal());
        return rspData;
    }
}
