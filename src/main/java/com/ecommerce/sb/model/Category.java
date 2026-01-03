package com.ecommerce.sb.model;

import jakarta.persistence.*;

import java.util.Optional;
@Entity(name =  "categories") //tabla name
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
    private String categoryName;

    public Category(String categoryName, Long categoryId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;

    }

    public Category() {
    }

    public Long getCategoryId() {

        return categoryId;

    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
