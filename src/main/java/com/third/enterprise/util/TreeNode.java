package com.third.enterprise.util;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaowei
 * 展示机构树
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {

    /**
     * 用于角色管理--编辑--回显(默认选中)
     */
    private static Map<String, Object> stateMap = new HashMap<String, Object>();

    static {
        stateMap.put("checked", true);
    }

    private String orgNo;

    private String parentOrgNo;

    private String simpleParentOrgName;

    private Integer id;

    private Integer parentId;

    private String value;

    private String text;

    private String href;

    private Map<String, Object> state;

    public Map<String, Object> getState() {
        return state;
    }

    /**
     * 用于角色管理--编辑--回显
     */
    public void setState() {
        this.state = stateMap;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeNode> nodes;

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public String getParentOrgNo() {
        return parentOrgNo;
    }

    public void setParentOrgNo(String parentOrgNo) {
        this.parentOrgNo = parentOrgNo;
    }

    public String getSimpleParentOrgName() {
        return simpleParentOrgName;
    }

    public void setSimpleParentOrgName(String simpleParentOrgName) {
        this.simpleParentOrgName = simpleParentOrgName;
    }
}
