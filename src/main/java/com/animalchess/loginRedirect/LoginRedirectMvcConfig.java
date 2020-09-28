package com.animalchess.loginRedirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;

import com.animalchess.loginRedirect.LoginRedirectInterceptor;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class LoginRedirectMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginRedirectInterceptor loginRedirector;

    @Override
    public void addInterceptors(InterceptorRegistry registory){
        registory.addInterceptor(loginRedirector)
                    .addPathPatterns("/**")
                    .excludePathPatterns("/login");
    }
}