package satbayev.kz.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import satbayev.kz.marketplace.domain.entity.Customer;
import satbayev.kz.marketplace.domain.repository.CustomerRepository;
import satbayev.kz.marketplace.service.customer.CustomerService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserAccountService userAccountService;

    @Override
    public Customer createCustomer(Customer customer) {
        Customer theCustomer = customerRepository.save(customer);
        userAccountService.createUserAccount(theCustomer);
        return theCustomer;
    }
}
