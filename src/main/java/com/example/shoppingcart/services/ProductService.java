package com.example.shoppingcart.services;


import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.repositories.ProductRepo;
import com.example.shoppingcart.utility.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepo repo;

    @Autowired
    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }
    public List<Product> findByPriceRange (int price1, int price2){
        return repo.findByPriceBetween(price1,price2);
    }
    public void create (Product product) {
        repo.save(product);
    }

    public List<Product> returnAll(){
        return repo.findAll();
    }


    public List<Product> findAbove(int price){
        return repo.findByPriceAfter(price);
    }
    public List<Product> findBelow(int price){
        return repo.findByPriceBefore(price);
    }

    public int findByCategory(Categories category){
        return repo.findByCategory(category).size();
    }
}
