package com.ofss.AccountMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private Long transactionId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String transactionType;
    private Double amount;
    private Double balanceAfterTransaction;
    private String timestamp;
    private String description;
}
