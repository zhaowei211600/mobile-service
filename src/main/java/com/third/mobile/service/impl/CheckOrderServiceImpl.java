package com.third.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.mobile.bean.CheckOrder;
import com.third.mobile.dao.CheckOrderMapper;
import com.third.mobile.service.ICheckOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckOrderServiceImpl implements ICheckOrderService {

    private static final Logger logger = LoggerFactory.getLogger(CheckOrderServiceImpl.class);

    @Autowired
    private CheckOrderMapper checkOrderMapper;

    @Override
    public CheckOrder selectById(Integer checkOrderId) {
        return checkOrderMapper.selectByPrimaryKey(checkOrderId);
    }

    @Override
    public boolean updateCheckOrder(CheckOrder checkOrder) {
        if(checkOrderMapper.updateByPrimaryKeySelective(checkOrder) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean saveCheckOrder(CheckOrder checkOrder) {
        if(checkOrderMapper.insertSelective(checkOrder) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasOpenCheckOrder(Integer orderId) {
        if(checkOrderMapper.hasOpenCheckOrder(orderId) > 0){
            return true;
        }
        return false;
    }
}
