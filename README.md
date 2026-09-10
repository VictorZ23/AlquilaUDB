# AlquilaUDB

AlquilaUDB es una aplicación web desarrollada en Java que permite gestionar
ofertas de viviendas orientadas a estudiantes universitarios.

El proyecto busca facilitar la consulta de opciones de alojamiento mediante
un sistema sencillo que permite registrar, visualizar, editar y eliminar
viviendas disponibles.

## Funcionalidades

Actualmente la aplicación permite:

- Listar viviendas registradas.
- Consultar el detalle de una vivienda.
- Registrar nuevas viviendas.
- Editar viviendas existentes.
- Eliminar viviendas.
- Indicar la disponibilidad de una vivienda.
- Validar los datos ingresados en los formularios.

## Tecnologías utilizadas

- Java 11
- Java Web
- Servlets
- JSP
- JSTL
- JDBC
- MySQL
- Maven
- Apache Tomcat 9
- HTML

## Arquitectura

El proyecto utiliza el patrón MVC (Modelo-Vista-Controlador).

La aplicación está organizada principalmente en:

- `model`: clases que representan las entidades del sistema.
- `dao`: acceso y operaciones sobre la base de datos.
- `controller`: Servlets encargados de gestionar las peticiones.
- `util`: utilidades como la conexión a MySQL.
- JSP: vistas utilizadas para presentar la información al usuario.

## Base de datos

El proyecto utiliza una base de datos MySQL llamada:

`alquilaudb`

El script necesario para crear la base de datos y la tabla se encuentra en:

`database/alquilaudb.sql`

Para instalar la base de datos:

1. Iniciar MySQL.
2. Abrir phpMyAdmin o un cliente MySQL.
3. Ejecutar el archivo `database/alquilaudb.sql`.

El script crea la base de datos, la tabla `vivienda` y algunos registros
de prueba.

## Configuración de la conexión

La conexión a la base de datos se encuentra configurada en la clase:

`com.alquilaudb.util.Conexion`

La configuración utilizada durante el desarrollo es:

- Base de datos: `alquilaudb`
- Servidor: `localhost`
- Puerto MySQL: `3306`
- Usuario: `root`

La contraseña debe ajustarse según la configuración local de MySQL.

## Ejecución

Para ejecutar el proyecto se necesita:

- JDK 11
- Apache Maven
- Apache Tomcat 9
- MySQL

Pasos generales:

1. Clonar o descargar el proyecto.
2. Ejecutar `database/alquilaudb.sql` en MySQL.
3. Configurar los datos de conexión a MySQL si es necesario.
4. Compilar el proyecto con Maven.
5. Desplegar la aplicación en Apache Tomcat 9.
6. Iniciar Tomcat.
7. Acceder a la aplicación desde el navegador.

Durante el desarrollo, la aplicación se ejecuta con el contexto:

`/AlquilaUDB`

## Persistencia

La aplicación utiliza JDBC para comunicarse con MySQL.

Las operaciones sobre viviendas se realizan mediante la clase
`ViviendaDAO`, utilizando `PreparedStatement` y consultas parametrizadas.

## Estado actual

La primera fase implementa el módulo básico de gestión de viviendas mediante
arquitectura MVC y operaciones CRUD conectadas a una base de datos MySQL.

El proyecto continuará evolucionando en las siguientes fases de desarrollo.