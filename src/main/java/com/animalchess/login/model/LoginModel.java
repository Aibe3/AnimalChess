package com.animalchess.login.model;

import com.animalchess.AnimalChessSession;
import com.animalchess.login.model.ILoginModel;

import org.springframework.beans.factory.annotation.Autowired;

public class LoginModel implements ILoginModel {

    @Autowired
    private AnimalChessSession session;

    @Override
    public boolean isValidUser(String id, String password) {
        // TODO:一旦固定文字で判定して結果を返す
        if("test@test.com".equals(id) && "password".equals(password)){
            this.session.setUserId(id);
            return true;
        }else{
            return false;
        }
    }
}