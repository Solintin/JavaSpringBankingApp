package org.bankapp.bankingapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private Long id;
    private int accountNumber;
    private String accountHolderName;
    private double balance;

}
