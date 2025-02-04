package org.bankapp.bankingapp.service;

import org.bankapp.bankingapp.dto.request.AccountDto;
import org.bankapp.bankingapp.dto.response.ResponseAccount;
import org.bankapp.bankingapp.entity.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    ResponseAccount createAccount(AccountDto account);
    Optional<Account> getSingleAccountById(Long id) throws AccountNotFoundException;
    String getSingleAccountByAndDelete(Long id) throws AccountNotFoundException;
    List<Account> getAllAccount();
    Double getSingleAccountBalance(Long id);
    Account deposit(double amount, Long id);
    Account withdraw(double amount, Long id);

}
