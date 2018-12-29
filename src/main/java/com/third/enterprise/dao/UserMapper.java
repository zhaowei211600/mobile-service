package com.third.enterprise.dao;

import com.third.enterprise.bean.User;
import com.third.enterprise.bean.request.CustomListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> listAll(CustomListRequest request);

    List<User> findByOrderId(Integer orderId);

    List<User> findByProductId(Integer productId);

    int updateStatus(@Param("userId") Integer userId,
                     @Param("status") String status);
}