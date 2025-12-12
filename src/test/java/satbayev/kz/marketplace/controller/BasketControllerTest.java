package satbayev.kz.marketplace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satbayev.kz.marketplace.dto.BasketDto;
import satbayev.kz.marketplace.dto.CustomerDto;
import satbayev.kz.marketplace.dto.ProductDto;
import satbayev.kz.marketplace.service.product.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketControllerTest {

    @Mock
    private BasketService basketService;

    @InjectMocks
    private BasketController basketController;

    private BasketDto basketDto;
    private ProductDto productDto;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto(1L, "Test Product", 100.0, "Category", 10, "Description", true);
        customerDto = new CustomerDto(1L, "John", "Doe", "john@example.com");
        basketDto = new BasketDto(1L, customerDto, productDto, 2);
    }

    @Test
    void testGetBasket_Success() {
        List<BasketDto> basketList = Arrays.asList(basketDto);
        when(basketService.getBasket(1L)).thenReturn(basketList);

        ResponseEntity<Map<String, Object>> response = basketController.getBasket(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("basketList"));
        assertTrue(response.getBody().containsKey("totalPrice"));
        assertEquals(200.0, (Double) response.getBody().get("totalPrice"));
        verify(basketService, times(1)).getBasket(1L);
    }

    @Test
    void testGetBasket_EmptyBasket() {
        List<BasketDto> emptyList = Arrays.asList();
        when(basketService.getBasket(1L)).thenReturn(emptyList);

        ResponseEntity<Map<String, Object>> response = basketController.getBasket(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0.0, (Double) response.getBody().get("totalPrice"));
    }

    @Test
    void testAddToBasket_Success() {
        doNothing().when(basketService).addToBasket(anyLong(), anyLong(), anyInt());

        ResponseEntity<Map<String, String>> response = basketController.addToBasket(1L, 1L, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Товар добавлен в корзину!", response.getBody().get("message"));
        verify(basketService, times(1)).addToBasket(1L, 1L, 2);
    }

    @Test
    void testAddToBasket_Error() {
        doThrow(new RuntimeException("Product not found")).when(basketService)
                .addToBasket(anyLong(), anyLong(), anyInt());

        ResponseEntity<Map<String, String>> response = basketController.addToBasket(1L, 999L, 2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("error"));
        verify(basketService, times(1)).addToBasket(1L, 999L, 2);
    }

    @Test
    void testRemoveFromBasket_Success() {
        doNothing().when(basketService).removeFromBasket(anyLong(), anyLong());

        ResponseEntity<Map<String, String>> response = basketController.removeFromBasket(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Товар удалён из корзины!", response.getBody().get("message"));
        verify(basketService, times(1)).removeFromBasket(1L, 1L);
    }

    @Test
    void testRemoveFromBasket_Error() {
        doThrow(new RuntimeException("Item not found in basket")).when(basketService)
                .removeFromBasket(anyLong(), anyLong());

        ResponseEntity<Map<String, String>> response = basketController.removeFromBasket(1L, 999L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("error"));
        verify(basketService, times(1)).removeFromBasket(1L, 999L);
    }
}

