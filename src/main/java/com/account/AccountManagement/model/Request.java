package com.account.AccountManagement.model;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  request bean to handle requests from clients
 * @author ola zeyad
 * 11-7-2021
 * @version 1.0
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {
    private String accountId;
    private String startDate;
    private String endDate;
    private String startAmount;
    private String endAmount;
}
