package satbayev.kz.marketplace.service.product;

import satbayev.kz.marketplace.dto.BasketDto;

import java.util.List;

public interface BasketService {
    void addToBasket(Long customerId, Long productId, Integer quantity);

    void removeFromBasket(Long customerId, Long productId);

    List<BasketDto> getBasket(Long customerId);
}
