package com.animalchess;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import lombok.Data;

@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AnimalChessSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    public void clear() {
        this.userId = null;
    }
}