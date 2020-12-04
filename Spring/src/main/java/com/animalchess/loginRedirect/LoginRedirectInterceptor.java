package com.animalchess.loginRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.animalchess.AnimalChessSession;

@Component
public class LoginRedirectInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AnimalChessSession session;

    /**
     * user-id がない＝＞ログインしていない
     * とみなしログイン画面にリダイレクトする
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) throws Exception {

        if(StringUtils.isEmpty(this.session.getUserId())){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}