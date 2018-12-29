package com.third.enterprise.bean.response;

public class UnifiedResultBuilder {

    private static final String DEFAULT_SUCCESS_CODE = "200";
    private static final String DEFAULT_SUCCESS_MESSAGE = "处理成功";

    public UnifiedResultBuilder() {
    }

    public static UnifiedResult defaultSuccessResult() {
        return new UnifiedResult(true, "200", "操作成功", (Object)null);
    }

    public static UnifiedResult successResult(String returnMessage) {
        return new UnifiedResult(true, "200", returnMessage, (Object)null);
    }

    public static UnifiedResult successResult(String returnCode, String returnMessage) {
        return new UnifiedResult(true, returnCode, returnMessage, (Object)null);
    }

    public static UnifiedResult successResult(String returnMessage, Object data) {
        return new UnifiedResult(true, "200", returnMessage, data);
    }

    public static UnifiedResult successResult(String returnCode, String returnMessage, Object data) {
        return new UnifiedResult(true, returnCode, returnMessage, data);
    }

    public static UnifiedResult successResult(String returnMessage, Object data, Long total) {
        return new UnifiedResult(true, "200", returnMessage, data, total);
    }

    public static UnifiedResult errorResult(String returnCode, String returnMessage) {
        return new UnifiedResult(false, returnCode, returnMessage, (Object)null);
    }
}
