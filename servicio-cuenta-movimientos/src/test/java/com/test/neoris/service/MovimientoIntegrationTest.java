package com.test.neoris.service;

import com.test.neoris.entity.Cuenta;
import com.test.neoris.entity.Movimiento;
import com.test.neoris.exception.BusinessException;
import com.test.neoris.repository.CuentaRepository;
import com.test.neoris.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MovimientoIntegrationTest {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Cuenta cuentaTest;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.execute("TRUNCATE TABLE movimiento");
        jdbcTemplate.execute("TRUNCATE TABLE cuenta");
        jdbcTemplate.execute("TRUNCATE TABLE cliente");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");

        jdbcTemplate.execute(
            "INSERT INTO cliente (id, nombre, genero, edad, identificacion, direccion, telefono, clienteid, contrasena, estado) " +
            "VALUES (1, 'Test Cliente', 'Masculino', 30, '123456789', 'Test Dir', '123456', 'TEST001', 'pass', true)"
        );

        jdbcTemplate.execute(
            "INSERT INTO cuenta (id, numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) " +
            "VALUES (1, '123456', 'Ahorros', 1000.0, true, 1)"
        );
        
        cuentaTest = cuentaRepository.findById(1L).orElseThrow();
    }

    @Test
    public void testGuardarMovimientoConSaldoSuficiente() {
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento("Retiro");
        movimiento.setValor(-500.0);
        movimiento.setCuenta(cuentaTest);

        Movimiento resultado = movimientoService.guardar(movimiento);

        assertNotNull(resultado.getId());
        assertEquals(500.0, resultado.getSaldo());
    }

    @Test
    public void testGuardarMovimientoSinSaldoDisponible() {
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento("Retiro");
        movimiento.setValor(-1500.0);
        movimiento.setCuenta(cuentaTest);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            movimientoService.guardar(movimiento);
        });

        assertEquals("Saldo no disponible", exception.getMessage());
    }

    @Test
    public void testActualizarSaldoConMultiplesMovimientos() {
        Movimiento mov1 = new Movimiento();
        mov1.setTipoMovimiento("Deposito");
        mov1.setValor(500.0);
        mov1.setCuenta(cuentaTest);
        movimientoService.guardar(mov1);

        Movimiento mov2 = new Movimiento();
        mov2.setTipoMovimiento("Retiro");
        mov2.setValor(-300.0);
        mov2.setCuenta(cuentaTest);
        Movimiento resultado = movimientoService.guardar(mov2);

        assertEquals(1200.0, resultado.getSaldo());
    }
}
