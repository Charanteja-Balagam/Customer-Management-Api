package com.customer.customer_management_service.repository;

import com.customer.customer_management_service.dto.CustomerRequest;
import com.customer.customer_management_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {


    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Customer> findByName(@Param("name") String name);


    Optional<Customer> findByEmail(String email);
}
