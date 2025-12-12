package satbayev.kz.marketplace.service.auth;


import satbayev.kz.marketplace.domain.entity.Customer;
import satbayev.kz.marketplace.domain.entity.UserAccount;

public interface AccountService {
    UserAccount createUserAccount(Customer customer);
}
