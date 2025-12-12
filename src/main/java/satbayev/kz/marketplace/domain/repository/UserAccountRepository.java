package satbayev.kz.marketplace.domain.repository;

import satbayev.kz.marketplace.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findUserAccountByUsername(String username);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.customer.email = :email")
    Optional<UserAccount> findByCustomerEmail(@Param("email") String email);
}