package satbayev.kz.marketplace.mapper;

import satbayev.kz.marketplace.domain.entity.UserAccount;
import satbayev.kz.marketplace.dto.UserAccountDto;
import satbayev.kz.marketplace.dto.UserDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserAccountMapper extends EntityMapper<UserAccountDto, UserAccount> {

    UserAccount toEntity(UserAccountDto dto);

    UserAccountDto toDto(UserAccount entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserAccount partialUpdate(UserAccountDto userAccountDto, @MappingTarget UserAccount userAccount);
}