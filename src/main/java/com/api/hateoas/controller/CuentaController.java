package com.api.hateoas.controller;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.model.Monto;
import com.api.hateoas.service.CuentaService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@RequestMapping("api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    //Guardar cuenta
    @PostMapping("guardarCuenta")
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta guardarCuenta (@RequestBody Cuenta cuentaNueva){

        //Llamamos el método -guardarCuenta- del servicio y lo asignamos a una variable
        Cuenta cuentaHateoas = cuentaService.guardarCuenta(cuentaNueva);

        //Haciendo uso de la variable generamos 2 enlaces;
        //El Primero direccionará a la cuenta específica por su id
        cuentaHateoas.add(linkTo(methodOn(CuentaController.class).obtenerCuentaPorId(cuentaHateoas.getId())).withSelfRel());
        //El Segundo direccionará de manera dinámica al método que Colecciona todas las cuentas de forma dinámica
        cuentaHateoas.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withRel(IanaLinkRelations.COLLECTION));

        //Finalmente se retornará la cuenta guardada + los dos enlaces anteriores
        return cuentaService.guardarCuenta(cuentaNueva);
    }

    //Obtener/Listar todas las cuentas
    @GetMapping("obtenerCuentas")
    @ResponseStatus(HttpStatus.OK)
    public List <Cuenta> obtenerCuentas (){

        //Línea que llama al Servicio
        List <Cuenta> cuentas = cuentaService.obtenerCuentas();

        //Creamos un ciclo for para iterar sobre las -cuentas almacenadas-
        for(Cuenta cuenta: cuentas){

            //Creamos una lista que va a guardar los enlaces que incluiremos en el GET como respuesta
            List <Link> enlaces = new ArrayList<>();

            //La lista -enlaces- agregará su primer link: cuenta por cada -id- con direccionamiento a este mismo método
            enlaces.add(linkTo(methodOn(CuentaController.class).obtenerCuentaPorId(cuenta.getId())).withSelfRel());

            //Este enlace apunta a la Colección completa de -cuentas- de manera dinámica, es más flexible si se presentan cambios futuros
            enlaces.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withRel(IanaLinkRelations.COLLECTION));

            //Aquí agregamos los enlaces anteriores a la lista cuenta del bucle for-each
            cuenta.add(enlaces);
        }

        //Finalmente, retornamos la lista -cuentas- que ahora incluye los enlaces obtenidos en el anterior bucle
        return  cuentas;
    }

    //Obtener cuenta por Id
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta obtenerCuentaPorId (@PathVariable (value = "id") Integer id){

        //Creamos una variable que guardará el llamado al método -obtenerCuentaPorId- del servicio
        Cuenta cuentaHateoas = cuentaService.obtenerCuentaPorId(id);

        //Enlace que apunta al -Id de la Cuenta-
        cuentaHateoas.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentaPorId(cuentaHateoas.getId())).withSelfRel());
        //Enlace Dinámico -obtenerCuentas
        cuentaHateoas.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withRel(IanaLinkRelations.COLLECTION));
        //Enlace Estático -obtenerCuentas
        cuentaHateoas.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withSelfRel());

        //Retornamos la -cuenta por Id-
        return cuentaService.obtenerCuentaPorId(id);
    }

    //Actualizar Cuenta
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta acutalizarCuenta (@PathVariable (value = "id") Integer id, @RequestBody Cuenta cuentaExistente){

        //Creamos una variable que guardará el llamado al método -actualizarCuenta- del servicio
        Cuenta cuentaHateoas = cuentaService.acutalizarCuenta(id, cuentaExistente);

        //Obtenemos el enlace de la cuenta actualizada y de las cuentas en general
        cuentaHateoas.add(linkTo(methodOn(CuentaController.class).obtenerCuentaPorId(cuentaHateoas.getId())).withSelfRel());
        cuentaHateoas.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withRel(IanaLinkRelations.COLLECTION));

        //Retornamos la cuentaActualizada
        return cuentaService.acutalizarCuenta(id,cuentaExistente);
    }

    //Eliminar Cuenta
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCuenta (@PathVariable (value = "id") Integer id){

        cuentaService.eliminarCuenta(id);

    }

    //------- Métodos especiales-------

    //Depositar Monto - Usamos PatchMapping - para modificar un solo dato de la Tabla y no todos
    @PatchMapping("depositar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta depositarDineroCuenta (@PathVariable (value = "id") Integer id, @RequestBody Monto monto){

        Cuenta montoADepositar = cuentaService.depositarDineroCuenta(monto.getMonto(),id);

        //Obtenemos el enlace de la cuenta actualizada y de las cuentas en general
        montoADepositar.add(linkTo(methodOn(CuentaController.class).obtenerCuentaPorId(montoADepositar.getId())).withSelfRel());
        montoADepositar.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withRel(IanaLinkRelations.COLLECTION));
        //Agregamos link de Depósitos
        montoADepositar.add(linkTo(methodOn(CuentaController.class).depositarDineroCuenta(montoADepositar.getId(), null)).withRel("depositos"));

        return montoADepositar;
    }

    //Retirar Monto
    @PatchMapping("retirar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta retirarDineroCuenta (@PathVariable (value = "id") Integer id, @RequestBody Monto monto){

        Cuenta montoARetirar = cuentaService.retirarDineroCuenta(monto.getMonto(),id);

        montoARetirar.add(linkTo(methodOn(CuentaController.class).obtenerCuentaPorId(montoARetirar.getId())).withSelfRel());
        montoARetirar.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withRel(IanaLinkRelations.COLLECTION));

        montoARetirar.add(linkTo(methodOn(CuentaController.class).retirarDineroCuenta(montoARetirar.getId(),null)).withRel("retiros"));

        return montoARetirar;
    }


}

/**
 * Cabe resaltar que al momento de usar enlaces lo podemos hacer de forma dinámica o estática, ambos son útiles:
 *
 * (Ejemplo): Para apuntar a la lista total de -cuentas- se puede hacer de estas formas:
 *
 *     (Este enlace apunta a la lista General de -cuentas- de manera estática con un link sencillo)
 *      ->     enlaces.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withSelfRel());
 *     (Este enlace apunta a la Colección completa de -cuentas- de manera dinámica, es más flexible si se presentan cambios futuros)
 *      ->    enlaces.add(linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).obtenerCuentas()).withRel(IanaLinkRelations.COLLECTION));
 */