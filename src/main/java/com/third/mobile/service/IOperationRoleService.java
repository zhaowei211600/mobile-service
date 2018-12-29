package com.third.mobile.service;

import com.third.mobile.bean.OperationRole;
import com.third.mobile.bean.request.OperationRoleRequest;

import java.util.List;

public interface IOperationRoleService {

    boolean checkExist(String roleName);

    Boolean save(OperationRole role);

    Boolean update(OperationRole role);

    Boolean checkRoleBind(Integer roleId);

    Boolean delete(Integer roleId);

    List<OperationRole> listRole(OperationRoleRequest request);

    OperationRole findRoleById(Integer roleId);

    List<OperationRole> allRoles();
}
