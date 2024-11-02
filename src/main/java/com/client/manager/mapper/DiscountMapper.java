package com.client.manager.mapper;

import com.client.manager.dto.DiscountDto;
import com.client.manager.model.entity.Discount;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public interface DiscountMapper {

    @Mapping(target = "client", ignore = true)
    Discount toEntity(DiscountDto discountDto);

    @Mapping(target = "client", ignore = true)
    DiscountDto toDto(Discount discount);

    @AfterMapping
    default void handleClient(Discount discount, @MappingTarget DiscountDto discountDto) {
        if (discount.getClient() != null) {
            discountDto.setClientId(discount.getClient().getClientId());
        }
    }

}
