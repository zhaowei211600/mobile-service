package com.third.enterprise.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorUtil {

    public ErrorUtil() {
    }

    public static String getErrorStackInfo(Throwable e) {
        StringWriter errorsWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(errorsWriter));
        return errorsWriter.toString();
    }

    public static String getValidatorErrors(BindingResult bindingResult) {
        StringBuffer stringBuffer = new StringBuffer();
        if (bindingResult != null && bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuffer.append(fieldError.getField() + ":" + fieldError.getDefaultMessage() + ";");
            }
        }
        return stringBuffer.toString();
    }
}
