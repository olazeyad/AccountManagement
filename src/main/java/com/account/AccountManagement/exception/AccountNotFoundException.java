package com.account.AccountManagement.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(Integer id) {

        super(String.format("Account with Id %d not found", id));
    }
}
