# AlquilaUDB — Product Backlog de la Fase III

**Proyecto:** AlquilaUDB
**Asignatura:** Desarrollo de Aplicaciones con Web Frameworks (DWF901)
**Sprint:** III — Servicios REST e Integración de Clientes
**Fecha de revisión:** 19 de noviembre de 2026

## 1. Objetivo del sprint

Extender AlquilaUDB mediante servicios REST que permitan consultar y gestionar viviendas y reseñas utilizando HTTP y JSON.

El desarrollo reutiliza las entidades y operaciones de persistencia implementadas durante la Fase II mediante JPA, Hibernate y MySQL.

El sprint incorpora un cliente independiente de JSF, desarrollado con HTML, CSS y JavaScript.

Inicialmente el cliente permitía consultar viviendas y publicar reseñas. Posteriormente se amplió para ofrecer también las operaciones administrativas de creación, edición y eliminación de viviendas.

## 2. Historias de usuario

| ID | Historia de usuario | Prioridad | Estado |
|---|---|---|---|
| HU-III-01 | Como desarrollador, quiero configurar Jersey y JAX-RS para ofrecer servicios REST. | Alta | Completada |
| HU-III-02 | Como consumidor, quiero consultar el estado de la API para verificar su disponibilidad. | Media | Completada |
| HU-III-03 | Como visitante, quiero consultar las viviendas registradas mediante JSON. | Alta | Completada |
| HU-III-04 | Como visitante, quiero consultar una vivienda específica. | Alta | Completada |
| HU-III-05 | Como administrador, quiero registrar viviendas mediante POST. | Alta | Completada |
| HU-III-06 | Como administrador, quiero actualizar viviendas mediante PUT. | Alta | Completada |
| HU-III-07 | Como administrador, quiero eliminar viviendas mediante DELETE cuando las reglas de integridad lo permitan. | Alta | Completada |
| HU-III-08 | Como visitante, quiero consultar las reseñas de las viviendas. | Alta | Completada |
| HU-III-09 | Como visitante, quiero publicar reseñas mediante REST. | Alta | Completada |
| HU-III-10 | Como usuario, quiero consultar viviendas y reseñas desde un cliente JavaScript independiente. | Alta | Completada |
| HU-III-11 | Como consumidor, quiero recibir respuestas HTTP y mensajes JSON apropiados ante errores. | Alta | Completada |
| HU-III-12 | Como administrador, quiero proteger las operaciones de escritura mediante una clave. | Alta | Completada |
| HU-III-13 | Como desarrollador, quiero documentar los endpoints y las validaciones de la API. | Alta | Completada |
| HU-III-14 | Como evaluador, quiero disponer de pruebas funcionales, negativas y de seguridad. | Alta | Completada para la API |
| HU-III-15 | Como usuario, quiero recibir información cuando el servidor no responda. | Media | Completada con mejora de mensaje pendiente de verificar |
| HU-III-16 | Como desarrollador, quiero verificar que la disponibilidad se almacene correctamente. | Media | Completada |
| HU-III-17 | Como evaluador, quiero disponer de un informe de evidencias de la Fase III. | Alta | Elaborado; actualización final pendiente |
| HU-III-18 | Como administrador, quiero agregar, editar y eliminar viviendas desde JavaScript sin depender de herramientas externas. | Alta | Implementada; evidencia individual pendiente |

## 3. Criterios de aceptación

### 3.1. API REST

- Los recursos se publican bajo /AlquilaUDB/api.
- Las consultas GET devuelven datos JSON.
- POST permite registrar viviendas y reseñas.
- PUT permite actualizar viviendas.
- DELETE permite eliminar viviendas sin reseñas.
- Las operaciones utilizan ViviendaJpaDAO.
- Las reglas de negocio de la Fase II permanecen vigentes.

### 3.2. Validaciones

- Los datos incorrectos se rechazan con HTTP 400.
- Los recursos inexistentes generan HTTP 404.
- La eliminación de viviendas con reseñas genera HTTP 409.
- Las solicitudes JSON malformadas generan HTTP 400.
- Los tipos de contenido incompatibles generan HTTP 415.
- Los errores controlados utilizan mensajes JSON.

### 3.3. Seguridad

- Las consultas públicas no requieren credenciales.
- El registro de reseñas permanece público.
- POST, PUT y DELETE de viviendas requieren X-API-Key.
- La clave se configura mediante variable de entorno.
- Las solicitudes sin credencial generan HTTP 401.
- Las credenciales incorrectas generan HTTP 403.
- La configuración administrativa ausente genera HTTP 503.
- Una clave válida no anula las restricciones de integridad.
- La clave administrativa no debe publicarse en GitHub.

### 3.4. Cliente JavaScript

- Consulta viviendas mediante fetch().
- Muestra detalles y reseñas.
- Permite publicar opiniones.
- Incorpora un apartado de administración.
- Permite registrar viviendas mediante POST.
- Permite editar viviendas mediante PUT.
- Permite eliminar viviendas mediante DELETE.
- Exige confirmación antes de eliminar.
- Envía la credencial administrativa mediante X-API-Key.
- Muestra mensajes de éxito y error.
- Actualiza el listado después de las operaciones.

## 4. Incremento desarrollado

### 4.1. Configuración REST

Se añadieron las dependencias de Jersey y Jackson al proyecto Maven.

La clase ApiConfig registra los recursos REST, el filtro de seguridad y los manejadores de excepciones.

### 4.2. Gestión de viviendas

ViviendaResource proporciona los siguientes servicios:

- GET /viviendas
- GET /viviendas/{id}
- POST /viviendas
- PUT /viviendas/{id}
- DELETE /viviendas/{id}

Las operaciones de persistencia utilizan ViviendaJpaDAO.

### 4.3. Gestión de reseñas

Se implementaron:

- GET /viviendas/{id}/resenas
- POST /viviendas/{id}/resenas

El sistema valida autor, calificación y comentario.

Las reseñas se relacionan con las viviendas mediante las entidades JPA.

### 4.4. Seguridad administrativa

Se implementó SeguridadApiFilter para proteger las operaciones administrativas.

La clave se obtiene de la variable de entorno ALQUILAUDB_API_KEY.

El filtro distingue solicitudes sin credencial, credenciales incorrectas y configuración ausente.

### 4.5. Manejo de excepciones

Se incorporaron:

- JsonMalformadoMapper
- TipoContenidoMapper

Estos componentes permiten devolver errores JSON uniformes para solicitudes malformadas y tipos de contenido incompatibles.

### 4.6. Cliente REST inicial

Se desarrolló cliente-rest.html con HTML, CSS y JavaScript.

Inicialmente permitía:

- Consultar viviendas.
- Consultar detalles.
- Consultar reseñas.
- Publicar reseñas.
- Mostrar estados de carga y error.

### 4.7. Ampliación del cliente administrativo

Posteriormente se incorporó un apartado denominado "Administrar viviendas".

La ampliación incluye:

- Formulario para registrar viviendas.
- Formulario para editar viviendas.
- Botones de eliminación.
- Confirmación previa al borrado.
- Campo de contraseña para X-API-Key.
- Mensajes de autorización y errores.
- Actualización del listado después de cada operación.
- Conservación de las funciones de consulta y reseñas.

La ampliación utiliza los endpoints REST existentes y no requiere duplicar la lógica de persistencia.

### 4.8. Control de disponibilidad

El formulario permite establecer la disponibilidad de las viviendas.

Para actualizar una vivienda, el usuario debe seleccionar expresamente su disponibilidad, debido a que las respuestas GET actuales no incluyen ese campo.

Se comprobó mediante MySQL que las operaciones PUT permiten almacenar valores de disponibilidad 0 y 1.

## 5. Pruebas del sprint

Durante el desarrollo se realizaron pruebas manuales con Git Bash, curl, Firefox, el cliente JavaScript y la interfaz JSF.

### 5.1. Operaciones REST

Se verificaron las operaciones de consulta, registro, actualización y eliminación.

También se comprobaron respuestas HTTP 200, 201, 204, 400, 404 y 409.

### 5.2. Seguridad

Se comprobaron:

- HTTP 503 por falta de configuración administrativa.
- HTTP 401 por credencial ausente.
- HTTP 403 por credencial incorrecta.
- HTTP 409 por conflicto de integridad con una clave válida.

### 5.3. Validaciones y errores

Se completaron las pruebas LIM-01 a LIM-10.

Se verificaron límites de longitud, calificaciones, títulos, identificadores inexistentes, JSON malformado, tipos de contenido incompatibles, persistencia de disponibilidad y fallos de comunicación.

### 5.4. Cliente JavaScript ampliado

Se implementó la interfaz administrativa y se confirmó su funcionamiento general.

Para cerrar las evidencias se necesita registrar individualmente la secuencia de creación, edición y eliminación de una vivienda temporal desde el navegador.

También conviene documentar los mensajes de autorización y conflicto mostrados desde el cliente.

Los detalles se encuentran en:

docs/matriz-pruebas-fase-III.md

## 6. Estado del incremento

| Componente | Estado |
|---|---|
| Configuración Jersey y JAX-RS | Completada |
| Endpoints REST de viviendas | Completados |
| Endpoints REST de reseñas | Completados |
| Integración JPA/Hibernate/MySQL | Completada |
| Validaciones y respuestas HTTP | Completadas |
| Seguridad administrativa | Completada |
| Manejadores de errores JSON | Completados |
| Cliente JavaScript de consultas | Completado |
| Cliente JavaScript administrativo | Implementado |
| Pruebas REST positivas y negativas | Completadas |
| Pruebas LIM-01 a LIM-10 | Completadas |
| Documentación técnica | Actualizada |
| Matriz de pruebas | Actualizada |
| Evidencias individuales del CRUD JavaScript | Pendientes de incorporar |
| Informe PDF definitivo | Pendiente de actualización |

## 7. Pendientes para el cierre

| Tarea | Prioridad | Estado |
|---|---|---|
| Registrar una vivienda temporal desde JavaScript | Alta | Pendiente de evidencia individual |
| Editar la vivienda temporal desde JavaScript | Alta | Pendiente de evidencia individual |
| Eliminar la vivienda temporal desde JavaScript | Alta | Pendiente de evidencia individual |
| Comprobar error 409 desde el cliente | Alta | Pendiente de evidencia individual |
| Verificar errores 401 y 403 desde el cliente | Media | Pendiente de evidencia individual |
| Actualizar el PDF con capturas del CRUD administrativo | Alta | Pendiente |
| Revisar README y documentación final | Media | Pendiente |
| Guardar código y documentos actualizados en GitHub | Alta | Pendiente |

## 8. Limitaciones y mejoras futuras

La seguridad actual utiliza una clave compartida para operaciones administrativas.

Esta solución es adecuada como control básico para el entorno académico, pero no sustituye una autenticación completa con usuarios y roles.

El cliente no guarda permanentemente la clave administrativa, aunque esta debe transmitirse únicamente mediante HTTPS en un despliegue público.

El registro público de reseñas requeriría protección adicional contra abusos si la aplicación se expusiera a Internet.

Como mejora futura se propone añadir el campo `disponible` a las respuestas GET para cargar automáticamente su valor durante la edición.

También sería conveniente ampliar las pruebas automatizadas de los servicios.

## 9. Conclusión

La Fase III permitió extender AlquilaUDB mediante una API REST integrada con JPA, Hibernate y MySQL.

Se desarrollaron servicios para consultar, registrar, actualizar y eliminar viviendas, además de consultar y publicar reseñas.

Se incorporaron validaciones, manejo uniforme de errores JSON y autorización básica para operaciones administrativas.

El cliente JavaScript se amplió para utilizar las operaciones principales de la API desde el navegador, manteniendo las funciones públicas existentes.

El trabajo pendiente se concentra en documentar las pruebas individuales de la interfaz administrativa, actualizar las evidencias visuales y realizar el respaldo final del incremento.

---

**Repositorio:** https://github.com/VictorZ23/AlquilaUDB