package com.liuurick.web.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 查询数据库中的用户信息
 * @author liubin
 */
@Component("customUserDetailsService")
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("请求认证的用户名: " + username);

        // 1. 通过请求的用户名去数据库中查询用户信息
        if(!"liubin".equalsIgnoreCase(username)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 假设当前这个用户在数据库当中存储的密码是123456
        String password = passwordEncoder.encode("123456");
        // 2. 查询该用户有哪一些权限

        // 3. 封装用户信息和权限信息
        // username 用户名, password 是数据库中这个用户存储的密码,
        // authorities 是权限资源标识, springsecurity会自动的判断用户是否合法,
        return new User(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
