package com.customer.customer_management_service.dto;

import com.customer.customer_management_service.model.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


public class CustomerRequest {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAnnualSpend() {
        return annualSpend;
    }

    public void setAnnualSpend(BigDecimal annualSpend) {
        this.annualSpend = annualSpend;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    @NotBlank(message="Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    private BigDecimal annualSpend;

    private LocalDate lastPurchaseDate;

    public Customer toEntity(){

        Customer customer = new Customer();
        customer.setName(this.name);
        customer.setEmail(this.email);
        customer.setAnnualSpend(this.annualSpend);
        customer.setLastPurchaseDate(this.lastPurchaseDate);
        return customer;
    }
}
