package com.third.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.mobile.bean.Order;
import com.third.mobile.bean.request.OrderRequest;
import com.third.mobile.bean.response.OrderStatResponse;
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
    public List<Order> listOrder(OrderRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return orderMapper.listOrder(request);
    }

    @Override
    public Integer countOrder(Integer productId) {
        return orderMapper.countOrder(productId);
    }

    @Override
    public Order findOrder(Integer productId, Integer userId) {
        return orderMapper.findOrder(productId, userId);
    }

    @Override
    public boolean saveOrder(Order order) {
        if(orderMapper.insertSelective(order) > 0){
            return true;
        }
        return false;
    }

    @Override
    public OrderStatResponse statOrder(Integer userId) {
        return orderMapper.statOrder(userId);
    }
}
