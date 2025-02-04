package org.bankapp.bankingapp.controller;

import org.bankapp.bankingapp.dto.request.AccountDto;
import org.bankapp.bankingapp.dto.response.ResponseAccount;
import org.bankapp.bankingapp.entity.Account;
import org.bankapp.bankingapp.service.Impl.AccountServiceImpl;
import org.bankapp.bankingapp.utils.ResponseHandler;
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
    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }
    //Add Account RestAPI

    @PostMapping
    public ResponseEntity<Object> addAccount(@RequestBody AccountDto accountDto) {
        ResponseAccount data = accountService.createAccount(accountDto);
        return ResponseHandler.response("Account created successfully", HttpStatus.CREATED, data);
    }

    @GetMapping
    public ResponseEntity<Object> getAllAccounts() {

        List<Account> allAccount = accountService.getAllAccount();
        return ResponseHandler.response("Account fetched successfully", HttpStatus.OK, allAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSingleAccount(@PathVariable Long id) throws AccountNotFoundException {
        Optional<Account> getSingleAcct = accountService.getSingleAccountById(id);
        return ResponseHandler.response("Account fetched successfully", HttpStatus.OK, getSingleAcct);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Object> getSingleAccountBalance(@PathVariable Long id) {
        Double getSingleAcctBalance = accountService.getSingleAccountBalance(id);
        return ResponseHandler.response("Account balance fetched successfully", HttpStatus.OK, getSingleAcctBalance);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<Object> deposit(@RequestBody Map<String, Double> depositValue, @PathVariable Long id) {
        Account myAccount = accountService.deposit(depositValue.get("amount"), id);
        return ResponseHandler.response("Deposit successful", HttpStatus.OK, myAccount);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Object> withdraw(@RequestBody Map<String, Double> withdrawValue, @PathVariable Long id) {
        Account myAccount = accountService.withdraw(withdrawValue.get("amount"), id);
        return ResponseHandler.response("Deposit successful", HttpStatus.OK, myAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id) throws AccountNotFoundException {
        accountService.getSingleAccountByAndDelete(id);
        return ResponseHandler.response("Account deleted successful", HttpStatus.OK, null);

    }
}
