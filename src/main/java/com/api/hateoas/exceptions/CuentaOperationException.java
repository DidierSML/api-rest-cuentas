package com.api.hateoas.exceptions;

//Por su parte esta (C) está creada para usarse solo en los metodos -Crear y Actualizar-
public class CuentaOperationException extends RuntimeException {

    public CuentaOperationException(String message) {
        super(message);
    }
}
