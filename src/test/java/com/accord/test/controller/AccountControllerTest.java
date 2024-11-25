package com.accord.test.controller;

import com.accord.model.Account;
import com.accord.repository.AccountRepository;
import com.accord.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


/**
 * User : Tanvir Ahmed
 * Date: 11/21/2024.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountControllerTest {

    @Mock
    private AccountRepository accountRepository;  // Mock repository

    @InjectMocks
    private AccountService accountService;  // Inject service

    private Pageable pageable;
    private Account account;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Create a sample account and pageable object for the test
        account = new Account();
        account.setACCOUNT_NUMBER("8872838283");
        account.setTRX_AMOUNT(new BigDecimal("123.00"));
        account.setDESCRIPTION("FUND TRANSFER");
        account.setTRX_DATE(LocalDate.of(2019, 9, 12));
        account.setTRX_TIME(LocalTime.of(11, 11, 11));
        account.setCUSTOMER_ID("222");

        pageable = PageRequest.of(0, 10);  // Create a pageable object
    }

    @Test
    public void testGetAccounts() {
        // Prepare a Page<Account> to be returned by the mock repository
        Page<Account> mockPage = new PageImpl<>(List.of(account));

        // Mock repository to return the mocked page
        when(accountRepository.searchAccounts("222", "8872838283", "FUND TRANSFER", pageable)).thenReturn(mockPage);

        // Call the service method
        Page<Account> result = accountService.getAccounts("222", "8872838283", "FUND TRANSFER", 0, 10);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("FUND TRANSFER", result.getContent().get(0).getDESCRIPTION());

        // Verify the repository method was called with the correct arguments
        verify(accountRepository, times(1)).searchAccounts("222", "8872838283", "FUND TRANSFER", pageable);
    }



    @Test
    public void testUpdateDescription_Success() {
        // Arrange: Create a mock account
        Account account = new Account();
        account.setId(1L);
        account.setDESCRIPTION("Old Description");
        account.setVersion(1L); // Set initial version

        // Mock findById to return the existing account
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Mock save to return the updated account
        Account updatedAccount = new Account();
        updatedAccount.setId(1L);
        updatedAccount.setDESCRIPTION("Updated Description");
        updatedAccount.setVersion(1L); // Version remains the same

        when(accountRepository.save(any(Account.class))).thenReturn(updatedAccount);

        // Act: Perform the update method
        Account result = accountService.updateDescription(1L, "Updated Description", 1L);

        // Assert: Validate the results
        assertNotNull(result);
        assertEquals("Updated Description", result.getDESCRIPTION());
        assertEquals(Long.valueOf(1L), result.getVersion());

        // Verify the repository save method was called exactly once
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testUpdateDescription_ConcurrentModificationException() {
        // Mocking the repository to return the existing account when findById is called
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // New description but with a different version (to simulate concurrency issue)
        String newDescription = "Updated Description";
        Long wrongVersion = 2L;  // Wrong version

        // Perform the update method and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.updateDescription(1L, newDescription, wrongVersion);
        });

        // Assert the exception message
        assertEquals("Concurrent modification detected", exception.getMessage());

        // Verify that save was not called because of the version mismatch
        verify(accountRepository, times(0)).save(account);
    }

    @Test
    public void testUpdateDescription_AccountNotFound() {
        // Mocking the repository to return empty Optional (account not found)
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // New description and version
        String newDescription = "Updated Description";
        Long newVersion = 1L;

        // Perform the update method and expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.updateDescription(1L, newDescription, newVersion);
        });

        // Assert the exception message
        assertEquals("Account not found", exception.getMessage());

        // Verify that save was not called
        verify(accountRepository, times(0)).save(account);
    }
}
