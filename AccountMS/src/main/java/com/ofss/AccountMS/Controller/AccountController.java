package com.ofss.AccountMS.Controller;

import com.ofss.AccountMS.DTO.AccountRequestDTO;
import com.ofss.AccountMS.DTO.ResponseDTO;
import com.ofss.AccountMS.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody AccountRequestDTO accountRequest){
        return accountService.createAccount(accountRequest);
    }
}
