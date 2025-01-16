package org.bankapp.bankingapp.controller;

import org.bankapp.bankingapp.dto.AccountDto;
import org.bankapp.bankingapp.dto.ResponseAccount;
import org.bankapp.bankingapp.entity.Account;
import org.bankapp.bankingapp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    //Add Account RestAPI

    @PostMapping
    public ResponseEntity<ResponseAccount> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.getAllAccount(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getSingleAccount(@PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.getSingleAccountById(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> getSingleAccountBalance(@PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.getSingleAccountBalance(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@RequestBody Map<String, Double> depositValue, @PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.deposit(depositValue.get("amount"), id), HttpStatus.OK);
    }
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@RequestBody Map<String, Double> withdrawValue, @PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.withdraw(withdrawValue.get("amount"), id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.getSingleAccountByAndDelete(id), HttpStatus.OK);
    }
}
