package com.ecommerce.sb.service;

import com.ecommerce.sb.model.Product;
import com.ecommerce.sb.payload.ProductDTO;
import com.ecommerce.sb.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchByKeyword(String keyword);
}
