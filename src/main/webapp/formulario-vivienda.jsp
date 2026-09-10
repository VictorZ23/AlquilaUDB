<%-- 
    Document   : formulario-vivienda
    Created on : 9 sep. 2026, 12:47:19
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">

        <c:choose>
            <c:when test="${empty vivienda.id}">
                <title>Agregar vivienda</title>
            </c:when>

            <c:otherwise>
                <title>Editar vivienda</title>
            </c:otherwise>
        </c:choose>
    </head>

    <body>

        <c:choose>
            <c:when test="${empty vivienda.id}">
                <h1>Agregar vivienda</h1>
            </c:when>

            <c:otherwise>
                <h1>Editar vivienda</h1>
            </c:otherwise>
        </c:choose>

        <!-- MENSAJES DE ERROR -->
        <c:if test="${not empty errores}">

            <div>
                <strong>No se pudo guardar la vivienda:</strong>

                <ul>
                    <c:forEach var="error" items="${errores}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>

        </c:if>

        <form action="${pageContext.request.contextPath}/vivienda"
              method="post">

            <input type="hidden"
                   name="id"
                   value="${vivienda.id}">

            <label for="titulo">Título:</label>
            <br>

            <input type="text"
                   id="titulo"
                   name="titulo"
                   value="${vivienda.titulo}"
                   required>

            <br><br>

            <label for="tipo">Tipo:</label>
            <br>

            <select id="tipo"
                    name="tipo"
                    required>

                <option value="">
                    Seleccione un tipo
                </option>

                <option value="Casa"
                        ${vivienda.tipo == 'Casa' ? 'selected' : ''}>
                    Casa
                </option>

                <option value="Apartamento"
                        ${vivienda.tipo == 'Apartamento' ? 'selected' : ''}>
                    Apartamento
                </option>

                <option value="Habitación"
                        ${vivienda.tipo == 'Habitación' ? 'selected' : ''}>
                    Habitación
                </option>

            </select>

            <br><br>

            <label for="direccion">Dirección:</label>
            <br>

            <input type="text"
                   id="direccion"
                   name="direccion"
                   value="${vivienda.direccion}"
                   required>

            <br><br>

            <label for="zona">Zona:</label>
            <br>

            <input type="text"
                   id="zona"
                   name="zona"
                   value="${vivienda.zona}"
                   required>

            <br><br>

            <label for="precio">Precio mensual:</label>
            <br>

            <input type="number"
                   id="precio"
                   name="precio"
                   step="0.01"
                   min="0.01"
                   value="${vivienda.precio}"
                   required>

            <br><br>

            <label for="habitaciones">Habitaciones:</label>
            <br>

            <input type="number"
                   id="habitaciones"
                   name="habitaciones"
                   min="1"
                   value="${vivienda.habitaciones}"
                   required>

            <br><br>

            <label for="descripcion">Descripción:</label>
            <br>

            <textarea id="descripcion"
                      name="descripcion"
                      rows="5"
                      cols="40">${vivienda.descripcion}</textarea>

            <br><br>

            <label>

                <input type="checkbox"
                       name="disponible"
                       ${empty vivienda.id || vivienda.disponible ? 'checked' : ''}>

                Disponible

            </label>

            <br><br>

            <c:choose>

                <c:when test="${empty vivienda.id}">
                    <button type="submit">
                        Guardar vivienda
                    </button>
                </c:when>

                <c:otherwise>
                    <button type="submit">
                        Guardar cambios
                    </button>
                </c:otherwise>

            </c:choose>

        </form>

        <br>

        <a href="${pageContext.request.contextPath}/vivienda">
            Volver al listado
        </a>

    </body>

</html>