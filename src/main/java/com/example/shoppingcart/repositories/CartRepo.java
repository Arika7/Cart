package com.example.shoppingcart.repositories;


import com.example.shoppingcart.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {


    Cart findById(Long id);
}
