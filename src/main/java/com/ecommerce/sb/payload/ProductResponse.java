package com.ecommerce.sb.payload;

import com.ecommerce.sb.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    List<ProductDTO> content;

    Optional<Category> category;


}
