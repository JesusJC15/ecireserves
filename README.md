# Proyecto ECIReserves (Backend) - NetRunners

## Descripción del proyecto 
Este proyecto es el backend del sistema de reservas de laboratorios ECIReserves. Está desarrollado con Java 17, Spring Boot, Maven y utiliza MongoDB como base de datos.

## Características
- Gestión de usuarios, laboratorios y reservas.
- Validaciones para evitar conflictos de horarios.
- API REST para interacción con el frontend.
- Integración con MongoDB.

## Tecnologias y Herramientas
- Lenguaje: Java 17
- Construcción: Apache Maven 3.9.x.
- Framework: SpringBoot 3.3.4.
- Despliegue: AzureDevops.
- Cubrimiento:Jacoco, SonarCloud.
- Base de Datos: MongoDB.
- Pruebas: JUnit 5, Mockito.
- Seguridad: JWT, Spring Security.

## Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener instalado:

- Java 17.
- Maven.
- MongoDB.

## Arquitectura del proyecto
El backend sigue una arquitectura basada en capas y el patrón MVC (Model-View-Controller).

### Modelo de arquitectura
![](/assets/ModeloArquitectura.jpg)

## Estructura del proyecto
![](/assets/EstructuraDelProyecto.png)

## Modelo de datos
El sistema maneja tres entidades principales: Usuario, Reserva y Laboratorio.
![](/assets/ModeloDeDatos.png)

## Diagrama de clases
![](/assets/DiagramaDeClases.png)

## Diagrama de capas 
### User
![](/assets/User.png)

### Laboratory
![](/assets/Laboratory.png)

### Reservation
![](/assets/Reservation1.png)
![](/assets/Reservation2.png)

## Diagrama de excepciones
![](/assets/Exception.png)

## Configuración del Entorno
1. Clona el repositorio:
- git clone https://github.com/JesusJC15/ECIReserves.git
- cd ECIReserves

2. Configura las variables de entorno en application.properties
- spring.data.mongodb.uri=mongodb+srv://usuario:contraseña@cluster.mongodb.net/dbname (nube)
- mongodb://localhost:27017/ECIReserves (local)
- server.port=8080

3. Compila el proyecto con Maven:
- mvn clean install

4. Inicia el backend:
- mvn spring-boot:run

## Uso de la API
Documentacion con Swagger de los endpoints principales:

https://ecireserves-bfccasdkhxcwgnev.canadacentral-01.azurewebsites.net/swagger-ui/index.html

| Endpoint | Código de Estado | Descripcion del Error | Ejemplo |
|--------------------------|------------------|----------------------|---------|
| GET /user/users/{id} | 400 Bad Request | El usuario no existe | Buscar un usuario por un id que no existe |
| GET /user/users/{email} | 404 Not found | Usuario no encontrado | Buscar un usuario con un correo que no existe |
| PUT /admin/users/{id} | 400 Bad Request | El usuario no existe | Actualizar un usuario que no existe |
| DELETE /admin/users/{id} | 400 Bad Request | El usuario no existe | Eliminar un usuario que no existe |
| POST /admin/users | 400 Bad Request | Los campos no pueden estar vacios | Crear un usuario con campos vacios |
| GET /admin/users | 403 Forbidden | Acceso denegado, no tienes acceso para esta operacion | Intentar acceder sin rol ADMINISTRADOR |
| GET /user/reservations/status/{status} | 400 Bad Request | La reserva no existe | Buscar un estado incorrecto |
| POST /user/reservations | 400 Bad Request | La franja horaria no es valida | Intentar crea una reserva con hora, fecha y duración incorrecta |
| PUT /admin/reservations/{id} | 400 Bad Request | La reserva no existe | Intentar actualizar una reserva que no existe |
| DELETE /user/reservations/{id} | 400 Bad Request | La reserva no existe | Intentar eliminar una reserva que no existe |
| GET /user/laboratories/{id} | 400 Bad Request | El laboratorio no existe | Buscar un laboratorio que no existe |
| GET /user/laboratories/capacity/{capacity} | 500 Internal Server Error | La capacidad debe ser un número | Buscar un laboratorio con capacidad en letras|
| PUT /admin/laboratories/{id}| 400 Bad Request | El laboratorio no existe| Actualizar un laboraorio que no existe |
| DELETE /admin/laboratories/{id} | 400 Bad Request | El laboratorio no existe | Eliminar un laboratorio que no existe |

## Pruebas y cubrimiento
Ejecutar los tests con:
- mvn test

Cobertura en Jacoco
![](/assets/CoberturaJacoco.png)

Cobertura en SonarCloud
![](/assets/CoberturaSonarCloud.png)

## Despliegue en Azure
1. Generar el JAR:
- mvn clean package

2. Subir el JAR a Azure App Service (usando GitHub Actions o manualmente).

