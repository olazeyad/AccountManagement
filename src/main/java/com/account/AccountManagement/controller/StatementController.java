package com.account.AccountManagement.controller;

import com.account.AccountManagement.ServiceImpl.StatementServiceImp;
import com.account.AccountManagement.entity.Statement;
import com.account.AccountManagement.exception.NumberParsingException;
import com.account.AccountManagement.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/statement")
public class StatementController {

    private static final Logger logger = LoggerFactory.getLogger(StatementController.class);
    @Autowired
    StatementServiceImp service;

    /**
     * Get all account statements within date range
     * @param request contain the parameters send by user: accountId, startDate and endDate
     * @return list of statements based on user request
     */
    @PostMapping(value="/filterStatementByDate",consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    private List<Statement> getStatementByAccountAndDate(@RequestBody Optional<Request> request) throws NoSuchAlgorithmException {

        if (request.isPresent() && request.get().getStartDate()!=null && request.get().getEndDate()!=null) {
            List<Statement> list = service.getStatementsByAccountAndDateRange(request.get().getAccountId(),
                    request.get().getStartDate(), request.get().getEndDate());
            return list;
        }
        else
            throw new NumberParsingException("Invalid Parameters");
    }

    /**
     * Get all account statements within amount range
     * @param request contain the parameters send by user: accountId, startAmount and endAmount
     * @return list of statements based on user request
     */
    @PostMapping(value="/filterStatementByAmount",consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    private List<Statement> getStatementByAccountAndAmount(@RequestBody Optional<Request> request){

        if (request.isPresent() && request.get().getStartAmount()!=null && request.get().getEndAmount()!=null) {
            List<Statement> list = service.getStatementsByAccountAndAmountRange(request.get().getAccountId(),
                    request.get().getStartAmount(), request.get().getEndAmount());
            return list;
        }
        else
                throw new NumberParsingException("Invalid Parameters");
    }

    /**
     * Get all account statements within the three past months
     * @param request is optional param contain the parameters send by user: accountId
     * @return list of statements based on user request
     */
    @PostMapping(value="/getAccountStatement")
    @ResponseBody
    private List<Statement> getThreeMonthsPastStatementsByAccount(@RequestBody Optional<Request> request){

        String id = null;
        if (request.isPresent() && request.get().getAccountId()!=null)
            id= request.get().getAccountId();

        List<Statement> list = service.getThreeMonthsPastStatementsByAccount(id);
        return list;
    }
}
