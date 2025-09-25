package com.ofss.BankMS.Repository;

import com.ofss.BankMS.models.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends CrudRepository<Bank,Long> {
}
