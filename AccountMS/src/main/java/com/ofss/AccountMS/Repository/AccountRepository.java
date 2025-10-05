package com.ofss.AccountMS.Repository;

import com.ofss.AccountMS.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(String accountNumber);

    boolean existsByAadhaarNumber(String aadhaarNumber);

    boolean existsByPanNumber(String panNumber);

    Optional<Account> findByAccountNumber(String accountNumber);
}
