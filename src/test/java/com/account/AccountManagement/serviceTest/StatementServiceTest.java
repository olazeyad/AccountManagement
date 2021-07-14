package com.account.AccountManagement.serviceTest;

import com.account.AccountManagement.ServiceImpl.StatementServiceImp;
import com.account.AccountManagement.entity.Account;
import com.account.AccountManagement.entity.Statement;
import com.account.AccountManagement.exception.DateParsingException;
import com.account.AccountManagement.exception.NoDataFoundException;
import com.account.AccountManagement.exception.NumberParsingException;
import com.account.AccountManagement.repository.StatementRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * statement service Junit test
 * @author ola zeyad
 * 11-7-2021
 * @version 1.0
 */
@SpringBootTest
public class StatementServiceTest {
    @Autowired
    private StatementServiceImp service;

    @MockBean
    private StatementRepo repo;

    @BeforeEach
    public void setup() {
        when(repo.findAll()).thenReturn(Stream.of(
                new Statement(1, new Account(1, "current", "73267263282"), "14.05.2019", "957.272849951391"),
                new Statement(2, new Account(3, "savings", "4378900078"), "31.05.2019", "376.672352972369"),
                new Statement(3, new Account(5, "current", "9080987549"), "21.10.2019", "113.619329901574"),
                new Statement(4, new Account(6, "savings", "765690000"), "23.11.2019", "103.422615473659"),
                new Statement(4, new Account(6, "savings", "765690000"), "23.05.2021", "103.422615473659"),
                new Statement(5, new Account(1, "current", "73267263282"), "05.05.2020", "578.931756366243"))
                .collect(Collectors.toList()));

        when(repo.findByAccount_AccountId(1)).thenReturn(Stream.of(
                new Statement(1, new Account(1, "current", "73267263282"), "31.05.2019", "376.672352972369"),
                new Statement(2, new Account(1, "current", "73267263282"), "21.10.2019", "113.619329901574"),
                new Statement(3, new Account(1, "current", "73267263282"), "23.11.2019", "103.422615473659"),
                new Statement(4, new Account(1, "current", "73267263282"), "05.05.2020", "578.931756366243"),
                new Statement(4, new Account(1, "current", "73267263282"), "11.12.2020", "578.931756366243"))
                .collect(Collectors.toList()));
    }

    @Test
    public void getStatementsByDateRangeTest() {

        assertEquals(5, service.getStatementsByAccountAndDateRange(null, "14.05.2019",
                "12.06.2020").size());
    }

    @Test
    public void getStatementsByAccountAndDateRangeTest() {

        assertEquals(3, service.getStatementsByAccountAndDateRange("1", "14.05.2019",
                "12.12.2019").size());

    }

    @Test
    public void getStatementsByAmountRangeTest() {

        assertEquals(6, service.getStatementsByAccountAndAmountRange(null, "10",
                "4000").size());
    }

    @Test
    public void getStatementsByAccountAndAmountRangeTest() {

        assertEquals(5, service.getStatementsByAccountAndAmountRange("1", "100",
                "5000").size());

    }

    @Test
    public void getThreeMonthsPastStatementsTest() {

        assertEquals(1, service.getThreeMonthsPastStatementsByAccount(null).size());
    }

    @Test
    public void getThreeMonthsPastStatementsByAccountTest() {

        assertThrows(NoDataFoundException.class, () -> {
            service.getThreeMonthsPastStatementsByAccount("1");
        });
    }

    @Test
    public void firstDateFormatExceptionTest() {

        assertThrows(DateParsingException.class, () -> {
            service.getStatementsByAccountAndDateRange(null, "14-05-2019",
                    "12.06.2020");
        });

    }

    @Test
    public void firstDateFormatExceptionWithAccountIdTest() {

        assertThrows(DateParsingException.class, () -> {
            service.getStatementsByAccountAndDateRange("1", "14-05-2019",
                    "12.06.2020");
        });

    }

    @Test
    public void secondDateFormatExceptionTest() {

        assertThrows(DateParsingException.class, () -> {
            service.getStatementsByAccountAndDateRange(null,
                    "12.06.2020", "14-05-2019");
        });

    }

    @Test
    public void secondDateFormatExceptionWithAccountIdTest() {

        assertThrows(DateParsingException.class, () -> {
            service.getStatementsByAccountAndDateRange("1",
                    "12.06.2020", "14-05-2019");
        });

    }

    @Test
    public void firstNumberParsingExceptionTest() {

        assertThrows(NumberParsingException.class, () -> {
            service.getStatementsByAccountAndAmountRange(null, "hello",
                    "500");
        });
    }

    @Test
    public void firstNumberParsingExceptionWithAccountIdTest() {

        assertThrows(NumberParsingException.class, () -> {
            service.getStatementsByAccountAndAmountRange("1", "hello",
                    "500");
        });
    }

    @Test
    public void secondNumberParsingExceptionTest() {

        assertThrows(NumberParsingException.class, () -> {
            service.getStatementsByAccountAndAmountRange(null, "500",
                    "hello");
        });
    }

    @Test
    public void secondNumberParsingExceptionWithAccountIdTest() {

        assertThrows(NumberParsingException.class, () -> {
            service.getStatementsByAccountAndAmountRange("1", "500",
                    "hello");
        });
    }

    @Test
    public void bothNumberParsingExceptionTest() {

        assertThrows(NumberParsingException.class, () -> {
            service.getStatementsByAccountAndAmountRange(null, "hi",
                    "hello");
        });
    }

    @Test
    public void bothNumberParsingExceptionWithAccountIdTest() {

        assertThrows(NumberParsingException.class, () -> {
            service.getStatementsByAccountAndAmountRange("1", "hi",
                    "hello");
        });
    }
}
