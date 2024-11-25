package com.accord.service;

import com.accord.model.Account;
import com.accord.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User : Tanvir Ahmed
 * Date: 11/21/2024.
 */

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;



    public Page<Account> getAccounts(String customerId, String accountNumber, String description, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.searchAccounts(customerId, accountNumber, description, pageable);
    }

    @Transactional
    public Account updateDescription(Long id, String description, Long version) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

        Long currentVersion = (account.getVersion() == null) ? 0 : account.getVersion();
        if (!currentVersion.equals(version)) {
            throw new RuntimeException("Concurrent modification detected");
        }
        account.setDESCRIPTION(description);
        return accountRepository.save(account);
    }
}
