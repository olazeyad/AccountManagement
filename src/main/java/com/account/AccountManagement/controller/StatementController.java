package com.account.AccountManagement.controller;

import com.account.AccountManagement.ServiceImpl.StatementServiceImp;
import com.account.AccountManagement.entity.Statement;
import com.account.AccountManagement.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statement")
public class StatementController {

    private static final Logger logger = LoggerFactory.getLogger(StatementController.class);
    @Autowired
    StatementServiceImp service;

    @GetMapping(value = "/hello")
    public String hello(){
        return "hellooo";
    }
    /**
     * @description get all account statements within date range
     * @param request
     * @return
     */
    @PostMapping(value="/filterStatementByDate",consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    private List<Statement> getStatementByAccountAndDate(@RequestBody Request request){

        List<Statement> list = service.getStatementsByAccountAndDateRange(request.getAccountId(),
                request.getStartDate(),request.getEndDate());
        logger.info(list.toString());
       return list;
    }

    /**
     * @description get all account statements within amount range
     * @param request
     * @return
     */
    @PostMapping(value="/filterStatementByAmount",consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    private List<Statement> getStatementByAccountAndAmount(@RequestBody Request request){

        List<Statement> list = service.getStatementsByAccountAndAmountRange(request.getAccountId(),
                request.getStartAmount(),request.getEndAmount());
        logger.info(list.toString());
        return list;
    }

    /**
     * @description get all account statements within the three past months
     * @return
     */
    @PostMapping(value="/getAccountStatement")
    @ResponseBody
    private List<Statement> getThreeMonthsPastStatementsByAccount(@RequestBody Request request){

        List<Statement> list = service.getThreeMonthsPastStatementsByAccount(request.getAccountId());
        logger.info(list.toString());
        return list;
    }
}
