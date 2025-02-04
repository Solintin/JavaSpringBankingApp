package org.bankapp.bankingapp.dto.request;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    private int accountNumber;
    private String accountHolderName;
    private double balance;

}
