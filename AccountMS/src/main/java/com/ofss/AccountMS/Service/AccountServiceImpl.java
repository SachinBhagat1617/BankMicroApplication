package com.ofss.AccountMS.Service;

import com.ofss.AccountMS.DTO.AccountRequestDTO;
import com.ofss.AccountMS.DTO.AccountResponseDTO;
import com.ofss.AccountMS.DTO.ResponseDTO;
import com.ofss.AccountMS.Models.Account;
import com.ofss.AccountMS.Models.AccountType;
import com.ofss.AccountMS.Models.Customer;
import com.ofss.AccountMS.Repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    private final WebClient customerWebClient;

    @Override
    public ResponseEntity<ResponseDTO> createAccount(AccountRequestDTO accountRequest) {

        // Validate Aadhaar
        if(!validateAadhaar(accountRequest.getAadhaarNumber())){
            return ResponseEntity.badRequest().body(new ResponseDTO("Invalid Aadhaar number", 400, null));
        }
        // Validate PAN
        if(!validatePAN(accountRequest.getPanNumber())){
            return ResponseEntity.badRequest().body(new ResponseDTO("Invalid PAN number", 400, null));
        }
        // Validate initial deposit based on account type
        if(!validateInitialDeposit(accountRequest.getInitialDeposit(), accountRequest.getAccountType())){
            return ResponseEntity.badRequest().body(new ResponseDTO("Insufficient initial deposit for " + accountRequest.getAccountType() + " account", 400, null));
        }
        // Generate unique account number
        String accountNumber = generateAccountNumber();
        Customer customer = getCustomerById(accountRequest.getCustomerId());
        System.out.println("Customer fetched: " + (customer != null ? customer.getCustomerName() : "null"));
        if(customer==null){
            return ResponseEntity.badRequest().body(new ResponseDTO("Customer not found with ID: " + accountRequest.getCustomerId(), 400, null));
        }
        Account newAccount = Account.builder()
                .accountNumber(accountNumber)
                .accountType(accountRequest.getAccountType())
                .balance(accountRequest.getInitialDeposit())
                .bankName(customer.getBankName())
                .aadhaarNumber(accountRequest.getAadhaarNumber())
                .panNumber(accountRequest.getPanNumber())
                .ifscCode(customer.getBankIfscCode())
                .build();
        System.out.println("New Account: " + newAccount.getBankName());
        Customer updatedCustomer = updateAccountNumberInCustomerService(accountRequest.getCustomerId(), accountNumber);
        if(updatedCustomer == null){
            return ResponseEntity.status(500).body(new ResponseDTO("Failed to update customer with account number", 500, null));
        }
        accountRepository.save(newAccount);
        return ResponseEntity.ok(
                new ResponseDTO("Account created successfully",
                        201,mapToAccountResponseDTO(newAccount)));
    }


    //------------------------------Helper Methods----------------------------------
    private boolean validateAadhaar(String aadhaarNumber){
        // Validate Aadhaar number (12 digits)
        return aadhaarNumber != null && aadhaarNumber.matches("\\d{12}");
    }
    private boolean validatePAN(String panNumber){
        // Validate PAN number (5 letters, 4 digits, 1 letter)
        return panNumber != null && panNumber.matches("[A-Z]{5}\\d{4}[A-Z]");
    }
    private boolean validateInitialDeposit(Double initialDeposit, AccountType accountType){
        // Validate initial deposit based on account type
        if(initialDeposit == null || accountType == null){
            return false;
        }
        switch (accountType) {
            case SAVINGS:
                return initialDeposit >= 500.00;
            case CURRENT:
                return initialDeposit >= 1000.00;
            case BUSINESS:
                return initialDeposit >= 5000.00;
            default:
                return false;
        }
    }

    private String generateAccountNumber() {
        String accountNumber;
        do {
            //  using current time in milliseconds
            String millis = String.valueOf(System.currentTimeMillis());
            String last12 = millis.substring(millis.length() - 12); // take last 12 digits
            int random = (int)(Math.random() * 10000); // 0–9999 (4 digits)
            accountNumber = "ACCT" + last12 + String.format("%04d", random);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private Customer getCustomerById(Long customerId){
        if(customerId == null){
            return null;
        }
        return customerWebClient.get()
                .uri("/api/v1/customers/customerId/{customerId}" , customerId)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
    }
    //// Important: This method is not used currently but can be used in future if needed
    private Customer updateAccountNumberInCustomerService(Long customerId, String accountNumber){
        if(customerId == null || accountNumber == null){
            return null;
        }
        return customerWebClient
                .put()
                .uri("/api/v1/customers/updateAccountNumber/customerId/{customerId}/accountNumber/{accountNumber}" , customerId , accountNumber)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();

    }
    private AccountResponseDTO mapToAccountResponseDTO(Account account){
        if(account == null){
            return null;
        }
        return AccountResponseDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .bankName(account.getBankName())
                .bankIfscCode(account.getIfscCode())
                .build();
    }
}
