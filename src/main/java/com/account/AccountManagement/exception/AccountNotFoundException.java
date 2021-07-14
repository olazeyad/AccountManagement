package com.account.AccountManagement.exception;

/**
 * Custom exception if there is no account found
 * @author ola zeyad
 * 13-7-2021
 * @version 1.0
 */
public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(Integer id) {

        super(String.format("Account with Id %d not found", id));
    }
}
