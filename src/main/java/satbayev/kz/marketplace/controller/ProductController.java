package satbayev.kz.marketplace.controller;

import lombok.RequiredArgsConstructor;
import satbayev.kz.marketplace.domain.entity.Product;
import satbayev.kz.marketplace.dto.ProductDto;
import satbayev.kz.marketplace.mapper.ProductMapper;
import satbayev.kz.marketplace.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false) String keyword) {
        List<ProductDto> products;
        if (keyword != null && !keyword.isEmpty()) {
            List<Product> productEntities = productService.getAllProducts(keyword);
            products = productEntities.stream()
                    .map(productMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            products = productService.getAllProductsAdmin();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);
        return ResponseEntity.ok("Продукт успешно добавлен!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto) {
        productService.updateProduct(productId, productDto);
        ProductDto updatedProduct = productService.getProductById(productId);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Продукт удален успешно!");
    }
}
