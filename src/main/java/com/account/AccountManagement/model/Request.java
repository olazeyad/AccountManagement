package com.account.AccountManagement.model;

import com.sun.istack.NotNull;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * @description request bean to handle requests from clients
 * @author ola zeyad
 * @date 11-7-2021
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
