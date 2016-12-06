package org.lightfw.util.ext.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageVo {

    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * recordCount 表示: 记录总数
     */
    private int recordCount;

    /**
     * pageSize 表示: 每页有多少条记录
     */
    private int pageSize;

    /**
     * currentPage 表示: 当前第几页
     */
    private int currentPage = 1;

    /**
     * 构造函数:
     *
     * @param recordCount 记录总数
     * @param pageSize    每页有多少条记录
     */
    public PageVo(int recordCount, int pageSize) {
        if (recordCount < 0) {
            recordCount = 0;
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.recordCount = recordCount;
        this.pageSize = pageSize;
    }



    /**
     * 功能: 获得总页数
     *
     * @return
     */
    public int getPageCount() {
        int pc = recordCount / pageSize + (recordCount % pageSize == 0 ? 0 : 1);
        if (pc == 0) {
            pc = 1;
        }
        return pc;
    }

    /**
     * 功能: 获得开始条数
     *
     * @return
     */
    public int getStartIndex() {
        return (currentPage - 1) * pageSize + 1;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage <= 1 || getPageCount() == 0) {
            currentPage = 1;
        } else if (currentPage > getPageCount()) {
            currentPage = getPageCount();
        }
        this.currentPage = currentPage;
    }
}
