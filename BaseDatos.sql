use basedatos;

DROP TABLE IF EXISTS persona;
CREATE TABLE persona (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INT,
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(250),
    telefono VARCHAR(20)
);

DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente (
    cliente_id INT PRIMARY KEY,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT fk_persona_cliente FOREIGN KEY (cliente_id) REFERENCES persona(id)
);

DROP TABLE IF EXISTS cuenta;
CREATE TABLE cuenta (
    id INT NOT NULL AUTO_INCREMENT,
    numero_cuenta VARCHAR(20) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cliente_cuenta FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
);

DROP TABLE IF EXISTS movimiento;
CREATE TABLE movimiento (
    id INT NOT NULL AUTO_INCREMENT,
    fecha DATETIME NOT NULL,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cuenta_movimiento FOREIGN KEY (cuenta_id) REFERENCES cuenta(id)
);

