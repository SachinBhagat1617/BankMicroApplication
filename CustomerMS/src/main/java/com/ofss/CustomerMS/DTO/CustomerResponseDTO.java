package com.ofss.CustomerMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private String accountNumber;
    private String bankName;
    private String bankIfscCode;
}
