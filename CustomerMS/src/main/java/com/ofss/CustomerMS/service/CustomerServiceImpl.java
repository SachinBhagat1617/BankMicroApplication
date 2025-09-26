package com.ofss.CustomerMS.service;

import com.ofss.CustomerMS.DTO.CustomerRequestDTO;
import com.ofss.CustomerMS.DTO.CustomerResponseDTO;
import com.ofss.CustomerMS.DTO.ResponseDTO;
import com.ofss.CustomerMS.Repository.CustomerRepository;
import com.ofss.CustomerMS.models.Bank;
import com.ofss.CustomerMS.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final WebClient bankWebClient;
    @Override
    public ResponseEntity<ResponseDTO> getAllCustomers() {
        Iterable<Customer> customers =  customerRepository.findAll();
        if(((List<Customer>) customers).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.builder()
                    .message("No customers found")
                    .data(null)
                    .build());
        }
        return ResponseEntity.ok(ResponseDTO.builder()
                .message("Customers retrieved successfully")
                .data(customers)
                .build());
    }

    @Override
    public ResponseEntity<ResponseDTO> createCustomer(CustomerRequestDTO customerRequest) {
        if(customerRequest.getBankName()==null || customerRequest.getBankName().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder()
                    .message("Bank name must be provided")
                    .data(null)
                    .build());
        }
        Bank bank = getBank(customerRequest.getBankName());
        if(bank==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder()
                    .message("Bank with name "+customerRequest.getBankName()+" does not exist")
                    .data(null)
                    .build());
        }
        try {
            Customer newCustomer=Customer.builder()
                    .customerId(generateUniqueCustomerId())
                    .customerName(customerRequest.getCustomerName())
                    .customerEmail(customerRequest.getCustomerEmail())
                    .customerAddress(customerRequest.getCustomerAddress())
                    .accountNumber("00000000000")
                    .customerPhone(customerRequest.getCustomerPhone())
                    .bankIfscCode(bank.getIfscCode())
                    .bankName(bank.getBankName())
                    .build();
            customerRepository.save(newCustomer);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.builder()
                    .message("Customer created successfully")
                    .data(newCustomer)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder()
                    .message("An error occurred while creating the customer")
                    .data(null)
                    .build());
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> partialUpdateCustomer(Long customerId, CustomerRequestDTO customerRequest) {
        if (customerId == null || customerId < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder()
                    .message("Customer ID must be provided in the path variable")
                    .data(null)
                    .build());
        }
        Optional<Customer> prevCustomer = customerRepository.findByCustomerId(customerId);
        if (prevCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.builder()
                    .message("Customer not found with customerId: " + customerId)
                    .data(null)
                    .build());
        }
        Customer existingCustomer = prevCustomer.get();
        if (customerRequest.getCustomerName() != null) {
            existingCustomer.setCustomerName(customerRequest.getCustomerName());
        }
        if (customerRequest.getCustomerEmail() != null) {
            existingCustomer.setCustomerEmail(customerRequest.getCustomerEmail());
        }
        if (customerRequest.getCustomerPhone() != null) {
            existingCustomer.setCustomerPhone(customerRequest.getCustomerPhone());
        }
        if (customerRequest.getCustomerAddress() != null) {
            existingCustomer.setCustomerAddress(customerRequest.getCustomerAddress());
        }
        // Bank name change is not allowed for customer
        if (customerRequest.getBankName() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder()
                    .message("Bank name change is not allowed for customer")
                    .data(null)
                    .build());
        }
        customerRepository.save(existingCustomer);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message("Customer updated successfully")
                .data(getCustomerResponseDTO(existingCustomer))
                .build());
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteCustomer(Long customerId) {
        if (customerId == null || customerId < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder()
                    .message("Customer ID must be provided in the path variable")
                    .data(null)
                    .build());
        }
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.builder()
                    .message("Customer not found with customerId: " + customerId)
                    .data(null)
                    .build());
        }
        customerRepository.delete(existingCustomer.get());
        return ResponseEntity.ok(ResponseDTO.builder()
                .message("Customer deleted successfully")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<Object> getCustomersById(Long customerId) {
        if ( customerId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder()
                    .message("Customer ID must be provided in the request parameter")
                    .data(null)
                    .build());
        }

        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isPresent()) {
            return ResponseEntity.ok(existingCustomer.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.builder()
                    .message("Customer not found with customerId: " + customerId)
                    .data(null)
                    .build());
        }
    }

    @Override
    public ResponseEntity<Object> updateAccountNumber(Long customerId, String accountNumbers) {
        if (customerId == null || customerId < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder()
                    .message("Customer ID must be provided in the request parameter")
                    .data(null)
                    .build());
        }
        if (accountNumbers == null || accountNumbers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder()
                    .message("Account number must be provided in the request parameter")
                    .data(null)
                    .build());
        }
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        System.out.println(existingCustomer.get().getCustomerName());
        if (existingCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO.builder()
                    .message("Customer not found with customerId: " + customerId)
                    .data(null)
                    .build());
        }
        Customer customer = existingCustomer.get();
        customer.setAccountNumber(accountNumbers);
        Customer savedCustomer=customerRepository.save(customer);
        return ResponseEntity.ok(savedCustomer);
    }


    //----------------------------------------------------------------------------------------------
    // helper methods are listed bellow
    public Bank getBank(String bankName) {
        try {
            return bankWebClient.get()
                    .uri("api/v1/banks/name/{bankName}", bankName)
                    .retrieve()
                    .bodyToMono(Bank.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CustomerResponseDTO getCustomerResponseDTO(Customer customer) {
        return CustomerResponseDTO.builder()
                .customerName(customer.getCustomerName())
                .customerEmail(customer.getCustomerEmail())
                .customerPhone(customer.getCustomerPhone())
                .customerAddress(customer.getCustomerAddress())
                .accountNumber(customer.getAccountNumber())
                .bankIfscCode(customer.getBankIfscCode())
                .bankName(customer.getBankName())
                .build();
    }
    private Long generateUniqueCustomerId() {
        long customerId;
        do {
            customerId = 10000000L + (long)(Math.random() * 90000000L);
        } while (customerRepository.existsByCustomerId(customerId));
        return customerId;
    }

}
