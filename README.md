# AlquilaUDB

**AlquilaUDB** es una aplicación web desarrollada en Java para gestionar y consultar ofertas de viviendas dirigidas a estudiantes universitarios.

El proyecto busca facilitar la búsqueda de alojamiento mediante un sistema que permite registrar, visualizar, modificar y eliminar viviendas, consultar su disponibilidad y publicar reseñas sobre las experiencias de alojamiento.

La aplicación se desarrolla como proyecto académico para la asignatura **Desarrollo de Aplicaciones con Web Frameworks (DWF901)** de la Universidad Don Bosco.

## 1. Descripción del proyecto

AlquilaUDB permite consultar información sobre viviendas, incluyendo su ubicación, precio mensual, tipo, cantidad de habitaciones y disponibilidad.

También incorpora un sistema de reseñas para que los usuarios puedan compartir opiniones y calificaciones sobre las viviendas registradas.

El desarrollo se ha realizado de manera incremental:

- **Fase I:** aplicación Java Web con arquitectura MVC, Servlets, JSP, JSTL, JDBC y MySQL.
- **Fase II:** incorporación de JSF, CDI, JPA, Hibernate, AJAX, validadores personalizados y relaciones entre entidades.

La implementación anterior permanece disponible para comprobar la evolución del proyecto y la persistencia compartida en MySQL.

## 2. Funcionalidades

### Gestión de viviendas

- Listar todas las viviendas registradas.
- Registrar nuevas viviendas.
- Consultar el detalle completo de una vivienda.
- Editar viviendas existentes.
- Eliminar viviendas, previa confirmación.
- Consultar precios, zonas, direcciones y disponibilidad.
- Buscar viviendas por título o zona.
- Filtrar por tipo de vivienda.
- Mostrar únicamente viviendas disponibles.
- Limpiar los filtros y recuperar el listado completo.

### Gestión de reseñas

- Consultar las reseñas asociadas a una vivienda.
- Registrar nuevas reseñas.
- Asignar calificaciones entre 1 y 5.
- Almacenar el autor, comentario y fecha de cada reseña.
- Relacionar las reseñas con su vivienda mediante JPA.
- Impedir la eliminación de viviendas que tengan reseñas asociadas.

### Validaciones y AJAX

- Validación de campos obligatorios.
- Validación de longitudes de texto.
- Validación de precios y cantidades de habitaciones.
- Validador JSF personalizado para los comentarios de reseñas.
- Mensajes de error durante la interacción con los formularios.
- Validación parcial de campos mediante AJAX.
- Búsqueda y actualización de resultados sin recargar completamente la página.

## 3. Tecnologías utilizadas

| Tecnología | Versión o implementación |
|---|---|
| Lenguaje | Java 11 |
| Construcción | Apache Maven |
| Servidor | Apache Tomcat 9 |
| Base de datos | MySQL |
| Acceso a datos de Fase I | JDBC |
| Controladores de Fase I | Java Servlets |
| Vistas de Fase I | JSP y JSTL |
| Vistas de Fase II | JavaServer Faces (JSF) 2.3 |
| Persistencia de Fase II | JPA 2.2 e Hibernate ORM 5.6 |
| Inyección de dependencias | CDI mediante Weld |
| Interacción asíncrona | AJAX de JSF |
| Interfaz | XHTML, HTML y CSS |
| IDE utilizado | Apache NetBeans 11.3 |
| Control de versiones | Git y GitHub |

Durante el desarrollo se utilizaron **Mojarra 2.3.9**, **Hibernate 5.6.15.Final** y **Weld 3.1.9.Final**, configurados mediante Maven para funcionar con Java 11 y Tomcat 9.

## 4. Arquitectura del proyecto

AlquilaUDB conserva dos implementaciones del módulo de viviendas.

### Fase I: MVC con JDBC

- `com.alquilaudb.model`: representación de las viviendas.
- `com.alquilaudb.dao`: operaciones JDBC mediante `ViviendaDAO`.
- `com.alquilaudb.controller`: procesamiento de peticiones mediante `ViviendaServlet`.
- `com.alquilaudb.util`: conexión y utilidades para MySQL.
- Páginas JSP: interfaz de consulta y administración.

### Fase II: JSF, CDI y JPA

- `com.alquilaudb.model`: entidades JPA `Vivienda` y `Resena`.
- `com.alquilaudb.jpa`: operaciones de persistencia con `EntityManager`, consultas JPQL y transacciones.
- `com.alquilaudb.bean`: Managed Beans CDI utilizados por las páginas JSF.
- `com.alquilaudb.validator`: validadores JSF personalizados.
- Páginas XHTML: interfaz de administración, consultas y reseñas.

La arquitectura de la Fase II sigue el flujo:

**Interfaz JSF → Managed Bean CDI → DAO JPA → Hibernate → MySQL**

Las operaciones de escritura utilizan transacciones con confirmación (`commit`) y reversión (`rollback`) en caso de error.

## 5. Modelo de datos

La aplicación utiliza una base de datos MySQL denominada:

`alquilaudb`

Contiene dos tablas principales.

### Tabla `vivienda`

Almacena las ofertas de alojamiento mediante los campos:

`id`, `titulo`, `tipo`, `direccion`, `zona`, `precio`, `habitaciones`, `descripcion` y `disponible`.

### Tabla `resena`

Almacena las opiniones mediante los campos:

`id`, `vivienda_id`, `autor`, `calificacion`, `comentario` y `fecha`.

### Relación entre entidades

Existe una relación **uno a muchos (1:N)**:

- Una vivienda puede tener varias reseñas.
- Cada reseña pertenece a una vivienda.

La relación está implementada con las anotaciones JPA `@OneToMany`, `@ManyToOne` y `@JoinColumn`.

La columna `resena.vivienda_id` es una clave foránea que referencia `vivienda.id`.

La base de datos utiliza `ON DELETE RESTRICT` para evitar que se eliminen viviendas que tengan reseñas asociadas.

## 6. Instalación de la base de datos

El script SQL del proyecto está ubicado en:

`database/alquilaudb.sql`

Para instalarlo en una base de datos nueva:

1. Iniciar el servicio MySQL.
2. Abrir phpMyAdmin o un cliente MySQL.
3. Ejecutar el contenido de `database/alquilaudb.sql`.
4. Comprobar que existen las tablas `vivienda` y `resena`.

El script crea la estructura de la base de datos y sus relaciones. **No inserta registros de ejemplo**.

No es necesario ejecutarlo nuevamente sobre una instalación local que ya tenga las tablas creadas.

## 7. Configuración de MySQL

La aplicación contiene dos mecanismos de acceso a datos.

### Conexión JDBC

La configuración de la Fase I se encuentra en:

`src/main/java/com/alquilaudb/util/Conexion.java`

### Conexión JPA e Hibernate

La configuración de la Fase II se encuentra en:

`src/main/resources/META-INF/persistence.xml`

La unidad de persistencia se denomina:

`AlquilaUDBPU`

Y utiliza transacciones de tipo `RESOURCE_LOCAL`.

Durante el desarrollo local se utilizó la siguiente conexión:

- Servidor: `localhost`
- Puerto: `3306`
- Base de datos: `alquilaudb`
- Usuario local de desarrollo: `root`

Las credenciales deben adaptarse al entorno donde se ejecute la aplicación. No deben publicarse contraseñas reales en el repositorio.

Hibernate tiene configurado `hibernate.hbm2ddl.auto=none`, de modo que no crea ni altera automáticamente las tablas. La estructura se prepara mediante el script SQL.

## 8. Requisitos para ejecutar el proyecto

Se necesita:

- JDK 11.
- Apache Maven.
- Apache Tomcat 9.
- MySQL compatible con el script SQL.
- Un navegador web.

El proyecto se desarrolló con NetBeans 11.3 y se probó con Tomcat 9.0.121.

Tomcat debe contar con la configuración necesaria para ejecutar JSF y CDI mediante las dependencias incluidas en el proyecto.

## 9. Compilación y ejecución

1. Clonar el repositorio de GitHub:

   `git clone https://github.com/VictorZ23/AlquilaUDB.git`

2. Abrir el proyecto Maven en NetBeans o desde una terminal.

3. Crear la base de datos ejecutando el script `database/alquilaudb.sql` en MySQL.

4. Configurar las conexiones de `Conexion.java` y `persistence.xml` según las credenciales locales.

5. Compilar el proyecto mediante:

   `mvn clean package`

6. Desplegar el archivo WAR generado en Apache Tomcat 9.

7. Configurar el contexto de despliegue como `/AlquilaUDB`. Si se utiliza el administrador de Tomcat, puede desplegarse con ese contexto; alternativamente, puede utilizarse el nombre `AlquilaUDB.war` cuando corresponda.

8. Iniciar Tomcat y acceder a las páginas del proyecto.

Durante el desarrollo en NetBeans se utiliza:

`http://localhost:8080/AlquilaUDB`

### Principales direcciones

| Funcionalidad | Dirección |
|---|---|
| Listado original con JDBC | `/AlquilaUDB/vivienda` |
| Listado JSF y JPA | `/AlquilaUDB/viviendas-jpa.xhtml` |
| Registrar vivienda | `/AlquilaUDB/registrar-vivienda.xhtml` |
| Detalle de vivienda | `/AlquilaUDB/detalle-vivienda.xhtml?id=1` |
| Editar vivienda | `/AlquilaUDB/editar-vivienda.xhtml?id=1` |
| Consultar y registrar reseñas | `/AlquilaUDB/resenas.xhtml?id=1` |

En las direcciones que contienen `id=1`, el identificador debe sustituirse por el de una vivienda existente.

La página inicial del proyecto puede conservar contenido de demostración. Para acceder a las funcionalidades de la Fase II, se recomienda abrir directamente `viviendas-jpa.xhtml`.

## 10. Persistencia y reglas de negocio

Las operaciones de la Fase I utilizan JDBC y sentencias SQL parametrizadas.

La Fase II utiliza Hibernate como proveedor JPA y `EntityManager` para gestionar entidades y transacciones.

Se implementaron operaciones de registro, consulta, actualización y eliminación de viviendas.

Las búsquedas utilizan JPQL con parámetros para filtrar viviendas por texto, tipo y disponibilidad.

En el módulo de reseñas se aplican reglas como:

- Calificaciones entre 1 y 5.
- Validación de autor y comentario.
- Asociación obligatoria de cada reseña con una vivienda existente.
- Protección contra la eliminación de viviendas con reseñas relacionadas.

## 11. Pruebas realizadas

Durante el desarrollo se comprobaron manualmente los siguientes escenarios:

- Consulta de viviendas desde JDBC y JPA.
- Registro de una vivienda y visualización desde ambas implementaciones.
- Actualización de una vivienda y persistencia de sus cambios.
- Eliminación de viviendas de prueba.
- Validación de campos mediante AJAX.
- Registro y consulta de reseñas asociadas a viviendas.
- Uso de un validador JSF personalizado.
- Búsqueda y filtrado de viviendas mediante JPQL.
- Visualización detallada de viviendas.
- Navegación entre las páginas principales.

Las evidencias de estas comprobaciones se organizarán en la documentación correspondiente a la Fase II.

## 12. Control de versiones

El código fuente del proyecto se administra con Git y se encuentra disponible en GitHub:

**Repositorio:** https://github.com/VictorZ23/AlquilaUDB

Los avances se registran mediante commits que permiten identificar la incorporación de las funcionalidades y recuperar versiones anteriores del proyecto.

## 13. Estado y evolución del proyecto

### Fase I

Implementación del módulo inicial de viviendas mediante Servlets, JSP, JSTL, JDBC y MySQL.

### Fase II

Incorporación de JSF, CDI, JPA, Hibernate, AJAX, validadores personalizados y la entidad `Resena`.

El incremento incluye un CRUD funcional, consultas JPQL, relaciones entre entidades y validaciones de formularios.

### Próximas fases

El proyecto continuará evolucionando conforme a los requisitos académicos de las siguientes entregas, incluyendo integración mediante servicios y otras tecnologías empresariales.

---

**AlquilaUDB — Universidad Don Bosco**  
**Asignatura:** Desarrollo de Aplicaciones con Web Frameworks (DWF901)