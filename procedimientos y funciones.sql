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

CALL daño_basico_tabla();




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

CALL daño_basico_insert(30);






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

SELECT daño_basico_especifico('Eco Nulo') AS daño_basico;

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

SELECT nombres_concatenados('Pablo') AS nombre_ataques_concat;
/*-- daño crítico
    (a.daño_base * p.multiplicador_ataque * ar.multiplicador_daño + ar.daño_extra) * ar.multiplicador_critico AS daño_critico,
    
    ar.prob_critico*/


-- selecciona la probabilidad de critico de un ataque
SELECT nombre_arma, 100*(prob_critico) AS porcentaje_critico
FROM armas
WHERE prob_critico > (
    SELECT AVG(prob_critico)
    FROM armas
);

-- METODO DE JAVA QUE RECORRA UN FOREACH DE LOS IDS ATAQUE, PARA ESO NECESITAMOS UN CURSOR QUE RECORRA UNO A UNO LOS QUE HAY HE IR CONTAN
-- es useless porque se puede hacer con un COUNT()

SELECT * FROM ataques a JOIN ataque_personaje ap
ON ap.id_ataque=a.id_ataque
JOIN personajes p ON p.id_personaje = ap.id_personaje
WHERE a.nombre LIKE "%Solar%";

SELECT * FROM ataques
WHERE nombre LIKE "%Solar%";

SELECT * from ataques WHERE nombre="fe";
SELECT * from ataque_elemento;
SELECT * from ataques;

DELETE FROM ataques WHERE nombre="aaaa";


SELECT MAX(id_ataque) FROM ataques;

SELECT * FROM ataques where id_ataque IN (SELECT MAX(id_ataque) FROM ataques);


-- haz una FUNCION PARA que te devuelva el daño critico PORQUE ASI LO PUEDES AÑADIR DIRECTAMENTE A LA BASE DE DATOS CON UN SELECT QUE ERA LO QE TU QUERIAS
-- por parametro el id del ataque y te devuelve el daño critico calculado


DELIMITER //
	DROP PROCEDURE IF EXISTS buscador //
    CREATE PROCEDURE buscador(IN p_tabla TEXT, IN p_nombre TEXT)
    BEGIN
    DECLARE contenedor TEXT;
    DECLARE fin BOOLEAN DEFAULT true;
    
    DECLARE fran CURSOR FOR
		SELECT * FROM p_tabla
        WHERE nombre LIKE CONCAT("%",p_nombre,"%");
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin=false;
    
    OPEN fran;
    loop_cursor:LOOP
    FETCH fran INTO contenedor;
        
    IF fin=false THEN 
		LEAVE loop_cursor;
    END IF;
    
    END LOOP loop_cursor;
    
    CLOSE fran;
    
    SELECT contenedor;
    END //
DELIMITER ;

CALL buscador('personajes','Pablo');

SELECT * FROM personajes;

SELECT * FROM ataques;

-- ALTER TABLE