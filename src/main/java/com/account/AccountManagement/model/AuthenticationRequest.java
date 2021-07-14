package com.account.AccountManagement.model;

import java.io.Serializable;

/**
 * Bean for authentication request
 * @author ola zeyad
 * 13-7-2021
 * @version 1.0
 */
public class AuthenticationRequest implements Serializable {


    private String username;
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
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

    //need default constructor for JSON Parsing
    public AuthenticationRequest()
    {

    }

}
