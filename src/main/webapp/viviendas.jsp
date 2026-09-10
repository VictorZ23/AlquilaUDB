<%-- 
    Document   : viviendas
    Created on : 8 sep. 2026, 09:23:35
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Viviendas disponibles</title>
    </head>

    <body>

        <h1>Viviendas disponibles</h1>

        <!-- MENSAJES DE RETROALIMENTACIÓN -->

        <c:if test="${param.mensaje == 'creada'}">
            <p>
                <strong>La vivienda fue registrada correctamente.</strong>
            </p>
        </c:if>

        <c:if test="${param.mensaje == 'actualizada'}">
            <p>
                <strong>La vivienda fue actualizada correctamente.</strong>
            </p>
        </c:if>

        <c:if test="${param.mensaje == 'eliminada'}">
            <p>
                <strong>La vivienda fue eliminada correctamente.</strong>
            </p>
        </c:if>

        <c:if test="${param.mensaje == 'error'}">
            <p>
                <strong>Ocurrió un error al realizar la operación.</strong>
            </p>
        </c:if>

        <p>
            <a href="${pageContext.request.contextPath}/formulario-vivienda.jsp">
                Agregar vivienda
            </a>
        </p>

        <c:choose>

            <c:when test="${empty viviendas}">

                <p>No hay viviendas registradas.</p>

            </c:when>

            <c:otherwise>

                <table border="1">

                    <thead>

                        <tr>
                            <th>ID</th>
                            <th>Título</th>
                            <th>Tipo</th>
                            <th>Dirección</th>
                            <th>Zona</th>
                            <th>Precio</th>
                            <th>Habitaciones</th>
                            <th>Disponible</th>
                            <th>Acciones</th>
                        </tr>

                    </thead>

                    <tbody>

                        <c:forEach var="vivienda" items="${viviendas}">

                            <tr>

                                <td>${vivienda.id}</td>
                                <td>${vivienda.titulo}</td>
                                <td>${vivienda.tipo}</td>
                                <td>${vivienda.direccion}</td>
                                <td>${vivienda.zona}</td>
                                <td>$${vivienda.precio}</td>
                                <td>${vivienda.habitaciones}</td>

                                <td>

                                    <c:choose>

                                        <c:when test="${vivienda.disponible}">
                                            Sí
                                        </c:when>

                                        <c:otherwise>
                                            No
                                        </c:otherwise>

                                    </c:choose>

                                </td>

                                <td>

                                    <a href="${pageContext.request.contextPath}/vivienda?accion=detalle&id=${vivienda.id}">
                                        Ver detalle
                                    </a>

                                    |

                                    <a href="${pageContext.request.contextPath}/vivienda?accion=editar&id=${vivienda.id}">
                                        Editar
                                    </a>

                                    |

                                    <a href="${pageContext.request.contextPath}/vivienda?accion=eliminar&id=${vivienda.id}"
                                       onclick="return confirm('¿Seguro que deseas eliminar esta vivienda?');">
                                        Eliminar
                                    </a>

                                </td>

                            </tr>

                        </c:forEach>

                    </tbody>

                </table>

            </c:otherwise>

        </c:choose>

    </body>

</html>