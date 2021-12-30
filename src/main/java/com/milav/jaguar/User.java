package com.milav.jaguar;

/**
 *
 * @author milavshah
 */

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * The method returns the user's email.
     * 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * The method sets the user's email.
     * 
     * @return void
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * The method returns the user's password.
     * 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * The method sets the user's password.
     * 
     * @return void
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The method returns the user's first name.
     * 
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The method sets the user's first name.
     * 
     * @return void
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * The method returns the user's last name.
     * 
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The method sets the user's last name.
     * 
     * @return void
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * The method prints the user's data.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.format(
                "User[email=%s, firstName='%s', lastName='%s']",
                email, firstName, lastName);
    }
}
