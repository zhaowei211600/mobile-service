package com.third.enterprise.bean.request;

public class CustomRegisterRequest {

    private String phone;

    private String password;

    private String passwordAgain;

    private String messageCode;

    private String realName;

    private String cardNo;

    private String cardImgFront;

    private String cardImgBack;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardImgFront() {
        return cardImgFront;
    }

    public void setCardImgFront(String cardImgFront) {
        this.cardImgFront = cardImgFront;
    }

    public String getCardImgBack() {
        return cardImgBack;
    }

    public void setCardImgBack(String cardImgBack) {
        this.cardImgBack = cardImgBack;
    }

    @Override
    public String toString() {
        return "CustomRegisterRequest{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", passwordAgain='" + passwordAgain + '\'' +
                ", messageCode='" + messageCode + '\'' +
                ", realName='" + realName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", cardImgFront='" + cardImgFront + '\'' +
                ", cardImgBack='" + cardImgBack + '\'' +
                '}';
    }
}
