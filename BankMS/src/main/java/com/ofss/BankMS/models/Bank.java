package com.ofss.BankMS.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BANK_MICRO_DB")
@Builder
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_ID")
    private Long bankId;
    @Column(name = "BANK_NAME")
    private String bankName;
    @Column(name = "IFSC_CODE")
    private String ifscCode;
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @Column(name = "BRANCH_ADDRESS")
    private String branchAddress;
}
