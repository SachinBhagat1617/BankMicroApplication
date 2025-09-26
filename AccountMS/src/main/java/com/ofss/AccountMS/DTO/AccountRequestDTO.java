package com.ofss.AccountMS.DTO;

import com.ofss.AccountMS.Models.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRequestDTO {
    private AccountType accountType;
    private Double initialDeposit;
    private Long customerId;
    private String aadhaarNumber;
    private String panNumber;
    private Double balance;
    private String bankName;
}
