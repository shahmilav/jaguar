package com.milav.jaguar.user;

import org.springframework.data.annotation.Id;

/**
 * A user object.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
public class User {

  /** User's email. Considered their id: must be unique. */
  @Id private String email;
  /** The user's password. */
  private String password;
  private String salt;
  /** The user's first name. */
  private String firstName;
  /** The user's last name. */
  private String lastName;

  /** The constructor creates an empty user object. */
  public User() {}

  /**
   * The constructor creates a user object given an email (as an id) and a password.
   *
   * @param email the user id
   * @param password the user password
   */
  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  /**
   * The constructor creates a user object.
   *
   * @param email the user id
   * @param password the password
   * @param firstName the first name
   * @param lastName the last name
   */
  public User(String email, String password, String firstName, String lastName) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public User(String email, String password, String salt, String firstName, String lastName) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salt = salt;
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
   * @param email the user's email
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
   * @param password the user's password.
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
   * @param firstName the user's first name.
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
   * @param lastName the user's last name.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getSalt() {
      return salt;
  }

  /**
   * The method prints the user's data.
   *
   * @return String
   */
  @Override
  public String toString() {
    return String.format(
        "User[email=%s, firstName='%s', lastName='%s']", email, firstName, lastName);
  }
}
