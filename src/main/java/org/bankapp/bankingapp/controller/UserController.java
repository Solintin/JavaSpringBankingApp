package org.bankapp.bankingapp.controller;

import jakarta.validation.Valid;
import org.bankapp.bankingapp.dto.request.CreateUserAccountDto;
import org.bankapp.bankingapp.service.UserService;
import org.bankapp.bankingapp.utils.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createAccount(@Valid @ModelAttribute CreateUserAccountDto request) throws IOException {
        try {
            userService.createUserAccount(request);
            return ResponseHandler.response("User Created Successfully", HttpStatus.CREATED, null);

        } catch (Exception e) {
            return ResponseHandler.response(e.getMessage(), HttpStatus.BAD_REQUEST, null);

        }
    }
}
