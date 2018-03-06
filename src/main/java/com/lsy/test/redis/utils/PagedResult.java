package com.lsy.test.redis.utils;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liangsongying on 2017/10/27.
 */
public class PagedResult<T> extends AbstractCollection<T> implements Serializable {
    private List<T> rows;
    private long totalPages;
    private long total;
    private int pageIndex;
    private long pageSize;

    public PagedResult() {
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public Iterator<T> iterator() {
        return this.rows.iterator();
    }

    public int size() {
        return this.rows == null?0:this.rows.size();
    }
}
