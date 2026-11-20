# AlquilaUDB

## Sistema web de gestión de viviendas para estudiantes universitarios

**Universidad Don Bosco**  
**Asignatura:** Desarrollo de Aplicaciones con Web Frameworks (DWF901)  
**Estudiante:** Carlos Víctor Zaun Gómez  
**Proyecto académico — Fases I, II y III**

---

## 1. Descripción del proyecto

AlquilaUDB es una aplicación web orientada a la consulta y administración de viviendas destinadas a estudiantes universitarios.

El sistema permite registrar, consultar, actualizar y eliminar viviendas, así como publicar reseñas y calificaciones.

El proyecto se desarrolló de manera incremental durante tres fases, incorporando diferentes tecnologías del ecosistema Java Web.

Durante la Fase III se implementó una API REST que permite utilizar las funcionalidades del sistema desde clientes independientes mediante HTTP y JSON.

También se desarrolló un cliente HTML y JavaScript que consume los servicios REST y permite realizar operaciones CRUD desde el navegador.

## 2. Objetivos

### Objetivo general

Desarrollar una aplicación web para gestionar viviendas estudiantiles, aplicando frameworks Java, persistencia de datos, servicios REST e integración de clientes.

### Objetivos específicos

- Implementar operaciones CRUD sobre viviendas.
- Consultar viviendas y sus características.
- Incorporar filtros de búsqueda.
- Gestionar reseñas y calificaciones.
- Integrar una base de datos MySQL.
- Utilizar JDBC, JPA e Hibernate.
- Desarrollar servicios REST mediante Jersey.
- Intercambiar información utilizando JSON.
- Implementar un cliente JavaScript independiente.
- Incorporar validaciones y manejo de errores.
- Proteger las operaciones administrativas de la API.
- Documentar el desarrollo y las pruebas realizadas.

## 3. Tecnologías utilizadas

| Tecnología | Utilización |
|---|---|
| Java 11 | Lenguaje principal |
| Apache Maven | Gestión de dependencias |
| Apache Tomcat 9 | Servidor de aplicaciones web |
| NetBeans 11.3 | Entorno de desarrollo |
| MySQL | Base de datos relacional |
| XAMPP | Entorno local de MySQL |
| JDBC | Acceso a datos en la Fase I |
| JSP y JSTL | Vistas de la Fase I |
| Servlets | Controladores de la Fase I |
| JSF 2.3 | Interfaz de la Fase II |
| CDI / Weld | Gestión de componentes |
| JPA / Hibernate | Persistencia de la Fase II y III |
| Jersey 2.48 | Implementación REST |
| JAX-RS | Definición de servicios HTTP |
| Jackson | Procesamiento JSON |
| HTML5 y CSS3 | Interfaces web |
| JavaScript / fetch() | Cliente REST |
| Git y GitHub | Control de versiones |

## 4. Desarrollo por fases

### 4.1. Fase I — JSP, Servlets y JDBC

En la primera fase se implementó una aplicación web basada en el patrón MVC.

Sus principales características incluyen:

- Listado de viviendas.
- Consulta de detalles.
- Registro de viviendas.
- Edición de viviendas.
- Eliminación con confirmación.
- Validaciones básicas.
- Conexión a MySQL mediante JDBC.
- Separación entre vistas, controladores y DAO.

Se utilizaron JSP, JSTL y Servlets.

### 4.2. Fase II — JSF, CDI, JPA e Hibernate

En la segunda fase se incorporó una nueva capa de presentación mediante JSF y una capa de persistencia mediante JPA e Hibernate.

Se implementaron:

- Listado de viviendas con JSF.
- Búsqueda por título y zona.
- Filtros por tipo y disponibilidad.
- Registro y edición de viviendas.
- Consulta de detalles.
- Eliminación controlada.
- Reseñas y calificaciones.
- Validaciones de formularios.
- Interacciones AJAX.
- Relaciones entre entidades JPA.

También se incorporó una regla de integridad que impide eliminar viviendas con reseñas asociadas.

Las funcionalidades de la Fase I se conservaron.

### 4.3. Fase III — API REST y cliente JavaScript

En la tercera fase se implementó una API REST utilizando Jersey y JAX-RS.

La API permite:

- Consultar viviendas.
- Consultar una vivienda específica.
- Registrar viviendas.
- Actualizar viviendas.
- Eliminar viviendas.
- Consultar reseñas.
- Registrar reseñas.
- Comprobar el estado de la API.

Las solicitudes utilizan HTTP y JSON.

También se implementaron:

- Validaciones del lado del servidor.
- Códigos HTTP apropiados.
- Manejo de errores JSON.
- Protección administrativa mediante API Key.
- Cliente consumidor desarrollado con HTML y JavaScript.

Posteriormente, el cliente JavaScript se amplió para incorporar formularios de creación y edición, botones de eliminación y confirmación previa al borrado.

De esta manera, el CRUD REST puede utilizarse desde el navegador sin depender de herramientas como curl.

## 5. Arquitectura del sistema

El proyecto conserva las funcionalidades de las fases anteriores y añade nuevos mecanismos de acceso.

### Fase I

JSP / JSTL → Servlets → DAO JDBC → MySQL

### Fase II

JSF → Managed Beans / CDI → DAO JPA → Hibernate → MySQL

### Fase III

Cliente HTML / JavaScript  
↓  
HTTP / JSON  
↓  
Jersey / JAX-RS  
↓  
Recursos REST y filtros  
↓  
ViviendaJpaDAO  
↓  
JPA / Hibernate  
↓  
MySQL

Los diferentes módulos utilizan la misma base de datos.

## 6. Estructura principal del proyecto

```text
AlquilaUDB/
├── database/
│   └── alquilaudb.sql
│
├── docs/
│   ├── api-rest-fase-III.md
│   ├── matriz-pruebas-fase-II.md
│   ├── matriz-pruebas-fase-III.md
│   ├── product-backlog-fase-II.md
│   └── product-backlog-fase-III.md
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/alquilaudb/
│       │       ├── api/
│       │       ├── bean/
│       │       ├── controller/
│       │       ├── dao/
│       │       ├── jpa/
│       │       └── model/
│       │
│       ├── resources/
│       │
│       └── webapp/
│           ├── css/
│           ├── cliente-rest.html
│           ├── viviendas-jpa.xhtml
│           ├── registrar-vivienda.xhtml
│           ├── editar-vivienda.xhtml
│           ├── detalle-vivienda.xhtml
│           ├── resenas.xhtml
│           └── archivos JSP de la Fase I
│
├── pom.xml
└── README.md
```

La estructura representa los principales módulos del proyecto.

## 7. Base de datos

El proyecto utiliza la base de datos:

`alquilaudb`

El archivo:

`database/alquilaudb.sql`

contiene el script para preparar la estructura utilizada por la aplicación.

### Tabla vivienda

Almacena:

- Identificador.
- Título.
- Tipo.
- Dirección.
- Zona.
- Precio.
- Habitaciones.
- Descripción.
- Disponibilidad.

### Tabla resena

Almacena:

- Identificador.
- Vivienda relacionada.
- Autor.
- Calificación.
- Comentario.
- Fecha.

Las reseñas se relacionan con las viviendas mediante una clave foránea.

El sistema no permite eliminar una vivienda cuando tiene reseñas asociadas.

## 8. Requisitos para ejecutar el proyecto

Se necesita:

1. JDK 11.
2. Apache NetBeans 11.3 o un IDE compatible.
3. Apache Maven.
4. Apache Tomcat 9.
5. MySQL.
6. Un navegador web moderno.

Durante el desarrollo se utilizó XAMPP para ejecutar MySQL localmente.

## 9. Instalación y ejecución

### 9.1. Obtener el proyecto

Clonar el repositorio:

```bash
git clone https://github.com/VictorZ23/AlquilaUDB.git
```

También puede descargarse desde GitHub.

### 9.2. Preparar MySQL

1. Iniciar MySQL desde XAMPP.
2. Abrir phpMyAdmin.
3. Importar el script `database/alquilaudb.sql`.
4. Verificar que exista la base de datos `alquilaudb`.
5. Comprobar que se hayan creado las tablas necesarias.

### 9.3. Configurar la conexión

Revisar las configuraciones de acceso a MySQL utilizadas por JDBC y JPA.

La unidad de persistencia JPA se denomina:

`AlquilaUDBPU`

La aplicación utiliza persistencia local con Hibernate.

Las credenciales y parámetros deben ajustarse al entorno donde se ejecutará el proyecto.

No deben publicarse contraseñas reales en el repositorio.

### 9.4. Abrir el proyecto

1. Abrir NetBeans.
2. Seleccionar File → Open Project.
3. Elegir la carpeta AlquilaUDB.
4. Comprobar que Maven cargue las dependencias.
5. Configurar Apache Tomcat 9 como servidor.

### 9.5. Compilar

Desde NetBeans:

1. Detener Tomcat si se encuentra ejecutándose.
2. Seleccionar Clean and Build.
3. Comprobar que aparezca BUILD SUCCESS.
4. Ejecutar el proyecto mediante Run.

También puede compilarse desde la carpeta principal mediante:

```bash
mvn clean package
```

### 9.6. Acceder a la aplicación

Las direcciones corresponden a Tomcat ejecutándose localmente en el puerto 8080.

**Módulo JSF:**

http://localhost:8080/AlquilaUDB/viviendas-jpa.xhtml

**Cliente REST JavaScript:**

http://localhost:8080/AlquilaUDB/cliente-rest.html

**Estado de la API:**

http://localhost:8080/AlquilaUDB/api/estado

**Listado JSON de viviendas:**

http://localhost:8080/AlquilaUDB/api/viviendas

## 10. Endpoints REST

La dirección base es:

`http://localhost:8080/AlquilaUDB/api`

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| GET | /estado | Estado de la API | Público |
| GET | /viviendas | Listar viviendas | Público |
| GET | /viviendas/{id} | Consultar vivienda | Público |
| POST | /viviendas | Registrar vivienda | Administrativo |
| PUT | /viviendas/{id} | Actualizar vivienda | Administrativo |
| DELETE | /viviendas/{id} | Eliminar vivienda | Administrativo |
| GET | /viviendas/{id}/resenas | Consultar reseñas | Público |
| POST | /viviendas/{id}/resenas | Registrar reseña | Público |

### Ejemplo de consulta

```bash
curl -i http://localhost:8080/AlquilaUDB/api/viviendas
```

La respuesta correcta devuelve HTTP 200 y una colección JSON.

### Ejemplo de registro de reseña

```json
{
  "autor": "Estudiante",
  "calificacion": 5,
  "comentario": "La vivienda se encuentra en buenas condiciones."
}
```

Para conocer todos los parámetros y las reglas de validación, consultar:

[Documentación REST de la Fase III](docs/api-rest-fase-III.md)

## 11. Seguridad de la API

Las operaciones administrativas sobre viviendas requieren una clave de acceso.

El servidor obtiene la clave mediante la variable de entorno:

`ALQUILAUDB_API_KEY`

La clave configurada debe tener al menos 24 caracteres.

Los clientes autorizados envían la credencial en la cabecera:

`X-API-Key`

### Configuración local

En Windows:

1. Abrir Variables de entorno.
2. Crear la variable de usuario `ALQUILAUDB_API_KEY`.
3. Asignarle una clave segura de al menos 24 caracteres.
4. Reiniciar NetBeans y Tomcat para que reciban la configuración.

No incluir la clave en archivos versionados.

### Respuestas de seguridad

| HTTP | Situación |
|---|---|
| 401 | Falta la credencial administrativa |
| 403 | La credencial es incorrecta |
| 503 | La clave del servidor no está configurada correctamente |

La autorización no elimina las reglas de negocio.

Por ejemplo, una solicitud autorizada para eliminar una vivienda con reseñas sigue recibiendo HTTP 409.

Este mecanismo es una protección básica para el entorno académico local.

No debe considerarse suficiente para publicar la aplicación en Internet sin HTTPS y controles adicionales.

## 12. Cliente REST JavaScript

El archivo:

`src/main/webapp/cliente-rest.html`

implementa una interfaz independiente de JSF.

### Funciones públicas

- Consultar viviendas.
- Consultar detalles.
- Consultar reseñas.
- Registrar reseñas.
- Actualizar información.
- Mostrar mensajes de carga y error.

### Administración de viviendas

El cliente incorpora el botón:

**Administrar viviendas**

Desde esa sección se pueden realizar las siguientes operaciones:

- Agregar viviendas mediante POST.
- Editar viviendas mediante PUT.
- Eliminar viviendas mediante DELETE.
- Confirmar las eliminaciones.
- Visualizar mensajes de error y éxito.

Las operaciones protegidas requieren introducir la clave administrativa.

La clave no se almacena de forma persistente en el navegador.

### Disponibilidad

El formulario permite seleccionar si una vivienda está disponible.

Actualmente, las respuestas GET no incluyen el valor de `disponible`.

Por esta razón, al editar una vivienda, el usuario debe seleccionar explícitamente su disponibilidad antes de guardar.

## 13. Validaciones y errores

La API valida los datos antes de realizar operaciones sobre la base de datos.

Entre las validaciones se incluyen:

- Longitud de títulos y direcciones.
- Precios positivos.
- Cantidad válida de habitaciones.
- Calificaciones enteras entre 1 y 5.
- Longitud de comentarios.
- Identificadores positivos.
- Existencia de recursos.
- Restricciones de integridad.

También se incorporaron manejadores de errores para JSON malformado y tipos de contenido incompatibles.

### Principales códigos HTTP

| Código | Significado |
|---|---|
| 200 | Operación correcta |
| 201 | Recurso creado |
| 204 | Recurso eliminado |
| 400 | Solicitud incorrecta |
| 401 | Credencial ausente |
| 403 | Credencial incorrecta |
| 404 | Recurso inexistente |
| 409 | Conflicto de integridad |
| 415 | Tipo de contenido incompatible |
| 503 | Seguridad administrativa no configurada |

Los errores controlados se representan mediante objetos JSON.

## 14. Pruebas realizadas

Durante la Fase III se comprobaron:

- Consultas REST.
- Registro de viviendas.
- Actualización de viviendas.
- Eliminación de viviendas.
- Registro y consulta de reseñas.
- Validaciones de datos.
- Recursos inexistentes.
- JSON malformado.
- Tipos de contenido incompatibles.
- Seguridad administrativa.
- Restricciones de integridad.
- Persistencia del campo disponible.
- Recuperación del cliente ante fallos de conexión.

Las pruebas LIM-01 a LIM-10 fueron completadas.

La ampliación administrativa del cliente JavaScript se encuentra implementada. Sus pruebas individuales y evidencias visuales se registran por separado en la matriz de pruebas.

## 15. Documentación del proyecto

### Fase II

- [Matriz de pruebas](docs/matriz-pruebas-fase-II.md)
- [Product Backlog](docs/product-backlog-fase-II.md)

### Fase III

- [Documentación técnica de la API REST](docs/api-rest-fase-III.md)
- [Matriz de pruebas](docs/matriz-pruebas-fase-III.md)
- [Product Backlog](docs/product-backlog-fase-III.md)

La documentación contiene los criterios de aceptación, las pruebas y el estado de las funcionalidades implementadas.

## 16. Limitaciones y mejoras futuras

Entre las mejoras identificadas se encuentran:

- Incorporar el campo disponible a las respuestas GET.
- Implementar autenticación con usuarios y roles.
- Utilizar HTTPS en un despliegue público.
- Añadir protección contra publicaciones abusivas de reseñas.
- Incorporar pruebas automatizadas.
- Ampliar las evidencias individuales del CRUD JavaScript.

## 17. Conclusión

AlquilaUDB evolucionó desde una aplicación Java Web basada en JSP, Servlets y JDBC hacia una solución que incorpora JSF, CDI, JPA, Hibernate, servicios REST y un cliente JavaScript independiente.

La implementación conserva la integración con MySQL y reutiliza las reglas de negocio desarrolladas en las fases anteriores.

La Fase III permite consumir la información mediante HTTP y JSON, incorporando operaciones CRUD, validaciones, manejo de errores y controles básicos de seguridad.

El cliente JavaScript ampliado permite demostrar las principales operaciones directamente desde el navegador.

El proyecto documenta así la integración entre frontend, servicios web y persistencia de datos.

---

**Repositorio oficial:**  
https://github.com/VictorZ23/AlquilaUDB