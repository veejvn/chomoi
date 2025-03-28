package com.ecommerce.chomoi.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthLogOutRequest {
    @NotNull(message = "Refresh token must be not null")
    String refreshToken;
}
