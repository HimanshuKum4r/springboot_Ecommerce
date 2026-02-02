package com.ecommerce.sb.service;

import com.ecommerce.sb.Repositories.CategoryRepository;
import com.ecommerce.sb.exceptions.ResourceNotFoundException;
import com.ecommerce.sb.exceptions.UserAlreadyExistException;
import com.ecommerce.sb.model.Category;
import com.ecommerce.sb.payload.CategoryDTO;
import com.ecommerce.sb.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class Categoryserviceimpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize,String SortBy, String SortOrder) {
        Sort SortByandOrder = SortOrder.equalsIgnoreCase("asc") ? Sort.by(SortBy).ascending():Sort.by(SortBy).descending();


        Pageable pagedetails = PageRequest.of(pageNumber, pageSize, SortByandOrder);//representing page details
        Page<Category> categoryPage = categoryRepository.findAll(pagedetails);

        List<Category> savedcategories = categoryPage.getContent(); //getcontent will return the list of categories
        if(savedcategories.isEmpty())
            throw new ResourceNotFoundException("no categories are created");
        List<CategoryDTO> categoryDTOS = savedcategories.stream().map(category -> modelMapper.map(category,CategoryDTO.class)).toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalelements(categoryPage.getTotalElements());
        categoryResponse.setTotalpages(categoryPage.getTotalPages());
        categoryResponse.setLastpage(categoryPage.isLast());
        categoryResponse.setResponse(categoryDTOS);
        return categoryResponse;

        // get all the category in the dtabase
    }

    @Override
    public CategoryDTO createcategory(CategoryDTO categorydtoreq) {
        Category category = modelMapper.map(categorydtoreq,Category.class);
        Category savedcategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedcategory != null)
            throw new UserAlreadyExistException("user already exist");
          Category c = categoryRepository.save(category);

          return modelMapper.map(c,CategoryDTO.class);

    }

    @Override
    public CategoryDTO deletecategory(Long categoryId) {
        Optional<Category> savedcategory = categoryRepository.findById(categoryId);

        Category optionalcategory = savedcategory.orElseThrow(()->new ResourceNotFoundException("category not found or it may be deleted"));

        categoryRepository.delete(optionalcategory);

        return modelMapper.map(optionalcategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updatecategory(CategoryDTO categorydto,Long categoryId){

        Category category = modelMapper.map(categorydto,Category.class);
        Category optionalcatgeory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Resource Not Found with id " + categoryId));

        optionalcatgeory.setCategoryName(category.getCategoryName());
        Category categoryupdate =  categoryRepository.save(optionalcatgeory);

        return modelMapper.map(categoryupdate,CategoryDTO.class);
    }
}
