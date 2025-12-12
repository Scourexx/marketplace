package satbayev.kz.marketplace.mapper;

import satbayev.kz.marketplace.domain.entity.Customer;
import satbayev.kz.marketplace.dto.CustomerDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {
    CustomerDto toDto(Customer entity);

    Customer toEntity(CustomerDto dto);

    List<Customer> toEntity(List<CustomerDto> dtoList);

    List<CustomerDto> toDto(List<Customer> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer partialUpdate(CustomerDto customerDto, @MappingTarget Customer customer);
}