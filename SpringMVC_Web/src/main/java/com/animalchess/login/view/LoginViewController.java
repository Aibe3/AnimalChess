package com.animalchess.login.view;

import com.animalchess.login.LoginForm;
import com.animalchess.login.presenter.ILoginPresenter;
import com.animalchess.login.presenter.LoginPresenter;

import lombok.Getter;

import org.springframework.stereotype.Component;

@Component
public class LoginViewController implements ILoginViewController {

    private ILoginPresenter presenter;

    @Getter
    private LoginForm form;

    @Getter
    private String destination;

    private boolean isSuccessLogin;

    public LoginViewController(){
        this.presenter = new LoginPresenter(this);
    }

    public LoginForm getCleanForm(){
        this.form = new LoginForm();
        this.form.setCsrfToken("CSRF Token");
        return this.form;
    }

    public boolean authenticate(String id, String password){
        //画面の再表示に備えてフォームを初期化
        this.form = new LoginForm();
        this.form.setCsrfToken("CSRF Token");
        this.form.setId(id);
        this.form.setPassword(password);

        this.presenter.login(id, password);

        return this.isSuccessLogin;
    }

    @Override
    public void showMenu() {
        this.isSuccessLogin = true;
        this.destination = "menu";
    }

    @Override
    public void showLoginError() {
        this.isSuccessLogin = false;
        this.destination = "login";
    }
}