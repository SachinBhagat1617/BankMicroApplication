package com.ofss.AccountMS.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private String bankIfscCode;
    private String accountNumber;
    private String bankName;
}
