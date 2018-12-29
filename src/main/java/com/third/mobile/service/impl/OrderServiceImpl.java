package com.third.mobile.service.impl;

import com.third.mobile.bean.Order;
import com.third.mobile.bean.request.OperationRoleRequest;
import com.third.mobile.dao.OrderMapper;
import com.third.mobile.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements IOrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> listOrder(OperationRoleRequest request) {
        return orderMapper.listOrder(request);
    }
}
