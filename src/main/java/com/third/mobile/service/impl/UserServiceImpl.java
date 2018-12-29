package com.third.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.mobile.bean.User;
import com.third.mobile.bean.request.CustomListRequest;
import com.third.mobile.dao.UserMapper;
import com.third.mobile.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public User findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    public boolean saveUser(User user) {
        if(user.getId() == null || user.getId() == 0){
            if(userMapper.insertSelective(user) > 0){
                return true;
            }
        }else{
            if(userMapper.updateByPrimaryKeySelective(user) > 0){
                return true;
            }
        }
        return false;
    }
}
