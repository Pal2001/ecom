package com.pal.ecom.api.model;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class RegistrationBody {
    @NotNull
    @NotBlank
    @Size(min=4, max=32) // Minimum 4 char
    private String userName;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$") // Minimum 8 char (one digit, one char, one special char)
    @Size(min=8, max=32)
    private String password;
    @NotNull
    @NotBlank
    @Email
    private String email;
}
