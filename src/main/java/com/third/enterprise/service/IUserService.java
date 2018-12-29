package com.third.enterprise.service;

import com.third.enterprise.bean.User;
import com.third.enterprise.bean.request.CustomListRequest;

import java.util.List;

public interface IUserService {

    List<User> listAll(CustomListRequest request);

    List<User> findByOrderId(Integer orderId);

    List<User> findByProductId(Integer productId);

    User findByUserId(Integer userId);

    boolean updateStatus(Integer userId, String status);

    boolean updateUser(User user);
}
