DROP DATABASE IF EXISTS Juego_cartas;
CREATE DATABASE Juego_cartas;
USE Juego_cartas;

-- ==========================================
-- 1. ESTADOS (Limpia)
-- ==========================================
CREATE TABLE estados (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL
);

INSERT INTO estados (nombre_estado) VALUES
('Depresión'), ('Parálisis'), ('Quemado'), ('Envenenado'), ('Cristalizado'), ('Mojado'), ('Moderado');

-- ==========================================
-- 2. ELEMENTOS (Relación directa con Estado)
-- ==========================================
CREATE TABLE elementos (
    id_elemento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    id_estado INT,
    FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);

INSERT INTO elementos (nombre, descripcion, id_estado) VALUES
('Vacío', 'Ausencia de materia.', 1),
('Electricidad', 'Conducción de corriente', 2),
('Solar', 'Energía solar canalizada', 3),
('Planta', 'Fostosintesis', 4),
('Mineral', 'Gigalith pre-evolución', 5),
('Líquido', 'Fluidos varios', 6),
('Admin', 'Más vale que sigas las reglas', 7);

-- ==========================================
-- 3. PERSONAJES (Limpia)
-- ==========================================
CREATE TABLE personajes (
    id_personaje INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    vida INT,
    multiplicador_ataque DECIMAL(3,2)
);

INSERT INTO personajes (nombre, vida, multiplicador_ataque) VALUES
('Aeris', 900, 1.2),
('Bolt el perro ese', 700, 1.4),
('Terrax', 1400, 0.85),
('Hydra', 1100, 1),
('Flora', 800, 1.3),
('Umbra', 600, 1.5),
('Ignis', 950, 1.15),
('Glacius', 1200, 0.95),
('Zephyr', 750, 1.35),
('Noctis', 650, 1.45),
('Pablo', 500, 2),
('Jaime', 5000, 1);

-- ==========================================
-- 4. ATAQUES (SOLO SU PRIMARY KEY Y DATOS)
-- ==========================================
CREATE TABLE ataques (
    id_ataque INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    potencia ENUM('ligero','normal','potente'),
    daño_base DECIMAL(5,2),
    coste_mana INT
);

INSERT INTO ataques (nombre, descripcion, potencia, daño_base, coste_mana) VALUES
('Eco Nulo', 'Ataque de vacío leve.', 'ligero', 18, 1),
('Rayo Solar', 'Haz de luz concentrado.', 'normal', 42, 3),
('Colapso Existencial', 'Gran distorsión.', 'potente', 85, 6),
('Chispa', 'Descarga rápida.', 'ligero', 20, 1),
('Impacto Trueno', 'Golpe eléctrico.', 'normal', 45, 3),
('Nova Solar', 'Explosión solar.', 'potente', 90, 6),
('Golpe Roca', 'Impacto sólido.', 'ligero', 25, 1),
('Muro de Piedra', 'Defensa ofensiva.', 'normal', 45, 3),
('Avalancha', 'Caída masiva.', 'potente', 95, 6),
('Salpicadura', 'Ataque leve.', 'ligero', 16, 1),
('Corriente', 'Flujo constante.', 'normal', 38, 3),
('Polen Tóxico', 'Envenena.', 'potente', 80, 6),
('Espina', 'Ataque básico.', 'ligero', 18, 1),
('Enredadera', 'Atrapa.', 'normal', 36, 3),
('Selva Viva', 'Ataque masivo.', 'potente', 88, 6),
('Grieta Oscura', 'Absorbe energía.', 'ligero', 22, 1),
('Vacío Interior', 'Ataque mental.', 'normal', 48, 3),
('Abismo Silente', 'Gran daño psíquico.', 'potente', 92, 6),
('Destello', 'Flash de luz.', 'ligero', 20, 1),
('Llama Radiante', 'Quema.', 'normal', 46, 3),
('Explosión Solar', 'Ataque fuerte.', 'potente', 93, 6),
('Salpicadura Helada', 'Agua fría.', 'ligero', 17, 1),
('Cristal Afilado', 'Fragmentos.', 'normal', 44, 3),
('Diluvio Final', 'Gran ataque.', 'potente', 90, 6),
('Chispa Rápida', 'Ataque veloz.', 'ligero', 19, 1),
('Rayo Paralizante', 'Inmoviliza.', 'normal', 43, 3),
('Tormenta Eléctrica', 'Área masiva.', 'potente', 89, 6),
('Eco Sombrío', 'Ataque oscuro.', 'ligero', 21, 1),
('Polen Oscuro', 'Envenena.', 'normal', 39, 3),
('Colapso Nocturno', 'Gran daño.', 'potente', 91, 6),
('Tortón', 'Con la mano abierta', 'ligero', 35, 2),
('Glock 18', 'Es más rápido...', 'normal', 130, 4),
('Banear', 'Te banea', 'potente', 270, 8),
('Corte oscuro', 'Te deja marca', 'ligero', 25, 3),
('Terror de las Tinieblas', '', 'normal', 70, 6),
('Decapitar', 'Se acabó', 'potente', 300, 12);

-- ==========================================
-- 5. TABLAS RELACIONALES (PARA LOS ATAQUES)
-- ==========================================

-- Relaciona cada ataque con su personaje
CREATE TABLE rel_ataque_personaje (
    id_ataque INT PRIMARY KEY,
    id_personaje INT,
    FOREIGN KEY (id_ataque) REFERENCES ataques(id_ataque),
    FOREIGN KEY (id_personaje) REFERENCES personajes(id_personaje)
);

INSERT INTO rel_ataque_personaje (id_ataque, id_personaje) VALUES
(1,1), (2,1), (3,1), (4,2), (5,2), (6,2), (7,3), (8,3), (9,3), (10,4), (11,4), (12,4),
(13,5), (14,5), (15,5), (16,6), (17,6), (18,6), (19,7), (20,7), (21,7), (22,8), (23,8), (24,8),
(25,9), (26,9), (27,9), (28,10), (29,10), (30,10), (31,11), (32,11), (33,11), (34,12), (35,12), (36,12);

-- Relaciona cada ataque con su elemento
CREATE TABLE rel_ataque_elemento (
    id_ataque INT PRIMARY KEY,
    id_elemento INT,
    FOREIGN KEY (id_ataque) REFERENCES ataques(id_ataque),
    FOREIGN KEY (id_elemento) REFERENCES elementos(id_elemento)
);

INSERT INTO rel_ataque_elemento (id_ataque, id_elemento) VALUES
(1,1), (2,3), (3,1), (4,2), (5,2), (6,3), (7,5), (8,5), (9,5), (10,6), (11,6), (12,4),
(13,4), (14,4), (15,4), (16,1), (17,1), (18,1), (19,3), (20,3), (21,3), (22,6), (23,5), (24,6),
(25,2), (26,2), (27,2), (28,1), (29,4), (30,1), (31,7), (32,7), (33,7), (34,7), (35,7), (36,7);

-- ==========================================
-- 6. INVOCACIONES (Directo a Personaje)
-- ==========================================
CREATE TABLE invocaciones (
    id_invocacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    daño DECIMAL(6,2),
    id_personaje INT UNIQUE,
    FOREIGN KEY (id_personaje) REFERENCES personajes(id_personaje)
);

INSERT INTO invocaciones (nombre, descripcion, daño, id_personaje) VALUES
('Fénix', 'Ave que renace de sus cenizas.', 120.0, 1),
('Dragón Eléctrico', 'Bestia de rayos.', 150.0, 2),
('Gólem', 'Gigante de piedra.', 130.0, 3),
('Kraken', 'Monstruo marino.', 170.0, 4),
('Ent', 'Árbol viviente.', 115.0, 5),
('Sombra Abisal', 'Entidad oscura.', 180.0, 6),
('Ifrit', 'Demonio de fuego.', 140.0, 7),
('Yeti', 'Bestia de hielo.', 135.0, 8),
('Grifo', 'Criatura alada.', 145.0, 9),
('Cerbero', 'Perro infernal.', 160.0, 10),
('Esqueleto Gigante','bomba boom', 200, 11),
('Tanque', 'Te revienta', 230, 12);

-- ==========================================
-- 7. ARMAS (Directo a Personaje)
-- ==========================================
CREATE TABLE armas (
    id_arma INT AUTO_INCREMENT PRIMARY KEY,
    nombre_arma VARCHAR(50) NOT NULL,
    descripcion TEXT,
    daño_extra DECIMAL(5,2) DEFAULT 0,
    multiplicador_daño DECIMAL(3,2) DEFAULT 1.00,
    prob_critico DECIMAL(3,2) DEFAULT 0.05,
    multiplicador_critico DECIMAL(3,2) DEFAULT 1.50,
    id_personaje INT UNIQUE,
    FOREIGN KEY (id_personaje) REFERENCES personajes(id_personaje)
);

INSERT INTO armas 
(nombre_arma, descripcion, daño_extra, multiplicador_daño, prob_critico, multiplicador_critico, id_personaje)
VALUES
('Espada del Alba', 'Espada mágica...', 25, 1.10, 0.15, 1.80, 1),
('Arco de Tormentas', 'Dispara flechas...', 20, 1.15, 0.20, 1.70, 2),
('Martillo de Titanes', 'Golpes devastadores.', 40, 1.25, 0.10, 2.00, 3),
('Tridente Abisal', 'Domina los océanos.', 30, 1.10, 0.12, 1.80, 4),
('Bastón de Raíces', 'Energía natural.', 18, 1.20, 0.18, 1.60, 5),
('Dagas de Sombra', 'Ataques rápidos.', 15, 1.30, 0.25, 1.90, 6),
('Rayban', 'Te deslumbra', 20, 1.4, 0.14, 1.67, 7),
('Bastón Glacial', 'Congela el entorno.', 22, 1.10, 0.16, 1.75, 8),
('Karambit del Viento', 'Spin to win', 17, 1.25, 0.22, 1.80, 9),
('Ballesta Arcana', 'Proyectiles precisos.', 28, 1.15, 0.19, 1.70, 10),
('M4A4 Silenciada', 'Con tres cargadores.', 50, 1.8, 0.34, 3, 11),
('Guadaña Infernal', 'Fuego del inframundo.', 35, 1.20, 0.14, 1.85, 12);

-- CAMBIA NOMBRE DE LAS TABLAS DE RELACION PARA QUITARLE EL REL!!!!!!!!
ALTER TABLE rel_ataque_personaje RENAME ataque_personaje;
ALTER TABLE rel_ataque_elemento RENAME ataque_elemento;


-- calcula el daño basico del ataque combinado con los multiplicadores de ataque del personaje y del arma
DELIMITER //

DROP PROCEDURE IF EXISTS daño_basico_tabla //
CREATE PROCEDURE daño_basico_tabla()
BEGIN
SELECT 
	a.nombre AS ataque,
    p.nombre AS personaje,
    ar.nombre_arma AS arma,
    
    -- daño base con personaje y arma
    (a.daño_base * p.multiplicador_ataque * ar.multiplicador_daño + ar.daño_extra) AS daño_basico
    
FROM ataques a
JOIN ataque_personaje ap ON a.id_ataque = ap.id_ataque
JOIN personajes p ON p.id_personaje = ap.id_personaje
JOIN armas ar ON p.id_personaje = ar.id_personaje;
END //
DELIMITER ;

CALL daño_basico_tabla();



DELIMITER //

DROP FUNCTION IF EXISTS daño_basico_especifico //
CREATE FUNCTION daño_basico_especifico(p_nombre VARCHAR(50))
RETURNS DECIMAL(10,2)
BEGIN
DECLARE calculo DECIMAL(10,2);
SELECT
-- daño base con personaje y arma
(a.daño_base * p.multiplicador_ataque * ar.multiplicador_daño + ar.daño_extra) AS daño_basico
INTO calculo
FROM ataques a
JOIN ataque_personaje ap ON a.id_ataque = ap.id_ataque
JOIN personajes p ON p.id_personaje = ap.id_personaje
JOIN armas ar ON p.id_personaje = ar.id_personaje
WHERE a.nombre=p_nombre;

RETURN calculo;
END //
DELIMITER ;

SELECT daño_basico_especifico('Eco Nulo') AS daño_basico;


-- para el buscador general, crear una funcion que devuelva los nombres de los ataques concatenados


/*-- daño crítico
    (a.daño_base * p.multiplicador_ataque * ar.multiplicador_daño + ar.daño_extra) * ar.multiplicador_critico AS daño_critico,
    
    ar.prob_critico*/


SELECT AVG(prob_critico)
    FROM armas;

-- selecciona la probabilidad de critico de un ataque
SELECT nombre_arma, 100*(prob_critico) AS porcentaje_critico
FROM armas
WHERE prob_critico > (
	SELECT AVG(prob_critico)
    FROM armas
);


