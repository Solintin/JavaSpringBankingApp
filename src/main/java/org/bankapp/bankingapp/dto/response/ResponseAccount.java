package org.bankapp.bankingapp.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseAccount {
    private int accountNumber;
    private String accountHolderName;
    private double balance;

}
