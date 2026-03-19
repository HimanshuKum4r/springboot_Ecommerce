package com.ecommerce.sb.service;

import com.ecommerce.sb.payload.CartDTO;


public interface CartService {
    CartDTO addProduct(Long productId, Integer quantity);
}
