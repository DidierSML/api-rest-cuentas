package com.api.hateoas.service;

import com.api.hateoas.exceptions.CuentaNotFoundException;
import com.api.hateoas.exceptions.CuentaOperationException;
import com.api.hateoas.model.Cuenta;
import com.api.hateoas.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CuentaService {

    //Inyectando CuentaRepository Interface
    private final CuentaRepository cuentaRepository;



    //Método Guardar Cuenta
    public Cuenta guardarCuenta (Cuenta cuenta){

        if (cuenta.getNumeroDeCuenta() == null || cuenta.getMonto() == null) {
            throw new CuentaOperationException("El número de cuenta y el monto son campos obligatorios");
        }

        return cuentaRepository.save(cuenta);
    }

    //Método Obtener todas las cuentas
    public List<Cuenta> obtenerCuentas(){

        return cuentaRepository.findAll();
    }

    //Método Obtener cuenta * id

    /*public Cuenta obtenerCuentaPorId (Integer id) throws NotFoundCusotmeException{
            if(!cuentaRepository.existById(id)){
                throw new NotFoundCustomeException("Esta Cuenta no existe en nuestra BD");
                }
            cuentaRepository.deleteById(id);
    */
    public Cuenta obtenerCuentaPorId (Integer id){

        return cuentaRepository.findById(id).orElseThrow
                (()-> new CuentaNotFoundException("Esta Cuenta no existe en nuestra BD"));

    }

    //Método Actualizar cuenta * id
    public Cuenta acutalizarCuenta (Integer id, Cuenta cuenta){

        if (cuenta.getNumeroDeCuenta() == null || cuenta.getMonto() == null || id == null) {
            throw new CuentaOperationException("El número de cuenta y el monto son campos obligatorios");
        }

        Cuenta cuentaExistente = cuentaRepository.findById(id).get();

        cuentaExistente.setNumeroDeCuenta(cuenta.getNumeroDeCuenta());
        cuentaExistente.setMonto(cuenta.getMonto());

        cuentaExistente = cuentaRepository.save(cuentaExistente);

        return cuentaExistente;

    }

    //Método Eliminar cuenta * id
    public void eliminarCuenta (Integer id){

        if(!cuentaRepository.existsById(id)){
            throw new CuentaNotFoundException("Este ID no se encuentra en la BD");
        }
        cuentaRepository.deleteById(id);
    }

    //Depositar Dinero Cuenta --------------- Métodos Especiales------------------
    public Cuenta depositarDineroCuenta (Float montoASumar, Integer id){

        //Acceda al método personalizado del repositorio -actualizarMonto->
        cuentaRepository.actualizarMonto(montoASumar, id);

        //Retorne la cuenta por Id al -Controlador <-
        return cuentaRepository.findById(id).get();

    }


    //Retirar Dinero Cuenta
    public Cuenta retirarDineroCuenta (Float montoARestar, Integer id){

        //Acceda al método personalizado del repositorio - actualizarMonto (el monto es Negativo) ->
        cuentaRepository.actualizarMonto(- montoARestar, id);

        //Retorne la cuenta por Id al -Controlador <-
        return cuentaRepository.findById(id).get();

    }


}

//    public Cuenta guardarCuenta(Cuenta cuenta) {
//        // Verificar si la cuenta es válida antes de guardarla
//        if (cuenta.getNumeroDeCuenta() == null || cuenta.getMonto() == null) {
//            throw new CuentaOperationException("La cuenta proporcionada es inválida");
//        }
//
//        // Intentar guardar la cuenta en el repositorio
//        try {
//            return cuentaRepository.save(cuenta);
//        } catch (Exception e) {
//            // Manejar cualquier error que pueda ocurrir durante la operación de guardado
//            throw new CuentaOperationException("Error al guardar la cuenta: " + e.getMessage());
//        }
//    }

//    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidCuentaIdException.class)
//    public ResponseEntity<String> handleInvalidCuentaIdException(InvalidCuentaIdException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    }