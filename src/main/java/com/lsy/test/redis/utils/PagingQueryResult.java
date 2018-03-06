package com.lsy.test.redis.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by liangsongying on 2017/11/26.
 */
public class PagingQueryResult implements Serializable {
    private Collection rows;
    private long totalPages;
    private long total;
    private long pageSize;

    public PagingQueryResult() {
    }

    public PagingQueryResult(Collection rows, long total) {
        this.rows = rows;
        this.total = total;
    }
    public PagingQueryResult(Collection rows, long total,long pageSize) {
        this.rows = rows;
        this.total = total;
        this.totalPages = totalPages;
        if(pageSize!=0&&total>0)
            this.totalPages= total % pageSize == 0 ? (total / pageSize) : (total / pageSize) + 1;
    }

    public Collection getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
