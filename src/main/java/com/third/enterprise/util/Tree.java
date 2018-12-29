package com.third.enterprise.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree {

    private static Map<String, Object> stateMap = new HashMap();

    private Integer id;

    private Integer parentId;

    private String value;

    private String name;

    private String link;

    private Boolean spread;

    private Map<String, Object> state;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Tree> children;

    public Tree() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public List<Tree> getChildren() {
        return this.children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSpread() {
        return this.spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState() {
        this.state = stateMap;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", spread=" + spread +
                ", children=" + children +
                '}';
    }
}
