package com.ecommerce.sb.service;

import com.ecommerce.sb.Repositories.CategoryRepository;
import com.ecommerce.sb.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Categoryserviceimpl implements CategoryService {

//    private List<Category> categories = new ArrayList<>();

    private Long nextid = 1L;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();// get all the category in the dtabase
    }

    @Override
    public void createcategory(Category category) {
        category.setCategoryId(null);
        categoryRepository.save(category);
    }

    @Override
    public String deletecategory(Long categoryId) {
        Optional<Category> savedcategory = categoryRepository.findById(categoryId);

        Category optionalcategory = savedcategory.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"category not found"));

        categoryRepository.delete(optionalcategory);
        return "deleted succesfully";
    }

    @Override
    public Category  updatecategory(Category category,Long categoryId){
        Optional<Category> savedcategory = categoryRepository.findById(categoryId);

        Category optionalcatgeory = savedcategory.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"resource not found"));

        optionalcatgeory.setCategoryName(category.getCategoryName());

        return categoryRepository.save(optionalcatgeory);
    }
}
