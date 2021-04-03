package com.liuurick.security.config;

import com.liuurick.security.authentication.code.ImageCodeValidateFilter;
import com.liuurick.security.authentication.mobile.MobileAuthenticationConfig;
import com.liuurick.security.authentication.mobile.MobileValidateFilter;
import com.liuurick.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * @author liubin
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入session失败策略
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService customUserDetailsService;

    /**
     * 验证码校验过滤器
      */
    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    /**
     * 注入自定义的认证成功处理器
      */
    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*String password = passwordEncoder().encode("123456");
        log.info("加密密码为："+password);
        auth.inMemoryAuthentication()
                .withUser("liubin")
                .password(password)
                .authorities("admin");*/

        //用户信息存储在数据库中
        auth.userDetailsService(customUserDetailsService);
    }

    /**
     * 记住我功能
     */
    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;
    /**
     * 资源权限配置（过滤器链）:
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            //http.httpBasic()
            http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() // 表单登录方式
                .loginPage("/login/page").permitAll()
                .loginProcessingUrl("/login/form")
                .usernameParameter("name")
                .passwordParameter("pwd")
                // 认证成功处理器
                .successHandler(customAuthenticationSuccessHandler)
                // 认证失败处理器
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .authorizeRequests() // 认证请求
                .antMatchers(securityProperties.getAuthentication().getLoginPage(), "/code/image","/mobile/page", "/code/mobile").permitAll()
                .anyRequest().authenticated() // 所有进入应用的HTTP请求都要进行认证
                .and()
                .rememberMe() //记住我
                //保持登陆信息
                .tokenRepository(jdbcTokenRepository())
                //保持登陆时间
                .tokenValiditySeconds(60*60*24*7)
                .and()
                .sessionManagement()
                // session失效后处理逻辑
                .invalidSessionStrategy(invalidSessionStrategy)
                .and()
                .sessionManagement()
                // session失效后处理逻辑
                .invalidSessionStrategy(invalidSessionStrategy)
                // 每个用户在系统中的最大session数
                .maximumSessions(1)
                // 当用户达到最大session数后，则调用此处的实现
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                // 当一个用户达到最大session数，则不允许后面进行登录
                .maxSessionsPreventsLogin(true)
        ;
        //将手机认证添加到过滤器链上
        http.apply(mobileAuthenticationConfig);
    }

    /**
     * 针对静态资源放行
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }


    /**
     * 针对静态资源放行
     */
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
//    }
}
