# AlquilaUDB — Product Backlog de la Fase II

**Proyecto:** AlquilaUDB  
**Asignatura:** Desarrollo de Aplicaciones con Web Frameworks (DWF901)  
**Institución:** Universidad Don Bosco  
**Incremento:** Fase II — Persistencia Empresarial e Integración JSF (Sprint II)  
**Tecnologías:** Java 11, JSF 2.3, CDI, JPA, Hibernate, AJAX y MySQL

## 1. Objetivo del producto

AlquilaUDB es una aplicación web orientada a facilitar la consulta y administración de opciones de alojamiento para estudiantes universitarios.

El sistema permite consultar viviendas disponibles, conocer sus características y acceder a opiniones que pueden ayudar a valorar distintas opciones de alojamiento.

Su desarrollo se organiza en incrementos que incorporan progresivamente nuevas funcionalidades y tecnologías.

## 2. Objetivo del Sprint II

Evolucionar el módulo de viviendas desarrollado durante la Fase I, incorporando persistencia mediante JPA e Hibernate, interfaces JSF, Managed Beans, validaciones, operaciones AJAX y relaciones entre entidades.

El incremento debe conservar el funcionamiento de la implementación anterior con JSP, Servlets y JDBC, mientras se introduce una nueva arquitectura basada en componentes.

## 3. Product Backlog

| ID | Historia de usuario | Prioridad | Estado |
|---|---|---|---|
| HU-01 | Como usuario, quiero consultar las viviendas registradas para conocer las opciones de alojamiento disponibles. | Alta | Completada |
| HU-02 | Como administrador del módulo, quiero registrar viviendas para incorporar nuevas ofertas de alojamiento. | Alta | Completada |
| HU-03 | Como administrador del módulo, quiero modificar viviendas existentes para mantener actualizada su información. | Alta | Completada |
| HU-04 | Como administrador del módulo, quiero eliminar viviendas que ya no deban aparecer en el sistema. | Alta | Completada |
| HU-05 | Como usuario, quiero consultar toda la información de una vivienda para conocer sus características antes de considerarla. | Alta | Completada |
| HU-06 | Como usuario, quiero buscar viviendas por título o zona para encontrar opciones relevantes con mayor facilidad. | Alta | Completada |
| HU-07 | Como usuario, quiero filtrar viviendas por tipo y disponibilidad para reducir los resultados de búsqueda. | Media | Completada |
| HU-08 | Como usuario, quiero consultar las reseñas de una vivienda para conocer opiniones sobre ella. | Alta | Completada |
| HU-09 | Como usuario, quiero publicar una reseña con calificación para compartir mi experiencia sobre una vivienda. | Alta | Completada |
| HU-10 | Como usuario, quiero recibir mensajes de validación durante el ingreso de datos para corregir errores antes de enviar un formulario. | Alta | Completada |
| HU-11 | Como responsable de los datos, quiero impedir la eliminación de viviendas con reseñas para conservar la integridad de la información. | Alta | Completada |
| HU-12 | Como desarrollador, quiero mantener funcionando el módulo JDBC anterior mientras incorporo JPA para comprobar la compatibilidad entre ambas implementaciones. | Alta | Completada |
| HU-13 | Como responsable del proyecto, quiero disponer de documentación de instalación y del esquema SQL para facilitar la reproducción del entorno. | Alta | En revisión |
| HU-14 | Como responsable del proyecto, quiero registrar pruebas y evidencias para demostrar el cumplimiento de los requisitos de la Fase II. | Alta | En proceso |
| HU-15 | Como responsable del proyecto, quiero realizar una demostración del incremento funcional para presentar sus resultados y recibir observaciones. | Alta | Pendiente |

Los estados corresponden al avance comprobado durante el desarrollo. Las historias sobre documentación y defensa permanecerán abiertas hasta verificar sus entregables.

## 4. Criterios de aceptación

### HU-01 — Consultar viviendas

- La información se obtiene desde MySQL mediante JPA e Hibernate.
- Se muestran los registros existentes.
- El listado incluye título, tipo, zona, precio, habitaciones y disponibilidad.

### HU-02 — Registrar viviendas

- El formulario recibe los datos necesarios.
- Los campos obligatorios y sus rangos son validados.
- La nueva vivienda se guarda mediante una transacción JPA.
- El registro puede consultarse desde las interfaces JSF y JDBC.

### HU-03 — Editar viviendas

- El sistema carga los datos de la vivienda seleccionada.
- Los cambios se guardan mediante JPA.
- La información actualizada persiste en MySQL.

### HU-04 — Eliminar viviendas

- El usuario debe confirmar la operación.
- La eliminación utiliza JPA.
- El registro desaparece del listado cuando la operación está permitida.
- Si existen restricciones de integridad, se informa del problema.

### HU-05 — Consultar detalle

- El usuario puede abrir el detalle desde el listado.
- Se muestran dirección, descripción y demás características.
- La vista permite navegar hacia edición y reseñas.

### HU-06 y HU-07 — Búsqueda y filtros

- Es posible buscar por título o zona.
- Es posible filtrar por tipo de vivienda.
- Es posible consultar solamente viviendas disponibles.
- Los resultados se actualizan mediante AJAX.
- Los filtros pueden restablecerse.

### HU-08 y HU-09 — Gestión de reseñas

- Cada reseña pertenece a una vivienda existente.
- Se almacena autor, calificación, comentario y fecha.
- La calificación debe encontrarse entre 1 y 5.
- Las reseñas se muestran en la página de su vivienda.
- La relación se implementa mediante JPA.

### HU-10 — Validaciones

- Se comprueban los campos obligatorios.
- Se validan longitudes y valores numéricos.
- Se utiliza al menos un validador JSF personalizado.
- Las validaciones AJAX permiten mostrar errores sin recargar completamente la página.

### HU-11 — Integridad de datos

- Una vivienda con reseñas asociadas no puede eliminarse.
- La aplicación muestra un mensaje explicativo.
- Los datos relacionados permanecen intactos.

### HU-12 — Compatibilidad

- El módulo original con JSP y JDBC continúa funcionando.
- El nuevo módulo JSF utiliza la misma base de datos.
- Los cambios realizados desde una implementación son visibles desde la otra.

## 5. Organización del Sprint II

El trabajo del incremento se desarrolló en las siguientes etapas:

| Etapa | Actividades principales | Resultado |
|---|---|---|
| 1. Configuración | Incorporación de JSF, CDI y Weld en Tomcat. | Entorno funcional |
| 2. Persistencia | Configuración de Hibernate, JPA y entidades. | Lectura de viviendas desde MySQL |
| 3. Operaciones CRUD | Registro, consulta, actualización y eliminación mediante JPA. | CRUD funcional |
| 4. Relaciones | Creación de la entidad `Resena` y la relación con `Vivienda`. | Reseñas vinculadas |
| 5. Validaciones | Incorporación de validaciones estándar, personalizadas y AJAX. | Formularios validados |
| 6. Consultas | Implementación de búsqueda y filtros con JPQL. | Listado filtrable |
| 7. Interfaz | Integración de detalle, edición y reseñas. | Navegación funcional |
| 8. Documentación | Actualización del README, script SQL y matriz de pruebas. | Entregables en preparación |

## 6. Correcciones del incremento

Durante el desarrollo se identificaron y resolvieron incidencias relacionadas con:

- Inicialización de JSF y CDI en Tomcat.
- Bloqueos de archivos durante la limpieza de Maven.
- Disponibilidad del servicio MySQL.
- Identificadores duplicados en componentes JSF.
- Sincronización entre las propiedades del Managed Bean y los métodos del DAO.

Estas incidencias se registran también en `docs/matriz-pruebas-fase-II.md`.

## 7. Elementos pendientes y mejoras futuras

Además de los entregables documentales, se identifican posibilidades de mejora para incrementos posteriores:

| ID | Mejora propuesta | Estado |
|---|---|---|
| MEJ-01 | Incorporar gestión de usuarios y permisos para restringir las operaciones administrativas. | Pendiente |
| MEJ-02 | Mejorar el tratamiento monetario usando `BigDecimal` para los precios. | Pendiente |
| MEJ-03 | Separar las credenciales de base de datos de los archivos de configuración versionados. | Pendiente |
| MEJ-04 | Automatizar pruebas de persistencia y reglas de negocio. | Pendiente |
| MEJ-05 | Adaptar la interfaz para una mejor experiencia en dispositivos móviles. | Pendiente |

Estas mejoras no se consideran funcionalidades
