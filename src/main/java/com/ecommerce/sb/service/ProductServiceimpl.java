package com.ecommerce.sb.service;

import com.ecommerce.sb.Repositories.CategoryRepository;
import com.ecommerce.sb.Repositories.ProductRepository;
import com.ecommerce.sb.exceptions.ResourceNotFoundException;
import com.ecommerce.sb.model.Category;
import com.ecommerce.sb.model.Product;
import com.ecommerce.sb.payload.CategoryDTO;
import com.ecommerce.sb.payload.ProductDTO;
import com.ecommerce.sb.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceimpl implements ProductService{
    @Autowired
    private CategoryRepository categoryRepository ;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {

        Category category  = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category not found with categoryId "+categoryId));

        product.setImage("default image");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount()*0.01)* product.getPrice());
        product.setSpecialPrice(specialPrice);

         Product savedproduct = productRepository.save(product);

        return modelMapper.map(savedproduct,ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {

        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOList = products.stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);

        return productResponse;
    }
}
