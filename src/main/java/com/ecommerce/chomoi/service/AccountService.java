package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.account.AccountRequest;
import com.ecommerce.chomoi.dto.account.AccountResponse;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.AccountMapper;
import com.ecommerce.chomoi.repository.AccountRepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;
    SecurityUtil securityUtil;
    AccountMapper accountMapper;

    public AccountResponse update(AccountRequest request){
        Account account = securityUtil.getAccount();
        accountMapper.updateAccount(account, request);
        accountRepository.save(account);
        return accountMapper.toAcountResponse(account);
    }

    public AccountResponse getAccount(){
        Account account = securityUtil.getAccount();
        return accountMapper.toAcountResponse(account);
    }
}
