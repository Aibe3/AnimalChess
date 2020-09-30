package com.animalchess.login;

import com.animalchess.AnimalChessSession;
import com.animalchess.login.view.LoginViewController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private LoginViewController view;

    @Autowired
    private AnimalChessSession session;

    @GetMapping("/login")
    public ModelAndView getLogin() {
        ModelAndView result = new ModelAndView();
        result.setViewName("login");
        result.addObject("from", this.view.getCleanForm());
        return result;
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password) {

        ModelAndView result = new ModelAndView();
        if (view.authenticate(email, password)) {
            this.session.setUserId(email);
            result.setViewName("redirect:" + this.view.getDestination());
        } else {
            result.setViewName(this.view.getDestination());
            result.addObject("form", this.view.getForm());
            result.addObject("isLoginError", true);
        }
        return result;
    }
}