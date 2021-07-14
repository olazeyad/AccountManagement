package com.account.AccountManagement.ServiceImpl;

import com.account.AccountManagement.Service.StatementService;
import com.account.AccountManagement.Util.HashingMapper;
import com.account.AccountManagement.entity.Statement;
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
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * statement service to getting and retrieving statements data
 * @author ola zeyad
 * 11-7-2021
 * @version 1.0
 */
@Service
@Transactional
public class StatementServiceImp implements StatementService {
    private static final Logger logger = LoggerFactory.getLogger(StatementServiceImp.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    private StatementRepo statementRepo;
    @Autowired
    private HashingMapper hashingMapper;

    BiPredicate<LocalDate,LocalDate> isEqual= LocalDate::isEqual;
    BiPredicate<LocalDate,LocalDate> isAfter= LocalDate::isAfter;
    BiPredicate<LocalDate,LocalDate> isBefore= LocalDate::isBefore;

    /**
     * method to get all statements for specific account within date range, when no accountId provided
     * then will return statement for all account within date range
     * @param accountId to determine which account to query
     * @param startDate start date point ex: 05.04.2020
     * @param endDate until date point
     * @return list of statement after filtering
     */
    public List<Statement> getStatementsByAccountAndDateRange(String accountId, String startDate,String endDate){
        List<Statement> statementsList;
        try {
            LocalDate fromDate=LocalDate.parse(startDate, formatter);
            LocalDate toDate =LocalDate.parse(endDate, formatter);

            Optional<String> checkValue = Optional.ofNullable(accountId);
            if(checkValue.isPresent()) // check for value is present or not
                statementsList = statementRepo.findByAccount_AccountId(Integer.parseInt(accountId));
            else
                statementsList = statementRepo.findAll();

            if (statementsList.isEmpty()) {
                if (checkValue.isPresent())
                    throw new NoDataFoundException(String.format("No statements found for account with id = %s", accountId));
                else
                    throw new NoDataFoundException("No statements found");
            }

            logger.info(String.format("repo result size: %d, Repo result: %s",statementsList.size(),statementsList));

            List<Statement> streamResult = statementsList.stream()
                    .filter(statement->isAfter.or(isEqual).test(LocalDate.parse(statement.getDate(), formatter),fromDate))
                    .filter(statement->isBefore.or(isEqual).test(LocalDate.parse(statement.getDate(), formatter),toDate))
                    .sorted(Comparator.comparing(statement->LocalDate.parse(statement.getDate(), formatter)))
                    .collect(Collectors.toList());

            if (streamResult.isEmpty()) {
                if (checkValue.isPresent()) {
                    throw new NoDataFoundException(
                            String.format("No statements found for account with id = %s  within the provided date range", accountId));
                }
                else
                    throw new NoDataFoundException("No statements found within the provided date range");
            }

            streamResult.forEach(statement -> {
                statement.getAccount().setAccountNumber(String.valueOf(
                        hashingMapper.encoder(statement.getAccount().getAccountNumber())));
            });
            logger.info(String.format("Stream result size: %d, Stream result: %s",streamResult.size(),streamResult));

            return streamResult;
        }catch (NumberFormatException ex){
            logger.error(String.format("NumberParsingException: %s",ex.getStackTrace()));
            throw new NumberParsingException("Not Valid Parameters");
        }catch (DateTimeParseException e ){
            logger.error(String.format("DateTimeParseException: %s",e.getStackTrace()));
            throw new DateParsingException("Invalid Date Format");
        }
    }

    /**
     *method to get all statements for specific account within amount range, when no accountId provided
     * then will return statement for all account within amount range
     * @param accountId to determine which account to query
     * @param startAmount start point for amount range
     * @param endAmount  end point for amount range
     * @return list of statement after filtering
     */
    public List<Statement> getStatementsByAccountAndAmountRange(String accountId, String startAmount,String endAmount) {
        List<Statement> statementsList;
        try {
            double fromAmount = Double.parseDouble(startAmount);
            double toAmount = Double.parseDouble(endAmount);

            Optional<String> checkValue = Optional.ofNullable(accountId);
            if(checkValue.isPresent()) // check for value is present or not
                statementsList = statementRepo.findByAccount_AccountId(Integer.parseInt(accountId));
            else
                statementsList = statementRepo.findAll();

            if (statementsList.isEmpty()) {
                if (checkValue.isPresent())
                    throw new NoDataFoundException(String.format("No statements found for account with id = %s", accountId));
                else
                    throw new NoDataFoundException("No statements found");
            }

            logger.info(String.format("repo result size: %d, Repo result: %s",statementsList.size(),statementsList));

            List<Statement> streamResult = statementsList.stream()
                    .filter(statement->Double.parseDouble(statement.getAmount()) >= fromAmount)
                    .filter(statement->Double.parseDouble(statement.getAmount()) <= toAmount)
                    .sorted(Comparator.comparing(statement->LocalDate.parse(statement.getDate(), formatter)))
                    .collect(Collectors.toList());

            if (streamResult.isEmpty()) {
                if (checkValue.isPresent())
                    throw new NoDataFoundException(
                            String.format("No Statements found for account with id = %s  with the provided amount range", accountId));
                else
                    throw new NoDataFoundException("No Statements found with the provided amount range");
            }

            streamResult.forEach(statement -> {
                statement.getAccount().setAccountNumber(String.valueOf(
                        hashingMapper.encoder(statement.getAccount().getAccountNumber())));
            });
            logger.info(String.format("Stream result size: %d, Stream result: %s",streamResult.size(),streamResult));

            return streamResult;

        }catch (NumberFormatException ex){
            logger.error(String.format("NumberParsingException: %s",ex.getStackTrace()));
            throw new NumberParsingException("Not Valid Parameters");
        }
    }

    /**
     *  method to get all statements for specific account in the past three months, when no accountId provided
     * then will return statement for all account within the past three months
     * @param accountId to determine which account to query
     * @return list of statement after filtering
     */
    public List<Statement> getThreeMonthsPastStatementsByAccount(String accountId) {
        List<Statement> statementsList;
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate pastDate = currentDate.minusMonths(3);

            Optional<String> checkValue = Optional.ofNullable(accountId);
            if(checkValue.isPresent()) // check for value is present or not
                statementsList = statementRepo.findByAccount_AccountId(Integer.parseInt(accountId));
            else
                statementsList = statementRepo.findAll();

            if (statementsList.isEmpty()) {
                if (checkValue.isPresent())
                    throw new NoDataFoundException(String.format("No statements found for account with id =  %s", accountId));
                else
                    throw new NoDataFoundException("No statements found");
            }

            logger.info(String.format("repo result size: %d, Repo result: %s",statementsList.size(), statementsList));

            List<Statement> streamResult = statementsList.stream()
                    .filter(statement -> isAfter.or(isEqual).test(LocalDate.parse(statement.getDate(), formatter), pastDate))
                    .filter(statement -> isBefore.or(isEqual).test(LocalDate.parse(statement.getDate(), formatter), currentDate))
                    .sorted(Comparator.comparing(statement -> LocalDate.parse(statement.getDate(), formatter)))
                    .collect(Collectors.toList());

            if (streamResult.isEmpty()) {
                if (checkValue.isPresent())
                    throw new NoDataFoundException(String.format("No statements found for account with id = %s within the last three months", accountId));
                else
                    throw new NoDataFoundException("No statements found within the last three months");
            }

            streamResult.forEach(statement -> {
                statement.getAccount().setAccountNumber(String.valueOf(
                        hashingMapper.encoder(statement.getAccount().getAccountNumber())));
            });
            logger.info(String.format("Stream result size: %d, Stream result: %s", streamResult.size(),streamResult));

            return streamResult;
        }catch (NumberFormatException ex){
            logger.error(String.format("NumberParsingException: %s",ex.getStackTrace()));
            throw new NumberParsingException("Not Valid Parameters");
        }
    }
}
