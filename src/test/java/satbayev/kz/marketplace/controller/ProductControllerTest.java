package satbayev.kz.marketplace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satbayev.kz.marketplace.domain.entity.Product;
import satbayev.kz.marketplace.dto.ProductDto;
import satbayev.kz.marketplace.mapper.ProductMapper;
import satbayev.kz.marketplace.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto(1L, "Test Product", 100.0, "Category", 10, "Description", true);
        product = new Product(1L, "Test Product", 100.0, "Category", 10, "Description", true);
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(1L)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productDto, response.getBody());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testGetAllProducts_WithoutKeyword() {
        List<ProductDto> products = Arrays.asList(productDto);
        when(productService.getAllProductsAdmin()).thenReturn(products);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(productService, times(1)).getAllProductsAdmin();
        verify(productService, never()).getAllProducts(anyString());
    }

    @Test
    void testGetAllProducts_WithKeyword() {
        String keyword = "test";
        List<Product> products = Arrays.asList(product);
        List<ProductDto> productDtos = Arrays.asList(productDto);

        when(productService.getAllProducts(keyword)).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts(keyword);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(productService, times(1)).getAllProducts(keyword);
        verify(productMapper, times(1)).toDto(product);
    }

    @Test
    void testCreateProduct() {
        doNothing().when(productService).saveProduct(any(ProductDto.class));

        ResponseEntity<String> response = productController.createProduct(productDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Продукт успешно добавлен!", response.getBody());
        verify(productService, times(1)).saveProduct(productDto);
    }

    @Test
    void testUpdateProduct() {
        when(productService.getProductById(1L)).thenReturn(productDto);
        doNothing().when(productService).updateProduct(eq(1L), any(ProductDto.class));

        ResponseEntity<ProductDto> response = productController.updateProduct(1L, productDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(productService, times(1)).updateProduct(1L, productDto);
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<String> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Продукт удален успешно!", response.getBody());
        verify(productService, times(1)).deleteProduct(1L);
    }
}

