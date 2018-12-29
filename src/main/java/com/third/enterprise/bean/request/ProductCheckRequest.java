package com.third.enterprise.bean.request;

public class ProductCheckRequest {

    private Integer productId;

    private String realCost;

    private String checkDesc;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getRealCost() {
        return realCost;
    }

    public void setRealCost(String realCost) {
        this.realCost = realCost;
    }

    public String getCheckDesc() {
        return checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }
}
