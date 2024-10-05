package com.ecommerce.chomoi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRegisterRequest {
    @NotNull(message = "Email is required")
    @Email(message  = "Email invalid")
    public String email;
    @NotNull(message = "Password is required")
    @Size(min = 4, message = "Password must be longer than 4 letters")
    public String password;
}
