
CREATE DATABASE IF NOT EXISTS proyectoPrimero;
USE proyectoPrimero;

CREATE TABLE IF NOT EXISTS usuarios (
    usuario VARCHAR(50) PRIMARY KEY,
    clave VARCHAR(100) NOT NULL
);

-- Usuario de prueba
INSERT INTO usuarios (usuario, clave) VALUES ('angela', 'angela')
ON DUPLICATE KEY UPDATE clave = 'angela';


-- Usuario de prueba
INSERT INTO usuarios (usuario, clave) VALUES ('angela1', 'angela1')
ON DUPLICATE KEY UPDATE clave = 'angela1';