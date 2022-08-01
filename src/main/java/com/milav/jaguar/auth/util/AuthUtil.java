package com.milav.jaguar.auth.util;

import com.milav.jaguar.auth.errors.PasswordGenException;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jigar Shah
 */
public class AuthUtil {

    /**
     * Pattern for a valid email address. username@domain.com
     */
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    /**
     * Number of iterations we do the hash.
     *
     * <p>The longer the stronger (against brute-force attacks), however if it is too high, the
     * process becomes very slow and an unpleasant experience as this happens <strong>every time
     * passwords are entered.</strong>
     */
    private static final int ITERATION_NUMBER = 1000;

    /**
     * The method returns HashMap that contains secure password and salt that is used to generate
     * password. Salt should be stored along with secure password in database so when the user enters
     * password during authentication, it can be hashed again and compared with this generated secured
     * password.
     *
     * <p>Use this during signup time.
     *
     * @param password the password to hash
     * @return HashMap
     * @throws PasswordGenException error generating the password.
     */
    public static HashMap<String, String> createSecurePasswordAndSalt(String password)
            throws PasswordGenException {

        HashMap<String, String> map = new HashMap<>();
        byte[] bSalt = createSecureSalt();
        String salt = byteToBase64(bSalt);

        String hashedPassword = createSecurePasswordGivenSalt(password, salt);

        map.put("password", hashedPassword);
        map.put("salt", salt);

        return map;
    }

    private static byte[] createSecureSalt() throws PasswordGenException {
        byte[] bSalt = new byte[8];
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // Salt generation 64 bits long
            random.nextBytes(bSalt);
        } catch (NoSuchAlgorithmException ex) {
            throw new PasswordGenException(ex.getLocalizedMessage(), ex.fillInStackTrace());
        }
        return bSalt;
    }

    /**
     * This method will return a hashed password when you pass plain text password and salt. You then
     * compare the hashed password generated from this method with the one stored in the database. If
     * both passwords match, then you should allow the user to login.
     *
     * <p>It runs 1000 iterations of hash. Use this during login time.
     *
     * @param password String The password to encrypt
     * @param salt     byte[] The salt
     * @return byte[] The digested password
     * @throws PasswordGenException If the algorithm doesn't exist
     */
    public static String createSecurePasswordGivenSalt(@NotNull String password, @NotNull String salt)
            throws PasswordGenException {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(salt.getBytes());
            byte[] input = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            for (int i = 0; i < ITERATION_NUMBER; i++) {
                digest.reset();
                input = digest.digest(input);
            }

            return byteToBase64(input);

        } catch (NoSuchAlgorithmException ex) {
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

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
