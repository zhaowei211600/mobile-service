package com.third.mobile.bean.request;

public class ResetPasswordRequest {

    private String phone;

    private String password;

    private String passwordAgain;

    private String messageCode;

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


    @Override
    public String toString() {
        return "CustomRegisterRequest{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", passwordAgain='" + passwordAgain + '\'' +
                ", messageCode='" + messageCode + '\'' +
                '}';
    }
}
