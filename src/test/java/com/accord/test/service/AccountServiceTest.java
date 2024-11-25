package com.accord.test.service;

import com.accord.model.Account;
import com.accord.repository.AccountRepository;
import com.accord.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * User : Tanvir Ahmed
 * Date: 11/21/2024.
 */
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    public AccountServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccounts() {
        // Arrange
        String customerId = "123";
        String accountNumber = "456";
        String description = "Test Account";
        int page = 0, size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Account account = new Account();
        Page<Account> mockPage = new PageImpl<>(Collections.singletonList(account));
        when(accountRepository.searchAccounts(customerId, accountNumber, description, pageable)).thenReturn(mockPage);

        // Act
        Page<Account> result = accountService.getAccounts(customerId, accountNumber, description, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(accountRepository, times(1)).searchAccounts(customerId, accountNumber, description, pageable);
    }

    @Test
    void testUpdateDescriptionSuccess() {
        // Arrange
        Long accountId = 1L;
        String newDescription = "Updated Description";
        Long version = 1L;

        Account account = new Account();
        account.setId(accountId);
        account.setDESCRIPTION("Old Description");
        account.setVersion(version);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        Account updatedAccount = accountService.updateDescription(accountId, newDescription, version);

        // Assert
        assertNotNull(updatedAccount);
        assertEquals(newDescription, updatedAccount.getDESCRIPTION());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testUpdateDescriptionAccountNotFound() {
        // Arrange
        Long accountId = 1L;
        String newDescription = "Updated Description";
        Long version = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.updateDescription(accountId, newDescription, version);
        });
        assertEquals("Account not found", exception.getMessage());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testUpdateDescriptionConcurrentModification() {
        // Arrange
        Long accountId = 1L;
        String newDescription = "Updated Description";
        Long version = 1L;

        Account account = new Account();
        account.setId(accountId);
        account.setDESCRIPTION("Old Description");
        account.setVersion(2L); // Different version to simulate conflict

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.updateDescription(accountId, newDescription, version);
        });
        assertEquals("Concurrent modification detected", exception.getMessage());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).save(any());
    }
}

