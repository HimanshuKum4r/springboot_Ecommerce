package com.ecommerce.sb.service;


import com.ecommerce.sb.Repositories.AddressRepository;
import com.ecommerce.sb.Repositories.UserRepository;
import com.ecommerce.sb.exceptions.ResourceNotFoundException;
import com.ecommerce.sb.model.Address;
import com.ecommerce.sb.model.User;
import com.ecommerce.sb.payload.AddrressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public AddrressDTO createAddress(AddrressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);

        List<Address> addressesList = user.getAddresses();

        addressesList.add(address);
        user.setAddresses(addressesList);

        address.setUser(user);
        Address savedAddress = addressRepository.save(address);



        return modelMapper.map(savedAddress, AddrressDTO.class);


    }

    @Override
    public List<AddrressDTO> getAddresses() {
        List<Address> addresses = addressRepository.findAll();

        return addresses.stream().map(address -> modelMapper.map(address,AddrressDTO.class)).toList();
    }

    @Override
    public AddrressDTO getAddressById(Long addressId) {
         Address address = addressRepository.findById(addressId)
                 .orElseThrow(()->new ResourceNotFoundException("address with id " + addressId + " not found"));

        return modelMapper.map(address,AddrressDTO.class);
    }

    @Override
    public List<AddrressDTO> getUserAddresses(User user) {
        List<Address> addresses = user.getAddresses();
        return addresses.stream().map(address -> modelMapper.map(address,AddrressDTO.class)).toList();

    }

    @Override
    public AddrressDTO updateAddress(Long addressId, AddrressDTO addressDTO) {
        Address addressfromDB = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("address with id " + addressId + " not found"));

        addressfromDB.setCity(addressDTO.getCity());
        addressfromDB.setPincode(addressDTO.getPincode());
        addressfromDB.setCountry(addressDTO.getCountry());
        addressfromDB.setBuildingName(addressDTO.getBuildingName());
        addressfromDB.setState(addressDTO.getState());
        addressfromDB.setStreet(addressDTO.getStreet());

        Address updatedAddress = addressRepository.save(addressfromDB);
        User user = addressfromDB.getUser();

        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);

        userRepository.save(user);

        return modelMapper.map(updatedAddress,AddrressDTO.class);

    }

    @Override
    public String deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("address with id " + addressId + " not found"));

        User user  = address.getUser();
        user.getAddresses().removeIf(add -> add.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(address);

        return "address deleted succesfully with addressId " + addressId;
    }
}
