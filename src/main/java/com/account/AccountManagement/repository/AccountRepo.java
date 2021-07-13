package com.account.AccountManagement.repository;

import com.account.AccountManagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description Account repository
 * @author ola zeyad
 * @date 11-7-2021
 * @version 1.0
 */
public interface AccountRepo extends JpaRepository<Account,Integer> {
}
