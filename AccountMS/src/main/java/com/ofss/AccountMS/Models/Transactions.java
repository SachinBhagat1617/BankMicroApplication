package com.ofss.AccountMS.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "TRANSACTION_MICRO_DB")
@Data
@AllArgsConstructor
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @DecimalMin(value = "0.01", message = "Transaction amount must be greater than 0")
    private Double amount;

    @NotBlank
    private Long fromAccountId;
    @NotBlank
    private Long toAccountId;

    @CreationTimestamp
    private String transactionDate;

    //private String description;
}
