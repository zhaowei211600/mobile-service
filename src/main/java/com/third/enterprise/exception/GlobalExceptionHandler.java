package com.third.enterprise.exception;

import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.ErrorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Iterator;
import java.util.List;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    public Object handleBadCredentialsException(BadCredentialsException exception){
        LOGGER.warn("BadCredentialsException:{}",exception.getMessage());
        return UnifiedResultBuilder.errorResult(Constants.CREDENTIALS_ERROR_CODE, Constants.CREDENTIALS_ERROR_MESSAGE);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Object handleUsernameNotFoundException(UsernameNotFoundException exception){
        LOGGER.warn("UsernameNotFoundException:{}",exception.getMessage());
        return UnifiedResultBuilder.errorResult(Constants.CREDENTIALS_ERROR_CODE,exception.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNotFoundException(NoHandlerFoundException exception){
        LOGGER.warn("[访问的路径不存在]:{}", ErrorUtil.getErrorStackInfo(exception));
        return UnifiedResultBuilder.errorResult(Constants.PATH_NOT_FOUND_ERROR_CODE,
                String.format(Constants.PATH_NOT_FOUND_ERROR_MESSAGE, exception.getRequestURL()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Object handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception){
        LOGGER.warn("[Content-type不支持]:{}", ErrorUtil.getErrorStackInfo(exception));
        return UnifiedResultBuilder.errorResult(Constants.CONTENT_TYPE_NOT_SUPPORT_ERROR_CODE,
                String.format(Constants.CONTENT_TYPE_NOT_SUPPORT_ERROR_MESSAGE, exception.getContentType()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingServletRequestParameterException(Exception exception){
        LOGGER.warn("[缺少请求参数]:{}",ErrorUtil.getErrorStackInfo(exception));
        return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,
                String.format(Constants.PARAMETER_NOT_VALID_ERROR_MESSAGE,exception.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        LOGGER.warn("[请求方式不支持]:{}", ErrorUtil.getErrorStackInfo(exception));
        return UnifiedResultBuilder.errorResult(Constants.METHOD_NOT_SUPPORT_ERROR_CODE,
                String.format(Constants.METHOD_NOT_SUPPORT_ERROR_MESSAGE, exception.getMethod()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleHttpMessageNotReadableException(Exception exception){
        LOGGER.warn("[请求体格式错误]:{}", ErrorUtil.getErrorStackInfo(exception));
        return UnifiedResultBuilder.errorResult(Constants.BODY_NOT_VALID_ERROR_CODE,
                Constants.BODY_NOT_VALID_ERROR_MESSAGE);
    }


    @ExceptionHandler(NullPointerException.class)
    public Object handleNullPointException(Exception exception){
        LOGGER.error("[空指针异常]:{} {}", exception.getMessage(), exception.getClass().getSimpleName());
        LOGGER.warn("[空指针异常]-堆栈信息：{}", ErrorUtil.getErrorStackInfo(exception));
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE, Constants.CALL_SERVICE_ERROR_MESSAGE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(Exception exception){
        MethodArgumentNotValidException e = (MethodArgumentNotValidException)exception;
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        Iterator iterator = errors.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()){
            ObjectError error = (ObjectError) iterator.next();
            stringBuilder.append("[").append(error.getDefaultMessage()).append("] ");
        }
        LOGGER.error("[参数校验异常]:{} {}",exception.getMessage(), exception.getClass().getSimpleName());
        LOGGER.warn("[参数校验异常]-堆栈信息：{}", ErrorUtil.getErrorStackInfo(exception));
        return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE, stringBuilder.toString());
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception exception){
        LOGGER.error("[未知异常]:{} {}",exception.getMessage(), exception.getClass().getSimpleName());
        LOGGER.warn("[未知异常]-堆栈信息：{}", ErrorUtil.getErrorStackInfo(exception));
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE, Constants.CALL_SERVICE_ERROR_MESSAGE);
    }

}


