package satbayev.kz.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import satbayev.kz.marketplace.domain.entity.Basket;
import satbayev.kz.marketplace.domain.entity.Customer;
import satbayev.kz.marketplace.domain.entity.Product;
import satbayev.kz.marketplace.domain.repository.BasketRepository;
import satbayev.kz.marketplace.domain.repository.CustomerRepository;
import satbayev.kz.marketplace.domain.repository.ProductRepository;
import satbayev.kz.marketplace.dto.BasketDto;
import satbayev.kz.marketplace.mapper.BasketMapper;
import satbayev.kz.marketplace.service.product.BasketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final BasketMapper basketMapper;

    @Override
    @Transactional
    public void addToBasket(Long customerId, Long productId, Integer quantity) {
        log.info("Проверка: customerId={}, productId={}, quantity={}", customerId, productId, quantity);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Пользователь с ID " + customerId + " не найден"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт с ID " + productId + " не найден"));

        log.info("Пользователь и продукт найдены: customerId={}, productId={}", customerId, productId);

        Basket existingBasket = basketRepository.findByCustomer_CustomerIdAndProduct_ProductId(customerId, productId);

        if (existingBasket != null) {
            log.info("Обновление записи в корзине: customerId={}, productId={}, текущий quantity={}",
                    customerId, productId, existingBasket.getQuantity());
            existingBasket.setQuantity(existingBasket.getQuantity() + quantity);
            basketRepository.save(existingBasket);
        } else {
            log.info("Создание новой записи в корзине: customerId={}, productId={}, quantity={}", customerId, productId, quantity);
            Basket basket = new Basket();
            basket.setCustomer(customer);
            basket.setProduct(product);
            basket.setQuantity(quantity);
            basketRepository.save(basket);
        }
    }




    @Override
    @Transactional
    public void removeFromBasket(Long customerId, Long productId) {
        log.info("customerId = " + customerId + ", productId = " + productId);
        basketRepository.deleteByCustomer_CustomerIdAndProduct_ProductId(customerId, productId);
    }


    @Override
    public List<BasketDto> getBasket(Long customerId) {
        List<Basket> baskets = basketRepository.findByCustomer_CustomerId(customerId);
        return basketMapper.toDto(baskets);
    }
}
