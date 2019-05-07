package com.third.mobile.integration.impl;

import com.third.mobile.integration.IMessageService;
import com.third.mobile.util.HttpClientUtil;
import com.third.mobile.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MessageServiceImpl implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    private static String messageServerUrl = "https://api.capcplus.com/requestCaptchaClient.do";

    private static String smsTemplateId = "2e8efa02-0309-4724-8b58-6b37b386fc0f";

    //private static String utilTemplateId = "9475a3cf-ee45-4841-9a33-0df46820f363";

    private static String ywId = "dfac4c49-0556-4789-98b5-92ba174afe7c_captcha";

    private static String ywPassword = "16433134";

    @Override
    public boolean sendCaptcha(String captcha,
                               String phone,
                               String type) {

        String authz = MD5Util.md5(ywId+ywPassword);
        String smsParam = "{\"captcha\":\""+captcha+"\"}";
        String msgId = UUID.randomUUID().toString();
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        if("2".equals(type)){
            //params.put("smsTemplateId", utilTemplateId);
        }else{
            params.put("smsTemplateId", smsTemplateId);
        }
        params.put("ywId", ywId);
        params.put("authz",authz);
        params.put("smsParam",smsParam);
        params.put("msgId","");


        String result = HttpClientUtil.doPostSSL(messageServerUrl, params,"utf-8");

        logger.info("[{}]短信验证码[{}]发送结果:{}", phone, captcha, result);
        if(result.startsWith("0")){
            return true;
        }
        return false;
    }
}
