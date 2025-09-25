package com.ofss.CustomerMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRequestDTO {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private String bankName;
}
