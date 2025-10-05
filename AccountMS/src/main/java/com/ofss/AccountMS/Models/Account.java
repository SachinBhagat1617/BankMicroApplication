package com.ofss.AccountMS.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ACCOUNT_MICRO_DB")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNTID")
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNTTYPE", nullable = false)
    private AccountType accountType;

    @Column(name = "AADHAARNUMBER", unique = true)
    private String aadhaarNumber;

    @Column(name="PANNUMBER",unique = true)
    private String panNumber;

    @DecimalMin(value = "500.00", message = "Minimum balance must be 500.00")
    @Column(name = "BALANCE", nullable = false)
    private Double balance;

    @Column(name="CUSTOMERID",unique = true)
    private Long customerId;

    @Column(name="BANKNAME",nullable = false)
    private String bankName;

    @Column(name = "ACCOUNTNUMBER", unique = true)
    private String accountNumber;

    @Column(name="IFSCCODE",nullable = false)
    private String ifscCode;

//    @Enumerated(EnumType.STRING)
//    private AccountStatus accountStatus;
}
