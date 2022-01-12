package com.milav.jaguar.user.util;

import com.milav.jaguar.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserUtil {

    public User fillUpUser(String firstName, String lastName, String email, String password) {
        return new User(email, password, firstName, lastName);
    }
}
