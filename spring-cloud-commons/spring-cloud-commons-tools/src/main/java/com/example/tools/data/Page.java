package com.example.tools.data;

/**
 * 分页查询数据模型
 * @author hzh 2018/9/12 16:06
 */
public class Page {

    private Integer page;
    private Integer rows;

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
}
