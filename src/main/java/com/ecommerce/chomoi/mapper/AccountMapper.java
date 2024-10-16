package com.ecommerce.chomoi.mapper;

import com.ecommerce.chomoi.dto.account.AccountRequest;
import com.ecommerce.chomoi.dto.account.AccountResponse;
import com.ecommerce.chomoi.dto.auth.AuthAccountInfoResponse;
import com.ecommerce.chomoi.dto.jwt.JWTPayloadDto;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "roles", target = "scope", qualifiedByName = "rolesToScope")
    JWTPayloadDto toJWTPayloadDto(Account account);
    @org.mapstruct.Named("rolesToScope")
    static String rolesToScope(Set<Role> roles) {
        StringBuilder scopeBuilder = new StringBuilder();
        for (Role role : roles) {
            scopeBuilder.append(String.format("ROLE_%s ", role.name()));
        }
        return scopeBuilder.toString().trim();
    }

    AuthAccountInfoResponse toAccountInfo(Account account);
    AccountResponse toAcountResponse(Account account);
    void updateAccount(@MappingTarget Account account, AccountRequest request);
}