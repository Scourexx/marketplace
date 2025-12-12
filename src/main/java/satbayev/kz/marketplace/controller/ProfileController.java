package satbayev.kz.marketplace.controller;

import lombok.RequiredArgsConstructor;
import satbayev.kz.marketplace.domain.entity.Product;
import satbayev.kz.marketplace.dto.ProductDto;
import satbayev.kz.marketplace.mapper.ProductMapper;
import satbayev.kz.marketplace.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts(
            @RequestParam(required = false) String keyword,
            Authentication authentication) {
        List<Product> products = productService.getAllProducts(keyword);
        List<ProductDto> productDtos = products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfo> getUserInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserInfo userInfo = new UserInfo(userDetails.getUsername());
        
        return ResponseEntity.ok(userInfo);
    }
    
    public static class UserInfo {
        private String username;
        
        public UserInfo(String username) {
            this.username = username;
        }
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
    }
}

