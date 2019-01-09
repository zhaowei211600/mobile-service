package com.third.mobile.bean.response;

public class OrderStatResponse {

    private Integer totalCount;

    private String finishAmount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getFinishAmount() {
        return finishAmount;
    }

    public void setFinishAmount(String finishAmount) {
        this.finishAmount = finishAmount;
    }
}
