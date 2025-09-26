package com.ofss.AccountMS.Repository;

import com.ofss.AccountMS.Models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    boolean existsByAccountNumber(String accountNumber);
}
