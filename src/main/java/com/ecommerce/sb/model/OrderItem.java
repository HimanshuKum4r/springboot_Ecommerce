package com.ecommerce.sb.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    private Integer quantity;

    private  double discount;

    private  double orderedProductPrice;




}
