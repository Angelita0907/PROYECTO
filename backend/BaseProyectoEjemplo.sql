create database competicion_deportiva;

use competicion_deportiva;

CREATE TABLE Pruebas (
    id_prueba INT AUTO_INCREMENT PRIMARY KEY,
    nombre_prueba VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    unidad_medida VARCHAR(20) NOT NULL,
    modalidad VARCHAR(20) NOT NULL,
    lugar VARCHAR(100),
    descripcion TEXT,
    
    CONSTRAINT chk_tipo CHECK (tipo IN ('resistencia', 'fuerza', 'velocidad', 'flexibilidad', 'coordinacion', 'equilibrio')),
    CONSTRAINT chk_modalidad CHECK (modalidad IN ('individual', 'grupo', 'parejas'))
);

-- Inserción de pruebas 
INSERT INTO Pruebas (nombre_prueba, tipo, unidad_medida, modalidad, lugar, descripcion)
VALUES
('Carrera de sacos', 'velocidad', 'segundos', 'individual', 'Patio escolar', 'Carrera saltando dentro de un saco de 10 metros'),
('Lanzamiento de pelota', 'fuerza', 'metros', 'individual', 'Cancha deportiva', 'Lanzamiento de pelota blanda con ambas manos'),
('Equilibrio en banco', 'equilibrio', 'segundos', 'individual', 'Gimnasio', 'Tiempo manteniendo el equilibrio sobre un banco sueco'),
('Salto de cuerda', 'resistencia', 'repeticiones', 'individual', 'Patio escolar', 'Número de saltos consecutivos en 1 minuto'),
('Carrera de obstáculos', 'coordinacion', 'segundos', 'individual', 'Cancha deportiva', 'Recorrido de 20m con 4 obstáculos bajos'),
('Relevos divertidos', 'velocidad', 'segundos', 'grupo', 'Pista escolar', 'Relevos de 4 niños con elementos divertidos'),
('Salto longitudinal', 'fuerza', 'centímetros', 'individual', 'Arenero', 'Salto de longitud sin carrera de impulso'),
('Flexiones modificadas', 'fuerza', 'repeticiones', 'individual', 'Gimnasio', 'Flexiones de brazos desde las rodillas en 30 segundos'),
('Circuito de habilidades', 'coordinacion', 'puntos', 'individual', 'Cancha múltiple', 'Puntuación por ejecución correcta de 5 estaciones');