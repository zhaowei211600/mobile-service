package com.third.enterprise.dao;

import com.third.enterprise.bean.OperationUserRole;

import java.util.List;

public interface OperationUserRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OperationUserRole record);

    int insertSelective(OperationUserRole record);

    OperationUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationUserRole record);

    int updateByPrimaryKey(OperationUserRole record);

    Integer checkRoleBind(Integer roleId);

    Integer batchSave(List<OperationUserRole> userRoleList);

    Integer deleteByUserId(Integer userId);
}