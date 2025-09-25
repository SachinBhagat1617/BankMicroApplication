package com.ofss.BankMS.service;

import com.ofss.BankMS.DTO.BankRequestDTO;
import com.ofss.BankMS.models.Bank;
import org.springframework.http.ResponseEntity;

public interface BankService {

    ResponseEntity<?> getAllBanks();

    ResponseEntity<?> createBank(BankRequestDTO bank);

    ResponseEntity<?> getBankById(Long id);

    ResponseEntity<?> updateBank(Long id, BankRequestDTO bankRequestDTO);

    ResponseEntity<?> deleteBank(Long id);

    ResponseEntity<?> partialUpdateBank(Long id, BankRequestDTO bankRequestDTO);
}
