package com.example.shoppingcart.models;

import com.example.shoppingcart.utility.Categories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Product")
@Data
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    String name;
    int price;
    Categories category;

    String description;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;
}
