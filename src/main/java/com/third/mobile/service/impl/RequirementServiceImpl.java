package com.third.mobile.service.impl;

import com.third.mobile.bean.Requirement;
import com.third.mobile.bean.SimpleMailParam;
import com.third.mobile.dao.RequirementMapper;
import com.third.mobile.service.IMailService;
import com.third.mobile.service.IRequirementService;
import com.third.mobile.util.FreemarkerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RequirementServiceImpl implements IRequirementService {

    private static final Logger logger = LoggerFactory.getLogger(RequirementServiceImpl.class);

    @Autowired
    private RequirementMapper requirementMapper;

    @Autowired
    private IMailService mailService;

    @Value("${admin.email}")
    private String adminEmail;

    @Override
    public boolean saveRequire(Requirement requirement) {

        if(requirementMapper.insertSelective(requirement) > 0){
            return true;
        }
        return false;
    }

    @Async
    @Override
    public void sendEmail(Requirement requirement) {
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("require", requirement);

        SimpleMailParam simpleMailParam = new SimpleMailParam();
        simpleMailParam.setSubject("我要找帮手客户需求");
        String content = FreemarkerUtil.fillContent("message.html",paraMap);
        simpleMailParam.setContent(content);
        simpleMailParam.setToAddress(adminEmail);
        mailService.sendHtmlMail(simpleMailParam);
    }
}
