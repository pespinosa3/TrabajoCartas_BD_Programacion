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

DROP PROCEDURE IF EXISTS daño_basico_insert //
CREATE PROCEDURE daño_basico_insert(IN p_id_ataque INT)
BEGIN
SELECT 
    -- daño base con personaje y arma
    (a.daño_base * p.multiplicador_ataque * ar.multiplicador_daño + ar.daño_extra) AS daño_basico
    
FROM ataques a
JOIN ataque_personaje ap ON a.id_ataque = ap.id_ataque
JOIN personajes p ON p.id_personaje = ap.id_personaje
JOIN armas ar ON p.id_personaje = ar.id_personaje
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
JOIN armas ar ON p.id_personaje = ar.id_personaje
WHERE a.nombre=p_nombre;

RETURN calculo;
END //
DELIMITER ;

SELECT daño_basico_especifico('Eco Nulo') AS daño_basico;

-- para el buscador general, crear una funcion que devuelva los nombres de los ataques concatenados
-- funcion que devuelve los nombres de los ataques concatenados de un personaje
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

SELECT nombres_concatenados('Pablo');
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