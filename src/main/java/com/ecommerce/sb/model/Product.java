package com.ecommerce.sb.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@ToString
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long productId;
     @NotBlank
     @Size(min = 5,message = "product description must contain atleast 6 character")
    private String productName;

    private Integer quantity;
    private String description;
    @NotNull
    private double price;
    private String image;
    private double Discount;
    private  Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();

}
