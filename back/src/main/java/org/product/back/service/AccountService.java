package org.product.back.service;

import org.product.back.entity.Account;
import org.product.back.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
