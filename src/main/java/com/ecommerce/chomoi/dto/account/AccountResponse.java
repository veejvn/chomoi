package com.ecommerce.chomoi.dto.account;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    String id;
    String email;
    String password;
    String displayName;
    LocalDate dob;
    String avatar;
    String phoneNumber;
    String providerName;
    String providerId;
    Boolean isLocal;
}
