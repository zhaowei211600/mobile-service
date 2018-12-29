package com.third.mobile.dao;

import com.third.mobile.bean.OperationRole;
import com.third.mobile.bean.request.OperationRoleRequest;

import java.util.List;

public interface OperationRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OperationRole record);

    int insertSelective(OperationRole record);

    OperationRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationRole record);

    int updateByPrimaryKey(OperationRole record);

    int checkExist(String roleName);

    List<OperationRole> listOperationRole(OperationRoleRequest request);

    Integer listOperationRoleCount(OperationRoleRequest request);

    OperationRole findRoleById(Integer roleId);

    List<OperationRole> allRoles();
}