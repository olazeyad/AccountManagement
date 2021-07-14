package com.account.AccountManagement.exception;

/**
 * Custom exception for NumberFormatException
 * @author ola zeyad
 * 13-7-2021
 * @version 1.0
 */
public class NumberParsingException extends RuntimeException {
    public NumberParsingException(String message) {
        super(message);
    }
}
