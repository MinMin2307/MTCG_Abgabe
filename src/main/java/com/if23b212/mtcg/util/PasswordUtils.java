package com.if23b212.mtcg.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    /**
     * Hashes the given String with BCrypt
     *
     * @param plainPassword the non hashed password(plain)
     * @return hashed password
     */
    public static String hashPassword(String plainPassword) {
        if(CommonUtils.checkString(plainPassword)) {
            return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        }
        return null;
    }

    /**
     * Checks if the provided passwords matches (first argument is non hashed)
     *
     * @param plainPassword the plain text password
     * @param hashedPassword the hashed password
     * @return true if they match, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}