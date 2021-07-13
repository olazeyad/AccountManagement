package com.account.AccountManagement.Service;

import com.account.AccountManagement.entity.Statement;

import java.util.List;

/**
 * @description statement service interface
 * @author ola zeyad
 * @date 11-7-2021
 * @version 1.0
 */
public interface StatementService {

    /**
     *
     * @param accountId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Statement> getStatementsByAccountAndDateRange(String accountId, String startDate, String endDate);

    /**
     *
     * @param accountId
     * @param fromAmount
     * @param toAmount
     * @return
     */
    public List<Statement> getStatementsByAccountAndAmountRange(String accountId, String fromAmount,String toAmount);

    /**
     *
     * @param accountId
     * @return
     */
    public List<Statement> getThreeMonthsPastStatementsByAccount(String accountId);

    }
