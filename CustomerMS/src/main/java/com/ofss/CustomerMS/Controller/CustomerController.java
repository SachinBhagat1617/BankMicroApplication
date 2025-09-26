package com.ofss.CustomerMS.Controller;
import com.ofss.CustomerMS.DTO.CustomerRequestDTO;
import com.ofss.CustomerMS.DTO.ResponseDTO;
import com.ofss.CustomerMS.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customerId/{customerId}")
    public ResponseEntity<Object> getCustomersById(@PathVariable Long customerId) {
        System.out.println("customerId is "+customerId);
        return customerService.getCustomersById(customerId);
    }
    @PostMapping
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO customerRequest){
        return customerService.createCustomer(customerRequest);
    }
    @PatchMapping("/customerId/{customerId}")
    public ResponseEntity<ResponseDTO> partialUpdateCustomer(@PathVariable Long customerId, @RequestBody @Valid CustomerRequestDTO customerRequest){
        return customerService.partialUpdateCustomer(customerId,customerRequest);
    }
    @DeleteMapping("/customerId/{customerId}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable Long customerId) {
        return customerService.deleteCustomer(customerId);
    }
    @PutMapping("/updateAccountNumber/customerId/{customerId}/accountNumber/{accountNumber}")
    public ResponseEntity<Object> updateAccountNumber(@PathVariable Long customerId, @PathVariable String accountNumber) {
        return customerService.updateAccountNumber(customerId, accountNumber);
    }
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }
}
