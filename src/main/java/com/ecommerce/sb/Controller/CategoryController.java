package com.ecommerce.sb.Controller;

import com.ecommerce.sb.model.Category;
import com.ecommerce.sb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController   {
     @Autowired
     private CategoryService categoryservice ;


    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryservice.getAllCategories();
       return new ResponseEntity<>(categories ,HttpStatus.FOUND);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createcategory(@RequestBody Category category){
        categoryservice.createcategory(category);
        return new ResponseEntity<>("category added succesfully",HttpStatus.CREATED);
    }
    @DeleteMapping("/api/admin/categories/{categoryId}")
     public ResponseEntity<String> deletecategory(@PathVariable Long categoryId){
       try {
           String status = categoryservice.deletecategory(categoryId);
           return new ResponseEntity<>(status, HttpStatus.OK);
       }
       catch (ResponseStatusException e){
           return  new ResponseEntity<>(e.getReason(),e.getStatusCode());
       }

    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updatecategory(@PathVariable Long categoryId ,@RequestBody Category category){
        try {
            Category savedcategory = categoryservice.updatecategory(category,categoryId);
            return new ResponseEntity<>("category with category id" + categoryId,HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }




}
