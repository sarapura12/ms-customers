package com.client.manager.mapper;

import com.client.manager.dto.ClientDto;
import com.client.manager.model.entity.Client;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = DiscountMapper.class)
public interface ClientMapper {

    @Mapping(target = "discount", ignore = true)
    Client toEntity(ClientDto clientDto);

    @Mapping(target = "discount", ignore = true)
    ClientDto toDto(Client client);

    @AfterMapping
    default void handleDiscount(Client client, @MappingTarget ClientDto clientDto) {
        if (client.getDiscount() != null) {
            clientDto.setDiscountId(client.getDiscount().getDiscoundId());
        }
    }
}
