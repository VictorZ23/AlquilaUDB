<%-- 
    Document   : detalle-vivienda
    Created on : 9 sep. 2026, 17:45:21
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Detalle de vivienda</title>
    </head>

    <body>

        <h1>Detalle de vivienda</h1>

        <h2>${vivienda.titulo}</h2>

        <p>
            <strong>ID:</strong>
            ${vivienda.id}
        </p>

        <p>
            <strong>Tipo:</strong>
            ${vivienda.tipo}
        </p>

        <p>
            <strong>Dirección:</strong>
            ${vivienda.direccion}
        </p>

        <p>
            <strong>Zona:</strong>
            ${vivienda.zona}
        </p>

        <p>
            <strong>Precio mensual:</strong>
            $${vivienda.precio}
        </p>

        <p>
            <strong>Habitaciones:</strong>
            ${vivienda.habitaciones}
        </p>

        <p>
            <strong>Descripción:</strong>
        </p>

        <p>
            ${vivienda.descripcion}
        </p>

        <p>
            <strong>Disponible:</strong>

            <c:choose>

                <c:when test="${vivienda.disponible}">
                    Sí
                </c:when>

                <c:otherwise>
                    No
                </c:otherwise>

            </c:choose>
        </p>

        <p>
            <a href="${pageContext.request.contextPath}/vivienda?accion=editar&id=${vivienda.id}">
                Editar vivienda
            </a>
        </p>

        <p>
            <a href="${pageContext.request.contextPath}/vivienda">
                Volver al listado
            </a>
        </p>

    </body>

</html>