package com.api.hateoas.service;

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

        return cuentaRepository.save(cuenta);
    }

    //Método Obtener todas las cuentas
    public List<Cuenta> obtenerCuentas(){

        return cuentaRepository.findAll();
    }

    //Método Obtener cuenta * id
    public Cuenta obtenerCuentaPorId (Integer id){

        return cuentaRepository.findById(id).get();

    }

    //Método Actualizar cuenta * id
    public Cuenta acutalizarCuenta (Integer id, Cuenta cuenta){

        Cuenta cuentaExistente = cuentaRepository.findById(id).get();

        cuentaExistente.setNumeroDeCuenta(cuenta.getNumeroDeCuenta());
        cuentaExistente.setMonto(cuenta.getMonto());

        cuentaExistente = cuentaRepository.save(cuentaExistente);

        return cuentaExistente;

    }

    //Método Eliminar cuenta * id
    public void eliminarCuenta (Integer id){

        cuentaRepository.deleteById(id);
    }




}
