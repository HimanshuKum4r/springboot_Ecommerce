package com.ecommerce.sb.Controller;

import com.ecommerce.sb.config.AppConstants;
import com.ecommerce.sb.payload.CategoryDTO;
import com.ecommerce.sb.payload.CategoryResponse;
import com.ecommerce.sb.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CategoryController   {
     @Autowired
     private CategoryService categoryservice ;


    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                             @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required =   false) Integer pageSize,
                                                             @RequestParam(name = "SortBy" ,defaultValue = AppConstants.SORT_BY,required = false) String SortBy,
                                                             @RequestParam(name = "SortOrder",defaultValue = AppConstants.SORT_ORDER,required = false) String SortOrder){
        CategoryResponse categoriesresponse = categoryservice.getAllCategories(pageNumber,pageSize,SortBy,SortOrder);
         return new ResponseEntity<>(categoriesresponse ,HttpStatus.OK);
    }


    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createcategory(@Valid @RequestBody CategoryDTO category){
       CategoryDTO savedcategory =  categoryservice.createcategory(category);
        return new ResponseEntity<>("category added succesfully " + savedcategory,HttpStatus.CREATED);
    }
    @DeleteMapping("/api/admin/categories/{categoryId}")
     public ResponseEntity<String> deletecategory(@PathVariable Long categoryId){

           CategoryDTO deletedcategory= categoryservice.deletecategory(categoryId);
           return new ResponseEntity<>("category deleted with categoryname "+deletedcategory.getCategoryName(), HttpStatus.OK);
    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updatecategory(@PathVariable Long categoryId ,@Valid @RequestBody CategoryDTO categorydto){

            CategoryDTO savedcategory = categoryservice.updatecategory(categorydto,categoryId);
            return new ResponseEntity<>("category with category id " + savedcategory,HttpStatus.OK);
    }




}
