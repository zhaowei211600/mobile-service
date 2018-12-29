package com.third.enterprise.controller;

import com.third.enterprise.bean.OperationRole;
import com.third.enterprise.bean.OperationUser;
import com.third.enterprise.bean.request.LoginRequest;
import com.third.enterprise.bean.request.OperationUserListRequest;
import com.third.enterprise.bean.request.RegisterRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IOperationUserService;
import com.third.enterprise.service.security.JwtUser;
import com.third.enterprise.service.security.OperationUserHelper;
import com.third.enterprise.service.security.OperationToken;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.ErrorUtil;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/operation/user")
public class OperationUserController {

    private static final Logger logger = LoggerFactory.getLogger(OperationUserController.class);

    @Autowired
    private OperationToken operationToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OperationUserHelper jwtHelper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private IOperationUserService operationUserService;

    private static final String LOGIN_REDIS_KEY = "OPERATION_USER_LOGIN_TIME:";

    @PostMapping(value = "/login")
    public UnifiedResult createAuthenticationToken(@RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtHelper.generateToken(userDetails);
        //记录登录时间
        String loginKey = LOGIN_REDIS_KEY + ((JwtUser)userDetails).getId();
        redisTemplate.opsForValue().set(loginKey, new Date());
        //返回登录信息
        Map<String,Object> paramMap = new HashMap<>(2);
        paramMap.put(operationToken.getHeader(),token);
        paramMap.put("userName", loginRequest.getUsername());
        return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, paramMap);
    }

    @PostMapping(value = "/register")
    public UnifiedResult userRegister(@RequestBody RegisterRequest registerRequest){
        //ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        //String key = Constants.RedisKey.OPERATION_REGISTER + registerRequest.getAccount();
        com.third.enterprise.bean.OperationUser auditingUserByNumber = operationUserService.findUserByAccount(registerRequest.getAccount());
        if(auditingUserByNumber != null){
            //unifiedResult = new UnifiedResult(false,ReturnConsts.DUPLICATE_PHONE_ERROR_CODE,ReturnConsts.DUPLICATE_PHONE_ERROR_MESSAGE,null);
            return UnifiedResultBuilder.errorResult(Constants.ACCOUNT_EXISTS_ERROR_CODE, Constants.ACCOUNT_EXISTS_ERROR_MESSAGE);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = registerRequest.getPassword();
        String encodePassword = encoder.encode(rawPassword);
        com.third.enterprise.bean.OperationUser operationUser = new com.third.enterprise.bean.OperationUser();
        operationUser.setAccount(registerRequest.getAccount());
        operationUser.setPassword(encodePassword);
        operationUser.setRealName(registerRequest.getRealName());
        operationUser.setContactPhone(registerRequest.getContactPhone());

        //保存角色和用户信息
        if(operationUserService.saveOperationUserAndRole(operationUser)){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.REGISTER_ERROR_CODE,
                Constants.REGISTER_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UnifiedResult edit(@Valid OperationUser operationUser,
                              BindingResult bindingResult,
                              String roleIds) {
        if (bindingResult.hasErrors()) {
            return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,
                    Constants.PARAMETER_NOT_VALID_ERROR_MESSAGE);
        }
        if (roleIds != null && !"".equals(roleIds)) {
            String[] roleIdArr = roleIds.split(",");
            List<OperationRole> operationRoleList = new ArrayList<>();
            for (String roleId : roleIdArr) {
                OperationRole operationRole = new OperationRole();
                operationRole.setId(Integer.parseInt(roleId));
                operationRoleList.add(operationRole);
            }
            operationUser.setRoleList(operationRoleList);
        }
        if (!StringUtils.isEmpty(operationUser.getPassword())) {
            operationUser.setPassword(new BCryptPasswordEncoder().encode(operationUser.getPassword()));
        }
        if (operationUser.getId() == null || operationUser.getId() == 0) {
            com.third.enterprise.bean.OperationUser operationUserByAccount = operationUserService.findUserByAccount(operationUser.getAccount());
            if(operationUserByAccount != null){
                return UnifiedResultBuilder.errorResult(Constants.ACCOUNT_EXISTS_ERROR_CODE, Constants.ACCOUNT_EXISTS_ERROR_MESSAGE);
            }
            if(operationUserService.saveOperationUserAndRole(operationUser)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        } else {
            if(operationUserService.updateOperationUserAndRole(operationUser)) {
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.REGISTER_ERROR_CODE,
                Constants.REGISTER_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public UnifiedResult list(OperationUserListRequest request) {
        List<com.third.enterprise.bean.OperationUser> operationUserList = operationUserService.listOperationUser(request);
        if(operationUserList != null && operationUserList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,operationUserList , Page.toPage(operationUserList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public UnifiedResult logout(HttpServletRequest request, @RequestAttribute("userId") Integer userId) {
        String token = request.getHeader(operationToken.getHeader());
        final String authToken = token.substring(operationToken.getTokenHead().length());
        Date expirationDateFromToken = jwtHelper.getExpirationDateFromToken(authToken);
        Long expirationMillis = expirationDateFromToken.getTime() - System.currentTimeMillis();
        try {
            redisTemplate.opsForValue().set(authToken, "isExpired", expirationMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("系统退出发生异常:{}", ErrorUtil.getErrorStackInfo(e));
            return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE, Constants.CALL_SERVICE_ERROR_MESSAGE);
        }
        return UnifiedResultBuilder.defaultSuccessResult();
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public UnifiedResult findById(Integer userId) {

        OperationUser operationUser = operationUserService.findUserById(userId);
        if(operationUser != null){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, operationUser);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }
}
