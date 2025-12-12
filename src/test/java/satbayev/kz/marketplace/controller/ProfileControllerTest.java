package satbayev.kz.marketplace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import satbayev.kz.marketplace.domain.entity.Product;
import satbayev.kz.marketplace.dto.ProductDto;
import satbayev.kz.marketplace.mapper.ProductMapper;
import satbayev.kz.marketplace.service.product.ProductService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private ProfileController profileController;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Test Product", 100.0, "Category", 10, "Description", true);
        productDto = new ProductDto(1L, "Test Product", 100.0, "Category", 10, "Description", true);
    }

    @Test
    void testGetProducts_WithKeyword() {
        String keyword = "test";
        List<Product> products = Arrays.asList(product);

        when(productService.getAllProducts(keyword)).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        ResponseEntity<List<ProductDto>> response = profileController.getProducts(keyword, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(productService, times(1)).getAllProducts(keyword);
        verify(productMapper, times(1)).toDto(product);
    }

    @Test
    void testGetProducts_WithoutKeyword() {
        List<Product> products = Arrays.asList(product);

        when(productService.getAllProducts(null)).thenReturn(products);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        ResponseEntity<List<ProductDto>> response = profileController.getProducts(null, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(productService, times(1)).getAllProducts(null);
    }

    @Test
    void testGetUserInfo_Success() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        ResponseEntity<ProfileController.UserInfo> response = profileController.getUserInfo(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getUsername());
    }

    @Test
    void testGetUserInfo_Unauthenticated() {
        when(authentication.isAuthenticated()).thenReturn(false);

        ResponseEntity<ProfileController.UserInfo> response = profileController.getUserInfo(authentication);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testGetUserInfo_NullAuthentication() {
        ResponseEntity<ProfileController.UserInfo> response = profileController.getUserInfo(null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}

