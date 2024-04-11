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

    //Guardar cuenta
    @PostMapping("guardarCuenta")
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta guardarCuenta (@RequestBody Cuenta cuentaNueva){

        return cuentaService.guardarCuenta(cuentaNueva);
    }

    //Obtener/Listar todas las cuentas
    @GetMapping("obtenerCuentas")
    @ResponseStatus(HttpStatus.OK)
    public List<Cuenta> obtenerCuentas (){
        return  cuentaService.obtenerCuentas();
    }

    //Obtener cuenta por Id
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta obtenerCuentaPorId (@PathVariable (value = "id") Integer id){
        return cuentaService.obtenerCuentaPorId(id);
    }

    //Actualizar Cuenta
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta acutalizarCuenta (@PathVariable (value = "id") Integer id, @RequestBody Cuenta cuentaExistente){
        return cuentaService.acutalizarCuenta(id,cuentaExistente);
    }

    //Eliminar Cuenta
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCuenta (@PathVariable (value = "id") Integer id){
        cuentaService.eliminarCuenta(id);
    }
}
