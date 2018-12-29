package com.third.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.third.enterprise.bean.OperationMenu;
import com.third.enterprise.dao.OperationMenuMapper;
import com.third.enterprise.service.IOperationMenuService;
import com.third.enterprise.util.MenuTreeUtil;
import com.third.enterprise.util.Tree;
import com.third.enterprise.util.TreeNode;
import com.third.enterprise.util.TreeNodeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("operationService")
public class OperationMenuServiceImpl implements IOperationMenuService{

    private static final Logger logger = LoggerFactory.getLogger(OperationMenuServiceImpl.class);

    @Resource
    private OperationMenuMapper menuMapper;

    @Override
    public List<TreeNode> getAllMenus(Integer roleId) {
        List<OperationMenu> allMenus = menuMapper.getAllMenus(roleId);
        if(!allMenus.isEmpty()){
            return MenuTreeUtil.generateMenuTree(allMenus);
        }
        return null;
    }

    @Override
    public List<Tree> loadMenus(Integer userId) {
        /*String redisKey = MENU_TREE_REDIS_KEY + userId;
        if(redisTemplate.opsForValue().get(redisKey) != null){
            String treeNodes = (String) redisTemplate.opsForValue().get(redisKey);
            return JSON.parseArray(treeNodes, Tree.class);
        }*/
        List<Tree> treeNodes = buildUserMenus(findMenusByUserId(userId));
        //redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(treeNodes), 10, TimeUnit.MINUTES);
        return treeNodes;
    }

    public List<Tree> buildUserMenus(List<OperationMenu> menuEntityList) {
        List treeNodes = (List) CollectionUtils.collect(
                menuEntityList,new Transformer() {
                    @Override
                    public Object transform(Object arg0) {
                        OperationMenu menuEntity = (OperationMenu) arg0;
                        Tree node = new Tree();
                        node.setId(menuEntity.getId());
                        node.setParentId(menuEntity.getParentId());
                        node.setName(menuEntity.getName());
                        node.setLink(menuEntity.getUrl());
                        node.setSpread(false);
                        return node;
                    }
                });
        return TreeNodeUtil.buildNodesTree(treeNodes);
    }

    @Override
    public List<OperationMenu> findMenusByUserId(Integer userId) {
        return menuMapper.findMenusByUserId(userId);
    }

}
