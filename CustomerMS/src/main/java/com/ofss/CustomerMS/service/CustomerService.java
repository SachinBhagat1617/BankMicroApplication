package com.ofss.CustomerMS.service;

import com.ofss.CustomerMS.DTO.CustomerRequestDTO;
import com.ofss.CustomerMS.DTO.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<ResponseDTO> getAllCustomers();

    ResponseEntity<ResponseDTO> createCustomer(@Valid CustomerRequestDTO customerRequest);

    ResponseEntity<ResponseDTO> partialUpdateCustomer(Long customerId, @Valid CustomerRequestDTO customerRequest);

    ResponseEntity<ResponseDTO> deleteCustomer(Long customerId);

    ResponseEntity<ResponseDTO> getCustomersById(Long customerId);
}
