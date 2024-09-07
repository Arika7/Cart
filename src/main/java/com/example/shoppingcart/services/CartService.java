package com.example.shoppingcart.services;


import com.example.shoppingcart.models.Cart;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.repositories.CartRepo;
import com.example.shoppingcart.repositories.ProductRepo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {


    private final CartRepo cartRepo;
    private final ProductRepo productRepo;

    private final RedisTemplate<Long, Cart> redisTemplate;

    public CartService(CartRepo cartRepo, ProductRepo productRepo, RedisTemplate<Long, Cart> redisTemplate) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.redisTemplate = redisTemplate;
    }

    public Cart getCart (Long id){

        Optional<Cart> cart = Optional.ofNullable(redisTemplate.opsForValue().get(id));
        if (cart.isPresent()) return cart.get();
        else {
            redisTemplate.opsForValue().set(id, cartRepo.findById(id));
            return cartRepo.findById(id);
        }
    }

    public void create(Cart cart){
        cartRepo.save(cart);
        redisTemplate.opsForValue().set(cart.getId(), cart);
    }

    public void addProduct(Long id, Product product){
        Cart cart = cartRepo.findById(id);
        cart.getProducts().add(product);
        cartRepo.save(cart);
        redisTemplate.opsForValue().getAndDelete(id);
    }

    public void removeProduct(Long id, int productId){
        Cart cart = cartRepo.findById(id);
        Product product = productRepo.findById(productId);
        cart.getProducts().remove(product);
        cartRepo.save(cart);
        redisTemplate.opsForValue().getAndDelete(id);
    }
}
