package com.account.AccountManagement.ServiceImpl;

import com.account.AccountManagement.Service.StatementService;
import com.account.AccountManagement.entity.Statement;
import com.account.AccountManagement.repository.AccountRepo;
import com.account.AccountManagement.repository.StatementRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.account.AccountManagement.exception.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * @description statement service to getting and retrieving statements data
 * @author ola zeyad
 * @date 11-7-2021
 * @version 1.0
 */
@Service
@Transactional
public class StatementServiceImp implements StatementService {
    private static final Logger logger = LoggerFactory.getLogger(StatementServiceImp.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Autowired
    private StatementRepo repo;
    @Autowired
    private AccountRepo accountRepo;

    BiPredicate<LocalDate,LocalDate> isEqual= LocalDate::isEqual;
    BiPredicate<LocalDate,LocalDate> isAfter= LocalDate::isAfter;
    BiPredicate<LocalDate,LocalDate> isBefore= LocalDate::isBefore;

    /**
     * @description method to get all statements for specific account within date range
     * @param accountId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Statement> getStatementsByAccountAndDateRange(String accountId, String startDate,String endDate) {
        logger.info("%%%%%%%%%% getStatementsByAccountAndDateRange %%%%%%%%%%%");
        try {
            LocalDate fromDate=LocalDate.parse(startDate, formatter);
            LocalDate toDate =LocalDate.parse(endDate, formatter);

            List<Statement> statementsList =repo.findByAccount_AccountId(Integer.parseInt(accountId));
            if (statementsList.isEmpty())
                throw new NoDataFoundException("No statements found for account with id = " + accountId);
            logger.info("repo result size: "+statementsList.size()+", Repo result: "+statementsList.toString());

            List<Statement> streamResult = statementsList.stream()
                    .filter(statement->isAfter.or(isEqual).test(LocalDate.parse(statement.getDate(), formatter),fromDate))
                    .filter(statement->isBefore.or(isEqual).test(LocalDate.parse(statement.getDate(), formatter),toDate))
                    .sorted(Comparator.comparing(statement->LocalDate.parse(statement.getDate(), formatter)))
                    .collect(Collectors.toList());

            if (streamResult.isEmpty())
                throw new NoDataFoundException("No statements found for account with id = " + accountId+ " within the provided date range");
            logger.info("Stream result size: "+streamResult.size()+", Stream result: "+streamResult.toString());

            return streamResult;
        }catch (NumberFormatException ex){
            throw new NumberParsingException("Can not parse number from string");
        }catch (DateTimeParseException e ){
            throw new DateParsingException("Invalid Date Format");
        }catch (NullPointerException e)
        {
            throw new NumberParsingException("Not Valid Parameters");
        }
    }

    /**
     *@description method to get all statements for specific account within amount range
     * @param accountId
     * @param startAmount
     * @param endAmount
     * @return
     */
    public List<Statement> getStatementsByAccountAndAmountRange(String accountId, String startAmount,String endAmount) {
        logger.info("%%%%%%%%%% getStatementsByAccountAndAmountRange %%%%%%%%%%%");

        try {
            double fromAmount = Double.parseDouble(startAmount);
            double toAmount = Double.parseDouble(endAmount);

            List<Statement> statementsList =repo.findByAccount_AccountId(Integer.parseInt(accountId));
            if (statementsList.isEmpty())
                throw new NoDataFoundException("No statements found for account with id = " + accountId);
            logger.info("repo result size: "+statementsList.size()+", Repo result: "+statementsList.toString());

            List<Statement> streamResult = statementsList.stream()
                    .filter(statement->Double.parseDouble(statement.getAmount()) >= fromAmount)
                    .filter(statement->Double.parseDouble(statement.getAmount()) <= toAmount)
                    .sorted(Comparator.comparing(statement->LocalDate.parse(statement.getDate(), formatter)))
                    .collect(Collectors.toList());

            if (streamResult.isEmpty())
                throw new NoDataFoundException("No Statements found for account with id = " + accountId+ " with the provided amount range");
            logger.info("Stream result size: "+streamResult.size()+", Stream result: "+streamResult.toString());

            return streamResult;

        }catch (NumberFormatException ex){
            throw new NumberParsingException("Can not parse number from string");
        }catch (NullPointerException e)
        {
            throw new NumberParsingException("Not Valid Parameters");
        }
    }

    /**
     * @description method to get all statements for specific account in the past three months
     * @param accountId
     * @return
     */
    public List<Statement> getThreeMonthsPastStatementsByAccount(String accountId) {
        logger.error("%%%%%%%%%% getThreeMonthsPastStatementsByAccount %%%%%%%%");
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate pastDate = currentDate.minusMonths(3);

            logger.info("Current date: " + currentDate + ", before 3 months: " + pastDate);

            List<Statement> statementsList = repo.findByAccount_AccountId(Integer.parseInt(accountId));
            if (statementsList.isEmpty())
                throw new NoDataFoundException("No statements found for account with id = " + accountId);

            logger.info("repo result size: " + statementsList.size() + ", Repo result: " + statementsList.toString());

            List<Statement> streamResult = statementsList.stream()
                    .filter(statement -> isAfter.or(isEqual).test(LocalDate.parse(statement.getDate(), formatter), pastDate))
                    .filter(statement -> isBefore.or(isEqual).test(LocalDate.parse(statement.getDate(), formatter), currentDate))
                    .sorted(Comparator.comparing(statement -> LocalDate.parse(statement.getDate(), formatter)))
                    .collect(Collectors.toList());

            if (streamResult.isEmpty())
                throw new NoDataFoundException("No statements found for account with id = " + accountId + " within the last three months");

            logger.info("Stream result size: " + streamResult.size() + ", Stream result: " + streamResult.toString());

            return streamResult;
        }catch (NumberFormatException ex){
            throw new NumberParsingException("Can not parse number from string");
        }catch (NullPointerException e)
        {
            throw new NumberParsingException("Not Valid Parameters");
        }
    }
}
