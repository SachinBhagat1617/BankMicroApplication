package com.ofss.CustomerMS.Repository;

import com.ofss.CustomerMS.models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    boolean existsByCustomerId(long customerId);

    @Query(value = "SELECT * FROM CUSTOMER_MICRO_DB c WHERE c.CUSTOMER_ID = :customerId", nativeQuery = true)
    Optional<Customer> findByCustomerId(@Param("customerId") Long customerId);
}
