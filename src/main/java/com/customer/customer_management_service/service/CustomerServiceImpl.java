package com.customer.customer_management_service.service;

import com.customer.customer_management_service.dto.CustomerRequest;
import com.customer.customer_management_service.dto.CustomerResponse;
import com.customer.customer_management_service.model.Customer;
import com.customer.customer_management_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements  CustomerService{

    @Autowired
    private CustomerRepository repository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = request.toEntity();
        return new CustomerResponse(repository.save(customer));
    }

    @Override
    public CustomerResponse getCustomerById(UUID id) {
        return repository.findById(id).map(CustomerResponse::new)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    @Override
    public CustomerResponse getCustomerByName(String name) {
        return repository.findByName(name).map(CustomerResponse::new)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        return repository.findByEmail(email).map(CustomerResponse::new)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    @Override
    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        return repository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setEmail(request.getEmail());
            existing.setAnnualSpend(request.getAnnualSpend());
            existing.setLastPurchaseDate(request.getLastPurchaseDate());
            return new CustomerResponse(repository.save(existing));
        }).orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    @Override
    public void deleteCustomer(UUID id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Customer not found");
        }
        repository.deleteById(id);
    }

}
