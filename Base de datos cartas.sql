DROP DATABASE IF EXISTS Juego_cartas;
CREATE DATABASE Juego_cartas;
USE Juego_cartas;

-- ESTADOS
CREATE TABLE estados (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL
);

INSERT INTO estados (nombre_estado) VALUES
('Depresión'),
('Parálisis'),
('Quemado'),
('Veneno'),
('Cristalizado'),
('Mojado');

-- ELEMENTOS
CREATE TABLE elementos (
    id_elemento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    id_estado INT,
    FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);

INSERT INTO elementos (nombre, descripcion, id_estado) VALUES
('vacío', 'Ausencia de materia.', 1),
('electricidad', 'Energía eléctrica.', 2),
('solar', 'Energía del sol.', 3),
('planta', 'Naturaleza viva.', 4),
('mineral', 'Roca y tierra.', 5),
('líquido', 'Fluidos.', 6);

-- PERSONAJES (AHORA 10)
CREATE TABLE personajes (
    id_personaje INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    vida INT,
    multiplicador_ataque DECIMAL(3,2)
);

INSERT INTO personajes (nombre, vida, multiplicador_ataque) VALUES
('Aeris', 900, 1.20),
('Volt', 700, 1.40),
('Terrax', 1400, 0.85),
('Hydra', 1100, 1.00),
('Flora', 800, 1.30),
('Umbra', 600, 1.50),
('Ignis', 950, 1.15),
('Glacius', 1200, 0.95),
('Zephyr', 750, 1.35),
('Noctis', 650, 1.45);

-- ATAQUES (sin cambios)
CREATE TABLE ataques (
    id_ataque INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    potencia ENUM('ligero','normal','potente'),
    coste_mana INT,
    id_personaje INT,
    daño_base DECIMAL(5,2),
    id_elemento INT,
    id_estado INT,
    FOREIGN KEY (id_personaje) REFERENCES personajes(id_personaje),
    FOREIGN KEY (id_elemento) REFERENCES elementos(id_elemento),
    FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);

-- (puedes reutilizar los mismos INSERT de ataques que ya tenías)

-- INVOCACIONES (AHORA 1 POR PERSONAJE)
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
('Cerbero', 'Perro infernal.', 160.0, 10);