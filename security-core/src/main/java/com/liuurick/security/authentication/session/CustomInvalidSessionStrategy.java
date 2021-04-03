package com.liuurick.security.authentication.session;

/**
 * @author liubin
 */

import com.liuurick.base.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效后的处理逻辑
 * @author liubin
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        // 将浏览器的sessionid清除，不关闭浏览器cookie不会被删除，一直请求都提示：Session失效
        cancelCookie(request, response);
        Result result = Result.build(
                HttpStatus.UNAUTHORIZED.value(), "登录已超时, 请重新登录");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result.toJsonString());
    }

    /**
     *  参考记住我功能的 AbstractRememberMeServices 代码
      */
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
