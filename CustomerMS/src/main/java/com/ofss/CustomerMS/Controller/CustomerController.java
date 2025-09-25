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
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/customerId")
    public ResponseEntity<ResponseDTO> getCustomersById(@RequestParam Long customerId) {
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
}
