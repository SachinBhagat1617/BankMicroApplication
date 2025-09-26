package com.ofss.AccountMS.DTO;

import com.ofss.AccountMS.Models.Customer;
import lombok.Data;

@Data
public class CustomerResponse {
    private String message;
    private Customer customer;
}
