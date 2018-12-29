package com.third.enterprise.dao;

import com.third.enterprise.bean.OperationUser;
import com.third.enterprise.bean.request.OperationUserListRequest;

import java.util.List;

public interface OperationUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OperationUser record);

    int insertSelective(OperationUser record);

    OperationUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationUser record);

    int updateByPrimaryKey(OperationUser record);

    List<OperationUser> findByAccount(String account);

    OperationUser selectUserAndRolesByAccount(String account);

    List<OperationUser> listOperationUser(OperationUserListRequest request);

    OperationUser selectUserAndRolesById(Integer id);
}