package satbayev.kz.marketplace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satbayev.kz.marketplace.domain.entity.Customer;
import satbayev.kz.marketplace.dto.CustomerDto;
import satbayev.kz.marketplace.dto.RegisterRequestDto;
import satbayev.kz.marketplace.mapper.CustomerMapper;
import satbayev.kz.marketplace.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    private RegisterRequestDto registerRequest;
    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequestDto();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");

        customerDto = new CustomerDto(1L, "John", "Doe", "john.doe@example.com");
    }

    @Test
    void testRegisterCustomer_Success() {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDto);

        ResponseEntity<CustomerDto> response = customerController.registerCustomer(registerRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customerDto.getEmail(), response.getBody().getEmail());
        assertEquals(customerDto.getFirstName(), response.getBody().getFirstName());
        verify(customerService, times(1)).createCustomer(any(Customer.class));
        verify(customerMapper, times(1)).toDto(any(Customer.class));
    }

    @Test
    void testRegisterCustomer_VerifyCustomerCreation() {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDto);

        customerController.registerCustomer(registerRequest);

        verify(customerService, times(1)).createCustomer(argThat(c -> 
            c.getFirstName().equals(registerRequest.getFirstName()) &&
            c.getLastName().equals(registerRequest.getLastName()) &&
            c.getEmail().equals(registerRequest.getEmail()) &&
            c.getPassword().equals(registerRequest.getPassword())
        ));
    }
}

