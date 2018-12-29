package com.third.enterprise.controller;

import com.third.enterprise.bean.OperationMenu;
import com.third.enterprise.bean.OperationRole;
import com.third.enterprise.bean.request.OperationRoleRequest;
import com.third.enterprise.bean.request.RoleListRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IOperationRoleService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.ErrorUtil;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/operation/role")
public class OperationRoleController {

    private static final Logger logger = LoggerFactory.getLogger(OperationRoleController.class);

    @Resource
    private IOperationRoleService operationRoleService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UnifiedResult edit(@Valid OperationRole operationRole,
                              BindingResult bindingResult,
                              String menus) {
        if (bindingResult.hasErrors()) {
            return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,
                    ErrorUtil.getValidatorErrors(bindingResult));
        }

        //权限ID赋值
        if (!StringUtils.isEmpty(menus)) {
            List<OperationMenu> list = new ArrayList<>();
            String[] menuIds = menus.split(",");
            for (String menuId : menuIds) {
                OperationMenu menuEntity = new OperationMenu();
                menuEntity.setId(Integer.parseInt(menuId));
                list.add(menuEntity);
            }
            operationRole.setMenuList(list);
        }
        if (operationRole.getId()== null || operationRole.getId() == 0) {
            if (operationRoleService.checkExist(operationRole.getName())) {
                return UnifiedResultBuilder.errorResult(Constants.ROLE_NAME_EXISTS_ERROR_CODE,
                        Constants.ROLE_NAME_EXISTS_ERROR_MESSAGE);
            }
            if(operationRoleService.save(operationRole)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        } else {
            if(operationRoleService.update(operationRole)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public UnifiedResult delete(OperationRole operationRole) {
        if(operationRole.getId() == null && operationRole.getId() == 0) {
            return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,
                    Constants.PARAMETER_NOT_VALID_ERROR_MESSAGE);
        }
        if(operationRoleService.checkRoleBind(operationRole.getId())) {
            return UnifiedResultBuilder.errorResult(Constants.ROLE_BIND_ERROR_CODE, Constants.ROLE_BIND_ERROR_MESSAGE);
        }
        if(operationRoleService.delete(operationRole.getId())){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE, Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public UnifiedResult list(OperationRoleRequest operationRoleRequest) {

        List<OperationRole> roleList = operationRoleService.listRole(operationRoleRequest);
        if(roleList != null && roleList.size() >0 ){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, roleList, Page.toPage(roleList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/findById")
    public UnifiedResult findById(Integer id) {
        OperationRole operationRole = operationRoleService.findRoleById(id);
        if(operationRole != null){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, operationRole);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/all")
    public UnifiedResult allRoles() {
        List<OperationRole> allRoles = operationRoleService.allRoles();
        if(allRoles != null){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, allRoles);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }
}
