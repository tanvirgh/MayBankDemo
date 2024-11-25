package com.accord.repository;

import com.accord.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * User : Tanvir Ahmed
 * Date: 11/20/2024.
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE " +
            "(:customerId IS NULL OR a.CUSTOMER_ID = :customerId) AND " +
            "(:accountNumber IS NULL OR a.ACCOUNT_NUMBER = :accountNumber) AND " +
            "(:description IS NULL OR a.DESCRIPTION LIKE %:description%)")
    Page<Account> searchAccounts(String customerId, String accountNumber, String description, Pageable pageable);

}
