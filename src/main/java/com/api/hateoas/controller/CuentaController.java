package com.api.hateoas.controller;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.service.CuentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    //Guardar cuenta Controller
    @PostMapping("guardarCuenta")
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta guardarCuenta (@RequestBody Cuenta cuentaNueva){

        return cuentaService.guardarCuenta(cuentaNueva);
    }

    //Obtener/Listar todas las cuentas Controller
    @GetMapping("obtenerCuentas")
    @ResponseStatus(HttpStatus.OK)
    public List<Cuenta> obtenerCuentas (){
        return  cuentaService.obtenerCuentas();
    }
}
