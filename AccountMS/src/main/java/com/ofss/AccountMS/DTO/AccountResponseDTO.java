package com.ofss.AccountMS.DTO;

import com.ofss.AccountMS.Models.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccountResponseDTO {
    private String accountNumber;
    private AccountType accountType;
    private Double balance;
    private String bankName;
    private String bankIfscCode;
}
