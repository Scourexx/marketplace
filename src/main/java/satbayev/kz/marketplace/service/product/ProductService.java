package satbayev.kz.marketplace.service.product;

import satbayev.kz.marketplace.domain.entity.Product;
import satbayev.kz.marketplace.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts(String keyword);

    List<ProductDto> getAllProductsAdmin();

    void saveProduct(ProductDto product);

    void deleteProduct(Long productId);

    ProductDto getProductById(Long productId);

    void updateProduct(Long productId, ProductDto productDto);
}
