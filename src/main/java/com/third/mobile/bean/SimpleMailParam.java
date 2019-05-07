package com.third.mobile.bean;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class SimpleMailParam implements Serializable {

    @NotBlank(message = "toAddress 不能为空")
    private String toAddress;

    @NotBlank(message = "subject 不能为空")
    private String subject;

    @NotBlank(message = "content 不能为空")
    private String content;

    /**
     * 0  普通邮件
     * 1  带附件邮件
     */
    @NotBlank(message = "type 不能为空")
    private String type = "0";


    public SimpleMailParam() {
    }

    public SimpleMailParam(String toAddress, String subject, String content, String type) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
        this.type = type;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SimpleMailParam{" +
                "toAddress='" + toAddress + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
