package com.third.enterprise.service;

import com.third.enterprise.bean.Order;
import com.third.enterprise.bean.request.OperationRoleRequest;

import java.util.List;

public interface IOrderService {

    List<Order> listOrder(OperationRoleRequest request);
}
