package satbayev.kz.marketplace.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link satbayev.kz.marketplace.domain.entity.Product}
 */
@Value
public class ProductDto implements Serializable {
    Long productId;
    String name;
    Double price;
    String category;
    Integer stock;
    String description;
    Boolean status;
}