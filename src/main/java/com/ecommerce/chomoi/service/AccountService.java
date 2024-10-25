package com.ecommerce.chomoi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.repository.AccountRepository;
import com.ecommerce.chomoi.dto.account.AccountRequest;
import com.ecommerce.chomoi.dto.account.AccountResponse;
import com.ecommerce.chomoi.mapper.AccountMapper;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;
    SecurityUtil securityUtil;
    AccountMapper accountMapper;

    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }

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
