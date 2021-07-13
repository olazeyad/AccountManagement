package com.account.AccountManagement.repository;

import com.account.AccountManagement.entity.Statement;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.function.Consumer;

@SpringBootTest
public class StatementRepoTest {

    private static final Logger logger = LoggerFactory.getLogger(StatementRepoTest.class);

    @Autowired
    StatementRepo statementRepo;

    Consumer<Statement> consumer= statement -> logger.info(statement.toString());

    @Test
    void getAccountStatementTest(){

        /*List<Statement> statementList= statementRepo.findAllByAccount_AccountIdAndDateBetween(
                1, "03.03.2012","09.08.2020");*/

       // List<Statement> statementList =statementRepo.findByAccountId(1);

//        statementList.forEach(consumer);

    }
}
