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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceimpl implements ProductService{
    @Autowired
    private CategoryRepository categoryRepository ;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO product1, Long categoryId) {

        Product product =modelMapper.map(product1,Product.class);

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
            throw new ResourceNotFoundException("NO products");
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

    @Override
    public ProductDTO updateProduct(Long productId,ProductDTO product1) {

        Product product = modelMapper.map(product1,Product.class);

        Product savedproduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found to replace"));

       savedproduct.setProductName(product.getProductName());
       savedproduct.setPrice(product.getPrice());
       savedproduct.setDiscount(product.getDiscount());
       savedproduct.setQuantity(product.getQuantity());
       savedproduct.setDescription(product.getDescription());
       savedproduct.setSpecialPrice(product.getSpecialPrice());

       productRepository.save(savedproduct);

        return modelMapper.map(savedproduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product savedproduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found to replace"));

        productRepository.delete(savedproduct);
        return modelMapper.map(savedproduct,ProductDTO.class);

    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {

        Product savedproduct = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("product not found"));

         String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
         Path path = Paths.get("uploads/",filename);

        Files.createDirectory(path.getParent());
        image.transferTo(path);

        savedproduct.setImage(filename);
        productRepository.save(savedproduct);

        return modelMapper.map(savedproduct,ProductDTO.class);
    }
}
