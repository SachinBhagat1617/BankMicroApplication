package com.ofss.CustomerMS.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    private String bankName;
    private String ifscCode;
    private String branchName;
    private String branchAddress;
}
