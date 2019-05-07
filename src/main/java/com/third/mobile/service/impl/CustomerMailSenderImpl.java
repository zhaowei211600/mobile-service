package com.third.mobile.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class CustomerMailSenderImpl extends JavaMailSenderImpl implements JavaMailSender {

    private ArrayList<String> userNameList;

    private ArrayList<String> passwordList;

    private ArrayList<String> hostList;

    private int currentMailId = 0;

    private final MailProperties properties;

    private static final Logger logger = LoggerFactory.getLogger(CustomerMailSenderImpl.class);

    public CustomerMailSenderImpl(MailProperties properties) {
        this.properties = properties;

        // 初始化账号
        if (userNameList == null){
            userNameList = new ArrayList<>();
        }
        String[] userNames = this.properties.getUsername().split(",");
        if (userNames != null) {
            for (String user : userNames) {
                userNameList.add(user);
            }
        }

        // 初始化密码
        if (passwordList == null){
            passwordList = new ArrayList<>();
        }
        String[] passwords = this.properties.getPassword().split(",");
        if (passwords != null) {
            for (String pw : passwords) {
                passwordList.add(pw);
            }
        }

        //初始化host
        if (hostList == null){
            hostList = new ArrayList<>();
        }
        String[] hosts = this.properties.getHost().split(",");
        if (hosts != null) {
            for (String host : hosts) {
                hostList.add(host);
            }
        }
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        setProperties();
        super.send(simpleMessage);
        // 轮询
        currentMailId  = (currentMailId + 1) % userNameList.size();
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        setProperties();
        super.send(mimeMessage);
        // 轮询
        currentMailId  = (currentMailId + 1) % userNameList.size();
    }

    private void setProperties(){
        super.setUsername(getUsername());
        super.setPassword(getPassword());

        // 设置编码和各种参数
        super.setHost(getHost());
        super.setDefaultEncoding(this.properties.getDefaultEncoding().name());
        logger.info(this.properties.getProperties().toString());
        super.setJavaMailProperties(asProperties(this.properties.getProperties()));
        //设置端口
        //logger.info("***设置端口为994***");
        super.setPort(465);
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    @Override
    public String getUsername() {
        String mail = userNameList.get(currentMailId);
        return mail;
    }

    @Override
    public String getPassword() {
        return passwordList.get(currentMailId);
    }

    @Override
    public String getHost() {
        return hostList.get(currentMailId);
    }
}
