package com.third.enterprise.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class Page<T> {

    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 总行数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 返回的结果集
     */
    private List<T> list;



    public Page(){

    }

    public Page(PageInfo pageInfo){
        this.setPageNum(pageInfo.getPageNum());
        this.setTotal(pageInfo.getTotal());
        this.setPages(pageInfo.getPages());
        this.setList(pageInfo.getList());
    }

    public static Page toPage(List list){
        Page page = new Page();
        PageInfo pageInfo = new PageInfo(list);
        page.setPageNum(pageInfo.getPageNum());
        page.setTotal(pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
        page.setList(pageInfo.getList());
        return page;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", total=" + total +
                ", pages=" + pages +
                ", list=" + list +
                '}';
    }
}
