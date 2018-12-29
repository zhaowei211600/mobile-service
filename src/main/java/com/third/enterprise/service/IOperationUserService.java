package com.third.enterprise.service;

import com.third.enterprise.bean.OperationUser;
import com.third.enterprise.bean.request.OperationUserListRequest;

import java.util.List;

public interface IOperationUserService {

    OperationUser findUserByAccount(String account);

    boolean saveOperationUserAndRole(OperationUser operationUser);

    boolean updateOperationUserAndRole(OperationUser operationUser);

    List<OperationUser> listOperationUser(OperationUserListRequest request);

    OperationUser findUserById(Integer userId);
}
