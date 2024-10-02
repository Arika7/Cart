package com.example.shoppingcart.excepcion;

import lombok.Getter;

@Getter
public class ConstraintsViolationError {
    private String property;
    private String message;

    public ConstraintsViolationError(String property, String message) {
        this.property = property;
        this.message = message;
    }
}
