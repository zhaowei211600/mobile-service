package com.third.mobile.bean.request;

public class ProductApplyRequest {

    private Integer productId;

    private String deliveryDesc;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getDeliveryDesc() {
        return deliveryDesc;
    }

    public void setDeliveryDesc(String deliveryDesc) {
        this.deliveryDesc = deliveryDesc;
    }

    @Override
    public String toString() {
        return "ProductApplyRequest{" +
                "productId=" + productId +
                ", deliveryDesc='" + deliveryDesc + '\'' +
                '}';
    }
}
