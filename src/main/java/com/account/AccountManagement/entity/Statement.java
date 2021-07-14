package com.account.AccountManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

/**
 * statement entity
 * @author ola zeyad
 * 11-7-2021
 * @version 1.0
 */
@Entity
@Table(name = "statement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statement implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "datefield")
    private String date;

    @Column(name = "amount")
    private String amount;
}
