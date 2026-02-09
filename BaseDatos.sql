CREATE DATABASE IF NOT EXISTS basedatos;
USE basedatos;

DROP TABLE IF EXISTS movimiento;
DROP TABLE IF EXISTS cuenta;
DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INT,
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(250),
    telefono VARCHAR(20),
    clienteid VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
);

CREATE TABLE cuenta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    numero_cuenta VARCHAR(20) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cliente_cuenta FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);

CREATE TABLE movimiento (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cuenta_movimiento FOREIGN KEY (cuenta_id) REFERENCES cuenta(id) ON DELETE CASCADE
);

INSERT INTO cliente (nombre, genero, edad, identificacion, direccion, telefono, clienteid, contrasena, estado) 
VALUES ('Jose Lema', 'Masculino', 30, '1234567890', 'Otavalo sn y principal', '098254785', 'jlema', 'pass123', TRUE);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) 
VALUES ('478758', 'Ahorros', 2000.00, TRUE, 1);

INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) 
VALUES (NOW(), 'Deposito', 500.00, 2500.00, 1);


DELIMITER $$

CREATE DEFINER=root@localhost PROCEDURE sp_generar_reporte(
    IN p_cliente_id BIGINT,
    IN p_fecha_inicio DATETIME,
    IN p_fecha_fin DATETIME
)
BEGIN
    SELECT
        m.fecha AS Fecha,
        cl.nombre AS Cliente,
        c.numero_cuenta AS NumeroCuenta,
        c.tipo_cuenta AS Tipo,
        c.saldo_inicial AS SaldoInicial,
        c.estado AS Estado,
        m.valor AS Movimiento,
        m.saldo AS SaldoDisponible
    FROM movimiento m
    INNER JOIN cuenta c ON m.cuenta_id = c.id
    INNER JOIN cliente cl ON c.cliente_id = cl.id
    WHERE cl.id = p_cliente_id
    AND m.fecha BETWEEN p_fecha_inicio AND p_fecha_fin;
END$$

DELIMITER ;
