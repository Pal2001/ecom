package com.pal.ecom.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginBody {

    @NotBlank
    @NotNull
    private String userName;
    @NotBlank
    @NotNull
    private String password;
}
