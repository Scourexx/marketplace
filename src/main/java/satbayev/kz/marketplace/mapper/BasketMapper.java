package satbayev.kz.marketplace.mapper;

import satbayev.kz.marketplace.domain.entity.Basket;
import satbayev.kz.marketplace.dto.BasketDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BasketMapper extends EntityMapper<BasketDto, Basket> {
    BasketDto toDto(Basket entity);

    Basket toEntity(BasketDto dto);

    List<Basket> toEntity(List<BasketDto> dtoList);

    List<BasketDto> toDto(List<Basket> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Basket partialUpdate(BasketDto basketDto, @MappingTarget Basket basket);
}
