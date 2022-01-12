package com.milav.jaguar.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class JaguarUtils {

    public boolean passwordCheck(@NotNull String password1, String password2) {
        return password1.equals(password2);
    }

    public void fillUpInfoOnPage(@NotNull Model model, String firstname, String lastname, String email) {
        model.addAttribute("firstname", firstname);
        model.addAttribute("lastname", lastname);
        model.addAttribute("email", email);
    }
}
