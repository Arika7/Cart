package com.example.shoppingcart.excepcion;

public class CartNotFoundException extends NotFoundException{


    public CartNotFoundException(String code, String message, String details) {
        super(code, message, details);
    }
}
