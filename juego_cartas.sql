DROP DATABASE IF EXISTS Juego_cartas;
CREATE DATABASE Juego_cartas;
USE Juego_cartas;

-- 1. Creamos la tabla de estados (ahora son status effects)
CREATE TABLE estados (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL
);

-- 2. Insertamos los estados tipo "efecto"
INSERT INTO estados (nombre_estado) VALUES
('Depresión'),     -- vacío
('Parálisis'),     -- electricidad
('Quemado'),       -- solar
('Veneno'),        -- planta
('Cristalizado'),  -- mineral
('Mojado');        -- líquido

-- 3. Creamos la tabla elementos con la relación
CREATE TABLE elementos (
    id_elemento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    id_estado INT,
    CONSTRAINT fk_estado FOREIGN KEY (id_estado) REFERENCES estados(id)
);

-- 4. Insertamos los elementos ya con su estado asignado
INSERT INTO elementos (nombre, descripcion, id_estado) VALUES
('vacío', 'Ausencia de materia en un espacio o lugar determinado.', 1),
('electricidad', 'Forma de energía que produce efectos luminosos, mecánicos, caloríficos y químicos.', 2),
('solar', 'Energía o radiación electromagnética procedente del Sol.', 3),
('planta', 'Ser orgánico que crece y vive, pero no muda de lugar por impulso voluntario.', 4),
('mineral', 'Sustancia inorgánica que se halla en la superficie o en las diversas capas de la corteza del globo.', 5),
('líquido', 'Estado de la materia en forma de fluido altamente incompresible.', 6);



-- 1. Tabla de personajes
CREATE TABLE personajes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- 2. Insertamos personajes de ejemplo
INSERT INTO personajes (nombre) VALUES
('Aeris'),
('Volt'),
('Terrax'),
('Hydra'),
('Flora'),
('Umbra');

-- 3. Tabla de ataques
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
    /*CONSTRAINT fk_personaje*/ FOREIGN KEY (id_personaje) REFERENCES personajes(id),
    CONSTRAINT fk_elemento FOREIGN KEY (id_elemento) REFERENCES elementos(id),
    CONSTRAINT fk_estado_ataque FOREIGN KEY (id_estado) REFERENCES estados(id)
);

-- 4. Insertamos ataques (5 por elemento)

INSERT INTO ataques (nombre, descripcion, potencia, coste_mana, id_personaje, daño_base, id_elemento, id_estado) VALUES

-- VACÍO 
('Eco Nulo', 'Una onda de vacío que debilita al enemigo.', 'ligero', 1, 1, 15.5, 1, 1),
('Abismo Silente', 'Silencio absoluto que daña la mente.', 'normal', 3, 1, 35.0, 1, 1),
('Colapso Existencial', 'Distorsiona la realidad causando gran daño.', 'potente', 6, 1, 75.0, 1, 1),
('Grieta Oscura', 'Abre una grieta que absorbe energía.', 'normal', 3, 6, 40.0, 1, 1),
('Vacío Interior', 'Ataque psíquico devastador.', 'potente', 6, 6, 80.0, 1, 1),

-- ELECTRICIDAD
('Chispa', 'Descarga eléctrica leve.', 'ligero', 1, 2, 18.0, 2, 2),
('Impacto Trueno', 'Golpe cargado de electricidad.', 'normal', 3, 2, 38.0, 2, 2),
('Tormenta Eléctrica', 'Gran descarga en área.', 'potente', 6, 2, 78.0, 2, 2),
('Rayo Paralizante', 'Inmoviliza al enemigo.', 'normal', 3, 1, 42.0, 2, 2),
('Sobrecarga', 'Explosión eléctrica intensa.', 'potente', 6, 2, 85.0, 2, 2),

-- SOLAR
('Destello', 'Flash de luz solar.', 'ligero', 1, 1, 20.0, 3, 3),
('Rayo Solar', 'Haz concentrado de energía.', 'normal', 3, 1, 45.0, 3, 3),
('Explosión Solar', 'Liberación masiva de energía.', 'potente', 6, 1, 90.0, 3, 3),
('Llama Radiante', 'Quema al enemigo.', 'normal', 3, 2, 48.0, 3, 3),
('Nova Solar', 'Ataque devastador.', 'potente', 6, 2, 95.0, 3, 3),

-- PLANTA 
('Espina', 'Ataque básico con espinas.', 'ligero', 1, 5, 16.0, 4, 4),
('Enredadera', 'Atrapa al enemigo.', 'normal', 3, 5, 34.0, 4, 4),
('Selva Viva', 'Ataque masivo natural.', 'potente', 6, 5, 70.0, 4, 4),
('Polen Tóxico', 'Envenena al enemigo.', 'normal', 3, 4, 36.0, 4, 4),
('Raíz Mortal', 'Ataque profundo y fuerte.', 'potente', 6, 5, 82.0, 4, 4),

-- MINERAL 
('Golpe Roca', 'Impacto sólido.', 'ligero', 1, 3, 22.0, 5, 5),
('Muro de Piedra', 'Ataque defensivo y ofensivo.', 'normal', 3, 3, 40.0, 5, 5),
('Avalancha', 'Gran caída de rocas.', 'potente', 6, 3, 88.0, 5, 5),
('Cristal Afilado', 'Fragmentos cortantes.', 'normal', 3, 3, 44.0, 5, 5),
('Prisión Cristalina', 'Encierra al enemigo.', 'potente', 6, 3, 85.0, 5, 5),

-- LÍQUIDO 
('Salpicadura', 'Ataque leve de agua.', 'ligero', 1, 4, 14.0, 6, 6),
('Corriente', 'Flujo de agua constante.', 'normal', 3, 4, 33.0, 6, 6),
('Tsunami', 'Ola devastadora.', 'potente', 6, 4, 92.0, 6, 6),
('Lluvia Ácida', 'Daño continuo.', 'normal', 3, 6, 37.0, 6, 6),
('Diluvio Final', 'Ataque masivo.', 'potente', 6, 4, 89.0, 6, 6);