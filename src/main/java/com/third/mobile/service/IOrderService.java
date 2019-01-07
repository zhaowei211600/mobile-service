package com.third.mobile.service;

import com.third.mobile.bean.Order;
import com.third.mobile.bean.request.OrderRequest;

import java.util.List;

public interface IOrderService {

    List<Order> listOrder(OrderRequest request);

    Integer countOrder(Integer productId);

    Order findOrder(Integer productId, Integer userId);

    boolean saveOrder(Order order);
}
