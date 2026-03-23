package com.ecommerce.sb.service;

import com.ecommerce.sb.payload.CartDTO;
import jakarta.transaction.Transactional;

import java.util.List;


public interface CartService {
    CartDTO addProduct(Long productId, Integer quantity);

    List<CartDTO> getALlCarts();

    CartDTO getCart(String email, Long cartId);

    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    String deleteProductFromCart(Long cartId, Long productId);
}
