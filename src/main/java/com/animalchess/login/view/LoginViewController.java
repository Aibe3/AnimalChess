package com.animalchess.login.view;

import com.animalchess.login.presenter.ILoginPresenter;
import com.animalchess.login.presenter.LoginPresenter;

import org.springframework.stereotype.Component;

@Component
public class LoginViewController implements ILoginViewController {

    private ILoginPresenter presenter;

    public LoginViewController(){
        this.presenter = new LoginPresenter(this);
    }

    @Override
    public boolean isValidUser(String id, String password){
        return this.presenter.isValidUser(id, password);
    }
}