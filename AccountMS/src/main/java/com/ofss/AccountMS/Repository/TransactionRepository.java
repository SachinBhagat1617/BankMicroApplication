package com.ofss.AccountMS.Repository;

import com.ofss.AccountMS.Models.Transactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transactions,Long> {
}
