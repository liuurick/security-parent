package com.liuurick.security.properties;

import lombok.Data;

/**
 * @author liubin
 */
@Data
public class AuthenticationProperties {

    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};


    /**
     * 登录成功后响应 JSON , 还是重定向
     * 如果application.yml 中没有配置，则取此初始值 REDIRECT
    */
    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    public LoginResponseType getLoginType() {
        return loginType;
    }
    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }

    /**
     * 获取图形验证码 url
     */
    private String imageCodeUrl = "/code/image";
    /**
     * 发送手机验证码 url
     */
    private String mobileCodeUrl = "/code/mobile";
    /**
     * 前往手机登录页面地址
     */
    private String mobilePage = "/mobile/page";
    /**
     * 记住我有效时长
     */
    private Integer tokenValiditySeconds = 60*60*24*7;

}
