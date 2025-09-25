package com.ofss.BankMS.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankRequestDTO {
    private String bankName;
    private String ifscCode;
    private String branchName;
    private String branchAddress;   
}
