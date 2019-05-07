package com.third.mobile.integration;

public interface IMessageService {

    boolean sendCaptcha(String captcha, String phone, String type);
}
