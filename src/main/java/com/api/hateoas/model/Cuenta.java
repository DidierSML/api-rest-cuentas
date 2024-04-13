package com.api.hateoas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cuentas")
//Cuando hacemos que extienda de esta Clase - Obtendremos la generación de Links HATEOAS-
public class Cuenta extends RepresentationModel<Cuenta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false, unique = true)
    private String numeroDeCuenta;

    @Column
    private Float monto;

    //Constructor con (Id y numeroDeCuenta) como parámetros
    public Cuenta(Integer id, String numeroDeCuenta) {
        this.id = id;
        this.numeroDeCuenta = numeroDeCuenta;
    }

    //Constructor con (Id y monto) como parámetros para los métodos -depositarCuenta- y -retirarCuenta-
    public Cuenta(Integer id, float monto){
        this.id = id;
        this.monto = monto;
    }

}
