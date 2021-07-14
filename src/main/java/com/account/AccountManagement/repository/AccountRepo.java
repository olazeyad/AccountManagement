package com.account.AccountManagement.repository;

import com.account.AccountManagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Account repository
 * @author ola zeyad
 * 11-7-2021
 * @version 1.0
 */
public interface AccountRepo extends JpaRepository<Account,Integer> {
}
