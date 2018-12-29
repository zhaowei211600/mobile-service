package com.third.enterprise.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageBuilder {

    public static Page toPage(List list){
        Page page = new Page();
        PageInfo pageInfo = new PageInfo(list);
        page.setPageNum(pageInfo.getPageNum());
        page.setTotal(pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
        page.setList(pageInfo.getList());
        return page;
    }


}
