package com.ofss.BankMS.controller;

import com.ofss.BankMS.DTO.BankRequestDTO;
import com.ofss.BankMS.models.Bank;
import com.ofss.BankMS.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;
    @GetMapping
    public ResponseEntity<?> getAllBanks(){
        return (bankService.getAllBanks());
    }
    @PostMapping
    public ResponseEntity<?> createBank(@RequestBody BankRequestDTO bankRequest){
        return (bankService.createBank(bankRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBankById(@PathVariable Long id) {
        return (bankService.getBankById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBank(@PathVariable Long id, @RequestBody BankRequestDTO bankRequest) {
        return (bankService.updateBank(id, bankRequest));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateBank(@PathVariable Long id, @RequestBody BankRequestDTO bankRequest){
        return (bankService.partialUpdateBank(id, bankRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBank(@PathVariable Long id) {
        return (bankService.deleteBank(id));
    }
}
