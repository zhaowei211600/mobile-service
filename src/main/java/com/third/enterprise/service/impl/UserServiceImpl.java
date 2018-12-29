package com.third.enterprise.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.enterprise.bean.User;
import com.third.enterprise.bean.request.CustomListRequest;
import com.third.enterprise.dao.UserMapper;
import com.third.enterprise.service.IUserService;
import com.third.enterprise.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService{

    private static final Logger Logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> listAll(CustomListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return userMapper.listAll(request);
    }

    @Override
    public List<User> findByOrderId(Integer orderId) {
        return userMapper.findByOrderId(orderId);
    }

    @Override
    public List<User> findByProductId(Integer productId) {
        return userMapper.findByProductId(productId);
    }

    @Override
    public User findByUserId(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean updateStatus(Integer userId, String status) {

        if(userMapper.updateStatus(userId, status) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        if(userMapper.updateByPrimaryKeySelective(user) > 0){
            return true;
        }
        return false;
    }
}
