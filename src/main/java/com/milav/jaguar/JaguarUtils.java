package com.milav.jaguar;

import org.springframework.stereotype.Service;

@Service
public class JaguarUtils {

    public boolean passwordCheck(String password1, String password2) {

        if (password1.equals(password2))
            return true;
        else
            return false;

    }

    public User fillUpUser(String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
