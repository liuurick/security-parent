package com.liuurick.security.authentication.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码相关异常类
 * @author liubin
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
