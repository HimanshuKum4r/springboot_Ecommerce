package com.ecommerce.sb.service;

import com.ecommerce.sb.payload.CategoryDTO;
import com.ecommerce.sb.payload.CategoryResponse;



public interface CategoryService {

     CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize ,String SortBy, String SortOrder);
     CategoryDTO createcategory(CategoryDTO category);
     CategoryDTO deletecategory(Long categoryId);
     CategoryDTO  updatecategory(CategoryDTO category,Long categoryid);




}
