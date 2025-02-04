package org.bankapp.bankingapp.service.Impl;


import jakarta.mail.MessagingException;
import org.bankapp.bankingapp.dto.request.CreateUserAccountDto;
import org.bankapp.bankingapp.entity.User;
import org.bankapp.bankingapp.exceptions.ConflictException;
import org.bankapp.bankingapp.repository.UserRepository;
import org.bankapp.bankingapp.service.EmailService;
import org.bankapp.bankingapp.service.UserService;
import org.bankapp.bankingapp.utils.FileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploader fileUploader;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, FileUploader fileUploader, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileUploader = fileUploader;
        this.emailService = emailService;
    }

    public void createUserAccount(CreateUserAccountDto userAccountPayload) throws IOException {
        Optional<User> getUser = userRepository.findByEmail(userAccountPayload.getEmail());
        if (getUser.isPresent()) {
            throw new ConflictException("User with this email already exist");
        }
        String getProfilePicture = fileUploader.upload(userAccountPayload.getEmail(), userAccountPayload.getProfilePicture());

        User user = User.builder()
                .email(userAccountPayload.getEmail())
                .firstName(userAccountPayload.getFirstName())
                .lastName(userAccountPayload.getLastName())
                .password(passwordEncoder.encode(userAccountPayload.getPassword()))
                .phoneNumber(userAccountPayload.getPhoneNumber())
                .gender(userAccountPayload.getGender())
                .address(userAccountPayload.getAddress())
                .profilePicture(getProfilePicture)
                .role(userAccountPayload.getRole())
                .build();
        userRepository.save(user);
        logger.debug("Created user account: {}", user);

        //Send Mail to user
        String subject = "Account Successfully Created";
        Map<String, Object> emailVariables = new HashMap<>();
        emailVariables.put("firstName", user.getFirstName());
        emailVariables.put("userEmail", user.getEmail());
        try {
            emailService.sendHtmlEmail(user.getEmail(), subject, emailVariables, "registration");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


}
