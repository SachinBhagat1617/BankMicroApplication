package com.ofss.AccountMS.Service;

import com.ofss.AccountMS.DTO.DepositResponseDTO;
import com.ofss.AccountMS.DTO.TransactionResponseDTO;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<DepositResponseDTO> deposit(String accountNumber, Double amount);

    ResponseEntity<DepositResponseDTO> withdraw(String accountNumber, double amount);

    ResponseEntity<TransactionResponseDTO> transfer(String fromAccountNumber, String toAccountNumber, Double amount);
}
