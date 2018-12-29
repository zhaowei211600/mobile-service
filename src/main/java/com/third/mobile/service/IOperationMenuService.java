package com.third.mobile.service;

import com.third.mobile.bean.OperationMenu;
import com.third.mobile.util.Tree;
import com.third.mobile.util.TreeNode;

import java.util.List;

public interface IOperationMenuService {

    List<TreeNode> getAllMenus(Integer roleId);

    List<Tree> loadMenus(Integer userId);

    List<OperationMenu> findMenusByUserId(Integer userId);

}
