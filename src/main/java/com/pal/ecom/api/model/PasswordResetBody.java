package com.pal.ecom.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetBody {

    @NotBlank
    @NotNull
    private String token;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$") // Minimum 8 char (one digit, one char, one special char)
    @Size(min=8, max=32)
    private String password;
}
