package com.third.mobile.dao;

import com.third.mobile.bean.OperationRoleMenu;

import java.util.List;

public interface OperationRoleMenuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OperationRoleMenu record);

    int insertSelective(OperationRoleMenu record);

    OperationRoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationRoleMenu record);

    int updateByPrimaryKey(OperationRoleMenu record);

    Integer batchSave(List<OperationRoleMenu> roleMenuList);

    Integer deleteByRoleId(Integer roleId);

}