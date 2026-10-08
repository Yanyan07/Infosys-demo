package com.infy.repository;

import com.infy.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByCustomerEmailId(String customerEmailId);
    Optional<Customer> findByCustomerEmailIdAndPassword(String customerEmailId, String password);
}
