package com.api.hateoas;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@Rollback(value = false)//false permite cambios en la BD - true no permite modificaciones de este tipo
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CuentaRepositoryTest {

    //Vamos a realizar una prueba al método -save- de -CuentaRepository-
    @Mock
    private CuentaRepository cuentaRepository;

    @Test
    void testAgregarCuenta(){
        // Configuración del mock
        Cuenta cuenta = new Cuenta(23,"125463");
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        // Ejecutar el método a probar
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        // Verificar el resultado
        assertEquals(cuenta.getId(), cuentaGuardada.getId());
        assertEquals(cuenta.getNumeroDeCuenta(), cuentaGuardada.getNumeroDeCuenta());
        assertNotNull(cuentaGuardada);


        // Verificar que el método save haya sido invocado una vez
        Mockito.verify(this.cuentaRepository, Mockito.times(1)).save(cuenta);

        // Verificar que el ID de la cuenta guardada es mayor que 0
        assertTrue(cuentaGuardada.getId() > 0);
    }
}
