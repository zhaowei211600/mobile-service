package com.third.enterprise.dao;

import com.third.enterprise.bean.Order;
import com.third.enterprise.bean.request.OperationRoleRequest;
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

    List<Order> listOrder(OperationRoleRequest request);
}