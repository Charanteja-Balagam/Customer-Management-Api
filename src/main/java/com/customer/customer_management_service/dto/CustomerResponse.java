package com.customer.customer_management_service.dto;

import com.customer.customer_management_service.model.Customer;
import com.customer.customer_management_service.model.Tier;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class CustomerResponse {

    private UUID id;

    public UUID getId() {
        return id;
    }



    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public BigDecimal getAnnualSpend() {
        return annualSpend;
    }



    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }







    private String name;
    private String email;
    private BigDecimal annualSpend;
    private LocalDate lastPurchaseDate;
    private Tier tier;

    public Tier getTier() {
        return tier;
    }



    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.annualSpend = customer.getAnnualSpend();
        this.lastPurchaseDate = customer.getLastPurchaseDate();
        this.tier =  tierType(customer);
    }

    private Tier tierType(Customer customer) {
        BigDecimal spend = customer.getAnnualSpend() != null ? customer.getAnnualSpend() : BigDecimal.ZERO;
        LocalDate purchaseDate = customer.getLastPurchaseDate();
        LocalDate now = LocalDate.now();
        if (spend.compareTo(BigDecimal.valueOf(10000)) >= 0 && purchaseDate != null && purchaseDate.isAfter(now.minusMonths(6))) {
            return Tier.PLATINUM;
        } else if (spend.compareTo(BigDecimal.valueOf(1000)) >= 0 && purchaseDate != null && purchaseDate.isAfter(now.minusMonths(12))) {
            return Tier.GOLD;
        } else {
            return Tier.SILVER;
        }
    }

}
