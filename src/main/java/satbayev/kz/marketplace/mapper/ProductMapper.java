package satbayev.kz.marketplace.mapper;

import satbayev.kz.marketplace.domain.entity.Product;
import satbayev.kz.marketplace.dto.ProductDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
    ProductDto toDto(Product entity);

    Product toEntity(ProductDto dto);

    List<Product> toEntity(List<ProductDto> dtoList);

    List<ProductDto> toDto(List<Product> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDto productDto, @MappingTarget Product product);
}