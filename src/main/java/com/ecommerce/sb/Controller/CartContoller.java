package com.ecommerce.sb.Controller;


import com.ecommerce.sb.Repositories.CartRepository;
import com.ecommerce.sb.model.Cart;
import com.ecommerce.sb.payload.CartDTO;
import com.ecommerce.sb.service.CartService;
import com.ecommerce.sb.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CartContoller {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProduct(@PathVariable Long productId,@PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProduct(productId,quantity);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts(){
        List<CartDTO> cartDTOS = cartService.getALlCarts();

        return new ResponseEntity<List<CartDTO>>(cartDTOS,HttpStatus.FOUND);
    }

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getcartById(){
        String email = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(email);
        Long cartId = cart.getCartId();

        CartDTO cartDTO = cartService.getCart(email,cartId);
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.FOUND);

    }

    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updatecartproduct(@PathVariable Long productId,
                                                     @PathVariable String operation){
        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,operation.equalsIgnoreCase("delete")?-1:1);

        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId){
        String status = cartService.deleteProductFromCart(cartId,productId);

        return ResponseEntity.ok().body(status);
    }

}
