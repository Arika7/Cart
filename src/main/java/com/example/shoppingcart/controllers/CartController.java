package com.example.shoppingcart.controllers;


import com.example.shoppingcart.models.Cart;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.services.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public Cart getShoppingCart(@PathVariable("id") Long id){
        return cartService.getCart(id);
    }

    @PostMapping
    public void addCart(@RequestBody Cart cart){
        cartService.create(cart);
    }

    @PostMapping("{id}/product")
    public void addProductToCart(@PathVariable("id") Long id,@RequestBody Product product){
        cartService.addProduct(id,product);
    }
    @DeleteMapping("{id}/product/{productId}")
    public void removeProductFromCart (@PathVariable("id")Long id, @PathVariable("productId")int productId){
        cartService.removeProduct(id, productId);
    }
}
