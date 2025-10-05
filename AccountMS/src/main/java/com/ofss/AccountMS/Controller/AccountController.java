package com.ofss.AccountMS.Controller;

import com.ofss.AccountMS.DTO.AccountRequestDTO;
import com.ofss.AccountMS.DTO.ResponseDTO;
import com.ofss.AccountMS.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody AccountRequestDTO accountRequest){
        return accountService.createAccount(accountRequest);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<ResponseDTO> getAccountByAccountNumber(@PathVariable String accountNumber){
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    @PatchMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<ResponseDTO> partialUpdateAccount(@PathVariable String accountNumber, @RequestBody AccountRequestDTO accountRequest){
        // Implement the partial update logic in the service layer
        return accountService.partialUpdateAccount(accountNumber, accountRequest);
    }

}
