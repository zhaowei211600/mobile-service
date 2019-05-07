package com.third.mobile.dao;

import com.third.mobile.bean.Requirement;

public interface RequirementMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Requirement record);

    int insertSelective(Requirement record);

    Requirement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Requirement record);

    int updateByPrimaryKey(Requirement record);
}