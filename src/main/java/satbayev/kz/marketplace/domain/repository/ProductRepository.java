package satbayev.kz.marketplace.domain.repository;

import satbayev.kz.marketplace.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE"
            + " CONCAT(p.name, ' ', p.category, ' ', p.description)"
            + " LIKE %?1%")
    List<Product> findAll(String keyWord);
}