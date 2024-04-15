package com.api.hateoas.exceptions;

//Esta (C) está diseñada para manejar el metodo -getById-
public class CuentaNotFoundException extends RuntimeException{

    //Constructor que usa la Clase -RuntimeException- y toma su metodo -message-
    public CuentaNotFoundException(String message) {
        super(message);

    }
}

