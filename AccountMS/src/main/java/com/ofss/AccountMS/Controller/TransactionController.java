package com.ofss.AccountMS.Controller;

import com.ofss.AccountMS.DTO.DepositResponseDTO;
import com.ofss.AccountMS.DTO.TransactionResponseDTO;
import com.ofss.AccountMS.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping("/deposit/{accountNumber}/{amount}")
    public ResponseEntity<DepositResponseDTO> deposit(@PathVariable String accountNumber,@PathVariable Double amount){
        return transactionService.deposit(accountNumber,amount);
    }
    @PostMapping("/withdraw/{accountNumber}/{amount}")
    public ResponseEntity<DepositResponseDTO> withdraw(@PathVariable String accountNumber,@PathVariable Double amount) {
        return transactionService.withdraw(accountNumber, amount);
    }
    @PostMapping("/transfer/{fromAccountNumber}/{toAccountNumber}/{amount}")
    public ResponseEntity<TransactionResponseDTO> transfer(@PathVariable String fromAccountNumber,@PathVariable String toAccountNumber,@PathVariable Double amount) {
        return transactionService.transfer(fromAccountNumber, toAccountNumber, amount);
    }


}
