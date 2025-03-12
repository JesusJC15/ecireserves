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
- Framework: SpringBoot 3.4.3.
- Despliegue: AzureDevops.
- Cubrimiento:Jacoco, SonarCloud.
- Base de Datos: MongoDB.
- Pruebas: JUnit 5, Mockito.

## Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener instalado:

- Java 17.
- Maven.
- MongoDB.

## Arquitectura del proyecto
El backend sigue una arquitectura basada en capas y el patrón MVC (Model-View-Controller).

### Modelo de arquitectura
![](/assets/ModeloArquitectura.png)

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
Endpoints principales:

### Usuarios
- Creación de usuarios

POST /users
![](/assets/1.png)
![](/assets/2.png)

- Obtener todos los usuarios

GET /users
![](/assets/3.png)

- Obtener usuario por id

GET /users/{id}
![](/assets/4.png)

- Obtener usuarios por nombre

GET /users/search
![](/assets/5.png)

- Actualización de un usuario

PUT /users/{id}
![](/assets/6.png)
![](/assets/7.png)

- Eliminación de usuarios

DELETE /users/{id}
![](/assets/8.png)
![](/assets/9.png)

### Laboratorios
- Creación de laboratorios

POST /laboratories
![](/assets/10.png)
![](/assets/11.png)

- Obtener todos los laboratorios

GET /laboratories
![](/assets/12.png)

- Obtener laboratorio por id

GET /laboratories/{id}
![](/assets/13.png)

- Obtener laboratorios por classroom

GET /laboratories/classroom/{classroom}
![](/assets/14.png)

- Obtener laboratorios por nombre

GET /laboratories/search
![](/assets/15.png)

- Obtener laboratorios por capacidad

GET /laboratories/capacity/{capacity}
![](/assets/16.png)

- Obtener laboratorios por día

GET /laboratories/day/{day}
![](/assets/17.png)

- Obtener laboratorios por hora de apertura

GET /laboratories/opening-time/{openingTime}
![](/assets/18.png)

- Actualización de un laboratorio

PUT /laboratories/{id}
![](/assets/19.png)
![](/assets/20.png)

- Eliminación de laboratorios

DELETE /laboratories/{id}
![](/assets/21.png)
![](/assets/22.png)

### Reservas
- Creación de reservas

POST /reservations
![](/assets/23.png)
![](/assets/24.png)

- Obtener todas las reservas

GET /reservations
![](/assets/25.png)

- Obtener reservas de un usuario

GET /reservations/user/{userId}
![](/assets/26.png)

- Obtener reservas de un laboratorio

GET /reservations/laboratory/{laboratoryId}
![](/assets/27.png)

- Obtener reservas por estatus

GET /reservations/status/{status}
![](/assets/28.png)
![](/assets/29.png)

- Obtener reservas de un usuario y su estatus

GET /reservations/user/{userId}/status/{status}
![](/assets/30.png)

- Obtener reservas por fecha

GET /reservations/date/{date}
![](/assets/31.png)

- Obtener reservas por hora de inicio

GET /reservations/startsTime/{startTime}
![](/assets/32.png)

- Obtener reservas por duración

GET /reservations/duration/{duration}
![](/assets/33.png)

- Actualización de una reserva

PUT /reservations/{id}
![](/assets/34.png)
![](/assets/35.png)

- Eliminación de reservas

DELETE /reservations/{id}
![](/assets/36.png)
![](/assets/37.png)

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