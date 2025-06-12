package com.customer.customer_management_service.controller;

import com.customer.customer_management_service.dto.CustomerRequest;
import com.customer.customer_management_service.dto.CustomerResponse;
import com.customer.customer_management_service.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCustomer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<CustomerResponse> getByName(@RequestParam String name) {
        return ResponseEntity.ok(service.getCustomerByName(name));
    }

    @GetMapping(params = "email")
    public ResponseEntity<CustomerResponse> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.getCustomerByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable UUID id, @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(service.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
