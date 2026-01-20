package com.ecommerce.sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity(name =  "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long categoryId;
    //for mysql database
//      @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "order_sequence")//for psotgressql
//      @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence",allocationSize = 1)
      //marked as primary key
//    @Version
//    private Integer version;

    @NotEmpty(message = "must not be blank")
    @Size(min = 5 ,message = "atleast 5 character")
    private String categoryName;

//    public Category(String categoryName, Long categoryId) {
//        this.categoryId = categoryId;
//        this.categoryName = categoryName;
//
//    }
//
//    public Category() {
//    }

//    public Long getCategoryId() {
//
//        return categoryId;
//
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }


}
