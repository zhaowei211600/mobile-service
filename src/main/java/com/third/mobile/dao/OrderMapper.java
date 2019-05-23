package com.third.mobile.dao;

import com.third.mobile.bean.Order;
import com.third.mobile.bean.request.OrderRequest;
import com.third.mobile.bean.response.OrderStatResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateOrderStatus(@Param("orderId") Integer orderId,
                          @Param("status") String status);

    List<Order> listOrder(OrderRequest request);

    Integer countOrder(Integer productId);

    Order findOrder(@Param("productId") Integer productId,
                    @Param("userId") Integer userId);

    OrderStatResponse statOrder(Integer userId);

    Order selectById(Integer orderId);
}