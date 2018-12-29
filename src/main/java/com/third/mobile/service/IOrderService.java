package com.third.mobile.service;

import com.third.mobile.bean.Order;
import com.third.mobile.bean.request.OperationRoleRequest;

import java.util.List;

public interface IOrderService {

    List<Order> listOrder(OperationRoleRequest request);
}
