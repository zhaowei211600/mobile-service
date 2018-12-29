package com.third.enterprise.service.impl;

import com.third.enterprise.bean.OperationMenu;
import com.third.enterprise.bean.OperationRole;
import com.third.enterprise.bean.OperationRoleMenu;
import com.third.enterprise.bean.request.OperationRoleRequest;
import com.third.enterprise.dao.OperationRoleMapper;
import com.third.enterprise.dao.OperationRoleMenuMapper;
import com.third.enterprise.dao.OperationUserRoleMapper;
import com.third.enterprise.service.IOperationRoleService;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("operationRoleService")
public class OperationRoleServiceImpl implements IOperationRoleService{

    private static final Logger logger = LoggerFactory.getLogger(OperationUserServiceImpl.class);

    @Resource
    private OperationRoleMapper roleMapper;

    @Resource
    private OperationRoleMenuMapper roleMenuMapper;

    @Resource
    private OperationUserRoleMapper userRoleMapper;

    @Override
    public boolean checkExist(String roleName) {
        return roleMapper.checkExist(roleName) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean save(OperationRole operationRole) {
        if(roleMapper.insertSelective(operationRole) > 0){
            List<OperationRoleMenu> roleMenuList = buildRoleMenuRelation(operationRole);
            if (roleMenuList != null && !roleMenuList.isEmpty()) {
                if(roleMenuMapper.batchSave(buildRoleMenuRelation(operationRole)) > 0){
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public Boolean update(OperationRole operationRole) {
        if(roleMapper.updateByPrimaryKeySelective(operationRole) > 0){
            roleMenuMapper.deleteByRoleId(operationRole.getId());
            if(roleMenuMapper.batchSave(buildRoleMenuRelation(operationRole)) > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean checkRoleBind(Integer roleId) {
        if(userRoleMapper.checkRoleBind(roleId) > 0){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer roleId) {
        if(roleMapper.deleteByPrimaryKey(roleId) > 0){
            if(roleMenuMapper.deleteByRoleId(roleId) > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<OperationRole> listRole(OperationRoleRequest request) {
        int count = roleMapper.listOperationRoleCount(request);
        List<OperationRole> roleList = roleMapper.listOperationRole(request);
        Page roleListPage = Page.toPage(roleList);
        roleListPage.setTotal(count);
        roleListPage.setPageNum(request.getPageNum());
        roleListPage.setPages(request.getPageSize());
        return roleListPage.getList();
    }

    @Override
    public OperationRole findRoleById(Integer roleId) {
        return roleMapper.findRoleById(roleId);
    }

    @Override
    public List<OperationRole> allRoles() {
        return roleMapper.allRoles();
    }

    /**
     * 创建角色和菜单关联实体
     */
    private static List<OperationRoleMenu> buildRoleMenuRelation(OperationRole operationRole) {
        List<OperationMenu> menuList = operationRole.getMenuList();
        List<OperationRoleMenu> roleMenuEntities = new ArrayList<>();
        if (menuList != null && !menuList.isEmpty()) {
            for (OperationMenu menu : menuList) {
                OperationRoleMenu roleMenu = new OperationRoleMenu();
                roleMenu.setRoleId(operationRole.getId());
                roleMenu.setMenuId(menu.getId());
                roleMenuEntities.add(roleMenu);
            }
        }
        return roleMenuEntities;
    }
}
