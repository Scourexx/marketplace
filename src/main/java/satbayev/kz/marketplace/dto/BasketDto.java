package satbayev.kz.marketplace.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link satbayev.kz.marketplace.domain.entity.Basket}
 */
@Value
public class BasketDto implements Serializable {
    Long id;
    CustomerDto customer;
    ProductDto product;
    @NotNull(message = "Not null")
    Integer quantity;
}