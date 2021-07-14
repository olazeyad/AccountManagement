package com.account.AccountManagement.exception;

import java.time.DateTimeException;

/**
 * Custom exception for DateTimeParsingException
 * @author ola zeyad
 * 13-7-2021
 * @version 1.0
 */
public class DateParsingException extends DateTimeException {

    public DateParsingException(String message) {
        super(message);
    }
}
