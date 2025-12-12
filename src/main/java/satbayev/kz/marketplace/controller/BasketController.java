package satbayev.kz.marketplace.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import satbayev.kz.marketplace.dto.BasketDto;
import satbayev.kz.marketplace.service.product.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {
    
    private final BasketService basketService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Map<String, Object>> getBasket(@PathVariable Long customerId) {
        List<BasketDto> basketList = basketService.getBasket(customerId);
        double totalPrice = basketList.stream()
                .mapToDouble(basket -> basket.getProduct().getPrice() * basket.getQuantity())
                .sum();
        
        Map<String, Object> response = new HashMap<>();
        response.put("basketList", basketList);
        response.put("totalPrice", totalPrice);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addToBasket(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            basketService.addToBasket(customerId, productId, quantity);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Товар добавлен в корзину!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Ошибка при добавлении товара в корзину: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, String>> removeFromBasket(
            @RequestParam Long customerId,
            @RequestParam Long productId) {
        try {
            basketService.removeFromBasket(customerId, productId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Товар удалён из корзины!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Ошибка при удалении товара из корзины: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}

