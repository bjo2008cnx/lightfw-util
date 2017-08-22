package org.lightfw.util.ext.model;

import java.util.ArrayList;
import java.util.List;

public class PagedList<T> {

    //默认每页显示数量
    private static int DEFAULT_PAGE_SIZE = 20;

    private List<T> list;//记录集
    private long totalCount;//总记录数
    private int page;//当前页码
    private int pageSize = DEFAULT_PAGE_SIZE;//每页显示数量
    private long totalPageCount = 0; //总页数

    public long getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(long totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public PagedList() {
        this(0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
    }

    public PagedList(int page, int pageSize, List<T> list) {
        this.page = page;
        this.pageSize = pageSize;
        this.list = list;
    }

    public PagedList(int page, int pageSize, long totalCount, List<T> list) {
        this.page = page;
        this.pageSize = pageSize;
        this.list = list;
        this.totalCount = totalCount;

        //将页数也直接计算并赋值
        this.totalPageCount = getCalTotalPageCount();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < 1)
            this.page = 1;
        else
            this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1)
            this.pageSize = DEFAULT_PAGE_SIZE;
        else
            this.pageSize = pageSize;
    }

    /**
     * 取当前页的记录.
     */
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 取总记录数.
     */
    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 取总页数.
     */
    public long getCalTotalPageCount() {
        if (totalCount % pageSize == 0)
            return totalCount / pageSize;
        else
            return totalCount / pageSize + 1;
    }

    /**
     * 取第一页.
     */
    public long getFirst() {
        return ((this.getPage() - 1) * pageSize);
    }

    /**
     * 该页是否有下一页.
     */
    public boolean hasNextPage() {
        return this.getPage() + 1 <= this.getCalTotalPageCount();
    }

    /**
     * 该页是否有上一页.
     */
    public boolean hasPreviousPage() {
        return this.getPage() - 1 >= 1;
    }

    /**
     * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
     *
     * @see #getStartOfPage(int, int)
     */
    protected static int getStartOfPage(int pageIndex) {
        return getStartOfPage(pageIndex, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据在数据集的位置.
     *
     * @param pageIndex 从1开始的页号
     * @param pageSize  每页记录条数
     * @return 该页第一条数据
     */
    public static int getStartOfPage(int pageIndex, int pageSize) {
        return (pageIndex - 1) * pageSize + 1;
    }
}
