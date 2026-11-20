# AlquilaUDB — Documentación de la API REST

**Proyecto:** AlquilaUDB
**Asignatura:** Desarrollo de Aplicaciones con Web Frameworks (DWF901)
**Fase:** III — Servicios REST e Integración de Clientes
**Fecha de revisión:** 19 de noviembre de 2026
**Repositorio:** https://github.com/VictorZ23/AlquilaUDB

## 1. Introducción

Durante la Fase III se incorporó una API REST a AlquilaUDB para permitir la consulta y administración de viviendas y reseñas mediante HTTP y JSON.

La implementación reutiliza las entidades y la persistencia JPA/Hibernate desarrolladas durante la Fase II.

También se desarrolló un cliente independiente con HTML, CSS y JavaScript. Inicialmente permitía consultar viviendas y publicar reseñas. Posteriormente se amplió para incorporar la creación, edición y eliminación de viviendas desde el navegador.

## 2. Objetivos

- Diseñar servicios REST para viviendas y reseñas.
- Implementar operaciones HTTP con Jersey y JAX-RS.
- Utilizar JSON como formato de intercambio.
- Integrar la API con JPA, Hibernate y MySQL.
- Validar solicitudes y proporcionar códigos HTTP adecuados.
- Proteger las operaciones administrativas.
- Desarrollar un cliente consumidor independiente de JSF.
- Demostrar el CRUD completo mediante HTTP y JavaScript.

## 3. Tecnologías utilizadas

| Tecnología | Función |
|---|---|
| Java 11 | Lenguaje de programación |
| Maven | Gestión del proyecto y dependencias |
| Apache Tomcat 9 | Servidor web |
| Jersey 2.48 | Implementación de JAX-RS |
| Jackson | Procesamiento y serialización JSON |
| CDI / Weld | Integración de componentes |
| JPA / Hibernate | Persistencia de entidades |
| MySQL | Base de datos |
| HTML5 y CSS3 | Interfaz del cliente |
| JavaScript y fetch() | Consumo de servicios REST |
| NetBeans 11.3 | Entorno de desarrollo |
| Git y GitHub | Control de versiones |

## 4. Arquitectura

La aplicación utiliza el siguiente flujo:

Cliente HTML/JavaScript
        |
        | HTTP / JSON
        v
Jersey / JAX-RS
        |
        v
SeguridadApiFilter
        |
        v
EstadoResource / ViviendaResource
        |
        v
ViviendaJpaDAO
        |
        v
JPA / Hibernate
        |
        v
MySQL

Las páginas JSP y JSF de fases anteriores siguen utilizando la misma base de datos.

La API REST constituye una vía adicional de acceso a las funcionalidades del sistema.

### 4.1. Componentes principales

| Clase o archivo | Responsabilidad |
|---|---|
| ApiConfig.java | Registrar recursos, filtros y manejadores |
| EstadoResource.java | Comprobar el estado de la API |
| ViviendaResource.java | Gestionar viviendas y reseñas |
| SeguridadApiFilter.java | Proteger las operaciones administrativas |
| JsonMalformadoMapper.java | Gestionar errores de JSON malformado |
| TipoContenidoMapper.java | Gestionar tipos de contenido incompatibles |
| ViviendaJpaDAO.java | Ejecutar operaciones de persistencia |
| Vivienda.java | Entidad de vivienda |
| Resena.java | Entidad de reseña |
| cliente-rest.html | Interfaz JavaScript consumidora de REST |

## 5. Dirección base

En el entorno de desarrollo:

http://localhost:8080/AlquilaUDB/api

Las direcciones corresponden a Tomcat ejecutándose localmente en el puerto 8080.

## 6. Contrato de endpoints

| Método | Endpoint | Funcionalidad | Acceso |
|---|---|---|---|
| GET | /estado | Consultar estado de la API | Público |
| GET | /viviendas | Listar viviendas | Público |
| GET | /viviendas/{id} | Obtener una vivienda | Público |
| POST | /viviendas | Registrar vivienda | Administrativo |
| PUT | /viviendas/{id} | Actualizar vivienda | Administrativo |
| DELETE | /viviendas/{id} | Eliminar vivienda | Administrativo |
| GET | /viviendas/{id}/resenas | Consultar reseñas | Público |
| POST | /viviendas/{id}/resenas | Registrar reseña | Público |

Los endpoints utilizan JSON para representar los recursos y los errores controlados.

## 7. Descripción de servicios

### 7.1. GET /estado

Comprueba el funcionamiento de la API.

Respuesta esperada: HTTP 200.

Ejemplo:

```json
{
  "aplicacion": "AlquilaUDB",
  "fase": "III",
  "estado": "API REST activa"
}
```

### 7.2. GET /viviendas

Obtiene todas las viviendas registradas.

Respuesta correcta: HTTP 200.

Ejemplo ilustrativo de un elemento de la colección:

```json
{
  "id": 1,
  "titulo": "Apartamento universitario",
  "tipo": "Apartamento",
  "direccion": "Colonia Universitaria",
  "zona": "San Salvador",
  "precio": 350.04,
  "habitaciones": 2,
  "descripcion": "Apartamento cercano a la universidad."
}
```

La disponibilidad se almacena en MySQL, pero el método actual que construye las respuestas JSON no incluye el campo `disponible`.

### 7.3. GET /viviendas/{id}

Obtiene la información de una vivienda específica.

Parámetro:

- `id`: identificador entero positivo.

Respuestas:

| HTTP | Situación |
|---|---|
| 200 | Vivienda encontrada |
| 400 | Identificador inválido |
| 404 | Vivienda inexistente |
| 500 | Error interno |

### 7.4. POST /viviendas

Registra una vivienda nueva.

Requiere:

- Content-Type: application/json
- X-API-Key: clave administrativa

Ejemplo de solicitud:

```json
{
  "titulo": "Habitacion de prueba REST",
  "tipo": "Habitacion",
  "direccion": "Colonia Universitaria, casa 12",
  "zona": "Soyapango",
  "precio": 175.50,
  "habitaciones": 1,
  "descripcion": "Registro temporal de prueba.",
  "disponible": true
}
```

Respuesta correcta: HTTP 201 Created.

La respuesta incluye una cabecera `Location` con la dirección del recurso creado y un JSON con sus datos.

Los datos inválidos producen HTTP 400.

### 7.5. PUT /viviendas/{id}

Actualiza una vivienda existente.

Requiere:

- Identificador válido en la URL.
- Cuerpo JSON con los datos completos.
- Cabecera X-API-Key.
- Campo `disponible` obligatorio.

El identificador no debe incluirse dentro del cuerpo JSON.

Ejemplo:

```json
{
  "titulo": "Habitacion REST actualizada",
  "tipo": "Habitacion",
  "direccion": "Colonia Universitaria, casa 12",
  "zona": "Soyapango",
  "precio": 180.50,
  "habitaciones": 2,
  "descripcion": "Registro actualizado desde REST.",
  "disponible": false
}
```

Respuestas:

| HTTP | Situación |
|---|---|
| 200 | Actualización correcta |
| 400 | Datos inválidos |
| 404 | Vivienda inexistente |
| 500 | Error interno |

### 7.6. DELETE /viviendas/{id}

Elimina una vivienda, siempre que no tenga reseñas asociadas.

Requiere la cabecera X-API-Key.

Respuestas:

| HTTP | Situación |
|---|---|
| 204 | Eliminación correcta, sin cuerpo |
| 400 | Identificador inválido |
| 404 | Vivienda inexistente |
| 409 | Vivienda con reseñas asociadas |
| 500 | Error interno |

La protección de integridad se implementa en la capa de persistencia y se conserva cuando la operación se ejecuta desde REST.

### 7.7. GET /viviendas/{id}/resenas

Consulta las reseñas de una vivienda.

Respuesta correcta: HTTP 200.

Ejemplo ilustrativo:

```json
[
  {
    "id": 5,
    "autor": "Estudiante",
    "calificacion": 4,
    "comentario": "La habitacion esta en buenas condiciones.",
    "fecha": "2026-11-19 08:32:37"
  }
]
```

Cuando una vivienda existe, pero no tiene reseñas, la respuesta es:

```json
[]
```

Un identificador inválido produce HTTP 400 y una vivienda inexistente produce HTTP 404.

### 7.8. POST /viviendas/{id}/resenas

Registra una reseña vinculada a una vivienda existente.

Esta operación es pública en la implementación actual.

Ejemplo:

```json
{
  "autor": "Cliente JavaScript",
  "calificacion": 4,
  "comentario": "Esta reseña fue enviada desde el cliente REST."
}
```

Respuestas:

| HTTP | Situación |
|---|---|
| 201 | Reseña registrada |
| 400 | Información inválida |
| 404 | Vivienda inexistente |
| 500 | Error interno |

La fecha es generada por MySQL. La respuesta inmediata del registro puede contener `fecha: null`; la fecha almacenada aparece al consultar nuevamente las reseñas.

## 8. Validaciones del servidor

### 8.1. Viviendas

| Campo | Reglas |
|---|---|
| titulo | Texto de 3 a 100 caracteres |
| tipo | Texto de 2 a 30 caracteres |
| direccion | Texto de 5 a 150 caracteres |
| zona | Texto de 2 a 100 caracteres |
| precio | Número positivo, máximo 99999999.99 y hasta dos decimales |
| habitaciones | Entero entre 1 y 100 |
| descripcion | Texto opcional, máximo 3000 caracteres |
| disponible | Booleano; opcional en POST y obligatorio en PUT |

Cuando `disponible` no se envía en POST, se utiliza `true` como valor predeterminado.

### 8.2. Reseñas

| Campo | Reglas |
|---|---|
| autor | Texto de 2 a 100 caracteres |
| calificacion | Entero entre 1 y 5 |
| comentario | Texto de 10 a 1000 caracteres |

Las validaciones se ejecutan en el servidor y no dependen de las comprobaciones del navegador.

## 9. Seguridad administrativa

Se implementó `SeguridadApiFilter`, un filtro JAX-RS que controla el acceso a las operaciones administrativas.

Las consultas GET son públicas, al igual que el registro de reseñas.

Las siguientes operaciones requieren autorización:

- POST /viviendas
- PUT /viviendas/{id}
- DELETE /viviendas/{id}

El cliente envía la credencial mediante:

X-API-Key: [credencial administrativa]

El servidor consulta la variable de entorno:

ALQUILAUDB_API_KEY

La clave configurada debe tener por lo menos 24 caracteres.

### 9.1. Respuestas de seguridad

| HTTP | Situación |
|---|---|
| 503 | Clave administrativa del servidor no configurada |
| 401 | Solicitud protegida sin credencial |
| 403 | Credencial incorrecta |

Una clave correcta autoriza la solicitud, pero no elimina las restricciones de integridad. Por ejemplo, eliminar una vivienda con reseñas devuelve HTTP 409.

### 9.2. Consideraciones de seguridad

La clave administrativa no se incorpora al código fuente ni al repositorio Git.

El cliente utiliza un campo de contraseña y no guarda la clave en localStorage ni sessionStorage.

Este mecanismo representa un control básico para la demostración académica local. No equivale a un sistema de usuarios con roles.

Antes de un despliegue público serían necesarios HTTPS, una gestión más robusta de credenciales y controles adicionales contra abusos.

## 10. Manejo de excepciones

Los errores controlados utilizan respuestas JSON con la propiedad `error`.

Ejemplo:

```json
{
  "error": "No existe una vivienda con el ID indicado."
}
```

Se incorporaron dos manejadores adicionales.

### JsonMalformadoMapper

Gestiona errores de sintaxis en solicitudes JSON.

Código: HTTP 400.

```json
{
  "error": "El contenido JSON de la solicitud no es valido."
}
```

### TipoContenidoMapper

Gestiona tipos de contenido incompatibles.

Código: HTTP 415.

```json
{
  "error": "Tipo de contenido no admitido. Utilice application/json."
}
```

Ambos manejadores fueron comprobados mediante curl.

## 11. Cliente consumidor JavaScript

El archivo `src/main/webapp/cliente-rest.html` implementa una interfaz independiente de JSF.

Utiliza JavaScript y `fetch()` para comunicarse con la API REST.

### 11.1. Funcionalidades públicas

El cliente permite:

- Consultar viviendas.
- Consultar detalles de una vivienda.
- Consultar reseñas registradas.
- Publicar reseñas.
- Actualizar la información después del registro.
- Mostrar mensajes de carga, éxito y error.

### 11.2. Administración de viviendas

El cliente incorpora una sección denominada "Administrar viviendas".

Permite:

| Operación | Método REST |
|---|---|
| Agregar vivienda | POST |
| Editar vivienda | PUT |
| Eliminar vivienda | DELETE |

El formulario incluye título, tipo, dirección, zona, precio, habitaciones, descripción y disponibilidad.

Para editar, el cliente consulta primero los datos mediante GET y los incorpora al formulario.

Como el GET actual no devuelve `disponible`, el usuario debe seleccionar expresamente la disponibilidad antes de guardar la edición.

La eliminación requiere confirmación del usuario antes de enviar DELETE.

Los botones administrativos se muestran cuando se abre la sección correspondiente.

### 11.3. Autorización desde el cliente

La sección administrativa incluye un campo de tipo contraseña para introducir la clave.

El cliente la envía mediante la cabecera X-API-Key.

La clave no se encuentra escrita en el código ni se almacena persistentemente en el navegador.

La validación de autorización se realiza en el servidor.

### 11.4. Mensajes al usuario

El cliente presenta:

- Estados de carga.
- Confirmación de registros y actualizaciones.
- Confirmación previa a la eliminación.
- Mensajes de autorización incorrecta.
- Mensajes de conflicto de integridad.
- Mensajes ante fallos de comunicación.

Los errores de red se traducen a un mensaje más comprensible para el usuario en la versión ampliada del cliente.

### 11.5. Diseño

La interfaz mantiene la identidad visual de AlquilaUDB mediante fondos azulados, tarjetas, paneles, formularios y botones.

La versión ampliada permite demostrar el CRUD administrativo desde el navegador sin utilizar herramientas externas como curl.

## 12. Pruebas realizadas

Se comprobaron los siguientes resultados de la API.

| Escenario | Resultado |
|---|---|
| Consultar viviendas | HTTP 200 |
| Consultar recurso inexistente | HTTP 404 |
| Enviar identificador inválido | HTTP 400 |
| Registrar vivienda válida | HTTP 201 |
| Registrar vivienda con precio inválido | HTTP 400 |
| Actualizar vivienda | HTTP 200 |
| Eliminar vivienda sin reseñas | HTTP 204 |
| Eliminar vivienda con reseñas | HTTP 409 |
| Registrar reseña válida | HTTP 201 |
| Enviar reseña inválida | HTTP 400 |
| Enviar JSON malformado | HTTP 400 JSON |
| Enviar tipo de contenido incompatible | HTTP 415 JSON |
| Solicitud administrativa sin configuración | HTTP 503 |
| Solicitud administrativa sin credencial | HTTP 401 |
| Solicitud administrativa con clave incorrecta | HTTP 403 |
| Solicitud autorizada con conflicto de integridad | HTTP 409 |

También se comprobó la persistencia del campo `disponible` en MySQL y la detección de interrupciones de comunicación desde el cliente.

La interfaz JavaScript ampliada se encuentra implementada y su funcionamiento general fue confirmado. Las pruebas individuales de creación, edición y eliminación desde sus controles deben registrarse en la matriz de pruebas con evidencias específicas.

Los resultados detallados se documentan en:

`docs/matriz-pruebas-fase-III.md`

## 13. Limitaciones y mejoras futuras

- Incorporar `disponible` a las respuestas GET.
- Completar evidencias individuales del CRUD desde el cliente JavaScript.
- Incorporar protección contra envíos abusivos de reseñas.
- Sustituir la clave administrativa compartida por un sistema de autenticación más robusto.
- Utilizar HTTPS en cualquier despliegue fuera del entorno local.
- Ampliar las pruebas automatizadas y de integración.

## 14. Conclusión

La Fase III incorporó servicios REST a AlquilaUDB, reutilizando la persistencia y las reglas de negocio de la Fase II.

Se implementaron operaciones CRUD sobre viviendas, consulta y registro de reseñas, validaciones, respuestas HTTP apropiadas, autorización administrativa y manejadores de errores JSON.

El cliente JavaScript se amplió para permitir tanto la consulta pública como la administración de viviendas desde el navegador.

La solución demuestra la integración entre HTML, JavaScript, Jersey, JAX-RS, JPA, Hibernate y MySQL.

---

**Repositorio:** https://github.com/VictorZ23/AlquilaUDB