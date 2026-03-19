package com.ecommerce.sb.Controller;


import com.ecommerce.sb.payload.CartDTO;
import com.ecommerce.sb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@RestController
@RequestMapping("/api")
public class CartContoller {
    @Autowired
    CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProduct(@PathVariable Long productId,@PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProduct(productId,quantity);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }


}
