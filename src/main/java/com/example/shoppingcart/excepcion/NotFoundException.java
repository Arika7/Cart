package com.example.shoppingcart.excepcion;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private String code;
    private String message;
    private String details;


    public NotFoundException(String code, String message, String details) {
        super(details);
        this.code = code;
        this.message = message;
        this.details = details;
    }
}
