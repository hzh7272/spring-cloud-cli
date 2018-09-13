package com.example.tools.data;

import java.util.List;

/**
 * 分页查询数据模型
 * @author hzh 2018/9/12 16:06
 */
public class Page<Q, R> {

    private Integer page;
    private Integer rows;
    private Integer totalCount;
    private List<R> data;
    private Q condition;

    public Integer getPage() {
        return null == page ? 0 : (page - 1) * getRows();
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return null == rows ? 10 : rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<R> getData() {
        return data;
    }

    public void setData(List<R> data) {
        this.data = data;
    }

    public Q getCondition() {
        return condition;
    }

    public void setCondition(Q condition) {
        this.condition = condition;
    }
}
