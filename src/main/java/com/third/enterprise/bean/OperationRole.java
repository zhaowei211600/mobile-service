package com.third.enterprise.bean;

import java.util.Date;
import java.util.List;

public class OperationRole {

    private Integer id;

    private String name;

    private String desc;

    private Integer rank;

    private List<OperationMenu> menuList;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<OperationMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<OperationMenu> menuList) {
        this.menuList = menuList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OperationRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", rank=" + rank +
                ", menuList=" + menuList +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}