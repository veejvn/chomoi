package com.ecommerce.chomoi.mapper;

import com.ecommerce.chomoi.dto.address.AddressRequest;
import com.ecommerce.chomoi.dto.address.AddressResponse;
import com.ecommerce.chomoi.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "account", ignore = true)
//    @Mapping(target = "shop", ignore = true)
    Address toAddress(AddressRequest request);

    AddressResponse toAddressResponse(Address address);

    List<AddressResponse> toListAddressResponse(List<Address> addresses);

    void updateAddress(@MappingTarget Address address, AddressRequest request);
}
