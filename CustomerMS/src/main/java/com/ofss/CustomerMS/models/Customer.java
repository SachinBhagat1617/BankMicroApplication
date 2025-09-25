package com.ofss.CustomerMS.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "CUSTOMER_MICRO_DB")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long customerId;

    @NotBlank(message = "Customer name is mandatory")
    @Size(min = 2, max = 60, message = "Customer name must be between 2 and 100 characters")
    private String customerName;

    @Email
    private String customerEmail;

    @NotBlank
    @Size(min = 10, max = 13, message = "Customer phone number must be between 10 and 15 characters")
    private String customerPhone;

    @NotBlank
    @Size(min = 10, max = 200, message = "Customer address must be between 10 and 200 characters")
    private String customerAddress;

    private String bankIfscCode;

    @Column(unique = true)
    private String accountNumber;// this we have to implement
    private String bankName;
}