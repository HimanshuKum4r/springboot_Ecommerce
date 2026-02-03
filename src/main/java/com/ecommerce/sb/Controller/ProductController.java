package com.ecommerce.sb.Controller;


import com.ecommerce.sb.model.Product;
import com.ecommerce.sb.payload.CategoryResponse;
import com.ecommerce.sb.payload.ProductDTO;
import com.ecommerce.sb.payload.ProductResponse;
import com.ecommerce.sb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product,
                                                 @PathVariable Long categoryId){
        ProductDTO productDTO = productService.addProduct(product,categoryId);

        return new ResponseEntity<>(productDTO , HttpStatus.CREATED);

    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/category/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategories(@PathVariable  Long categoryId){
        ProductResponse response = productService.searchByCategory(categoryId);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }
    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword){

        ProductResponse response  = productService.searchByKeyword(keyword);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }

}
