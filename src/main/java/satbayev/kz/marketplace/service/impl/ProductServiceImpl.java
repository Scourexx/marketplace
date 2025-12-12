package satbayev.kz.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import satbayev.kz.marketplace.domain.entity.Product;
import satbayev.kz.marketplace.domain.repository.ProductRepository;
import satbayev.kz.marketplace.dto.ProductDto;
import satbayev.kz.marketplace.mapper.ProductMapper;
import satbayev.kz.marketplace.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts(String keyWord) {
        List<Product> products;
        if (keyWord != null && !keyWord.isEmpty()) {
            products = productRepository.findAll(keyWord);
        } else {
            products = productRepository.findAll();
        }
        return products;
    }

    @Override
    public List<ProductDto> getAllProductsAdmin() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public void saveProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product.setStatus(true);
        product.setDescription(productDto.getDescription());
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден: " + productId));
        return productMapper.toDto(product);
    }

    @Override
    public void updateProduct(Long productId, ProductDto productDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден: " + productId));
        Product updatedProduct = productMapper.partialUpdate(productDto, existingProduct);
        productRepository.save(updatedProduct);
    }
}
