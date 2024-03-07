package com.if23b212.mtcg.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UserCredentials holds the data for auth related requests
 */
public class UserCredentials {
    //Mappes variable to json attribute
    @JsonProperty("Username")
    private String username;
    @JsonProperty("Password")
    private String password;

    public UserCredentials() { }

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
