package com.third.enterprise.dao;

import com.third.enterprise.bean.OperationMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OperationMenuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OperationMenu record);

    int insertSelective(OperationMenu record);

    OperationMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationMenu record);

    int updateByPrimaryKey(OperationMenu record);

    List<OperationMenu> getAllMenus(@Param("roleId") Integer roleId);

    List<OperationMenu> findMenusByUserId(Integer userId);

}