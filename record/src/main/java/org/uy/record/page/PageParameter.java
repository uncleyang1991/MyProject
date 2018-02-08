package org.uy.record.page;

public class PageParameter {

    //每页数据数量
    private Integer pageSize;

    //当前页
    private Integer page;

    //分页总数
    private Integer totalPage;

    //数据总数
    private Integer totalRow;

    public PageParameter() { }

    public PageParameter(Integer pageSize, Integer page) {
        this.pageSize = pageSize;
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize==null?5:pageSize<=0?5:pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page==null?1:page<=0?1:page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }
}
