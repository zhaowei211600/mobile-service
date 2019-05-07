package com.third.mobile.service.impl;

import com.third.mobile.bean.SimpleMailParam;
import com.third.mobile.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @Author: Jingjiadong
 * @Description:邮件服务service
 * @Date: 2:46 PM 24/10/2017
 */
@Service
public class MailServiceImpl implements IMailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private CustomerMailSenderImpl mailSender;


    @Override
    public boolean sendHtmlMail(SimpleMailParam simpleMailParam) {
        logger.info("当前发送邮件的账号为：{}", mailSender.getUsername());

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailSender.getUsername());
            mimeMessageHelper.setTo(simpleMailParam.getToAddress());
            mimeMessageHelper.setSubject(simpleMailParam.getSubject());
            mimeMessageHelper.setText(simpleMailParam.getContent());

            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(simpleMailParam.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mimeMessage.setContent(mainPart);

            mailSender.send(mimeMessage);
        }catch (Exception e){
            logger.error("邮件发送异常:{}", e.getMessage(), e.getClass().getSimpleName());
            return false;
        }
        logger.info("邮件发送成功！");
        return true;
    }


}
