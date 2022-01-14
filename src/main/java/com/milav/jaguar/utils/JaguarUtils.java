package com.milav.jaguar.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class JaguarUtils {

    /**
     * Checks if two passwords are equal.
     *
     * @param password1 a password
     * @param password2 another password
     * @return boolean
     */
    public boolean passwordCheck(@NotNull String password1, String password2) {
        return password1.equals(password2);
    }

    /**
     * Fills up the information on the page.
     *
     * @param model     model
     * @param firstname first name of the user
     * @param lastname  last name of the user
     * @param email     user's email
     */
    public void fillUpInfoOnPage(@NotNull Model model, String firstname, String lastname, String email) {
        model.addAttribute("firstname", firstname);
        model.addAttribute("lastname", lastname);
        model.addAttribute("email", email);
    }
}
