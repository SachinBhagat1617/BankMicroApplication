package com.ofss.AccountMS.Service;

import com.ofss.AccountMS.DTO.DepositResponseDTO;
import com.ofss.AccountMS.DTO.TransactionDTO;
import com.ofss.AccountMS.DTO.TransactionResponseDTO;
import com.ofss.AccountMS.Models.Account;
import com.ofss.AccountMS.Models.TransactionType;
import com.ofss.AccountMS.Models.Transactions;
import com.ofss.AccountMS.Repository.AccountRepository;
import com.ofss.AccountMS.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    @Override
    @Transactional
    public ResponseEntity<DepositResponseDTO> deposit(String accountNumber, Double amount) {
        Optional<Account> account=accountRepository.findByAccountNumber(accountNumber);

        if(account.isEmpty()){
            return ResponseEntity.badRequest().body(new DepositResponseDTO("Account not found with account number: " + accountNumber, account.get().getBalance()));
        }
        // needs to be implemented if amount is greater than 50000 then it should be reported to email
        if(amount<=0){
            return ResponseEntity.badRequest().body(new DepositResponseDTO("Deposit amount must be greater than zero", account.get().getBalance()));
        }
        account.get().setBalance(account.get().getBalance()+amount);
        accountRepository.save(account.get());
        accountRepository.flush();
        Transactions transactions=Transactions.builder()
                .fromAccountNumber("--SYSTEM--")
                .toAccountNumber(accountNumber)
                .amount(amount)
                .transactionType(TransactionType.valueOf("DEPOSIT"))
                .build();
        transactionRepository.save(transactions);
        return ResponseEntity.ok(new DepositResponseDTO("Amount deposited successfully", account.get().getBalance()));
    }

    @Override
    @Transactional
    public ResponseEntity<DepositResponseDTO> withdraw(String accountNumber, double amount) {
        Optional<Account> account=accountRepository.findByAccountNumber(accountNumber);

        if(account.isEmpty()){
            return ResponseEntity.badRequest().body(new DepositResponseDTO("Account not found with account number: " + accountNumber, account.get().getBalance()));
        }
        // needs to be implemented if amount is greater than 50000 then it should be reported to email
        if(amount<=0){
            return ResponseEntity.badRequest().body(new DepositResponseDTO("Withdraw amount must be greater than zero", account.get().getBalance()));
        }
        if(account.get().getBalance()<amount){
            return ResponseEntity.badRequest().body(new DepositResponseDTO("Insufficient balance", account.get().getBalance()));
        }
        account.get().setBalance(account.get().getBalance()-amount);
        accountRepository.save(account.get());
        Transactions transactions=Transactions.builder()
                .fromAccountNumber("--SYSTEM--")
                .toAccountNumber(accountNumber)
                .amount(amount)
                .transactionType(TransactionType.valueOf("WITHDRAW"))
                .build();
        transactionRepository.save(transactions);
        return ResponseEntity.ok(new DepositResponseDTO("Amount withdrawn successfully", account.get().getBalance()));
    }

    @Override
    @Transactional
    public ResponseEntity<TransactionResponseDTO> transfer(String fromAccountNumber, String toAccountNumber, Double amount) {
        Optional<Account> fromAccount=accountRepository.findByAccountNumber(fromAccountNumber);
        Optional<Account> toAccount=accountRepository.findByAccountNumber(toAccountNumber);
        if(fromAccount.isEmpty()){
            return ResponseEntity.badRequest().body(new TransactionResponseDTO("Source account not found with account number: " + fromAccountNumber, null));
        }
        if(toAccount.isEmpty()){
            return ResponseEntity.badRequest().body(new TransactionResponseDTO("Destination account not found with account number: " + toAccountNumber, null));
        }
        if(amount<=0){
            return ResponseEntity.badRequest().body(new TransactionResponseDTO("Transfer amount must be greater than zero", null));
        }
        if(fromAccount.get().getBalance()<amount){
            return ResponseEntity.badRequest().body(new TransactionResponseDTO("Insufficient balance in source account", null));
        }
        fromAccount.get().setBalance(fromAccount.get().getBalance()-amount);
        toAccount.get().setBalance(toAccount.get().getBalance()+amount);
        accountRepository.save(fromAccount.get());
        accountRepository.save(toAccount.get());
        Transactions transactions=Transactions.builder()
                .fromAccountNumber(fromAccountNumber)
                .toAccountNumber(toAccountNumber)
                .amount(amount)
                .transactionType(TransactionType.valueOf("TRANSFER"))
                .build();
        Transactions savedTransaction=transactionRepository.save(transactions);

        TransactionDTO transactionDTO=TransactionDTO.builder()
                .transactionId(savedTransaction.getTransactionId())
                .fromAccountNumber(savedTransaction.getFromAccountNumber())
                .toAccountNumber(savedTransaction.getToAccountNumber())
                .amount(savedTransaction.getAmount())
                .transactionType(savedTransaction.getTransactionType().name())
                .balanceAfterTransaction(fromAccount.get().getBalance())
                .timestamp(savedTransaction.getTransactionDate())
                .description("Amount transferred successfully")
                .build();
        return ResponseEntity.ok(new TransactionResponseDTO("Amount transferred successfully", transactionDTO));
    }
}
