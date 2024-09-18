package com.example.shoppingcart.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.shoppingcart.models.Cart;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.repositories.CartRepo;
import com.example.shoppingcart.repositories.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

public class CartServiceTest {

    @Mock
    private CartRepo cartRepo;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private RedisTemplate<Long, Cart> redisTemplate;

    @Mock
    private ValueOperations<Long, Cart> valueOperations;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testGetCart_WhenCartIsInCache() {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(valueOperations.get(cartId)).thenReturn(cart);

        Cart result = cartService.getCart(cartId);

        assertEquals(cart, result);
        verify(valueOperations, times(1)).get(cartId);
        verify(cartRepo, never()).findById(cartId);
    }

    @Test
    public void testGetCart_WhenCartIsNotInCache() {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(valueOperations.get(cartId)).thenReturn(null);
        when(cartRepo.findById(cartId)).thenReturn(cart);

        Cart result = cartService.getCart(cartId);

        assertEquals(cart, result);
        verify(valueOperations, times(1)).get(cartId);
        verify(cartRepo, times(1)).findById(cartId);
        verify(valueOperations, times(1)).set(cartId, cart);
    }

    @Test
    public void testCreateCart() {
        Cart cart = new Cart();
        cart.setId(1L);

        cartService.create(cart);

        verify(cartRepo, times(1)).save(cart);
        verify(valueOperations, times(1)).set(cart.getId(), cart);
    }

    @Test
    public void testAddProduct() {
        Long cartId = 1L;
        Product product = new Product();
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepo.findById(cartId)).thenReturn(cart);

        cartService.addProduct(cartId, product);

        assertTrue(cart.getProducts().contains(product));
        verify(cartRepo, times(1)).save(cart);
        verify(valueOperations, times(1)).getAndDelete(cartId);
    }

    @Test
    public void testRemoveProduct() {
        Long cartId = 1L;
        int productId = 2;
        Product product = new Product();
        Cart cart = new Cart();

        when(cartRepo.findById(cartId)).thenReturn(cart);
        when(productRepo.findById(productId)).thenReturn(product);

        cartService.removeProduct(cartId, productId);

        assertFalse(cart.getProducts().contains(product));
        verify(cartRepo, times(1)).save(cart);
        verify(valueOperations, times(1)).getAndDelete(cartId);
    }
}
