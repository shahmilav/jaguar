package com.milav.jaguar.database.errors;

/**
 * Custom exception for database related errors.
 *
 * @author Jigar Shah
 */
public class DBException extends Exception {

  /**
   * Custom database exception.
   *
   * @param message the message to send.
   * @param cause the error to throw.
   */
  public DBException(String message, Throwable cause) {
    super(message, cause);
  }
}
