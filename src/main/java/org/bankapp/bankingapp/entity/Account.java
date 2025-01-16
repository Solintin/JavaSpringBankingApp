package org.bankapp.bankingapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
@NamedQuery(name= "Account.findAll", query = "SELECT a from Account a")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private double balance = 0.00;
    @Column(name = "account_holder_name")
    private String accountHolderName;
    private int accountNumber;


}
