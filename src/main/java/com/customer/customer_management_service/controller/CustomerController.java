package com.customer.customer_management_service.controller;

import com.customer.customer_management_service.dto.CustomerRequest;
import com.customer.customer_management_service.dto.CustomerResponse;
import com.customer.customer_management_service.logging.ExecutionTimer;
import com.customer.customer_management_service.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService service;



    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {

        logger.info("POST /customer/add - Request: {}", request);
        ExecutionTimer timer = new ExecutionTimer(logger, "createCustomer");
        CustomerResponse response = service.createCustomer(request);
        logger.info("Customer created - Response: {}", response.getEmail());
        timer.logDuration();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable UUID id) {
        logger.info("GET /customer/id/{} - Fetching customer by ID", id);
        ExecutionTimer timer = new ExecutionTimer(logger, "getById");
        CustomerResponse response = service.getCustomerById(id);
        logger.info("Fetched customer - Response: {}", response.getEmail());
        timer.logDuration();
        return ResponseEntity.ok(response);
    }

    @GetMapping(params = "name")
    public ResponseEntity<CustomerResponse> getByName(@RequestParam String name) {
        logger.info("GET /customer/name/{} - Fetching customer by name", name);
        ExecutionTimer timer = new ExecutionTimer(logger, "getByName");
        CustomerResponse response = service.getCustomerByName(name);
        logger.info("Fetched customer - Response: {}", response.getName());
        timer.logDuration();
        return ResponseEntity.ok(response);
    }

    @GetMapping(params = "email")
    public ResponseEntity<CustomerResponse> getByEmail(@RequestParam String email) {
        logger.info("GET /customer/email/{} - Fetching customer by email", email);
        ExecutionTimer timer = new ExecutionTimer(logger, "getByEmail");
        CustomerResponse response = service.getCustomerByEmail(email);
        logger.info("Fetched customer - Response: {}", response.getName());
        timer.logDuration();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable UUID id, @Valid @RequestBody CustomerRequest request) {
        logger.info("PUT /customer/update/{} - Updating customer", id);
        ExecutionTimer timer = new ExecutionTimer(logger, "updateCustomer");
        CustomerResponse response = service.updateCustomer(id, request);
        logger.info("Updated customer - Response: {}", response.getEmail());
        timer.logDuration();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        logger.info("DELETE /customer/delete/{} - Deleting customer", id);
        ExecutionTimer timer = new ExecutionTimer(logger, "deleteCustomer");
        service.deleteCustomer(id);
        logger.info("Deleted customer with ID: {}", id);
        timer.logDuration();
        return ResponseEntity.noContent().build();
    }

}
