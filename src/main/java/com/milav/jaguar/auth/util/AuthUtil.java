package com.milav.jaguar.auth.util;

import com.milav.jaguar.auth.errors.PasswordGenException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author jigarshah */
public class AuthUtil {

  private static final int ITERATION_NUMBER = 1000;

  /**
   * The method returns HashMap that contains secure password and salt that is used to generate
   * password. Salt should be stored along with secure password in database so when the user enters
   * password during authentication, it can be hashed again and compared with this generated secured
   * password.
   *
   * @param password the password to hash
   * @return HashMap
   * @throws PasswordGenException error generating the password.
   */
  public static HashMap<String, String> createSecurePassword(String password)
      throws PasswordGenException {
    HashMap<String, String> map = new HashMap<>();

    byte[] bSalt = createSecureSalt();
    // Digest computation
    byte[] bDigest = getHash(password, bSalt);
    String sDigest = byteToBase64(bDigest);
    String sSalt = byteToBase64(bSalt);

    map.put("password", sDigest);
    map.put("salt", sSalt);

    return map;
  }

  public static byte[] createSecureSalt() throws PasswordGenException {
    byte[] bSalt = new byte[8];
    try {
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
      // Salt generation 64 bits long
      random.nextBytes(bSalt);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(AuthUtil.class.getName()).log(Level.SEVERE, null, ex);
      throw new PasswordGenException(ex.getLocalizedMessage(), ex.fillInStackTrace());
    }
    return bSalt;
  }

  /**
   * From a password and a salt, returns the corresponding digest. It runs 1000 iterations of hash.
   *
   * @param password String The password to encrypt
   * @param salt byte[] The salt
   * @return byte[] The digested password
   * @throws PasswordGenException If the algorithm doesn't exist
   */
  public static byte[] getHash(String password, byte[] salt) throws PasswordGenException {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      digest.reset();
      digest.update(salt);
      byte[] input = digest.digest(password.getBytes(StandardCharsets.UTF_8));
      for (int i = 0; i < ITERATION_NUMBER; i++) {
        digest.reset();
        input = digest.digest(input);
      }
      return input;
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(AuthUtil.class.getName()).log(Level.SEVERE, null, ex);
      throw new PasswordGenException(ex.getLocalizedMessage(), ex.fillInStackTrace());
    }
  }

  /**
   * From a base 64 representation, returns the corresponding byte[]
   *
   * @param data String The base64 representation
   * @return byte[]
   */
  public static byte[] base64ToByte(String data) {
    return Base64.getDecoder().decode(data);
  }

  /**
   * From a byte[] returns a base 64 representation
   *
   * @param data byte[]
   * @return String
   */
  public static String byteToBase64(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }

  public static String getHashedPassword(String password) throws PasswordGenException {

    return createSecurePassword(password).get("password");
  }

  public static void main(String[] args) throws PasswordGenException {

    HashMap<String, String> map = createSecurePassword("milav");
    String salt = map.get("salt");
    String password = map.get("password");
    System.out.println(password);

    //        byte[] hash = getHash("milav", salt.getBytes());
    //        System.out.println(byteToBase64(hash));
  }
}
