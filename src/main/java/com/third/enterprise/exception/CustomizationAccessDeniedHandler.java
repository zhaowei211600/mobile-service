package com.third.enterprise.exception;

import com.alibaba.fastjson.JSON;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.util.Constants;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        UnifiedResult unifiedResult = new UnifiedResult(false, Constants.ACCESS_DENIED_ERROR_CODE, Constants.ACCESS_DENIED_ERROR_MESSAGE,null);
        httpServletResponse.getWriter().println(JSON.toJSONString(unifiedResult));
        httpServletResponse.getWriter().flush();
    }
}
