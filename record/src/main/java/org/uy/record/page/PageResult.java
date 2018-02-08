package org.uy.record.page;

import java.util.ArrayList;
import java.util.List;

public class PageResult<E> extends ArrayList<E>{

    private static final long serialVersionUID = 3855513376047570862L;

    //分页总数
    private Integer totalPage;

    //当前页数
    private Integer currentPage;

    //数据总条数
    private Integer totalRow;

    //每页数据数量
    private Integer pageSize;

    public PageResult(){

    }

    public PageResult(List<E> result){
        this.addAll(result);
    }

    public PageResult(List<E> result, Integer totalPage, Integer currentPage, Integer totalRow, Integer pageSize) {
        this.addAll(result);
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.totalRow = totalRow;
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
