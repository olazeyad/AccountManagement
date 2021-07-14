package com.account.AccountManagement.Service;

import com.account.AccountManagement.entity.Statement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static java.security.MessageDigest.getInstance;

/**
 * Statement service interface
 * @author ola zeyad
 * 11-7-2021
 * @version 1.0
 */
public interface StatementService  {

    /**
     * method to get all statements for specific account within date range, when no accountId provided
     * then will return statement for all account within date range
     * @param accountId to determine which account to query
     * @param startDate start date point ex: 05.04.2020
     * @param endDate until date point
     * @return list of statement after filtering
     */
    public List<Statement> getStatementsByAccountAndDateRange(String accountId, String startDate, String endDate);

    /**
     *method to get all statements for specific account within amount range, when no accountId provided
     * then will return statement for all account within amount range
     * @param accountId to determine which account to query
     * @param fromAmount start point for amount range
     * @param toAmount  end point for amount range
     * @return list of statement after filtering
     */
    public List<Statement> getStatementsByAccountAndAmountRange(String accountId, String fromAmount,String toAmount);

    /**
     *  method to get all statements for specific account in the past three months, when no accountId provided
     * then will return statement for all account within the past three months
     * @param accountId to determine which account to query
     * @return list of statement after filtering
     */
    public List<Statement> getThreeMonthsPastStatementsByAccount(String accountId);

    }
