package com.accord.controller;


import com.accord.model.Account;
import com.accord.service.AccountService;
import com.accord.service.dto.UpdateAccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User : Tanvir Ahmed
 * Date: 11/22/2024.
 */
@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping
    public ResponseEntity<Page<Account>> get(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Account> accounts = accountService.getAccounts(customerId, accountNumber, description, page, size);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }


    // Update account description with concurrency control
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateDescription(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAccountRequest updateRequest) {

        try {
            Account updatedAccount = accountService.updateDescription(id, updateRequest.getDescription(), updateRequest.getVersion());
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);  // Conflict due to concurrent modification
        }
    }

}
