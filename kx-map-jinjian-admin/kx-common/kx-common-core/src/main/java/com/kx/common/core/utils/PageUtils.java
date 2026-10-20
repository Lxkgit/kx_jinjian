package com.kx.common.core.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kx.common.core.utils.sql.SqlUtil;
import com.kx.common.core.web.page.PageDomain;
import com.kx.common.core.web.page.TableSupport;

/**
 * 分页工具类
 *
 * @author kangxu
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     *
     * @return
     */
    public static Page<?> startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        return PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
