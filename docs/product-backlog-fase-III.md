\# AlquilaUDB — Product Backlog de la Fase III



\*\*Asignatura:\*\* Desarrollo de Aplicaciones con Web Frameworks (DWF901)  

\*\*Sprint:\*\* III — Servicios REST e Integración de Clientes  

\*\*Proyecto:\*\* AlquilaUDB  

\*\*Fecha de revisión:\*\* 19 de noviembre de 2026



\## 1. Objetivo del sprint



Extender AlquilaUDB mediante una API REST que permita consultar y gestionar viviendas y reseñas utilizando HTTP y JSON, reutilizando la persistencia JPA/Hibernate desarrollada durante la Fase II.



El incremento incorpora un cliente consumidor independiente, validaciones, respuestas HTTP apropiadas y un mecanismo básico de autorización para las operaciones administrativas.



\## 2. Historias de usuario



| ID | Historia de usuario | Prioridad | Estado |

|---|---|---|---|

| HU-III-01 | Como desarrollador, quiero configurar una API REST con Jersey y JAX-RS para exponer los servicios de AlquilaUDB. | Alta | Completada |

| HU-III-02 | Como consumidor, quiero consultar el estado de la API para comprobar que está funcionando. | Media | Completada |

| HU-III-03 | Como visitante, quiero consultar las viviendas disponibles en el sistema mediante JSON. | Alta | Completada |

| HU-III-04 | Como visitante, quiero consultar los datos de una vivienda específica. | Alta | Completada |

| HU-III-05 | Como administrador, quiero registrar viviendas mediante POST para incorporar nuevos inmuebles. | Alta | Completada |

| HU-III-06 | Como administrador, quiero actualizar viviendas mediante PUT para mantener sus datos. | Alta | Completada |

| HU-III-07 | Como administrador, quiero eliminar viviendas mediante DELETE cuando las reglas de integridad lo permitan. | Alta | Completada |

| HU-III-08 | Como visitante, quiero consultar las reseñas de cada vivienda. | Alta | Completada |

| HU-III-09 | Como visitante, quiero registrar reseñas mediante la API REST. | Alta | Completada |

| HU-III-10 | Como usuario, quiero utilizar un cliente HTML y JavaScript para consumir los servicios REST. | Alta | Completada |

| HU-III-11 | Como consumidor de la API, quiero recibir códigos HTTP y mensajes JSON apropiados cuando una solicitud sea incorrecta. | Alta | Completada |

| HU-III-12 | Como administrador, quiero proteger las operaciones de escritura mediante una clave de acceso. | Alta | Completada |

| HU-III-13 | Como desarrollador, quiero documentar los endpoints, parámetros, validaciones y respuestas HTTP. | Alta | Completada |

| HU-III-14 | Como evaluador, quiero disponer de una matriz de pruebas que demuestre los escenarios positivos, negativos y de seguridad. | Alta | Completada |

| HU-III-15 | Como usuario del cliente JavaScript, quiero recibir mensajes claros cuando el servidor no responda. | Media | Completada con observación de usabilidad |

| HU-III-16 | Como desarrollador, quiero comprobar que la actualización de disponibilidad mediante REST se almacena correctamente. | Media | Completada |

| HU-III-17 | Como evaluador, quiero disponer de evidencias visuales y documentación final de la Fase III. | Alta | Pendiente |



\## 3. Criterios de aceptación



\### Servicios REST



Se considera aceptada la funcionalidad cuando:



\- Los servicios se encuentran disponibles bajo `/AlquilaUDB/api`.

\- Las solicitudes utilizan los métodos HTTP correspondientes.

\- Las respuestas de consulta y registro se representan mediante JSON.

\- Las operaciones de persistencia utilizan `ViviendaJpaDAO`.

\- La API respeta las restricciones de integridad entre viviendas y reseñas.



\### Validaciones y errores



\- Los datos incorrectos son rechazados con HTTP 400.

\- Los recursos inexistentes generan HTTP 404.

\- La eliminación de viviendas con reseñas genera HTTP 409.

\- El contenido JSON malformado genera HTTP 400 con respuesta JSON.

\- Los tipos de contenido incompatibles generan HTTP 415 con respuesta JSON.



\### Seguridad



\- Las consultas públicas no necesitan credenciales.

\- Registrar, actualizar y eliminar viviendas requiere `X-API-Key`.

\- La ausencia de credencial genera HTTP 401 cuando el servidor está configurado.

\- Una credencial inválida genera HTTP 403.

\- La ausencia de configuración administrativa genera HTTP 503.

\- Una credencial correcta no permite incumplir las reglas de negocio.

\- La clave administrativa no se almacena dentro del repositorio.



\### Cliente consumidor



\- Consulta viviendas utilizando `fetch()`.

\- Presenta detalles y reseñas.

\- Permite registrar nuevas reseñas.

\- Actualiza la información después del registro.

\- Dispone de mensajes de carga, éxito y error.



\## 4. Incremento desarrollado



\### Configuración REST



Se incorporaron las dependencias necesarias de Jersey y Jackson al proyecto Maven.



La clase `ApiConfig` configura los recursos REST, el filtro de seguridad y los manejadores de excepciones.



\### Gestión de viviendas



Se implementaron las operaciones GET, POST, PUT y DELETE.



Los servicios reutilizan la persistencia JPA/Hibernate de la Fase II, evitando duplicar la lógica de acceso a datos.



\### Gestión de reseñas



Se implementaron la consulta y el registro de reseñas asociadas a viviendas.



La API valida autor, calificación y comentario antes del registro.



\### Seguridad



Se implementó `SeguridadApiFilter` para controlar las solicitudes administrativas mediante una clave configurada como variable de entorno.



\### Manejo de excepciones



Se añadieron `JsonMalformadoMapper` y `TipoContenidoMapper` para devolver errores JSON uniformes ante solicitudes malformadas o formatos incompatibles.



\### Cliente REST



Se desarrolló `cliente-rest.html` con JavaScript y `fetch()` como consumidor independiente de los servicios.



\## 5. Pruebas del sprint



Se comprobaron operaciones satisfactorias de consulta, registro, actualización y eliminación, además de respuestas ante datos inválidos, recursos inexistentes y conflictos de integridad.



También se verificaron los códigos HTTP 401, 403 y 503 correspondientes a situaciones de autorización y configuración administrativa.



Las pruebas adicionales LIM-01 a LIM-10 se ejecutaron satisfactoriamente. En LIM-09 se verificó directamente en MySQL la persistencia de los cambios de disponibilidad. En LIM-10 se observó que el cliente detecta los fallos de conexión y recupera las consultas cuando se restablece el servidor.



La información detallada se encuentra en `docs/matriz-pruebas-fase-III.md`.



\## 6. Pendientes para el cierre



| Tarea | Prioridad | Estado |

|---|---|---|

| Confirmar persistencia del campo `disponible` mediante PUT | Media | Completada |

| Verificar el manejo de fallos de conexión en el cliente JavaScript | Media | Completada |

| Mejorar el mensaje de error de conexión para hacerlo más comprensible | Baja | Mejora opcional |

| Revisar la documentación y el README del repositorio | Alta | Pendiente |

| Reunir capturas de las funcionalidades y respuestas HTTP | Alta | Pendiente |

| Preparar el informe de evidencias de la Fase III | Alta | Pendiente |

| Respaldar el Product Backlog en GitHub | Alta | Pendiente |



\## 7. Consideraciones técnicas



La autorización implementada se limita a los recursos REST protegidos. No constituye un sistema general de usuarios y roles para toda la aplicación.



El registro público de reseñas conserva sus validaciones, pero requeriría medidas adicionales contra abusos si la aplicación se publicara en Internet.



La API se ha desarrollado y comprobado en un entorno local de Tomcat y MySQL.



El cliente JavaScript puede detectar fallos de comunicación; sin embargo, el mensaje técnico mostrado en esas circunstancias puede mejorarse para ofrecer una experiencia más comprensible.



\## 8. Conclusión



La Fase III incorpora comunicación REST, serialización JSON, un cliente independiente, operaciones CRUD, validaciones y controles básicos de autorización.



El sprint conserva la integración con las funcionalidades de las fases anteriores y proporciona un incremento funcional que puede comprobarse tanto desde herramientas HTTP como desde la interfaz web.



Las pruebas funcionales, negativas, de límites y de seguridad se han completado. El trabajo restante se concentra en la revisión documental, la preparación de evidencias y el cierre de la entrega.



\---



\*\*Repositorio:\*\* https://github.com/VictorZ23/AlquilaUDB

