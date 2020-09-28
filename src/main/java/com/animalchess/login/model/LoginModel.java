package com.animalchess.login.model;

import com.animalchess.login.model.ILoginModel;

import org.springframework.stereotype.Component;

@Component
public class LoginModel implements ILoginModel {

    @Override
    public boolean isValidUser(String id, String password) {
        // TODO:一旦固定文字で判定して結果を返す
        if("test@test.com".equals(id) && "password".equals(password)){
            return true;
        }else{
            return false;
        }
    }
}