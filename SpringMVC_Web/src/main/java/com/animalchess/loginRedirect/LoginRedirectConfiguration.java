package com.animalchess.loginRedirect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.animalchess.loginRedirect.LoginRedirectInterceptor;

@Configuration
public class LoginRedirectConfiguration {

    @Bean
    public HandlerInterceptorAdapter loginRedirector() throws Exception {
        return new LoginRedirectInterceptor();
    }
}