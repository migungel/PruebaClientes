# Sistema de Gestión Bancaria - Microservicios

## 📋 Descripción

Sistema bancario basado en microservicios con Java Spring Boot, implementando gestión de clientes, cuentas y movimientos financieros con comunicación asincrónica mediante RabbitMQ.

## 🏗️ Arquitectura

### Microservicios

- **servicio-cliente-persona** (Puerto 8081): Gestión de clientes y personas
- **servicio-cuenta-movimientos** (Puerto 8082): Gestión de cuentas y movimientos
- **MySQL** (Puerto 3306): Base de datos compartida
- **RabbitMQ** (Puertos 5672, 15672): Mensajería asincrónica

## 🚀 Despliegue

### Requisitos Previos

- Docker y Docker Compose instalados
- Puerto 3306 libre (detener MySQL local si está corriendo)

### Levantar el Proyecto

```bash
# 1. Navegar al directorio del proyecto
cd PruebaClientes

# 2. Construir y levantar todos los servicios
docker-compose up --build

# O en segundo plano
docker-compose up -d
```

### Verificar que todo está corriendo

```bash
# Ver contenedores activos (deberías ver 4)
docker ps

# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker logs servicio-cliente-persona -f
docker logs servicio-cuenta-movimientos -f
```

### Detener el Proyecto

```bash
# Detener servicios
docker-compose down

# Detener y eliminar volúmenes (limpieza completa)
docker-compose down -v
```

## 🧪 Probar los Endpoints

Importa el archivo `PruebaNeoris.postman_collection.json` en Postman para probar todos los endpoints de forma rápida y organizada.

**Pasos:**

1. Abre Postman
2. Click en "Import"
3. Selecciona el archivo `PruebaNeoris.postman_collection.json`
4. Ejecuta las peticiones

**Nota sobre Reportes:**
El sistema implementa dos formas de generar reportes de estado de cuenta:

- **Query directo (JPQL)**: Endpoint `/api/reportes`
- **Stored Procedure (SP)**: Endpoint `/api/reportes/sp`

Ambas opciones están disponibles en la colección de Postman y permiten comparar el rendimiento entre query directo y stored procedure.

### Verificar en los logs

Deberías ver:

```
Evento recibido: Cliente creado con ID: X
Nombre: Test Async
ClienteID: ASYNC001
```

## 📊 Acceso a Interfaces

- **Cliente-Persona API**: http://localhost:8081/api
- **Cuenta-Movimientos API**: http://localhost:8082/api
- **RabbitMQ Management**: http://localhost:15672 (guest/guest)
- **MySQL**: localhost:3306 (root/root)

## 🧪 Ejecutar Pruebas

### Pruebas Unitarias - Servicio Cliente-Persona

```bash
cd servicio-cliente-persona
mvn test
```

### Pruebas de Integración - Servicio Cuenta-Movimientos

```bash
cd servicio-cuenta-movimientos
mvn test
```

## 📁 Estructura del Proyecto

```
PruebaClientes/
├── servicio-cliente-persona/       # Microservicio 1
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── servicio-cuenta-movimientos/    # Microservicio 2
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── BaseDatos.sql                   # Script de inicialización
├── docker-compose.yml              # Orquestación de servicios
├── ARQUITECTURA.md                 # Documentación detallada
└── README.md                       # Este archivo
```

## ⚠️ Solución de Problemas

### Puerto 3306 ocupado

```bash
# Detener MySQL local
net stop MySQL80
```

### Ver logs de errores

```bash
docker-compose logs
```

### Reiniciar un servicio específico

```bash
docker-compose restart servicio-cliente-persona
```

### Reconstruir imágenes

```bash
docker-compose up --build --force-recreate
```
