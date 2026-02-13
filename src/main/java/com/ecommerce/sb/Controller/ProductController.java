package com.ecommerce.sb.Controller;


import com.ecommerce.sb.config.AppConstants;
import com.ecommerce.sb.payload.ProductDTO;
import com.ecommerce.sb.payload.ProductResponse;
import com.ecommerce.sb.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO product, @PathVariable Long categoryId){
        ProductDTO productDTO = productService.addProduct(product,categoryId);

        return new ResponseEntity<>(productDTO , HttpStatus.CREATED);

    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                          @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required =   false) Integer pageSize,
                                                          @RequestParam(name = "sortBy" ,defaultValue = AppConstants.PRODUCT_SORTBY,required = false) String sortBy,
                                                          @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder  ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/category/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategories(@PathVariable  Long categoryId,
                                                                  @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                                  @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required =   false) Integer pageSize,
                                                                  @RequestParam(name = "sortBy" ,defaultValue = AppConstants.PRODUCT_SORTBY,required = false) String sortBy,
                                                                  @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder){
        ProductResponse response = productService.searchByCategory(categoryId,pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }
    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword, @RequestParam(name = "pageNumber" ,defaultValue = AppConstants.PAGE_NUMBER, required =false ) Integer pageNumber,
                                                               @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                               @RequestParam(name = "sortBy", defaultValue = AppConstants.PRODUCT_SORTBY,required = false) String sortBy,
                                                               @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER ) String sortOrder)
    {

        ProductResponse response  = productService.searchByKeyword(keyword,pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }
    @PutMapping("/admin/products/{ProductId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long ProductId, @Valid@RequestBody ProductDTO product){
        ProductDTO productDTO = productService.updateProduct(ProductId,product);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @DeleteMapping ("/admin/products/{ProductId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long ProductId){
        ProductDTO productDTO = productService.deleteProduct(ProductId);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/{productId}/image")
    public ResponseEntity<ProductDTO> uploadProductImage(@PathVariable Long productId, @RequestParam("image")MultipartFile  image) throws IOException {

        ProductDTO updatedproduct = productService.updateProductImage(productId,image);
        return new ResponseEntity<>(updatedproduct,HttpStatus.OK);
    }


}
