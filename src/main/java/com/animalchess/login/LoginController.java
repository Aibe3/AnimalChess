package com.animalchess.login;

import com.animalchess.AnimalChessSession;
import com.animalchess.login.view.LoginViewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private LoginViewController view;

    @Autowired
    private AnimalChessSession session;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) {

        String page;
        if (view.isValidUser(email, password)) {
            this.session.setUserId(email);
            page = "redirect:menu";
        } else {
            page = "login";
        }
        return page;
    }
}