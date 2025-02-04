package org.bankapp.bankingapp.service;

import org.bankapp.bankingapp.dto.request.AccountDto;
import org.bankapp.bankingapp.dto.request.CreateUserAccountDto;
import org.bankapp.bankingapp.dto.response.ResponseAccount;
import org.bankapp.bankingapp.entity.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUserAccount(CreateUserAccountDto createUserPayload) throws IOException;


}
