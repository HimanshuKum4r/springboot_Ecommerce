package com.ecommerce.sb.service;

import com.ecommerce.sb.Repositories.CategoryRepository;
import com.ecommerce.sb.Repositories.ProductRepository;
import com.ecommerce.sb.exceptions.ResourceNotFoundException;
import com.ecommerce.sb.model.Category;
import com.ecommerce.sb.model.Product;
import com.ecommerce.sb.payload.ProductDTO;
import com.ecommerce.sb.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceimpl implements ProductService{
    @Autowired
    private CategoryRepository categoryRepository ;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

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
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy,String sortOrder) {

        Sort SortbyandOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pagedetails = PageRequest.of(pageNumber,pageSize,SortbyandOrder);
        Page<Product> productspage = productRepository.findAll(pagedetails);

        List<Product> products = productspage.getContent();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("NO products");
        }
        List<ProductDTO> productDTOList = products.stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setPageNumber(productspage.getNumber());
        productResponse.setPageSize(productspage.getSize());
        productResponse.setTotalelements(productspage.getTotalElements());
        productResponse.setTotalpages(productspage.getTotalPages());
        productResponse.setLastpage(productspage.isLast());
        productResponse.setContent(productDTOList);

        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy,String sortOrder) {
        Category optionalcatgeory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Resource Not Found with id "));

        Sort sortByandOrder = sortOrder.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByandOrder);
        Page<Product> productspage = productRepository.findByCategory(optionalcatgeory,pageable);

        List<Product> products = productspage.getContent();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products in this Category");
        }

        List<ProductDTO> productDTOList = products.stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setPageNumber(productspage.getNumber());
        productResponse.setPageSize(productspage.getSize());
        productResponse.setTotalelements(productspage.getTotalElements());
        productResponse.setTotalpages(productspage.getTotalPages());
        productResponse.setLastpage(productspage.isLast());
        productResponse.setCategory(categoryRepository.findById(categoryId));
        productResponse.setContent(productDTOList);

        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword,Integer pageNumber, Integer pageSize, String sortBy,String sortOrder) {

        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByandOrder);
        Page<Product> productspage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword + '%',pageable);

        List<Product> products = productspage.getContent();

            List<ProductDTO> productDTOList = products.stream()
                    .map(product -> modelMapper.map(product,ProductDTO.class)).toList();

            ProductResponse productResponse = new ProductResponse();
            productResponse.setPageNumber(productspage.getNumber());
            productResponse.setPageSize(productspage.getSize());
            productResponse.setTotalelements(productspage.getTotalElements());
            productResponse.setTotalpages(productspage.getTotalPages());
            productResponse.setLastpage(productspage.isLast());
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

        String filename = fileService.UploadImage(image);

        savedproduct.setImage(filename);
        productRepository.save(savedproduct);

        return modelMapper.map(savedproduct,ProductDTO.class);
    }





}
