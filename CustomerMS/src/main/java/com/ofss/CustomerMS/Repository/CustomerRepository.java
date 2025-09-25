package com.ofss.CustomerMS.Repository;

import com.ofss.CustomerMS.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    boolean existsByCustomerId(long customerId);

    Optional<Customer> findByCustomerId(Long customerId);
}
