package satbayev.kz.marketplace.domain.repository;

import satbayev.kz.marketplace.domain.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByCustomer_CustomerId(Long customerId);
    void deleteByCustomer_CustomerIdAndProduct_ProductId(Long customerId, Long productId);

    Basket findByCustomer_CustomerIdAndProduct_ProductId(Long customerId, Long productId);
}