package org.bankapp.bankingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bankapp.bankingapp.entity.enums.AccountType;

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
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
