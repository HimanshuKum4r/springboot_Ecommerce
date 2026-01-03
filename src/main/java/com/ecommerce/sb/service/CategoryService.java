package com.ecommerce.sb.service;

import com.ecommerce.sb.model.Category;

import java.util.List;

public interface CategoryService {

     List<Category> getAllCategories();
     void createcategory(Category category);
     String deletecategory(Long categoryId);
     Category  updatecategory(Category category,Long categoryid);




}
