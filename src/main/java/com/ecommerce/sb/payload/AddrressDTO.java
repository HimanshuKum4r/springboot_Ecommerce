package com.ecommerce.sb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddrressDTO {

    private Long addressId;

    private String street;

    private String buildingName;

    private  String city;

    private String state;

    private String pincode;

    private String country;
}
