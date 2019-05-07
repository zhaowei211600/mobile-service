package com.third.mobile.service;


import com.third.mobile.bean.SimpleMailParam;

public interface IMailService {

    boolean sendHtmlMail(SimpleMailParam simpleMailParam);

}
