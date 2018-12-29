package com.third.mobile.controller;

import com.third.mobile.bean.User;
import com.third.mobile.bean.request.CustomListRequest;
import com.third.mobile.bean.request.CustomRegisterRequest;
import com.third.mobile.bean.request.LoginRequest;
import com.third.mobile.bean.response.UnifiedResult;
import com.third.mobile.bean.response.UnifiedResultBuilder;
import com.third.mobile.service.IProductService;
import com.third.mobile.service.IUserService;
import com.third.mobile.service.security.user.JwtUser;
import com.third.mobile.service.security.user.UserHelper;
import com.third.mobile.service.security.user.UserToken;
import com.third.mobile.util.Constants;
import com.third.mobile.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IProductService productService;

    @Autowired
    private UserToken userToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserHelper jwtHelper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String LOGIN_REDIS_KEY = "USER_LOGIN_TIME:";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        stringRedisTemplate.opsForValue().set(loginKey, simpleDateFormat.format(new Date()));
        //返回登录信息
        Map<String,Object> paramMap = new HashMap<>(2);
        paramMap.put(userToken.getHeader(),token);
        paramMap.put("phone", loginRequest.getUsername());
        return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, paramMap);
    }

    @PostMapping(value = "/verification")
    public UnifiedResult userVerification(String phone){
        User user = userService.findByPhone(phone);
        //用户存在且非待激活状态
        if(user != null && !Constants.UserState.WAIT_ACTIVATE.equals(user.getStatus())){
            return UnifiedResultBuilder.errorResult(Constants.ACCOUNT_EXISTS_ERROR_CODE,
                    Constants.ACCOUNT_EXISTS_ERROR_MESSAGE);
        }
        //生成激活码
        String code = "123456";
        String redisKey = Constants.RedisKey.USER_REGISTER + phone;
        if(stringRedisTemplate.hasKey(redisKey)){
            return UnifiedResultBuilder.errorResult(Constants.MESSAGE_CODE_WAIT_ERROR_CODE,
                    Constants.MESSAGE_CODE_WAIT_ERROR_MESSAGE);
        }else{
            stringRedisTemplate.opsForValue().set(redisKey, code, 5, TimeUnit.MINUTES);
            return UnifiedResultBuilder.defaultSuccessResult();
        }

    }

    @PostMapping(value = "/register")
    public UnifiedResult userRegister(@RequestBody CustomRegisterRequest request){
        String redisKey = Constants.RedisKey.USER_REGISTER + request.getPhone();
        if(!request.getMessageCode().equals(stringRedisTemplate.opsForValue().get(redisKey))){
            return UnifiedResultBuilder.errorResult(Constants.MESSAGE_CODE_ERROR_CODE,
                    Constants.MESSAGE_CODE_ERROR_MESSAGE);
        }
        if(StringUtils.isEmpty(request.getPassword())
                || !request.getPassword().equals(request.getPasswordAgain())){
            return UnifiedResultBuilder.errorResult(Constants.INCONSISTENT_PASSWORD_ERROR_CODE,
                    Constants.INCONSISTENT_PASSWORD_ERROR_MESSAGE);
        }
        User existUser = userService.findByPhone(request.getPhone());
        if(existUser != null && !Constants.UserState.WAIT_ACTIVATE.equals(existUser.getStatus())){
            return UnifiedResultBuilder.errorResult(Constants.ACCOUNT_EXISTS_ERROR_CODE,
                    Constants.ACCOUNT_EXISTS_ERROR_MESSAGE);
        }else if(existUser == null){
            existUser = new User();
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = request.getPassword();
        String encodePassword = encoder.encode(rawPassword);
        existUser.setPhone(request.getPhone());
        existUser.setPassword(encodePassword);
        existUser.setRealName(request.getRealName());
        existUser.setCardNo(request.getCardNo());
        existUser.setCardImgBack(request.getCardImgBack());
        existUser.setCardImgFront(request.getCardImgFront());
        existUser.setStatus(Constants.UserState.WAIT_CONFIRM);
        existUser.setRegisterTime(new Date());

        if(userService.saveUser(existUser)){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.REGISTER_ERROR_CODE,
                Constants.REGISTER_ERROR_MESSAGE);
    }

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
