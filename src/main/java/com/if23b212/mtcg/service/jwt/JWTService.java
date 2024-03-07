package com.if23b212.mtcg.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Creates a token to access data from server and perform actions
 */
public class JWTService {


    private static final String SECRET_KEY = "MCgCIQDZZke0DZt/zvF618jjGo7kcdKapnDDj9SegORrEYVCBwIDAQAB\n";
    private static final long EXPIRATION_TIME = 1000*60*60*24;

    /**
     * Generates a new JWT Token using the secret key and defining expiration date
     * @param username is used as the subject
     * @return a new token
     */
    public static String generateToken(String username) {
     try {
         if(username != null) {
             return username+"-mtcgToken";
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
      return null;
    }
}
