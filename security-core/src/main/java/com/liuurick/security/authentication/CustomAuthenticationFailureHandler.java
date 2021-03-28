package com.liuurick.security.authentication;

import com.liuurick.base.Result;
import com.liuurick.security.properties.LoginResponseType;
import com.liuurick.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理失败认证的
 * @author liubin
 */
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * @param exception 认证失败时抛出异常
     */
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request,
//            HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        // 认证失败响应JSON字符串，
//        Result result = Result.build(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(result.toJsonString());
//    }

    @Autowired
    SecurityProperties securityProperties;

    /**
     * @param exception 认证失败时抛出异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {
            // 认证失败响应JSON字符串，
            Result result = Result.build(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        }else {
            // 重写向回认证页面，注意加上 ?error
            // super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage()+"?error");
            // 获取上一次请求路径
            String referer = request.getHeader("Referer");
            logger.info("referer:" + referer);
            String lastUrl = StringUtils.substringBefore(referer,"?");
            logger.info("上一次请求的路径 ：" + lastUrl);
            super.setDefaultFailureUrl(lastUrl+"?error");
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
