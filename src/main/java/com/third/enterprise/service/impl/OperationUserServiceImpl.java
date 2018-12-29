package com.third.enterprise.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.enterprise.bean.OperationRole;
import com.third.enterprise.bean.OperationUser;
import com.third.enterprise.bean.OperationUserRole;
import com.third.enterprise.bean.request.OperationUserListRequest;
import com.third.enterprise.dao.OperationUserMapper;
import com.third.enterprise.dao.OperationUserRoleMapper;
import com.third.enterprise.service.IOperationUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("operationUserService")
public class OperationUserServiceImpl implements IOperationUserService {

    private static final Logger logger = LoggerFactory.getLogger(OperationUserServiceImpl.class);

    @Autowired
    private OperationUserMapper operationUserMapper;

    @Autowired
    private OperationUserRoleMapper operationUserRoleMapper;

    @Override
    public OperationUser findUserByAccount(String account) {
        List<OperationUser> userList = operationUserMapper.findByAccount(account);
        if(!userList.isEmpty()){
            return userList.get(0);
        }
        return null;
    }

    /**
     * 保存运营用户及角色
     * @param operationUser
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean saveOperationUserAndRole(OperationUser operationUser) {
        if(operationUserMapper.insertSelective(operationUser) > 0){
            return afterSaveUser(operationUser);
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    @Override
    public boolean updateOperationUserAndRole(OperationUser operationUser) {
        if(operationUserMapper.updateByPrimaryKeySelective(operationUser) >0){
            //删除原来的角色绑定
            if(operationUserRoleMapper.deleteByUserId(operationUser.getId()) > 0){
                return afterSaveUser(operationUser);

            }
        }
        return false;
    }

    @Override
    public List<OperationUser> listOperationUser(OperationUserListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return operationUserMapper.listOperationUser(request);
    }

    @Override
    public OperationUser findUserById(Integer userId) {
        return operationUserMapper.selectUserAndRolesById(userId);
    }

    private  boolean afterSaveUser(OperationUser operationUser){
        List<OperationUserRole> userRoleList = buildUserRoleRelation(operationUser);
        if (userRoleList != null && !userRoleList.isEmpty()) {
            if(operationUserRoleMapper.batchSave(buildUserRoleRelation(operationUser)) > 0){
                return true;
            }
        }
        return false;
    }
    /**
     * 创建角色和用户关联实体
     */
    private static List<OperationUserRole> buildUserRoleRelation(OperationUser operationUser) {
        List<OperationRole> roleList = operationUser.getRoleList();
        List<OperationUserRole> userRoleList = new ArrayList<>();
        if (roleList != null && !roleList.isEmpty()) {
            for (OperationRole operationRole : roleList) {
                OperationUserRole userRole = new OperationUserRole();
                userRole.setRoleId(operationRole.getId());
                userRole.setUserId(operationUser.getId());
                userRoleList.add(userRole);
            }
        }
        return userRoleList;
    }
}
