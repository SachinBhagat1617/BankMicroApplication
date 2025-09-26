package com.ofss.AccountMS.Service;

import com.ofss.AccountMS.DTO.AccountRequestDTO;
import com.ofss.AccountMS.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<ResponseDTO> createAccount(AccountRequestDTO accountRequest);
}
