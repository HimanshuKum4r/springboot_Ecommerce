package com.ecommerce.sb.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private String image;
    private Double price;
    private Double Discount;
    private  Double specialPrice;


}
