package com.ecommerce.sb.payload;

import com.ecommerce.sb.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private Long CartId;
    private  Double totalPrice;
    private List<ProductDTO> products = new ArrayList<>();

}
