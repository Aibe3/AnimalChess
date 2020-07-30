package com.animalchess.login.presenter;

import com.animalchess.login.model.ILoginModel;
import com.animalchess.login.model.LoginModel;
import com.animalchess.login.presenter.ILoginPresenter;
import com.animalchess.login.view.ILoginViewController;

public class LoginPresenter implements ILoginPresenter {

    private ILoginViewController view;

    private ILoginModel model;

    public LoginPresenter(ILoginViewController view){
        this.view = view;
        this.model = new LoginModel();
    }

    @Override
    public boolean isValidUser(String id, String password){
        return model.isValidUser(id, password);
    }
}