package com.account.AccountManagement.exception;

import java.time.DateTimeException;

public class DateParsingException extends DateTimeException {

    public DateParsingException(String message) {
        super(message);
    }
}
