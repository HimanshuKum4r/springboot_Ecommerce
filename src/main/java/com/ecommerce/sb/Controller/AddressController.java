package com.ecommerce.sb.Controller;


import com.ecommerce.sb.model.User;
import com.ecommerce.sb.payload.AddrressDTO;
import com.ecommerce.sb.service.AddressService;
import com.ecommerce.sb.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<AddrressDTO> createAddress(@Valid @RequestBody AddrressDTO addressDTO){
        User user = authUtil.loggedInUser();

        AddrressDTO savedAddress = addressService.createAddress(addressDTO,user);

        return new ResponseEntity<AddrressDTO>(savedAddress, HttpStatus.CREATED);
    }

    @GetMapping("/addresses/all")
    public ResponseEntity<List<AddrressDTO>> getAddresses(){
        List<AddrressDTO> addrressDTOList =  addressService.getAddresses();
        return new ResponseEntity<>(addrressDTOList,HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public  ResponseEntity<AddrressDTO> getAddressById(@PathVariable Long addressId){
       AddrressDTO addrressDTO = addressService.getAddressById(addressId);
       return new ResponseEntity<>(addrressDTO,HttpStatus.FOUND);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddrressDTO>> getUserAddresses(){

        User user = authUtil.loggedInUser();

        List<AddrressDTO> addrressDTOList =  addressService.getUserAddresses(user);
        return new ResponseEntity<>(addrressDTOList,HttpStatus.OK);
    }

    @PostMapping("/addresses/{addressId}")
    public  ResponseEntity<AddrressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddrressDTO addrressDTO){

        AddrressDTO updatedAddress  = addressService.updateAddress(addressId, addrressDTO);

        return  new ResponseEntity<>(updatedAddress,HttpStatus.OK);


    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> updateAddress(@PathVariable Long addressId){
        String status = addressService.deleteAddress(addressId);

        return new ResponseEntity<>(status,HttpStatus.OK);


    }




}
