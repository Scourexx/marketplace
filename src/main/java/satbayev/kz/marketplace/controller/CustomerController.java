package satbayev.kz.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import satbayev.kz.marketplace.domain.entity.Customer;
import satbayev.kz.marketplace.dto.CustomerDto;
import satbayev.kz.marketplace.dto.RegisterRequestDto;
import satbayev.kz.marketplace.mapper.CustomerMapper;
import satbayev.kz.marketplace.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> registerCustomer(@Valid @RequestBody RegisterRequestDto registerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(registerRequest.getFirstName());
        customer.setLastName(registerRequest.getLastName());
        customer.setEmail(registerRequest.getEmail());
        customer.setPassword(registerRequest.getPassword());
        
        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerDto responseDto = customerMapper.toDto(createdCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}

