package com.account.AccountManagement.exception;

/**
 * Custom exception if there is no data
 * @author ola zeyad
 * 13-7-2021
 * @version 1.0
 */
public class NoDataFoundException extends RuntimeException {

        public NoDataFoundException(String message) {

            super(message);
        }
}
