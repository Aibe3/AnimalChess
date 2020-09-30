package com.animalchess.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    private String id;

    private String password;

    private String csrfToken;
}
