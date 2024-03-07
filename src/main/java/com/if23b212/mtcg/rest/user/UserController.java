package com.if23b212.mtcg.rest.user;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.if23b212.mtcg.exception.user.UserExceptionHelper;
import com.if23b212.mtcg.model.user.User;
import com.if23b212.mtcg.model.user.UserCredentials;
import com.if23b212.mtcg.model.user.UserUpdate;
import com.if23b212.mtcg.rest.HTTPMethod;
import com.if23b212.mtcg.rest.HTTPStatusCode;
import com.if23b212.mtcg.service.user.UserService;
import com.if23b212.mtcg.util.HttpUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserController {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static UserService service = new UserService();

    private final static String REGISTERED_RESPONSE_MESSAGE = "User successfully created";
    private final static String LOGGED_IN_RESPONSE_MESSAGE = "User login successful";
    private final static String GET_USER_RESPONSE_MESSAGE = "User fetch successful";
    private final static String UPDATE_USER_RESPONSE_MESSAGE = "User update successful";

    public static class UserHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if(exchange != null && HTTPMethod.checkMethod(HTTPMethod.POST, exchange.getRequestMethod())) {
            	register(exchange);
            } else if(exchange != null && HTTPMethod.checkMethod(HTTPMethod.GET, exchange.getRequestMethod())) {
            	get(exchange);
            } else if(exchange != null && HTTPMethod.checkMethod(HTTPMethod.PUT, exchange.getRequestMethod())) {
            	update(exchange);
            } else {
                HttpUtils.sendResponse(exchange,HTTPStatusCode.METHOD_NOT_ALLOWED.name(),HTTPStatusCode.METHOD_NOT_ALLOWED.getCode());
            }
        }
    }
    
    private static void register(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        try {
            UserCredentials userCredentials = objectMapper.readValue(is, UserCredentials.class);

            service.saveUser(userCredentials);
            HttpUtils.sendResponse(exchange,REGISTERED_RESPONSE_MESSAGE,HTTPStatusCode.CREATED.getCode());

        }catch (UserExceptionHelper.AlreadyRegisteredException e) {
            e.printStackTrace();
            HttpUtils.sendResponse(exchange,e.getMessage(),HTTPStatusCode.CONFLICT.getCode());
        }
    }
    
    private static void update(HttpExchange exchange) throws IOException {
    	 String path = exchange.getRequestURI().getPath();
         String[] pathParts = path.split("/");
         String username = pathParts[pathParts.length - 1];
         String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

         if (authHeader != null && authHeader.startsWith("Bearer ")) {
             String token = authHeader.substring(7);
             if (!token.equals(username + "-mtcgToken")) {
                 HttpUtils.sendResponse(exchange, "Unauthorized", HTTPStatusCode.FORBIDDEN.getCode());
                 return;
             }

             try {
            	 InputStream requestBody = exchange.getRequestBody();
            	 UserUpdate userData = objectMapper.readValue(requestBody, UserUpdate.class);
                 service.updateUser(username, userData);
                 String jsonResponse = "{\"description\":\""+UPDATE_USER_RESPONSE_MESSAGE+"\"}";
                 HttpUtils.sendResponse(exchange, jsonResponse, HTTPStatusCode.OK.getCode());
             } catch (Exception e) {
                 HttpUtils.sendResponse(exchange, "Error: " + e.getMessage(), HTTPStatusCode.INTERNAL_SERVER_ERROR.getCode());
             }
         } else {
             HttpUtils.sendResponse(exchange, "Unauthorized", HTTPStatusCode.FORBIDDEN.getCode());
         }
    }
    
    private static void get(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        String username = pathParts[pathParts.length - 1]; // Assumes the last part of the path is the username
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Basic token validation (just checks if it's the user's token)
            if (!token.equals(username + "-mtcgToken")) {
                HttpUtils.sendResponse(exchange, "Unauthorized", HTTPStatusCode.FORBIDDEN.getCode());
                return;
            }

            try {
                User user = service.getUser(username);
                String jsonResponse = "{\"description\":\""+GET_USER_RESPONSE_MESSAGE+"\", \"user\":\"" + user.toString() + "\"}";
                HttpUtils.sendResponse(exchange, jsonResponse, HTTPStatusCode.OK.getCode());            } catch (Exception e) {
            }
        } else {
            HttpUtils.sendResponse(exchange, "Unauthorized", HTTPStatusCode.FORBIDDEN.getCode());
        }
    }

    

    public static class UserLogin implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if(exchange != null && HTTPMethod.checkMethod(HTTPMethod.POST, exchange.getRequestMethod())) {

                InputStream is = exchange.getRequestBody();
                try {
                    UserCredentials userCredentials = objectMapper.readValue(is, UserCredentials.class);
                    String token = service.loginUser(userCredentials);
                    // Create a JSON response with the token
                    String jsonResponse = "{\"description\":\""+LOGGED_IN_RESPONSE_MESSAGE+"\", \"token\":\"" + token + "\"}";
                    HttpUtils.sendResponse(exchange, jsonResponse, HTTPStatusCode.OK.getCode());

                }catch (UserExceptionHelper.InvalidCredentialsException e) {
                    e.printStackTrace();
                    HttpUtils.sendResponse(exchange,e.getMessage(),HTTPStatusCode.UNAUTHORIZED.getCode());
                }
            } else {
                HttpUtils.sendResponse(exchange,HTTPStatusCode.METHOD_NOT_ALLOWED.name(),HTTPStatusCode.METHOD_NOT_ALLOWED.getCode());
            }
        }
    }
}
