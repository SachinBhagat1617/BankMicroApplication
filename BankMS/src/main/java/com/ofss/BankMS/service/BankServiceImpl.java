package com.ofss.BankMS.service;

import com.ofss.BankMS.DTO.BankRequestDTO;
import com.ofss.BankMS.DTO.BankResponseDTO;
import com.ofss.BankMS.Repository.BankRepository;
import com.ofss.BankMS.models.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService{
    private final BankRepository bankRepository;

    @Override
    public ResponseEntity<?> getAllBanks() {
        List<Bank> banks = (List<Bank>) bankRepository.findAll();
        if(banks.isEmpty()){
            return ResponseEntity.status(404).body("No banks found");
        }
        List<BankResponseDTO>bankResponseDTOList=getBankResponseDTOList(banks);
        return ResponseEntity.ok(bankResponseDTOList);
    }

    @Override
    public ResponseEntity<?> createBank(BankRequestDTO bank) {
        Bank newBank = new Bank();
        newBank.setBankName(bank.getBankName());
        newBank.setBranchName(bank.getBranchName());
        newBank.setBranchAddress(bank.getBranchAddress());
        newBank.setIfscCode(bank.getIfscCode());
        bankRepository.save(newBank);

        return ResponseEntity.status(201).body(newBank);
    }

    @Override
    public ResponseEntity<?> getBankById(Long id) {
        return bankRepository.findById(id).isPresent() ?
                ResponseEntity.ok(bankRepository.findById(id).get()) :
                ResponseEntity.status(404).body("Bank not found with id: " + id);
    }

    @Override
    public ResponseEntity<?> updateBank(Long id, BankRequestDTO bankRequest) {
        if(id==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank ID must be provided in the request body");
        }
        Optional<Bank> existingBank = bankRepository.findById(id);
        if(existingBank.isEmpty()){
            return ResponseEntity.status(404).body("Bank not found with id: " + id);
        }
        bankRepository.deleteById(id);
        Bank bank=Bank.builder()
                .bankName(bankRequest.getBankName())
                .ifscCode(bankRequest.getIfscCode())
                .branchName(bankRequest.getBranchName())
                .branchAddress(bankRequest.getBranchAddress())
                .build();
        return ResponseEntity.ok(bankRepository.save(bank)) ;
    }

    @Override
    public ResponseEntity<?> deleteBank(String bankName) {
        if (bankRepository.findByBankName(bankName).isPresent()) {
            bankRepository.deleteByBankName(bankName);
            return ResponseEntity.ok("Bank deleted Successfully");
        } else {
            return ResponseEntity.status(404).body("Bank not found with name: " + bankName);
        }
    }

    @Override
    public ResponseEntity<?> partialUpdateBank(Long id, BankRequestDTO bankRequestDTO) {
        Bank prevBank = bankRepository.findById(id).isPresent()?
                bankRepository.findById(id).get():null;
        if(prevBank==null){
            return ResponseEntity.status(404).body("Bank not found with id: " + id);
        }
        if(bankRequestDTO.getBankName()!=null){
            prevBank.setBankName(bankRequestDTO.getBankName());
        }
        if(bankRequestDTO.getIfscCode()!=null){
            prevBank.setIfscCode(bankRequestDTO.getIfscCode());
        }
        if(bankRequestDTO.getBranchAddress()!=null){
            prevBank.setBranchAddress(bankRequestDTO.getBranchAddress());
        }
        if(bankRequestDTO.getBranchName()!=null){
            prevBank.setBranchName(bankRequestDTO.getBranchName());
        }
        bankRepository.save(prevBank);
        return ResponseEntity.ok(prevBank);
    }

    @Override
    public ResponseEntity<?> getBankByName(String bankName) {
        Optional<Bank> bank= bankRepository.findByBankName(bankName);
        if(bank.isPresent()){
            return ResponseEntity.ok(bank);
        }
        return null;
    }

//    ----------------------Helper Methods----------------------
    private List<BankResponseDTO> getBankResponseDTOList(List<Bank> banks) {
        return banks.stream().map(this::getBankResponse).toList();
    }

    private BankResponseDTO getBankResponse(Bank bank) {
        return new BankResponseDTO(
                bank.getBankName(),
                bank.getIfscCode(),
                bank.getBranchName(),
                bank.getBranchAddress()
        );
    }
}
