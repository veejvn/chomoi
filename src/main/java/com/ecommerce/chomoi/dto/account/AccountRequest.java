package com.ecommerce.chomoi.dto.account;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {

    @NotBlank(message = "Display name is required")
    String displayName;

    @NotNull(message = "Date of birth is required")
    LocalDate dob;

    @NotBlank(message = "Avatar is required")
    String avatar;

    @NotBlank(message = "Phone number is required")
    String phoneNumber;

}
