package com.third.enterprise.util;

import com.third.enterprise.bean.OperationMenu;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import java.util.List;

public class MenuTreeUtil {

    /*public static List<Tree> buildUserMenus(List<OperationMenu> menuEntityList) {
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
    }*/

    public static List<TreeNode> generateMenuTree(List<OperationMenu> menuList) {
        List treeNodes = (List) CollectionUtils.collect(
                menuList,new Transformer() {
                    @Override
                    public Object transform(Object arg0) {
                        OperationMenu menuEntity = (OperationMenu) arg0;
                        TreeNode node = new TreeNode();
                        node.setId(menuEntity.getId());
                        node.setParentId(menuEntity.getParentId());
                        node.setText(menuEntity.getName());
                        node.setHref(menuEntity.getUrl());
                        if(menuEntity.getChecked()) {
                            node.setState();
                        }
                        return node;
                    }
                });
        return TreeNodeUtil.buildNodeTree(treeNodes);
    }
}
