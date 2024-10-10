package com.ecommerce.chomoi.dto.address;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse{
    String id;
    String province;
    String district;
    String ward;
    String detail;
    Boolean isDefault;
    String receiverName;
    String receiverPhone;
}
