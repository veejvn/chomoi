package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.account.AccountRequest;
import com.ecommerce.chomoi.dto.account.AccountResponse;
import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PutMapping
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(@RequestBody @Valid AccountRequest request) {
        AccountResponse accountResponse = accountService.update(request);
        ApiResponse<AccountResponse> apiResponse = ApiResponse.<AccountResponse>builder()
                .code("account-s-01")
                .data(accountResponse)
                .message("Update account successfully!")
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount() {
        AccountResponse accountResponse = accountService.getAccount();
        ApiResponse<AccountResponse> apiResponse = ApiResponse.<AccountResponse>builder()
                .code("account-s-02")
                .data(accountResponse)
                .message("Get account information successfully!")
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
