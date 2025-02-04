package org.bankapp.bankingapp.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.bankapp.bankingapp.entity.enums.Gender;
import org.bankapp.bankingapp.entity.enums.Role;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateUserAccountDto {
    @NotBlank(message = "FirstName cannot be blank")
    private String firstName;
    @NotBlank(message = "FirstName cannot be blank")
    private String lastName;
    @NotBlank(message = "FirstName cannot be blank")
    @Email(message = "Email is invalid")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotNull(message = "phoneNumber cannot be null")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;
    @NotNull(message = "phoneNumber cannot be null")
    private String address;
    @NotNull(message = "Gender cannot be blank")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private MultipartFile profilePicture;
    @NotNull(message = "Role cannot be blank")
    @Enumerated(EnumType.STRING)
    private Role role;
}
