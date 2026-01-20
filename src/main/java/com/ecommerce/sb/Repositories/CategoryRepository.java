package com.ecommerce.sb.Repositories;

import com.ecommerce.sb.model.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{


    Category findByCategoryName(@NotEmpty(message = "must not be blank") @Size(min = 5 ,message = "atleast 5 character") String categoryName);
}
