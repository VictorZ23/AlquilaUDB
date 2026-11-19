# AlquilaUDB — Documentación de la API REST

**Proyecto:** AlquilaUDB  
**Asignatura:** Desarrollo de Aplicaciones con Web Frameworks (DWF901)  
**Fase:** III — Servicios REST e Integración de Clientes  
**Tecnologías:** Java 11, Jersey 2.48, JAX-RS, Jackson, CDI, JPA, Hibernate y MySQL

## 1. Introducción

Durante la Fase III de AlquilaUDB se implementó una API REST para permitir que aplicaciones cliente consulten y gestionen información de viviendas mediante solicitudes HTTP y respuestas JSON.

La API reutiliza las entidades, los métodos de persistencia y las reglas de negocio desarrolladas durante la Fase II.

También se incorporó un cliente independiente desarrollado con HTML y JavaScript, que utiliza `fetch()` para comunicarse con los servicios REST.

## 2. Arquitectura

La aplicación sigue el siguiente flujo:

**Cliente HTTP → Jersey / JAX-RS → ViviendaResource → ViviendaJpaDAO → Hibernate / JPA → MySQL**

Los principales componentes son:

| Componente | Responsabilidad |
|---|---|
| `ApiConfig.java` | Configurar y registrar los recursos REST y el filtro de seguridad |
| `EstadoResource.java` | Comprobar el funcionamiento de la API |
| `ViviendaResource.java` | Procesar las solicitudes HTTP sobre viviendas y reseñas |
| `SeguridadApiFilter.java` | Controlar el acceso a las operaciones administrativas |
| `ViviendaJpaDAO.java` | Realizar las operaciones de persistencia |
| `Vivienda.java` | Representar la entidad de vivienda |
| `Resena.java` | Representar las reseñas vinculadas a viviendas |
| `cliente-rest.html` | Consumir la API mediante JavaScript |

El formato de intercambio de información es JSON, procesado mediante Jackson.

## 3. Dirección base

En el entorno local, los servicios se encuentran bajo:

`http://localhost:8080/AlquilaUDB/api`

Las direcciones documentadas corresponden a la configuración utilizada durante el desarrollo. El contexto y el puerto pueden variar en otro servidor.

## 4. Endpoints disponibles

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| GET | `/estado` | Comprobar estado de la API | Público |
| GET | `/viviendas` | Listar viviendas | Público |
| GET | `/viviendas/{id}` | Consultar vivienda individual | Público |
| POST | `/viviendas` | Registrar vivienda | Administrativo |
| PUT | `/viviendas/{id}` | Actualizar vivienda | Administrativo |
| DELETE | `/viviendas/{id}` | Eliminar vivienda | Administrativo |
| GET | `/viviendas/{id}/resenas` | Consultar reseñas | Público |
| POST | `/viviendas/{id}/resenas` | Registrar reseña | Público |

## 5. Descripción de los servicios

### 5.1. GET /estado

Comprueba que la API REST esté funcionando.

**Respuesta:** HTTP 200.

Ejemplo:

```json
{
  "aplicacion": "AlquilaUDB",
  "fase": "III",
  "estado": "API REST activa"
}
```

### 5.2. GET /viviendas

Obtiene la colección de viviendas registradas en MySQL.

**Respuesta:** HTTP 200.

Ejemplo ilustrativo:

```json
[
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
]
```

La respuesta contiene los campos seleccionados por `convertirVivienda()`.

La disponibilidad se almacena en la entidad, pero todavía no se incluye en la representación JSON actual de esta API.

### 5.3. GET /viviendas/{id}

Consulta los datos de una vivienda específica.

**Parámetro:** `id`, entero positivo.

Respuestas:

- HTTP 200: vivienda encontrada.
- HTTP 400: identificador inválido.
- HTTP 404: vivienda inexistente.
- HTTP 500: error interno de consulta.

### 5.4. POST /viviendas

Registra una nueva vivienda.

**Acceso:** requiere la cabecera `X-API-Key`.

**Formato:** `Content-Type: application/json`.

Ejemplo de solicitud:

```json
{
  "titulo": "Habitacion de prueba REST",
  "tipo": "Habitacion",
  "direccion": "Colonia Universitaria, casa 12",
  "zona": "Soyapango",
  "precio": 175.50,
  "habitaciones": 1,
  "descripcion": "Vivienda registrada desde REST.",
  "disponible": true
}
```

Si el registro es correcto, responde HTTP 201 Created e incluye una cabecera `Location` con la dirección del nuevo recurso.

Cuando los datos no cumplen las reglas de validación, responde HTTP 400.

### 5.5. PUT /viviendas/{id}

Actualiza una vivienda existente, sustituyendo los campos administrados mediante la API.

**Acceso:** requiere `X-API-Key`.

**Parámetro:** `id`, entero positivo.

**Formato:** JSON con los datos de la vivienda. En esta operación debe incluirse `disponible`.

El identificador se indica exclusivamente en la URL y no debe enviarse dentro del cuerpo JSON.

Respuestas:

- HTTP 200: actualización correcta.
- HTTP 400: datos o identificador inválidos.
- HTTP 404: vivienda inexistente.
- HTTP 500: error interno.

### 5.6. DELETE /viviendas/{id}

Elimina una vivienda cuando las reglas de integridad lo permiten.

**Acceso:** requiere `X-API-Key`.

Respuestas:

- HTTP 204: vivienda eliminada correctamente, sin cuerpo de respuesta.
- HTTP 400: identificador inválido.
- HTTP 404: vivienda inexistente.
- HTTP 409: vivienda con reseñas asociadas; eliminación rechazada.
- HTTP 500: error interno.

La operación reutiliza el método de eliminación del DAO, que comprueba la existencia de reseñas y protege la relación entre entidades.

### 5.7. GET /viviendas/{id}/resenas

Consulta las reseñas registradas para una vivienda existente.

**Respuesta:** HTTP 200.

Ejemplo ilustrativo:

```json
[
  {
    "id": 5,
    "autor": "Prueba REST",
    "calificacion": 5,
    "comentario": "Reseña registrada mediante la API.",
    "fecha": "2026-11-19 08:32:37"
  }
]
```

Si la vivienda existe, pero no tiene reseñas, se devuelve una colección vacía: `[]`.

Si la vivienda no existe, se devuelve HTTP 404.

### 5.8. POST /viviendas/{id}/resenas

Registra una nueva reseña asociada a una vivienda existente.

**Formato:** JSON.

Ejemplo de solicitud:

```json
{
  "autor": "Cliente JavaScript",
  "calificacion": 4,
  "comentario": "Esta reseña fue registrada desde el cliente REST."
}
```

Respuestas:

- HTTP 201: reseña registrada.
- HTTP 400: información inválida.
- HTTP 404: vivienda inexistente.
- HTTP 500: error interno.

La fecha de la reseña es generada por MySQL. Por ello, la respuesta inmediata del registro puede mostrar `fecha: null`. La fecha almacenada aparece al consultar nuevamente las reseñas.

## 6. Reglas de validación

### Viviendas

| Campo | Reglas |
|---|---|
| `titulo` | Texto de 3 a 100 caracteres |
| `tipo` | Texto de 2 a 30 caracteres |
| `direccion` | Texto de 5 a 150 caracteres |
| `zona` | Texto de 2 a 100 caracteres |
| `precio` | Número positivo, máximo 99,999,999.99 y dos decimales |
| `habitaciones` | Entero entre 1 y 100 |
| `descripcion` | Texto opcional de hasta 3,000 caracteres |
| `disponible` | Booleano `true` o `false`; obligatorio en PUT y opcional en POST |

### Reseñas

| Campo | Reglas |
|---|---|
| `autor` | Texto de 2 a 100 caracteres |
| `calificacion` | Entero entre 1 y 5 |
| `comentario` | Texto de 10 a 1,000 caracteres |

Las validaciones se ejecutan en el servidor. El cliente JavaScript realiza comprobaciones adicionales para facilitar la corrección de errores, pero no sustituye las validaciones de la API.

## 7. Seguridad y autorización

Se implementó `SeguridadApiFilter`, un filtro JAX-RS que intercepta las solicitudes antes de ejecutar las operaciones protegidas.

Las operaciones administrativas requieren una clave enviada mediante la cabecera HTTP:

`X-API-Key`

La clave válida se obtiene de la variable de entorno:

`ALQUILAUDB_API_KEY`

Esta variable debe configurarse en el entorno donde se ejecuta Tomcat.

La API exige que la clave configurada tenga al menos 24 caracteres.

### Respuestas del filtro

| HTTP | Situación |
|---|---|
| 503 | La clave administrativa del servidor no está configurada correctamente |
| 401 | La solicitud administrativa no proporciona una clave |
| 403 | La clave recibida es incorrecta |

Las consultas GET permanecen públicas. El registro de reseñas también es público en la implementación actual.

Este mecanismo representa una medida básica de control de acceso para el entorno académico local. No constituye un sistema completo de gestión de usuarios y roles.

Para un despliegue público se necesitarían HTTPS, gestión más robusta de credenciales, controles contra abusos y medidas adicionales de seguridad.

## 8. Formato de errores

Los errores controlados se devuelven como objetos JSON.

Ejemplo:

```json
{
  "error": "No existe una vivienda con el ID indicado."
}
```

Otro ejemplo:

```json
{
  "error": "La clave administrativa no es valida."
}
```

Esto permite que los clientes interpreten los errores y muestren mensajes comprensibles al usuario.

## 9. Cliente consumidor

El archivo `cliente-rest.html` contiene una aplicación JavaScript independiente de JSF.

El cliente utiliza `fetch()` para:

- Consultar viviendas mediante GET.
- Consultar detalles de viviendas.
- Consultar reseñas.
- Registrar reseñas mediante POST.
- Mostrar mensajes de carga, éxito y error.
- Actualizar las reseñas después de registrarlas.

El cliente mantiene el diseño visual utilizado en las fases anteriores, con paneles, fondos degradados y botones estilizados.

Las operaciones administrativas POST, PUT y DELETE de viviendas todavía no tienen formularios propios en este cliente.

## 10. Pruebas realizadas

Durante el desarrollo se comprobaron los siguientes comportamientos:

| Caso | Resultado |
|---|---|
| Consulta de estado de la API | HTTP 200 |
| Consulta de todas las viviendas | HTTP 200 |
| Consulta de vivienda existente | HTTP 200 |
| Identificador inválido | HTTP 400 |
| Vivienda inexistente | HTTP 404 |
| Registro válido de vivienda | HTTP 201 |
| Registro con precio negativo | HTTP 400 |
| Actualización válida de vivienda | HTTP 200 |
| Actualización con precio inválido | HTTP 400 |
| Eliminación válida | HTTP 204 |
| Eliminación de vivienda con reseñas | HTTP 409 |
| Registro válido de reseña | HTTP 201 |
| Reseña con calificación fuera del rango | HTTP 400 |
| Operación administrativa sin configuración de clave | HTTP 503 |
| Operación administrativa sin credencial | HTTP 401 |
| Operación administrativa con clave incorrecta | HTTP 403 |
| Operación autorizada que infringe integridad | HTTP 409 |
| Consulta y publicación de reseñas desde JavaScript | Funcionamiento comprobado |

Estas comprobaciones se realizaron manualmente mediante Firefox, Git Bash con `curl`, el cliente JavaScript y la interfaz JSF.

## 11. Limitaciones y mejoras pendientes

Al finalizar las pruebas principales se identifican los siguientes aspectos por revisar:

- Incorporar la disponibilidad a las respuestas JSON de viviendas.
- Revisar el control de acceso antes de publicar la aplicación fuera del entorno local.
- Incorporar protección frente a envíos abusivos de reseñas.
- Ampliar el cliente consumidor con operaciones administrativas autorizadas.
- Preparar una colección de pruebas reutilizable y los casos límite adicionales.
- Completar las evidencias y documentación de entrega.

## 12. Repositorio

**GitHub:** https://github.com/VictorZ23/AlquilaUDB

El repositorio contiene el código fuente, las configuraciones del proyecto Maven y la documentación académica.

Las credenciales administrativas deben permanecer fuera del control de versiones.

## 13. Conclusión

La Fase III permitió incorporar servicios REST al sistema AlquilaUDB, reutilizando la persistencia empresarial de la fase anterior.

Se implementaron operaciones HTTP de consulta, registro, actualización y eliminación de viviendas, además de consultas y registro de reseñas.

También se desarrolló un cliente consumidor en HTML y JavaScript, se añadieron validaciones y respuestas de error JSON, y se implementó un control básico de autorización para las operaciones administrativas.

La solución demuestra la integración de aplicaciones mediante HTTP, JSON, JAX-RS, Jersey, JPA, Hibernate y MySQL.
