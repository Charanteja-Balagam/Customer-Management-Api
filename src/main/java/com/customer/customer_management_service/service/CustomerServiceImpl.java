package com.customer.customer_management_service.service;

import com.customer.customer_management_service.dto.CustomerRequest;
import com.customer.customer_management_service.dto.CustomerResponse;
import com.customer.customer_management_service.model.Customer;
import com.customer.customer_management_service.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements  CustomerService{

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository repository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        logger.info("Creating customer with email: {}", request.getEmail());
        Customer customer = request.toEntity();
        Customer saved = repository.save(customer);
        logger.debug("Saved customer with ID: {}", saved.getId());
        return new CustomerResponse(saved);
    }

    @Override
    public CustomerResponse getCustomerById(UUID id) {
        logger.info("Fetching customer by ID: {}", id);
        return repository.findById(id).map(CustomerResponse::new)
                .orElseThrow(() -> {
                    logger.warn("Customer not found with ID: {}", id);
                    return new NoSuchElementException("Customer not found");
                });
    }

    @Override
    public CustomerResponse getCustomerByName(String name) {
        logger.info("Fetching customer by name: {}", name);
        return repository.findByName(name).map(CustomerResponse::new)
                .orElseThrow(() -> {
                    logger.warn("Customer not found with name: {}", name);
                    return new NoSuchElementException("Customer not found");
                });
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        logger.info("Fetching customer by email: {}", email);
        return repository.findByEmail(email).map(CustomerResponse::new)
                .orElseThrow(() -> {
                    logger.warn("Customer not found with email: {}", email);
                    return new NoSuchElementException("Customer not found");
                });
    }

    @Override
    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        logger.info("Updating customer with ID: {}", id);
        return repository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setEmail(request.getEmail());
            existing.setAnnualSpend(request.getAnnualSpend());
            existing.setLastPurchaseDate(request.getLastPurchaseDate());
            Customer updated = repository.save(existing);
            logger.debug("Updated customer with ID: {}", updated.getId());
            return new CustomerResponse(updated);
        }).orElseThrow(() -> {
            logger.warn("Customer not found with ID: {}", id);
            return new NoSuchElementException("Customer not found");
        });
    }

    @Override
    public void deleteCustomer(UUID id) {
        logger.info("Deleting customer with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Attempted to delete non-existent customer with ID: {}", id);
            throw new NoSuchElementException("Customer not found");
        }
        repository.deleteById(id);
        logger.debug("Deleted customer with ID: {}", id);
    }

}
