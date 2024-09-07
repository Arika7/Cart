package com.example.shoppingcart.repositories;

import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.utility.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    List<Product> findByPriceBetween(int price1,int price2);

    Product findById(int id);

    @Query(value = "SELECT e from Product e")
    List<Product> findAll();

    List<Product> findByPriceAfter(int price);

    List<Product> findByPriceBefore(int price);

    List<Product> findByCategory(Categories categories);
}
