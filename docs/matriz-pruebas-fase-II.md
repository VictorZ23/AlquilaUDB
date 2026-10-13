# AlquilaUDB — Matriz de pruebas de la Fase II

**Proyecto:** AlquilaUDB  
**Asignatura:** Desarrollo de Aplicaciones con Web Frameworks (DWF901)  
**Fase:** II — Persistencia Empresarial e Integración JSF  
**Entorno:** Java 11, Apache Tomcat 9, MySQL, JSF 2.3, JPA e Hibernate 5.6  
**Tipo de pruebas:** Funcionales manuales y de integración

## 1. Objetivo

Verificar el funcionamiento de las operaciones de gestión de viviendas y reseñas desarrolladas durante la Fase II, comprobando la persistencia real de los datos, la integración de JSF y Hibernate, las validaciones AJAX y la integridad de las relaciones entre entidades.

## 2. Casos de prueba

| ID | Funcionalidad | Procedimiento | Resultado esperado | Estado |
|---|---|---|---|---|
| P01 | Consultar viviendas | Abrir `viviendas-jpa.xhtml`. | Mostrar los registros existentes en MySQL mediante JPA. | Aprobada |
| P02 | Registrar vivienda | Completar el formulario con datos válidos y guardar. | La vivienda aparece en los listados JSF y JDBC. | Aprobada |
| P03 | Editar vivienda | Modificar el título de una vivienda de prueba. | El cambio persiste en MySQL y aparece en ambos listados. | Aprobada |
| P04 | Eliminar vivienda | Eliminar una vivienda de prueba sin reseñas, confirmando la operación. | La vivienda desaparece de los listados. | Aprobada |
| P05 | Validaciones AJAX de viviendas | Introducir título corto, precio cero y habitaciones cero. | Mostrar mensajes de error sin recargar toda la página. | Aprobada |
| P06 | Registrar reseña | Seleccionar una vivienda, introducir autor, calificación y comentario válidos. | La reseña queda asociada a la vivienda. | Aprobada |
| P07 | Consultar reseñas | Abrir las reseñas de una vivienda existente. | Mostrar autor, calificación, comentario y fecha. | Aprobada |
| P08 | Validador personalizado | Introducir un comentario demasiado corto o compuesto por espacios. | Mostrar un mensaje de validación y rechazar el valor. | Aprobada |
| P09 | Búsqueda y filtrado AJAX | Buscar por zona, filtrar por tipo y disponibilidad. | Mostrar solamente las viviendas que cumplen los criterios, sin recargar toda la página. | Aprobada |
| P10 | Integridad referencial | Intentar eliminar una vivienda con reseñas asociadas. | Impedir la eliminación y mostrar un mensaje explicativo. | Aprobada |
| P11 | Detalle de vivienda | Abrir «Ver detalle» desde el listado. | Mostrar todos los atributos de la vivienda seleccionada. | Aprobada |
| P12 | Navegación JSF | Acceder a edición y reseñas desde el detalle. | Abrir las páginas correspondientes a la misma vivienda. | Aprobada |

Los estados reflejan las comprobaciones funcionales realizadas durante el desarrollo.

## 3. Comprobación de persistencia

Las viviendas registradas o modificadas mediante JSF y Hibernate también pudieron consultarse desde la implementación original basada en JSP y JDBC.

Esto permitió comprobar que ambas implementaciones utilizan la misma base de datos MySQL y que las operaciones JPA producen cambios persistentes.

## 4. Integridad y reglas de negocio

Se comprobó que la aplicación impide eliminar una vivienda con reseñas asociadas.

Al intentar eliminarla, el sistema presentó el mensaje:

**«No se puede eliminar esta vivienda porque tiene reseñas asociadas».**

La vivienda permaneció registrada, evitando la pérdida de información relacionada.

La relación entre `Vivienda` y `Resena` se implementó mediante las anotaciones `@OneToMany` y `@ManyToOne`, junto con una clave foránea en MySQL.

## 5. Validaciones y AJAX

Se comprobaron validaciones de longitud, campos obligatorios, precios, habitaciones y calificaciones.

También se implementó un validador personalizado para los comentarios de reseñas, que comprueba su longitud después de eliminar los espacios iniciales y finales.

Las peticiones AJAX permiten mostrar mensajes de validación y actualizar los resultados de búsqueda sin recargar completamente las páginas.

## 6. Incidencias y correcciones durante el desarrollo

| Incidencia | Causa identificada | Corrección | Estado |
|---|---|---|---|
| JSF no iniciaba correctamente | Configuración incompleta de CDI en Tomcat. | Integración de Weld y configuración de `beans.xml`. | Resuelta |
| Fallo de limpieza Maven | Archivos JAR bloqueados por Tomcat. | Detener el servidor antes de ejecutar `Clean and Build`. | Resuelta |
| No aparecían las viviendas | Servicio MySQL detenido. | Reiniciar MySQL desde XAMPP. | Resuelta |
| Error HTTP 500 por identificadores duplicados | Componentes JSF repetidos en el formulario. | Corregir los identificadores y reemplazar el XHTML. | Resuelta |
| Error al acceder a `textoBusqueda` | Bean y DAO no estaban actualizados conjuntamente. | Incorporar las propiedades del Managed Bean y el método de búsqueda JPQL. | Resuelta |

## 7. Evidencias pendientes de adjuntar

Las pruebas anteriores fueron verificadas funcionalmente durante el desarrollo. Para completar la entrega documental, se deben incorporar capturas representativas de los formularios, las operaciones de persistencia, las validaciones AJAX, las reseñas, la búsqueda y el control de integridad.

## 8. Conclusión

Las pruebas realizadas muestran que AlquilaUDB integra satisfactoriamente JSF, CDI, JPA, Hibernate y MySQL para la gestión de viviendas y reseñas.

Las funcionalidades principales de la Fase II fueron comprobadas mediante pruebas manuales, incluyendo operaciones CRUD, relaciones entre entidades, validaciones, consultas JPQL y manejo de errores.
