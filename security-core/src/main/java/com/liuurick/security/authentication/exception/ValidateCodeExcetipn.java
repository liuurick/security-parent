package com.liuurick.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author liubin
 */
public class ValidateCodeExcetipn extends AuthenticationException {
    public ValidateCodeExcetipn(String msg, Throwable t) {
        super(msg, t);
    }
    public ValidateCodeExcetipn(String msg) {
        super(msg);
    }
}
