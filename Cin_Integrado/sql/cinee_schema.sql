-- Cinee: esquema compartido por el proyecto cliente y el rol administrador.
-- Ejecutar una sola vez en MySQL 8.0+ (por ejemplo, desde MySQL Workbench).
CREATE DATABASE IF NOT EXISTS cinee_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cinee_db;

CREATE TABLE IF NOT EXISTS administradores (
  administrador_id BIGINT NOT NULL AUTO_INCREMENT,
  usuario VARCHAR(40) NOT NULL,
  salt_hex CHAR(32) NOT NULL,
  hash_hex CHAR(64) NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ultimo_acceso TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (administrador_id),
  UNIQUE KEY uq_administradores_usuario (usuario)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS clientes (
  cliente_id BIGINT NOT NULL AUTO_INCREMENT,
  tipo_documento VARCHAR(20) NULL,
  documento VARCHAR(30) NULL,
  nombre VARCHAR(160) NOT NULL,
  email VARCHAR(180) NULL,
  telefono VARCHAR(40) NULL,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (cliente_id),
  UNIQUE KEY uq_cliente_documento (tipo_documento, documento),
  KEY idx_clientes_nombre (nombre)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS peliculas (
  pelicula_id INT NOT NULL,
  titulo VARCHAR(180) NOT NULL,
  actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (pelicula_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS salas (
  codigo_sala VARCHAR(20) NOT NULL,
  numero_sala INT NOT NULL,
  capacidad INT NOT NULL DEFAULT 0,
  PRIMARY KEY (codigo_sala),
  UNIQUE KEY uq_salas_numero (numero_sala)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS funciones (
  codigo_funcion VARCHAR(191) NOT NULL,
  funcion_id_origen VARCHAR(40) NULL,
  pelicula_id INT NOT NULL,
  codigo_sala VARCHAR(20) NOT NULL,
  fecha_funcion VARCHAR(60) NOT NULL,
  hora_funcion VARCHAR(40) NOT NULL,
  creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (codigo_funcion),
  KEY idx_funcion_busqueda (pelicula_id, fecha_funcion, hora_funcion, codigo_sala),
  CONSTRAINT fk_funciones_pelicula FOREIGN KEY (pelicula_id) REFERENCES peliculas(pelicula_id),
  CONSTRAINT fk_funciones_sala FOREIGN KEY (codigo_sala) REFERENCES salas(codigo_sala)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS productos (
  producto_codigo VARCHAR(40) NOT NULL,
  nombre VARCHAR(160) NOT NULL,
  categoria VARCHAR(40) NOT NULL DEFAULT 'OTRO',
  precio_referencia DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (producto_codigo)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ventas (
  venta_id VARCHAR(50) NOT NULL,
  fecha_venta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  cliente_id BIGINT NULL,
  codigo_funcion VARCHAR(191) NULL,
  cliente_tipo_documento_snapshot VARCHAR(20) NULL,
  cliente_documento_snapshot VARCHAR(30) NULL,
  cliente_nombre_snapshot VARCHAR(160) NULL,
  cliente_email_snapshot VARCHAR(180) NULL,
  cliente_telefono_snapshot VARCHAR(40) NULL,
  pelicula_titulo_snapshot VARCHAR(180) NULL,
  fecha_funcion_snapshot VARCHAR(60) NULL,
  hora_funcion_snapshot VARCHAR(40) NULL,
  codigo_sala_snapshot VARCHAR(20) NULL,
  numero_sala_snapshot INT NULL,
  ticket_codigo VARCHAR(80) NULL,
  qr_codigo VARCHAR(120) NULL,
  metodo_pago VARCHAR(40) NOT NULL DEFAULT 'No registrado',
  total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  estado VARCHAR(20) NOT NULL DEFAULT 'CONFIRMADA',
  PRIMARY KEY (venta_id),
  KEY idx_ventas_fecha (fecha_venta),
  KEY idx_ventas_cliente (cliente_id),
  KEY idx_ventas_pelicula (pelicula_titulo_snapshot),
  KEY idx_ventas_pago (metodo_pago),
  CONSTRAINT fk_ventas_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id),
  CONSTRAINT fk_ventas_funcion FOREIGN KEY (codigo_funcion) REFERENCES funciones(codigo_funcion)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS venta_detalles (
  detalle_id BIGINT NOT NULL AUTO_INCREMENT,
  venta_id VARCHAR(50) NOT NULL,
  producto_codigo VARCHAR(40) NULL,
  producto_nombre VARCHAR(160) NOT NULL,
  categoria VARCHAR(40) NOT NULL DEFAULT 'OTRO',
  cantidad INT NOT NULL DEFAULT 1,
  precio_unitario DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (detalle_id),
  KEY idx_detalle_venta (venta_id),
  CONSTRAINT fk_detalle_venta FOREIGN KEY (venta_id) REFERENCES ventas(venta_id) ON DELETE CASCADE,
  CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_codigo) REFERENCES productos(producto_codigo)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS venta_asientos (
  asiento_venta_id BIGINT NOT NULL AUTO_INCREMENT,
  venta_id VARCHAR(50) NOT NULL,
  codigo_funcion VARCHAR(191) NULL,
  codigo_asiento VARCHAR(12) NOT NULL,
  codigo_sala_snapshot VARCHAR(20) NULL,
  PRIMARY KEY (asiento_venta_id),
  UNIQUE KEY uq_asiento_vendido_funcion (codigo_funcion, codigo_asiento),
  KEY idx_asientos_venta (venta_id),
  CONSTRAINT fk_asiento_venta FOREIGN KEY (venta_id) REFERENCES ventas(venta_id) ON DELETE CASCADE,
  CONSTRAINT fk_asiento_funcion FOREIGN KEY (codigo_funcion) REFERENCES funciones(codigo_funcion)
) ENGINE=InnoDB;

-- Usuario de BD recomendado para el desarrollo local. Cambia la clave antes de ejecutar.
-- Ejecutar manualmente si deseas un usuario limitado en lugar de root:
-- CREATE USER 'cinee_app'@'localhost' IDENTIFIED BY 'CAMBIA_ESTO_POR_UNA_CLAVE_LARGA';
-- GRANT SELECT, INSERT, UPDATE, DELETE ON cinee_db.* TO 'cinee_app'@'localhost';
-- FLUSH PRIVILEGES;
