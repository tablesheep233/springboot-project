package org.table.neweims.dto;

import java.util.List;
import java.util.Map;

public class Page<T> {

    private int currPage;	//当前页数

    private int pageSize;	//每页显示记录数

    private int totalCount;		//总记录数

    private int totalPage;		//总页数=总记录数/每页记录数

    private List<T> result;

    private String search;

    private List<Map<String,Object>> myResult;

    public Page(){}

    public Page(int currPage, int pageSize,int totalCount){
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = ((Double)Math.ceil((double)totalCount/pageSize)).intValue();
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public List<Map<String, Object>> getMyResult() {
        return myResult;
    }

    public void setMyResult(List<Map<String, Object>> myResult) {
        this.myResult = myResult;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "currPage=" + currPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", result=" + result +
                '}';
    }
}
