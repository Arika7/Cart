package com.example.shoppingcart.excepcion;

public class DataExistsException extends NotFoundException{

    public DataExistsException(String code, String message, String details) {
        super(code, message, details);
    }
}
