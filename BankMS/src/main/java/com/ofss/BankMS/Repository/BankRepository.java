package com.ofss.BankMS.Repository;

import com.ofss.BankMS.models.Bank;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends CrudRepository<Bank,Long> {
    Optional<Bank> findByBankName(String bankName);
    @Transactional
    void deleteByBankName(String bankName);
}
