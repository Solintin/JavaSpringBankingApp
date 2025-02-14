package org.bankapp.bankingapp.service.Impl;


import org.bankapp.bankingapp.dto.request.AccountDto;
import org.bankapp.bankingapp.dto.response.ResponseAccount;
import org.bankapp.bankingapp.entity.Account;
import org.bankapp.bankingapp.exceptions.InsufficientFundsException;
import org.bankapp.bankingapp.repository.AccountRepository;
import org.bankapp.bankingapp.service.AccountService;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseAccount createAccount(AccountDto account) {
        Account newAccount = Account.builder()
                .accountHolderName(account.getAccountHolderName())
                .accountNumber(generateUniqueAccountNumber())
                .build();
        accountRepository.save(newAccount);

        return ResponseAccount.builder()
                .accountNumber(newAccount.getAccountNumber())
                .accountHolderName(newAccount.getAccountHolderName())
                .balance(newAccount.getBalance())
                .build();
    }

    public Optional<Account> getSingleAccountById(Long id) throws AccountNotFoundException {
        return Optional.ofNullable(accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account don't exist")));
    }

    public String getSingleAccountByAndDelete(Long id) throws AccountNotFoundException {
        if (accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
            return "Account Deleted";
        } else
            throw new AccountNotFoundException("Account Not Found");
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }


    public Account deposit(double amount, Long id) {
        System.out.println(amount);
        Optional<Account> account = Optional.ofNullable(accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist")));
        account.ifPresent(value -> value.setBalance(value.getBalance() + amount));
        accountRepository.save(account.get());
        return account.get();
    }

    public Account withdraw(double amount, Long id){
        System.out.println(amount);
        Optional<Account> account = Optional.ofNullable(accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist")));
        account.ifPresent(value -> {
            if (value.getBalance() < amount) {
                throw new InsufficientFundsException("Insufficient Balance");
            }
            value.setBalance(value.getBalance() - amount);
            accountRepository.save(account.get());
        });
        return account.get();
    }


    public int generateUniqueAccountNumber() {
        int accountNumber;
        do {
            accountNumber = 1000000000 + new Random().nextInt(900000000);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    public Double getSingleAccountBalance(Long id) {
        Optional<Account> account = Optional.of(accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist")));
        return account.get().getBalance();
    }
}
