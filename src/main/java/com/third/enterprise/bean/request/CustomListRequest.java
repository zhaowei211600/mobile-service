package com.third.enterprise.bean.request;

public class CustomListRequest {

    private String phone;

    private String registerTimeStart;

    private String registerTimeEnd;

    private String passTimeStart;

    private String passTimeEnd;

    private String status;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegisterTimeStart() {
        return registerTimeStart;
    }

    public void setRegisterTimeStart(String registerTimeStart) {
        this.registerTimeStart = registerTimeStart;
    }

    public String getRegisterTimeEnd() {
        return registerTimeEnd;
    }

    public void setRegisterTimeEnd(String registerTimeEnd) {
        this.registerTimeEnd = registerTimeEnd;
    }

    public String getpassTimeStart() {
        return passTimeStart;
    }

    public void setpassTimeStart(String passTimeStart) {
        this.passTimeStart = passTimeStart;
    }

    public String getpassTimeEnd() {
        return passTimeEnd;
    }

    public void setpassTimeEnd(String passTimeEnd) {
        this.passTimeEnd = passTimeEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "CustomListRequest{" +
                "phone='" + phone + '\'' +
                ", registerTimeStart='" + registerTimeStart + '\'' +
                ", registerTimeEnd='" + registerTimeEnd + '\'' +
                ", passTimeStart='" + passTimeStart + '\'' +
                ", passTimeEnd='" + passTimeEnd + '\'' +
                ", status='" + status + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
