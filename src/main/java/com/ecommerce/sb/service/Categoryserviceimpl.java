package com.ecommerce.sb.service;

import com.ecommerce.sb.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class Categoryserviceimpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private Long nextid = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories ;
    }

    @Override
    public void createcategory(Category category) {
        category.setCategoryId(nextid++);
        categories.add(category);
    }

    @Override
    public String deletecategory(Long categoryId) {
     Category category = categories.stream().filter(p->p.getCategoryId().equals(categoryId))
             .findFirst().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"resource not found"));

     categories.remove(category);
        return "deleted succesfully";
    }
}
