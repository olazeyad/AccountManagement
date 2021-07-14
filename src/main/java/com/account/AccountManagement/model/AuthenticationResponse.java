package com.account.AccountManagement.model;

import java.io.Serializable;

/**
 * Bean for authentication response
 * @author ola zeyad
 * 13-7-2021
 * @version 1.0
 */
public class AuthenticationResponse implements Serializable {

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
