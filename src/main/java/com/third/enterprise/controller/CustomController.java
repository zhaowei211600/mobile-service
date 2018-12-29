package com.third.enterprise.controller;

import com.third.enterprise.bean.User;
import com.third.enterprise.bean.request.CustomListRequest;
import com.third.enterprise.bean.request.CustomRegisterRequest;
import com.third.enterprise.bean.request.LoginRequest;
import com.third.enterprise.bean.request.RegisterRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IProductService;
import com.third.enterprise.service.IUserService;
import com.third.enterprise.service.security.JwtUser;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/operation/custom")
public class CustomController {

    private static final Logger logger = LoggerFactory.getLogger(CustomController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IProductService productService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/check")
    public UnifiedResult waitCheck(Integer productId){

        List<User> waitCheckUser = userService.findByOrderId(productId);
        if(waitCheckUser != null && waitCheckUser.size() >0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, waitCheckUser);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping("/list")
    public UnifiedResult listCustiom(CustomListRequest request){

        List<User> customList = userService.listAll(request);
        if(customList != null && customList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, customList, Page.toPage(customList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping(value = "/register")
    public UnifiedResult customRegister(@RequestBody CustomRegisterRequest request){
        String redisKey = Constants.RedisKey.CUSTOM_REGISTER + request.getPhone();
        if(!request.getMessageCode().equals(stringRedisTemplate.opsForValue().get(redisKey))){
            return UnifiedResultBuilder.errorResult(Constants.MESSAGE_CODE_ERROR_CODE,
                    Constants.MESSAGE_CODE_ERROR_MESSAGE);
        }
        if(StringUtils.isEmpty(request.getPassword())
                || !request.getPassword().equals(request.getPasswordAgain())){
            return UnifiedResultBuilder.errorResult(Constants.INCONSISTENT_PASSWORD_ERROR_CODE,
                    Constants.INCONSISTENT_PASSWORD_ERROR_MESSAGE);
        }
        //TODO
        return UnifiedResultBuilder.errorResult(Constants.REGISTER_ERROR_CODE,
                Constants.REGISTER_ERROR_MESSAGE);
    }

    @RequestMapping("/confirm")
    public UnifiedResult confirmUser(Integer userId){

        User customer = userService.findByUserId(userId);
        if(customer != null){
            if(Constants.UserState.WAIT_CONFIRM.equals(customer.getStatus())){
                customer.setStatus(Constants.UserState.PASSED);
                customer.setPassTime(new Date());
                if(userService.updateUser(customer)){
                    return UnifiedResultBuilder.defaultSuccessResult();
                }
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @RequestMapping("/stop")
    public UnifiedResult stopUser(Integer userId){

        User customer = userService.findByUserId(userId);
        if(customer != null){
            if(userService.updateStatus(userId, Constants.UserState.STOPED)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }
}
