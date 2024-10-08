package com.example.shoppingcart.services;


import com.example.shoppingcart.excepcion.CartNotFoundException;
import com.example.shoppingcart.excepcion.DataExistsException;
import com.example.shoppingcart.models.Cart;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.repositories.CartRepo;
import com.example.shoppingcart.repositories.ProductRepo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

        return cartRepo.findById(id).orElseThrow(() -> new CartNotFoundException("CART_NOT_FOUND_001", "Cart not found", String.format("Cart with id %s not found", id)));


    }

    public void create(Cart cart){
        if(cartRepo.findById(cart.getId()).isPresent()) {throw new DataExistsException("DATA_EXISTS", "Data Exists", String.format("Cart with id %s exists", cart.getId()));
        }
        cartRepo.save(cart);

        redisTemplate.opsForValue().set(cart.getId(), cart);
    }

    public void addProduct(Long id, Product product){
        Cart cart = cartRepo.findById(id).get();
        cart.getProducts().add(product);
        cartRepo.save(cart);
        redisTemplate.opsForValue().getAndDelete(id);
    }

    public void removeProduct(Long id, int productId){
        Cart cart = cartRepo.findById(id).get();
        Product product = productRepo.findById(productId);
        cart.getProducts().remove(product);
        cartRepo.save(cart);
        redisTemplate.opsForValue().getAndDelete(id);
    }
}
