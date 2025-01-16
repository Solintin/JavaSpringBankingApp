package org.bankapp.bankingapp.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ResponseAccount {
    private int accountNumber;
    private String accountHolderName;
    private double balance;

}
