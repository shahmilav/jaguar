package com.milav.jaguar.user.util;

import com.milav.jaguar.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * Utilities related to users.
 *
 * @author Milav Shah
 */
@Service
public class UserUtil {

    /**
     * Returns a user object with all information filled up.
     *
     * @param firstName user's first name
     * @param lastName  user's last name
     * @param email     user's email
     * @param password  user's email
     * @return User
     */
    public User fillUpUser(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String password) {
        return new User(email, password, firstName, lastName);
    }
}
