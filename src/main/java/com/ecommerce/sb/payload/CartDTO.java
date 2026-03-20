package com.ecommerce.sb.payload;

import com.ecommerce.sb.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CartDTO {

    private Long CartId;
    private  Double totalPrice;
    private List<ProductDTO> products = new ArrayList<>();

}
