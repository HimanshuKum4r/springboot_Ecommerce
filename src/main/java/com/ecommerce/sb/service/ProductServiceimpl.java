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
        Double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01)* product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product savedproduct = productRepository.save(product);

        return modelMapper.map(savedproduct,ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {

        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("no categoreiss");
        }
        List<ProductDTO> productDTOList = products.stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);

        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {

        Category optionalcatgeory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Resource Not Found with id "));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(optionalcatgeory);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products in this Category");
        }

        List<ProductDTO> productDTOList = products.stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(categoryRepository.findById(categoryId));
        productResponse.setContent(productDTOList);

        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
            List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%'+keyword + '%');

            List<ProductDTO> productDTOList = products.stream()
                    .map(product -> modelMapper.map(product,ProductDTO.class)).toList();

            ProductResponse productResponse = new ProductResponse();
            productResponse.setContent(productDTOList);

            return productResponse;
    }
}
