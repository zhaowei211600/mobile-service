package com.third.enterprise.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UnifiedResult<T> {

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String returnCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String returnMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memo1;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memo2;

    public UnifiedResult() {
    }

    public UnifiedResult(boolean success, String returnCode, String returnMessage, T data) {
        this.success = success;
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
    }

    public UnifiedResult(boolean success, String returnCode, String returnMessage, T data, Long total) {
        this.success = success;
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
        this.total = total;
    }

    public UnifiedResult(boolean success, String returnCode, String returnMessage, T data, Long total, String memo1, String memo2) {
        this.success = success;
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
        this.total = total;
        this.memo1 = memo1;
        this.memo2 = memo2;
    }

    public UnifiedResult(String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }

    public UnifiedResult(String returnCode, String returnMessage, T data) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
        this.total = this.total;
    }

    public UnifiedResult(String returnCode, String returnMessage, T data, String memo1, String memo2) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
        this.memo1 = memo1;
        this.memo2 = memo2;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return this.returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getMemo1() {
        return this.memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getMemo2() {
        return this.memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    @Override
    public String toString() {
        return "UnifiedResult{success=" + this.success + ", returnCode='" + this.returnCode + '\'' + ", returnMessage='" + this.returnMessage + '\'' + ", data=" + this.data + '\'' + ", total=" + this.total + '\'' + ", memo1=" + this.memo1 + '\'' + ", memo2=" + this.memo2 + '}';
    }
}
