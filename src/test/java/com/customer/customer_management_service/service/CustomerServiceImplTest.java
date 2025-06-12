package com.customer.customer_management_service.service;

import com.customer.customer_management_service.dto.CustomerRequest;
import com.customer.customer_management_service.dto.CustomerResponse;
import com.customer.customer_management_service.model.Customer;
import com.customer.customer_management_service.model.Tier;
import com.customer.customer_management_service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_shouldReturnCustomerResponse() {
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setAnnualSpend(BigDecimal.valueOf(12000));
        request.setLastPurchaseDate(LocalDate.now().minusMonths(3));

        Customer savedCustomer = request.toEntity();
        savedCustomer.setId(UUID.randomUUID());

        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerResponse response = service.createCustomer(request);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(Tier.PLATINUM, response.getTier());
        verify(repository, times(1)).save(any(Customer.class));
    }

    @Test
    void getCustomerById_shouldReturnCustomerResponse() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Jane Smith");
        customer.setEmail("jane@example.com");
        customer.setAnnualSpend(BigDecimal.valueOf(500));

        when(repository.findById(id)).thenReturn(Optional.of(customer));

        CustomerResponse response = service.getCustomerById(id);

        assertNotNull(response);
        assertEquals("Jane Smith", response.getName());
        assertEquals(Tier.SILVER, response.getTier());
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomerResponse() {
        UUID id = UUID.randomUUID();
        Customer existing = new Customer();
        existing.setId(id);
        existing.setName("Old Name");
        existing.setEmail("old@example.com");

        CustomerRequest request = new CustomerRequest();
        request.setName("New Name");
        request.setEmail("new@example.com");
        request.setAnnualSpend(BigDecimal.valueOf(3000));
        request.setLastPurchaseDate(LocalDate.now().minusMonths(8));

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        CustomerResponse response = service.updateCustomer(id, request);

        assertEquals("New Name", response.getName());
        assertEquals("new@example.com", response.getEmail());
        assertEquals(Tier.GOLD, response.getTier());
    }

    @Test
    void deleteCustomer_shouldCallRepositoryDelete() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.deleteCustomer(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void tierCalculation_shouldReturnCorrectTier() {
        Customer customer = new Customer();
        customer.setName("Tier Tester");
        customer.setEmail("tier@example.com");

        // Silver
        customer.setAnnualSpend(BigDecimal.valueOf(200));
        customer.setLastPurchaseDate(LocalDate.now().minusYears(2));
        assertEquals(Tier.SILVER, new CustomerResponse(customer).getTier());

        // Gold
        customer.setAnnualSpend(BigDecimal.valueOf(3000));
        customer.setLastPurchaseDate(LocalDate.now().minusMonths(8));
        assertEquals(Tier.GOLD, new CustomerResponse(customer).getTier());

        // Platinum
        customer.setAnnualSpend(BigDecimal.valueOf(15000));
        customer.setLastPurchaseDate(LocalDate.now().minusMonths(4));
        assertEquals(Tier.PLATINUM, new CustomerResponse(customer).getTier());
    }

    @Test
    void emailValidation_shouldFailWithInvalidEmail() {
        CustomerRequest request = new CustomerRequest();
        request.setName("Invalid Email");
        request.setEmail("not-an-email");

        assertFalse(request.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"));
    }


}
