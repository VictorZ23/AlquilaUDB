# AlquilaUDB — Matriz de pruebas de la Fase III

**Proyecto:** AlquilaUDB
**Asignatura:** Desarrollo de Aplicaciones con Web Frameworks (DWF901)
**Estudiante:** Carlos Víctor Zaun Gómez
**Fase:** III — Servicios REST e Integración de Clientes
**Fecha de revisión:** 19 de noviembre de 2026
**Entorno:** Java 11, Jersey 2.48, Tomcat 9, JPA, Hibernate, MySQL y JavaScript

## 1. Objetivo

Verificar el funcionamiento de la API REST de AlquilaUDB, sus operaciones CRUD, las validaciones, la seguridad administrativa, la persistencia en MySQL y la integración con un cliente JavaScript independiente.

Las pruebas incluyen escenarios positivos, negativos, límites, errores de comunicación y restricciones de integridad.

Se distinguen las pruebas comprobadas mediante curl de las comprobaciones del cliente realizadas desde el navegador.

## 2. Pruebas funcionales de la API REST

| ID | Operación o escenario | Resultado esperado | Estado |
|---|---|---|---|
| REST-01 | GET /api/estado | HTTP 200 y JSON del servicio | Aprobada |
| REST-02 | GET /api/viviendas | HTTP 200 y colección JSON | Aprobada |
| REST-03 | GET de vivienda existente | HTTP 200 y datos del registro | Aprobada |
| REST-04 | GET de vivienda inexistente | HTTP 404 | Aprobada |
| REST-05 | GET con identificador inválido | HTTP 400 | Aprobada |
| REST-06 | GET de reseñas de vivienda existente | HTTP 200 y colección JSON | Aprobada |
| REST-07 | GET de reseñas de vivienda inexistente | HTTP 404 | Aprobada |
| REST-08 | GET de reseñas con ID inválido | HTTP 400 | Aprobada |
| REST-09 | POST de reseña válida | HTTP 201 | Aprobada |
| REST-10 | POST de reseña con calificación inválida | HTTP 400 | Aprobada |
| REST-11 | POST de vivienda válida | HTTP 201 y cabecera Location | Aprobada |
| REST-12 | POST de vivienda con precio negativo | HTTP 400 | Aprobada |
| REST-13 | PUT de vivienda existente | HTTP 200 | Aprobada |
| REST-14 | PUT con precio negativo | HTTP 400 | Aprobada |
| REST-15 | DELETE de vivienda con reseñas | HTTP 409 | Aprobada |
| REST-16 | GET de reseñas de vivienda sin opiniones | HTTP 200 y [] | Aprobada |
| REST-17 | DELETE de vivienda temporal sin reseñas | HTTP 204 | Aprobada |
| REST-18 | GET después de eliminar una vivienda | HTTP 404 | Aprobada |

### Observación

Las primeras pruebas de POST, PUT y DELETE se realizaron antes de activar la protección administrativa.

Posteriormente se comprobó que estas operaciones requieren una clave válida.

La vivienda temporal con ID 19 fue creada, modificada y eliminada durante las pruebas.

## 3. Pruebas de seguridad

| ID | Escenario | HTTP esperado | HTTP obtenido | Estado |
|---|---|---|---|---|
| SEG-01 | Consulta GET pública | 200 | 200 | Aprobada |
| SEG-02 | Solicitud administrativa sin clave configurada en Tomcat | 503 | 503 | Aprobada |
| SEG-03 | Solicitud protegida sin X-API-Key | 401 | 401 | Aprobada |
| SEG-04 | Solicitud con clave incorrecta | 403 | 403 | Aprobada |
| SEG-05 | Clave válida, pero DELETE de vivienda con reseñas | 409 | 409 | Aprobada |

### Interpretación

El código 401 demuestra que el servidor exige credenciales.

El código 403 confirma que se rechazan las claves incorrectas.

El código 409 demuestra que una solicitud autorizada sigue sometida a las reglas de integridad de la aplicación.

La clave administrativa permanece fuera del código fuente.

## 4. Pruebas del cliente JavaScript

El archivo `cliente-rest.html` utiliza `fetch()` para consumir la API REST.

La versión ampliada incorpora una sección administrativa para agregar, editar y eliminar viviendas.

### 4.1. Consultas y reseñas

| ID | Funcionalidad | Resultado esperado | Estado |
|---|---|---|---|
| CLI-01 | Abrir cliente-rest.html | Mostrar viviendas cargadas desde REST | Aprobada |
| CLI-02 | Consultar detalle | Mostrar información de vivienda | Aprobada |
| CLI-03 | Consultar reseñas | Mostrar reseñas asociadas | Aprobada |
| CLI-04 | Registrar reseña | Guardar opinión mediante POST | Aprobada |
| CLI-05 | Actualizar reseñas después del registro | Mostrar la nueva opinión | Aprobada |
| CLI-06 | Comprobar información compartida con JSF | Mostrar información persistente en MySQL | Aprobada |
| CLI-07 | Detener y reiniciar Tomcat | Detectar fallo y recuperar consultas | Aprobada con observación |

En CLI-07 se observó que el cliente anterior detectaba la interrupción y posteriormente recuperaba la consulta.

La versión ampliada incluye un mensaje más comprensible. Su presentación exacta puede verificarse durante la revisión final.

### 4.2. Administración de viviendas

| ID | Funcionalidad | Resultado esperado | Estado |
|---|---|---|---|
| CLI-08 | Abrir "Administrar viviendas" | Mostrar formulario y botones administrativos | Aprobada |
| CLI-09 | Registrar vivienda desde formulario | HTTP 201 y nuevo registro visible | Pendiente de evidencia individual |
| CLI-10 | Editar vivienda desde formulario | HTTP 200 y cambios persistentes | Pendiente de evidencia individual |
| CLI-11 | Eliminar vivienda temporal desde botón | HTTP 204 y registro eliminado | Pendiente de evidencia individual |
| CLI-12 | Eliminar vivienda con reseñas desde botón | HTTP 409 y registro conservado | Pendiente de evidencia individual |
| CLI-13 | Operación administrativa sin clave | HTTP 401 y mensaje de error | Pendiente de evidencia individual |
| CLI-14 | Operación administrativa con clave incorrecta | HTTP 403 y mensaje de error | Pendiente de evidencia individual |
| CLI-15 | Consultar y publicar reseñas con cliente ampliado | Conservar funciones anteriores | Pendiente de evidencia individual |

### 4.3. Alcance de las comprobaciones

El funcionamiento general de la nueva interfaz fue confirmado después de actualizar y desplegar `cliente-rest.html`.

Sin embargo, todavía no se han incorporado a esta matriz resultados individuales de cada operación administrativa ejecutada desde sus botones.

Las operaciones REST equivalentes sí fueron comprobadas previamente mediante curl.

Antes de cerrar la entrega se recomienda realizar una secuencia completa con una vivienda temporal:

1. Registrar una vivienda desde JavaScript.
2. Confirmar que aparece en el listado.
3. Modificar el título y el precio.
4. Confirmar que los nuevos valores aparecen.
5. Eliminar la vivienda temporal.
6. Confirmar que desaparece del listado.

También conviene comprobar desde la interfaz que no se puede eliminar una vivienda con reseñas.

## 5. Pruebas de límites y validaciones

| ID | Condición | Resultado esperado | Estado |
|---|---|---|---|
| LIM-01 | Comentario de 5 caracteres | HTTP 400 | Aprobada |
| LIM-02 | Comentario de 1001 caracteres | HTTP 400 | Aprobada |
| LIM-03 | Calificación decimal 3.5 | HTTP 400 | Aprobada |
| LIM-04 | Título de dos caracteres | HTTP 400 | Aprobada |
| LIM-05 | PUT sobre vivienda inexistente | HTTP 404 | Aprobada |
| LIM-06 | DELETE sobre vivienda inexistente | HTTP 404 | Aprobada |
| LIM-07 | JSON malformado | HTTP 400 y JSON de error | Aprobada |
| LIM-08 | Content-Type text/plain en endpoint JSON | HTTP 415 y JSON de error | Aprobada |
| LIM-09 | Cambios de disponibilidad mediante PUT | Persistencia de 0 y 1 en MySQL | Aprobada |
| LIM-10 | Interrupción del servidor y recuperación | Mensaje de error y recuperación | Aprobada con observación |

### 5.1. Resultados LIM-01 a LIM-03

Las tres solicitudes devolvieron HTTP 400.

La API rechazó comentarios demasiado cortos, comentarios demasiado largos y calificaciones no enteras.

### 5.2. Resultados LIM-04 a LIM-06

Se verificaron:

- HTTP 400 al intentar registrar un título de dos caracteres.
- HTTP 404 al actualizar un registro inexistente.
- HTTP 404 al eliminar un registro inexistente.

### 5.3. Resultados LIM-07 y LIM-08

Inicialmente, JSON malformado devolvía texto plano y el contenido incompatible devolvía HTML.

Se incorporaron manejadores JAX-RS y se repitieron ambas pruebas.

Resultados finales:

- HTTP 400 con Content-Type application/json.
- HTTP 415 con Content-Type application/json.

### 5.4. Resultado LIM-09

Se creó una vivienda temporal para comprobar el campo `disponible`.

Se modificó mediante PUT y se verificó en MySQL que podía almacenarse con los valores 0 y 1.

El estudiante confirmó ambas comprobaciones.

### 5.5. Resultado LIM-10

Con Tomcat detenido, el cliente detectó el error de comunicación.

Después de reiniciar Tomcat, las consultas funcionaron nuevamente.

La prueba comprobó el manejo funcional del fallo. Se identificó como mejora la presentación de mensajes más comprensibles.

## 6. Códigos HTTP comprobados

| Código | Significado | Aplicación |
|---|---|---|
| 200 | OK | Consultas y actualizaciones |
| 201 | Created | Registro de viviendas y reseñas |
| 204 | No Content | Eliminación correcta |
| 400 | Bad Request | Datos inválidos o JSON malformado |
| 401 | Unauthorized | Credencial ausente |
| 403 | Forbidden | Credencial incorrecta |
| 404 | Not Found | Recurso inexistente |
| 409 | Conflict | Restricción por reseñas asociadas |
| 415 | Unsupported Media Type | Contenido incompatible |
| 503 | Service Unavailable | Seguridad administrativa sin configurar |

## 7. Incidencias del desarrollo

| ID | Incidencia | Solución | Estado |
|---|---|---|---|
| INC-01 | Clases REST ubicadas inicialmente en una ruta incorrecta | Traslado al paquete com.alquilaudb.api | Resuelta |
| INC-02 | Problemas de conexión con Tomcat | Reinicio y comprobación del servidor | Resuelta |
| INC-03 | POST de vivienda devolvió inicialmente HTTP 404 | Revisión del despliegue y de los recursos Jersey | Resuelta |
| INC-04 | Clave administrativa demasiado corta | Corrección de variable de entorno | Resuelta |
| INC-05 | Tomcat no recibía la clave actualizada | Reinicio completo del entorno | Resuelta |
| INC-06 | Errores 400/415 sin formato JSON uniforme | Incorporación de ExceptionMapper | Resuelta |
| INC-07 | Cliente REST sin controles administrativos | Ampliación de cliente-rest.html | Resuelta |
| INC-08 | Navegador mostraba una versión anterior del cliente | Recarga y comprobación del despliegue | Resuelta |

## 8. Evidencias para el informe final

Se recomienda incluir capturas auténticas de:

- El paquete com.alquilaudb.api en NetBeans.
- ApiConfig.java con recursos, filtro y manejadores.
- GET /api/viviendas devolviendo JSON.
- Cliente JavaScript con el listado de viviendas.
- Detalle y reseñas de una vivienda.
- Formulario de administración de viviendas.
- Registro de una vivienda temporal desde JavaScript.
- Edición y eliminación desde el navegador.
- Respuestas HTTP 401, 403 y 409.
- Documentación y commits del repositorio GitHub.

No deben incluirse claves administrativas ni otros secretos.

## 9. Resultado general

Las operaciones REST, validaciones, controles de acceso, manejo de errores JSON y persistencia fueron comprobados durante el Sprint III.

El cliente JavaScript se amplió para integrar las operaciones administrativas y se confirmó el funcionamiento general de la interfaz.

Para completar la evidencia del cliente ampliado deben registrarse los resultados individuales CLI-09 a CLI-15.

## 10. Conclusión

La matriz demuestra el funcionamiento de los endpoints REST, las validaciones de entradas, las reglas de integridad y el control de acceso básico.

La ampliación del cliente JavaScript permite utilizar el CRUD desde una interfaz web y mejora la demostración de integración entre frontend, servicios REST y base de datos.

La documentación diferencia los resultados efectivamente observados de los casos que todavía necesitan evidencia individual.

---

**Repositorio:** https://github.com/VictorZ23/AlquilaUDB