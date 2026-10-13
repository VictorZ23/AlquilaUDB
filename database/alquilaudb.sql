-- =====================================================
-- ALQUILAUDB
-- Script de base de datos
-- Fase II: JSF, JPA, Hibernate y MySQL
-- =====================================================

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS alquilaudb
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE alquilaudb;

-- =====================================================
-- TABLA: vivienda
-- Almacena las viviendas disponibles para alquiler
-- =====================================================

CREATE TABLE IF NOT EXISTS vivienda (

    id INT NOT NULL AUTO_INCREMENT,

    titulo VARCHAR(100) NOT NULL,

    tipo VARCHAR(30) NOT NULL,

    direccion VARCHAR(150) NOT NULL,

    zona VARCHAR(100) NOT NULL,

    precio DECIMAL(10,2) NOT NULL,

    habitaciones INT NOT NULL,

    descripcion TEXT NULL,

    disponible TINYINT(1) NOT NULL DEFAULT 1,

    PRIMARY KEY (id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_general_ci;

-- =====================================================
-- TABLA: resena
-- Almacena reseñas asociadas a viviendas
-- =====================================================

CREATE TABLE IF NOT EXISTS resena (

    id INT NOT NULL AUTO_INCREMENT,

    vivienda_id INT NOT NULL,

    autor VARCHAR(100) NOT NULL,

    calificacion TINYINT NOT NULL,

    comentario TEXT NOT NULL,

    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    INDEX idx_resena_vivienda (vivienda_id),

    CONSTRAINT fk_resena_vivienda
        FOREIGN KEY (vivienda_id)
        REFERENCES vivienda(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT chk_resena_calificacion
        CHECK (calificacion BETWEEN 1 AND 5)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_general_ci;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================
