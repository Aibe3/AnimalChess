package com.animalchess.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {
    
    @GetMapping("/menu")
    public String menuGet(Model model) {
        return "menu";
    }
    
    @PostMapping("/menu")
    public String menuPost(Model model) {
        return "menu";
    }
}
