package satbayev.kz.marketplace.domain.repository;

import satbayev.kz.marketplace.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.userAccount.userAccountId = :userAccountId")
    Role findByUserAccountId(Long userAccountId);
}