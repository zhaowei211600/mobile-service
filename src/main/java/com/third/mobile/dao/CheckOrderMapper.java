package com.third.mobile.dao;


import com.third.mobile.bean.CheckOrder;


public interface CheckOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckOrder record);

    int insertSelective(CheckOrder record);

    CheckOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckOrder record);

    int updateByPrimaryKey(CheckOrder record);

    int hasOpenCheckOrder(Integer orderId);

}