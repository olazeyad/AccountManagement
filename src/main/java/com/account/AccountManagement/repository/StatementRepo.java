package com.account.AccountManagement.repository;

import com.account.AccountManagement.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @description statement Repository
 * @author ola zeyad
 * @date 11-7-2021
 * @version 1.0
 */
public interface StatementRepo extends JpaRepository<Statement, Integer> {
    List<Statement> findByAccount_AccountId(int accountId);
}
