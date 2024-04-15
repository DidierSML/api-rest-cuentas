package com.api.hateoas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//Aún cuando no se vean usos, esta (C) Centraliza las excepciones y ejecuta el manejo de las mismas
@RestControllerAdvice
public class CustomeExceptionHandler extends ResponseEntityExceptionHandler {

    //Ejecuta el manejo de la excepción al no encontrar por (Id)
    @ExceptionHandler(CuentaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)//No Encontrada
    @ResponseBody
    public String manejoExcepcionCuentaNoEncontrada (CuentaNotFoundException exception){

        return exception.getMessage();

    }

    //Ejecuta el manejo de la excepción al haber errores al (Crear y Actualizar)
    @ExceptionHandler(CuentaOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//Error de Requerimiento
    @ResponseBody
    public String manejoExcepcionCUDCuenta (CuentaOperationException exceptionCUD){

        return exceptionCUD.getMessage();
    }



}
