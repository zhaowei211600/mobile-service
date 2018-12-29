package com.third.mobile.service;

import com.third.mobile.bean.User;
import com.third.mobile.bean.request.CustomListRequest;

import java.util.List;

public interface IUserService {

    List<User> listAll(CustomListRequest request);

    List<User> findByOrderId(Integer orderId);

    List<User> findByProductId(Integer productId);

    User findByUserId(Integer userId);

    boolean updateStatus(Integer userId, String status);

    boolean updateUser(User user);

    User findByPhone(String phone);

    boolean saveUser(User user);
}
