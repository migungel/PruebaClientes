# Sistema de Gestión Bancaria - Arquitectura de Microservicios

## 📋 Descripción
Sistema bancario basado en microservicios con Java Spring Boot, implementando gestión de clientes, cuentas y movimientos financieros.

## 🏗️ Arquitectura

### Microservicios

#### 1. **servicio-cliente-persona** (Puerto 8081)
- **Responsabilidad**: Gestión de clientes y personas
- **Entidades**: 
  - Persona (clase base)
  - Cliente (hereda de Persona)
- **Endpoints**:
  - `GET /clientes` - Listar todos los clientes
  - `GET /clientes/{id}` - Obtener cliente por ID
  - `POST /clientes` - Crear nuevo cliente
  - `PUT /clientes/{id}` - Actualizar cliente
  - `DELETE /clientes/{id}` - Eliminar cliente

#### 2. **servicio-cuenta-movimientos** (Puerto 8082)
- **Responsabilidad**: Gestión de cuentas y movimientos financieros
- **Entidades**:
  - Cuenta
  - Movimiento
  - Cliente (referencia simplificada)
- **Endpoints**:
  - `GET /cuentas` - Listar todas las cuentas
  - `POST /cuentas` - Crear nueva cuenta
  - `PUT /cuentas/{id}` - Actualizar cuenta
  - `DELETE /cuentas/{id}` - Eliminar cuenta
  - `GET /movimientos` - Listar movimientos
  - `POST /movimientos` - Registrar movimiento
  - `PUT /movimientos/{id}` - Actualizar movimiento
  - `DELETE /movimientos/{id}` - Eliminar movimiento
  - `GET /reportes?clienteId={id}&fechaInicio={fecha}&fechaFin={fecha}` - Generar reporte

## 📊 Diagrama de Clases

```
┌─────────────────────────────────────────────────────────────┐
│                  SERVICIO-CLIENTE-PERSONA                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────┐                                          │
│  │   Persona    │ (MappedSuperclass)                       │
│  ├──────────────┤                                          │
│  │ - nombre     │                                          │
│  │ - genero     │                                          │
│  │ - edad       │                                          │
│  │ - identificacion                                        │
│  │ - direccion  │                                          │
│  │ - telefono   │                                          │
│  └──────┬───────┘                                          │
│         │                                                   │
│         │ extends                                           │
│         ▼                                                   │
│  ┌──────────────┐                                          │
│  │   Cliente    │                                          │
│  ├──────────────┤                                          │
│  │ - id (PK)    │                                          │
│  │ - clienteid  │                                          │
│  │ - contrasena │                                          │
│  │ - estado     │                                          │
│  └──────────────┘                                          │
│                                                             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│               SERVICIO-CUENTA-MOVIMIENTOS                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────┐                                          │
│  │   Cliente    │ (Referencia)                             │
│  ├──────────────┤                                          │
│  │ - clienteId  │                                          │
│  │ - estado     │                                          │
│  └──────┬───────┘                                          │
│         │                                                   │
│         │ 1:N                                               │
│         ▼                                                   │
│  ┌──────────────┐                                          │
│  │    Cuenta    │                                          │
│  ├──────────────┤                                          │
│  │ - id (PK)    │                                          │
│  │ - numeroCuenta                                          │
│  │ - tipoCuenta │                                          │
│  │ - saldoInicial                                          │
│  │ - estado     │                                          │
│  │ - cliente_id │ (FK)                                     │
│  └──────┬───────┘                                          │
│         │                                                   │
│         │ 1:N                                               │
│         ▼                                                   │
│  ┌──────────────┐                                          │
│  │  Movimiento  │                                          │
│  ├──────────────┤                                          │
│  │ - id (PK)    │                                          │
│  │ - fecha      │                                          │
│  │ - tipoMovimiento                                        │
│  │ - valor      │                                          │
│  │ - saldo      │                                          │
│  │ - cuenta_id  │ (FK)                                     │
│  └──────────────┘                                          │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## 📁 Estructura de Carpetas

```
PruebaClientes/
├── servicio-cliente-persona/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/test/neoris/
│   │   │   │   ├── controller/
│   │   │   │   │   └── ClienteController.java
│   │   │   │   ├── dto/
│   │   │   │   │   └── ApiResponse.java
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Persona.java
│   │   │   │   │   └── Cliente.java
│   │   │   │   ├── exception/
│   │   │   │   │   ├── BusinessException.java
│   │   │   │   │   ├── ErrorResponse.java
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   │   └── ResourceNotFoundException.java
│   │   │   │   ├── repository/
│   │   │   │   │   └── ClienteRepository.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── ClienteService.java
│   │   │   │   │   └── ClienteServiceImpl.java
│   │   │   │   └── NeorisApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       └── java/com/test/neoris/
│   │           └── service/
│   │               └── ClienteTest.java
│   ├── Dockerfile
│   └── pom.xml
│
├── servicio-cuenta-movimientos/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/test/neoris/
│   │   │   │   ├── controller/
│   │   │   │   │   ├── CuentaController.java
│   │   │   │   │   ├── MovimientoController.java
│   │   │   │   │   └── ReporteController.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── ApiResponse.java
│   │   │   │   │   ├── ClienteDTO.java
│   │   │   │   │   ├── ReporteEstadoCuentaDTO.java
│   │   │   │   │   └── ReporteInterface.java
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Cliente.java
│   │   │   │   │   ├── Cuenta.java
│   │   │   │   │   └── Movimiento.java
│   │   │   │   ├── exception/
│   │   │   │   │   ├── BusinessException.java
│   │   │   │   │   ├── ErrorResponse.java
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   │   └── ResourceNotFoundException.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── CuentaRepository.java
│   │   │   │   │   └── MovimientoRepository.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── CuentaService.java
│   │   │   │   │   ├── CuentaServiceImpl.java
│   │   │   │   │   ├── MovimientoService.java
│   │   │   │   │   └── MovimientoServiceImpl.java
│   │   │   │   └── NeorisApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       └── java/com/test/neoris/
│   │           └── service/
│   │               └── MovimientoIntegrationTest.java
│   ├── Dockerfile
│   └── pom.xml
│
├── BaseDatos.sql
├── docker-compose.yml
└── README.md
```

## 🔧 Tecnologías

- **Java 17**
- **Spring Boot 3.5.10**
- **Spring Data JPA**
- **MySQL 8.0**
- **Lombok**
- **Maven**
- **Docker & Docker Compose**

## 🚀 Despliegue

### Requisitos Previos
- Docker y Docker Compose instalados
- Java 17 (para desarrollo local)
- Maven 3.9+ (para desarrollo local)

### Despliegue con Docker Compose

```bash
# Construir y levantar todos los servicios
docker-compose up --build

# Levantar en segundo plano
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down
```

### Ejecución Local (sin Docker)

```bash
# Terminal 1 - Base de datos
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=basedatos mysql:8.0

# Terminal 2 - Servicio Cliente-Persona
cd servicio-cliente-persona
mvn spring-boot:run

# Terminal 3 - Servicio Cuenta-Movimientos
cd servicio-cuenta-movimientos
mvn spring-boot:run
```

## 🧪 Pruebas

### Ejecutar Pruebas Unitarias
```bash
# Servicio Cliente-Persona
cd servicio-cliente-persona
mvn test

# Servicio Cuenta-Movimientos
cd servicio-cuenta-movimientos
mvn test
```

## 🔐 Lógica de Negocio - Validación de Saldo

El servicio de movimientos implementa una validación robusta de saldo:

1. **Antes de guardar**: Calcula el saldo actual sumando todos los movimientos previos
2. **Validación**: Si el nuevo saldo resultante es negativo, lanza `BusinessException` con mensaje "Saldo no disponible"
3. **Transaccionalidad**: Usa `@Transactional` para garantizar consistencia
4. **Métodos auxiliares**:
   - `calcularSaldoActual()`: Calcula saldo considerando todos los movimientos
   - `calcularSaldoActualExcluyendo()`: Para actualizaciones, excluye el movimiento actual

## 📡 Endpoints Principales

### Servicio Cliente-Persona (8081)
- `POST http://localhost:8081/clientes`
- `GET http://localhost:8081/clientes`

### Servicio Cuenta-Movimientos (8082)
- `POST http://localhost:8082/cuentas`
- `POST http://localhost:8082/movimientos`
- `GET http://localhost:8082/reportes?clienteId=1&fechaInicio=2024-01-01T00:00:00&fechaFin=2024-12-31T23:59:59`

## 🗄️ Base de Datos

La base de datos se inicializa automáticamente con el script `BaseDatos.sql` que contiene:
- Tabla `persona`
- Tabla `cliente` (con FK a persona)
- Tabla `cuenta` (con FK a cliente)
- Tabla `movimiento` (con FK a cuenta)

## 📝 Patrones Implementados

- **Repository Pattern**: Separación de lógica de acceso a datos
- **Service Layer**: Lógica de negocio encapsulada
- **DTO Pattern**: Transferencia de datos entre capas
- **Global Exception Handler**: Manejo centralizado de excepciones
- **Builder Pattern**: Construcción de objetos complejos (Lombok)

## 👥 Autor

Desarrollado como prueba técnica para demostrar arquitectura de microservicios con Spring Boot.
