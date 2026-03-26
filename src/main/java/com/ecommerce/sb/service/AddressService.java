package com.ecommerce.sb.service;

import com.ecommerce.sb.model.User;
import com.ecommerce.sb.payload.AddrressDTO;

import java.util.List;

public interface AddressService {
    AddrressDTO createAddress(AddrressDTO addressDTO, User user);

    List<AddrressDTO> getAddresses();

    AddrressDTO getAddressById(Long addressId);

    List<AddrressDTO> getUserAddresses(User user);

    AddrressDTO updateAddress(Long addressId, AddrressDTO addrressDTO);

    String deleteAddress(Long addressId);
}
