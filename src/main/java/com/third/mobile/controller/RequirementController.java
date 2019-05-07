package com.third.mobile.controller;


import com.third.mobile.bean.Requirement;
import com.third.mobile.bean.User;
import com.third.mobile.bean.request.RequireCommitRequest;
import com.third.mobile.bean.response.UnifiedResult;
import com.third.mobile.bean.response.UnifiedResultBuilder;
import com.third.mobile.service.IRequirementService;
import com.third.mobile.service.IUserService;
import com.third.mobile.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/require")
public class RequirementController {

    private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IRequirementService requirementService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/commit")
    public UnifiedResult commitRequire(@RequestBody RequireCommitRequest request,
                                       @RequestAttribute("username")String phone){

        User user = userService.findByPhone(phone);
        if(user != null){
            if(StringUtils.isEmpty(request.getImageCode())){
                return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,
                        Constants.PARAMETER_NOT_VALID_ERROR_MESSAGE);
            }

            String imageCode = request.getImageCode().toUpperCase();
            String imageCodeRedisKey = Constants.IMAGE_CODE_PREFIX + imageCode;
            String messageCodeRedisKey = Constants.MESSAGE_CODE_PREFIX + request.getContactPhone();
            if(!imageCode.equals(redisTemplate.opsForValue().get(imageCodeRedisKey))){
                return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,"图形验证码错误");
            }

            if(!request.getMessageCode().equals(redisTemplate.opsForValue().get(messageCodeRedisKey))){
                return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,"短信验证码错误");
            }

            Requirement requirement = new Requirement();
            requirement.setUserId(user.getId());
            requirement.setBudget(request.getBudget());
            requirement.setContactPhone(request.getContactPhone());
            requirement.setDesc(request.getDesc());
            requirement.setTitle(request.getTitle());

            if(requirementService.saveRequire(requirement)){
                // 发送邮件
                requirementService.sendEmail(requirement);
                return UnifiedResultBuilder.defaultSuccessResult();
            }

        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

}
