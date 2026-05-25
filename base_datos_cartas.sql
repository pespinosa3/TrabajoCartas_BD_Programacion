DROP DATABASE IF EXISTS Juego_cartas;
CREATE DATABASE Juego_cartas;
USE Juego_cartas;


CREATE TABLE estados (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    turnos INT
);

INSERT INTO estados (nombre, descripcion, turnos) VALUES
('Depresión','Ve al pscicologo',2), ('Parálisis','La corriente te stunnea',3), ('Quemado','Usa proteccion solar a la proxima',1),
('Envenenado', 'Te va drenando vida',1), ('Cristalizado','La roca',2),
('Mojado','Duchita fria para empezar el dia',1), ('Moderado','Has sido banneano',3);



CREATE TABLE elementos (
    id_elemento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT
);

INSERT INTO elementos (nombre, descripcion) VALUES
('Vacío', 'Ausencia de materia.'),
('Electricidad', 'Energía eléctrica.'),
('Solar', 'Energía del sol.'),
('Planta', 'Naturaleza viva.'),
('Mineral', 'Roca y tierra.'),
('Líquido', 'Fluidos.'),
('Admin', 'Más vale que siga las reglas');


CREATE TABLE casas (
    id_casa INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    emblema VARCHAR(50),
    descripcion VARCHAR(150),
    id_comandante INT, 
    dicho VARCHAR(100)
);


INSERT INTO casas (nombre, emblema, descripcion, id_comandante, dicho) VALUES
('Legión Administrativa', 'Martillo Dorado', 'Obedece la normativa o serás expulsado' , 11, 'Sigue la normativa, está subida en #general'), -- Pablo
('Orden del Caos', 'Dementor', 'Orden abisal', 12, 'EL invierno está cerca'),      -- Jaime
('Lannister', 'Lobo dorado','Cuanto más prima...', 2, 'Un Lannister siempre paga sus deudas'),           -- Bolt
('Marea Eterna', 'Nemo','Se la pasan en el mar', 4, 'Buscando a Nemo');  -- Hydra


CREATE TABLE personajes (
    id_personaje INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(150),
    vida INT,
    multiplicador_ataque DECIMAL(3,2),
    id_casa INT,
    id_comandante INT,
    id_elemento_1 INT,
    id_elemento_2 INT, 
    
    FOREIGN KEY (id_casa) REFERENCES casas(id_casa) ON DELETE CASCADE,
    FOREIGN KEY (id_comandante) REFERENCES personajes(id_personaje) ON DELETE CASCADE,
    FOREIGN KEY (id_elemento_1) REFERENCES elementos(id_elemento) ON DELETE CASCADE,
    FOREIGN KEY (id_elemento_2) REFERENCES elementos(id_elemento) ON DELETE CASCADE
);

-- Capitanes primero para evitar errores de forin ki
INSERT INTO personajes (id_personaje, nombre, descripcion, vida, multiplicador_ataque, id_casa, id_comandante, id_elemento_1, id_elemento_2) VALUES
-- Dictadores
(11, 'Pablo','moderador de las tierras sagradas', 500, 2.00, 1, NULL, 7, NULL),
(12, 'Jaime','moderador de las tierras oscuras', 5000, 1.00, 2, NULL, 7, NULL),
(2, 'Bolt el perro ese','el perro de la pelicula esa (no se como se llama)', 700, 1.40, 3, NULL, 2, 3),
(4, 'Hydra','la del dark souls 1', 1100, 1.00, 4, NULL, 6, 4),

-- Esclavos
(1, 'Aeris','ninfa del bosque', 900, 1.20, 1, 11, 1, 3),
(3, 'Terrax','golem de roca', 1400, 0.85, 1, 11, 5, NULL),
(5, 'Flora','flower power', 800, 1.30, 2, 12, 4, NULL),
(6, 'Fran','leñador experto, ha perfeccionado el uso del hacha', 600, 1.50, 2, 12, 1, NULL),
(7, 'Mary Angel','Te controla la excepción', 950, 1.15, 3, 2, 3, NULL),
(8, 'Santa Claus','Te va a traer carbón que te has portado como el culo', 1200, 0.95, 3, 2, 6, 5),
(9, 'Pikacho','Primo agresivo, drogadicto de pikachu', 750, 1.35, 4, 4, 2, NULL),
(10, 'Gondalf','Gandalf si fuera gitano', 650, 1.45, 4, 4, 1, 4);




CREATE TABLE ataques (
    id_ataque INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    potencia ENUM('ligero','normal','potente'),
    daño_base DECIMAL(5,2),
    coste_mana INT
);

INSERT INTO ataques (nombre, descripcion, potencia, daño_base, coste_mana) VALUES
('Espacio-tiempo del desempleado', 'Pierdes noción del tiempo además de no tener ganas de trabajar', 'ligero', 18, 1),
('Rayo Solar', 'Rayo potenciado por la cantidad de luz solar que haya en el campo de batalla', 'normal', 42, 3),
('Crisis Existencial', 'Gran distorsión.', 'potente', 85, 6),
('Chispa', 'Descarga rápida de electricidad en forma de pelota', 'ligero', 20, 1),
('Impactrueno', 'Esta es una idea original mia, se me acaba de ocurrir', 'normal', 45, 3),
('Sol de Murcia en Julio', 'Mucha suerte saliendo a la calle', 'potente', 90, 6),
('Golpe Roca', 'Impacto sólido.', 'ligero', 25, 1),
('Muro de Piedra', 'Defensa ofensiva.', 'normal', 45, 3),
('Avalancha', 'Caída masiva de nieve sobre la cabeza de tu oponente', 'potente', 95, 6),
('Salpicadura', 'Ataque leve.', 'ligero', 16, 1),
('Corriente', 'Voltaje entre tiempo o algo así era (suspendí física)', 'normal', 38, 3),
('Polen Tóxico', 'El archienemigo de los porretas', 'potente', 80, 6),
('Espina', 'Ataque básico.', 'ligero', 18, 1),
('Enredadera', 'Atrapa', 'normal', 36, 3),
('Selva Viva', 'Ataque masivo.', 'potente', 88, 6),
('Grieta Oscura', 'Absorbe energía.', 'ligero', 22, 1),
('Vacío Interior', 'Ataque mental.', 'normal', 48, 3),
('Abismo', 'Gran daño psíquico.', 'potente', 92, 6),
('Flashbang', 'Te tira las largas (no ves nada)', 'ligero', 20, 1),
('Llama Radiante', 'Quema.', 'normal', 46, 3),
('Explosión Solar', 'Lo más parecido a una supernova que se va a alcanzar en la tierra', 'potente', 93, 6),
('Salpicadura Helada', 'Imaginate si magikarp en vez de tirarte agua a 15º te la tira a -30º', 'ligero', 17, 1),
('Cristal Afilado', 'Fragmentos.', 'normal', 44, 3),
('Hidromeado', 'Parecido a hidrobomba, pero no es de agua', 'potente', 90, 6),
('Zap', 'Daño en area leve que puede paralizar', 'ligero', 19, 1),
('Trueno', 'La onda de sonido es devastadora, te rompe los tímpanos', 'normal', 43, 3),
('Tormenta Eléctrica', 'Área masiva de daño persistente que dura el resto de la partida', 'potente', 89, 6),
('Eco Nulo', 'Primer ataque que se creo en la base de datos', 'ligero', 21, 1),
('Polen Oscuro', 'Envenena a todo jugador fumador de porros', 'normal', 39, 3),
('Colapso Nocturno', 'Gran daño.', 'potente', 91, 6),
('Tortón', 'Con la mano abierta', 'ligero', 35, 2),
('Glock 18', 'Es más rápido cambiar a la secundaria que recargar la principal', 'normal', 130, 4),
('Banear', 'Te banea', 'potente', 270, 8),
('Corte oscuro', 'Te deja marca', 'ligero', 25, 3),
('Terror de las Tinieblas', 'Huye de ahí', 'normal', 70, 6),
('Decapitar', 'Se acabó', 'potente', 300, 12);



-- esto es para no tener que añadirle a la tabla de personaje el id de sus tres ataques
/* los dos son primary key porque si te creas un personaje nuevo le tienes que poder añadir un ataque, entonces un ataque puede aparecer varias veces
en la tabla, y id_personaje tiene que salir mas de una vez si queremos que tengan 3 ataques cada personaje, y primary key tienen que ser para no 
repetirse la combinacion*/
CREATE TABLE ataque_personaje (
    id_ataque INT,
    id_personaje INT,
    PRIMARY KEY (id_ataque, id_personaje),
    FOREIGN KEY (id_ataque) REFERENCES ataques(id_ataque) ON DELETE CASCADE,
    FOREIGN KEY (id_personaje) REFERENCES personajes(id_personaje) ON DELETE CASCADE
);

INSERT INTO ataque_personaje (id_ataque, id_personaje) VALUES
(1,1), (2,1), (3,1), (4,2), (5,2), (6,2), (7,3), (8,3), (9,3), (10,4), (11,4), (12,4),
(13,5), (14,5), (15,5), (16,6), (17,6), (18,6), (19,7), (20,7), (21,7), (22,8), (23,8), (24,8),
(25,9), (26,9), (27,9), (28,10), (29,10), (30,10), (31,11), (32,11), (33,11), (34,12), (35,12), (36,12);


-- otra tabla relacional separada, de nuevo para que sea mas facil acceder a los datos, por funcinalidad realmente no es necesario
CREATE TABLE ataque_elemento (
    id_ataque INT,
    id_elemento INT,
    id_estado INT,
    PRIMARY KEY(id_ataque),
    FOREIGN KEY (id_ataque) REFERENCES ataques(id_ataque) ON DELETE CASCADE,
    FOREIGN KEY (id_elemento) REFERENCES elementos(id_elemento) ON DELETE CASCADE,
    FOREIGN KEY (id_estado) REFERENCES estados(id_estado) ON DELETE CASCADE
);


INSERT INTO ataque_elemento (id_ataque, id_elemento, id_estado) VALUES
(1, 1, 1), (2, 3, 3), (3, 1, 1), (4, 2, 2), (5, 2, 2), (6, 3, 3), 
(7, 5, 5), (8, 5, 5), (9, 5, 5), (10, 6, 6), (11, 6, 6), (12, 4, 4),
(13, 4, 4), (14, 4, 4), (15, 4, 4), (16, 1, 1), (17, 1, 1), (18, 1, 1), 
(19, 3, 3), (20, 3, 3), (21, 3, 3), (22, 6, 6), (23, 5, 5), (24, 6, 6),
(25, 2, 2), (26, 2, 2), (27, 2, 2), (28, 1, 1), (29, 4, 4), (30, 1, 1), 
(31, 7, 7), (32, 7, 7), (33, 7, 7), (34, 7, 7), (35, 7, 7), (36, 7, 7);

-- ==========================================
-- 8. INVOCACIONES Y ARMAS
-- ==========================================
CREATE TABLE invocaciones (
    id_invocacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    daño DECIMAL(6,2),
    id_personaje INT UNIQUE,
    FOREIGN KEY (id_personaje) REFERENCES personajes(id_personaje) ON DELETE CASCADE
);

INSERT INTO invocaciones (nombre, descripcion, daño, id_personaje) VALUES
('Fénix', 'Ave que renace de sus cenizas', 120.0, 1),
('Dragón Eléctrico', 'Tarda mucho en cargar el ataque de rayo, pero si lo hace es devastador', 150.0, 2),
('Gólem', 'Gigante de piedra con gran fuerza', 130.0, 3),
('Kraken', 'Calamar colosal marino de las profundidades', 170.0, 4),
('Ent', 'Árbol viviente.', 115.0, 5),
('Sombra Abisal', 'Entidad oscura.', 180.0, 6),
('Pyro', 'Un hombre con una mascara de gas y un lanzallamas', 140.0, 7),
('Yeti', 'Gigante con el cuerpo peludo y pelaje blanco, residente de las montañas nevadas', 135.0, 8),
('Grifo', 'El león alado, no el que no funciona en el baño de los tios', 145.0, 9),
('Cerbero', 'El perro de Hades de tres cabezas', 160.0, 10),
('Pandilla de duendes','Horda de 6 duendes verdes gitanos, 3 con cuchillos y otros 3 con lanzas', 200, 11),
('Tanque', 'Te revienta', 230, 12);

CREATE TABLE armas (
    id_arma INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    daño_extra DECIMAL(5,2) DEFAULT 0,
    multiplicador_daño DECIMAL(3,2) DEFAULT 1.00,
    prob_critico DECIMAL(3,2) DEFAULT 0.05,
    multiplicador_critico DECIMAL(3,2) DEFAULT 1.50
);

INSERT INTO armas (nombre, descripcion, daño_extra, multiplicador_daño, prob_critico, multiplicador_critico) VALUES
('Espadón de Luz de Luna', 'Espada gigante de uso a dos manos que requiere sabiduría para usarse', 25, 1.10, 0.15, 1.80),
('Arco de Tormentas', 'Dispara flechas con gran potencia', 20, 1.15, 0.20, 1.70),
('Martillo de Titanes', 'Golpes devastadores.', 40, 1.25, 0.10, 2.00),
('Tridente Abisal', 'Domina los océanos.', 30, 1.10, 0.12, 1.80),
('Bastón de Raíces', 'Energía natural.', 18, 1.20, 0.18, 1.60),
('Hacha de Evaluación', 'El hacha caera sobre ti si osas equivocarte', 15, 1.30, 0.25, 1.90),
('Rayban', 'Para que yo pueda lucir, tu vas a sufrir', 20, 1.4, 0.14, 1.67),
('Bastón Glacial', 'Congela el entorno.', 22, 1.10, 0.16, 1.75),
('Karambit de Viento', 'Cuchillo giratorio con la fuerza de los espiritus de Viento', 17, 1.25, 0.22, 1.80),
('Ballesta Arcana', 'Proyectiles precisos.', 28, 1.15, 0.19, 1.70),
('M4A4 Silenciada', 'Con tres cargadores extra', 28, 1.15, 0.19, 1.70),
('Guadaña Infernal', 'Fuego del inframundo.', 35, 1.20, 0.14, 1.85);


/*Esta tabla se creo con el proposito de hacer el juego jugable, con el poder del ataque ya calculado para no tener que estar calculandolo todo el
rato cada vez quelo quieras usar

Los datos se insertan desde java, en el archivo sql solo encontrarás el CREATE TABLE de la tabla, no el INSERT INTO*/
CREATE TABLE daño_calculado(
	id_ataque INT NOT NULL,
    daño_basico DECIMAL(10,2),
    FOREIGN KEY (id_ataque) REFERENCES ataques(id_ataque) ON DELETE CASCADE
);

/*Hemos creado esta tabla relacional porque necesitabamos que un mismo personaje tuviera varias armas, aunque al principio decidieramos lo contrario,
cambios de opinion, entonces esta tabla es necesaria para que un mismo personaje pueda tener varias armas, que no va a ser el caso, o para que un arma
pueda ser asignada a varios personajes, que es lo que buscamos nosotros cuando añadamos un nuevo personaje.

Tambien es necesario poner dentro de PRIMARY KEY() las dos variables para que no se repitan los datos por ejemplo (1,1) en la tabla, y los valores sean unicos

Vale de hecho en verdad lo que hay que hacer es solo meter personaje como primary key, porque es la que queremos que no se repita, la que se repite es el arma
entonces no hace falta meter en primary key, porque si metes dos valores en primary key lo  unico que impides es que se repita el patron combinando los dos,
pero los dos se pueden usar muchas veces dentro de la base de datos

Conclusión: PRIMARY KEY(id_personaje): impides que se repita el personaje más veces en la tabla, y cada personaje tendrá un arma asignada
			PRIMARY KEY(id_arma): se repetirá solo una vez el id de cada arma, y tendrá un id_personaje asignado, podrá tener varios asignados
            PRIMARY KEY(id_personaje, id_arma): impides que se repita la combinación de (id_personaje, id_arma) pero no restringes el número de veces que se menciona
												ninguno de los IDs
                                                
No le ponemos NOT NULL a ninunga tabla relacional porque rompería la base de datos si queremos estar añadiendo nuevos datos constantemente ya que siempre que 
añadieras un dato, saldría error porque otro campo no podría ser nulo, y los campos que se rellenan automaticamente no serán nulos
---->
De hecho habría que añadir a las tablas que tengan dos primary keys (que hay que cambiar eso, porque no debería de haber en casi ninguna, ya que siempre hay un campo
donde no queremos que se repita una de las keys de referencia (excepto ataque_personaje), como por ejemplo, en ataque elemento, ataque no se repite) los datos
manualmente ya que no se van a llenar solos si tenemos dos primary keys, por lo que entiendo.*/
CREATE TABLE personaje_arma(
	id_personaje INT,
    id_arma INT,
    PRIMARY KEY(id_personaje),
    FOREIGN KEY (id_personaje) REFERENCES personajes(id_personaje) ON DELETE CASCADE,
    FOREIGN KEY (id_arma) REFERENCES armas(id_arma) ON DELETE CASCADE
);

INSERT INTO personaje_arma(id_personaje, id_arma) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10),
(11,11),
(12,12);

UPDATE personajes
SET descripcion='Ojalá os guste el trabajo'
WHERE nombre='Pablo';

UPDATE casas
SET descripcion='Gente de familia'
WHERE nombre='Lannister';

UPDATE personajes
SET nombre='Luis'
WHERE nombre='Aeris';

UPDATE personajes
SET descripcion='Ten piedad, no he tocado el scripting de Linux todavia'
WHERE nombre='Luis';



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
JOIN personaje_arma pa ON pa.id_personaje = p.id_personaje
JOIN armas ar ON ar.id_arma=pa.id_arma;
END //
DELIMITER ;

-- CALL daño_basico_tabla();




DELIMITER //

DROP PROCEDURE IF EXISTS daño_basico_insert //
CREATE PROCEDURE daño_basico_insert(IN p_id_ataque INT)
BEGIN
SELECT 
    -- daño base con personaje y arma
    (a.daño_base * p.multiplicador_ataque * ar.multiplicador_daño + ar.daño_extra) AS daño_basico
    
FROM ataques a
JOIN ataque_personaje ap ON a.id_ataque = ap.id_ataque
JOIN personajes p ON p.id_personaje = ap.id_personaje
JOIN personaje_arma pa ON pa.id_personaje = p.id_personaje
JOIN armas ar ON ar.id_arma=pa.id_arma
WHERE p_id_ataque = a.id_ataque;
END //
DELIMITER ;

-- CALL daño_basico_insert(30);






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
JOIN personaje_arma pa ON pa.id_personaje = p.id_personaje
JOIN armas ar ON ar.id_arma=pa.id_arma
WHERE a.nombre=p_nombre;

RETURN calculo;
END //
DELIMITER ;

-- SELECT daño_basico_especifico('Eco Nulo') AS daño_basico;

-- para el buscador general, crear una funcion que devuelva los nombres de los ataques concatenados
-- funcion que devuelve los nombres de los ataques concatenados de un personaje
-- la verdad que no me acuerdo para que la necesitaba sinceramente aaaaa era para mostrar los nombres si buscaba por personaje, ya recuerdo
-- pero no lo voy a usar creo porque solo voy a seleccionar las opciones con el select de cada tabla y a chuparla, aunque puedo poner que si es personaje llame
-- a esta funcion solo que es un por culo impresionante tener que llamarla porque necesitaria crear otro metodo, creo?
DELIMITER //
DROP FUNCTION IF EXISTS nombres_concatenados //
CREATE FUNCTION nombres_concatenados(p_nombre_personaje TEXT)
RETURNS TEXT
BEGIN
	DECLARE v_ataque TEXT;
    DECLARE fin_cursor INT DEFAULT 0;
    DECLARE ataques_concat TEXT DEFAULT '';
    DECLARE cur CURSOR FOR
		SELECT a.nombre FROM ataques a
        JOIN ataque_personaje ap ON a.id_ataque=ap.id_ataque
        JOIN personajes p ON p.id_personaje=ap.id_personaje
        WHERE p.nombre=p_nombre_personaje;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin_cursor=1;
    OPEN cur;
    loop_cursor:LOOP
    FETCH cur INTO v_ataque;
        
    IF fin_cursor=1 THEN 
		LEAVE loop_cursor;
    END IF;
    
        SET ataques_concat=CONCAT(ataques_concat,', ',v_ataque);
    
    END LOOP loop_cursor;
    
    CLOSE cur;
    
	RETURN SUBSTRING(ataques_concat,3);
END //
DELIMITER ;