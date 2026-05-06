package proyecto_cartas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Consultas {
	
	//me gustaria hacer uno que recibiera la query por parametro tambien, o que te pidiera el nombre de las tablas
	//de las que quieres buscar un dato, y luego el nombre del dato especifico, y hiciera un select y un where o algo asi
	
	//tambien me deberia de crear otra clase para todos los metodos de consultas, insercion y eliminacion de datos
	
	/*podrias tambien, crear una clase con atributos estandard y asi solo tener que insertarlos dentro de los values del
	preparedStatement en las interrogaciones, en lo de setTipodedato*/
	//o mejor, que el usuario por parametro busque los datos que quiere encontrar, como un buscador, como dijo fran

	//buscador general, en el que introduzcas un dato y te lo busque en toda la base de datos, implementar subconsultas y funcion
	//buscador de ataques, de personajes, armas, etc
	
	
	//y tambien insertar un personaje que el usuario quiera introducir con los datos que quiera. con su arma y su invocacion
	public static void ataques(Connection conexion) {
		
		String query = "SELECT nombre, potencia, daño_base FROM ataques WHERE nombre='Paliza'; ";
		
		try {
			
			Statement comando = conexion.createStatement();

			ResultSet resultado = comando.executeQuery(query);
			
			//NEXT(): true if the new current row is valid; false if there are no more rows
			
			/*If an input stream is open for the current row, a call to the method next will implicitly close it.
			A ResultSet object's warning chain is cleared when a new row is read.*/
			
			if (!resultado.next()) {
				System.out.println("La consulta:\n"+query+"\nno se encuentra en la base de datos actualmente");
			}
			else {
				/*ESTE WHILE NO CUMPLE LA CONDICION!! PORQUE??? Pues porque cuando tu llamas al next, el cursor ya está apuntando a esa
				 * fila despues de haberlo llamado con la condicion del if,
				 * entonces si lo vuelves a llamar, estás pasando a apuntar a la siguiente fila con el cursor, entonces
				 * si quieres seguir evaluando la primera fila, deberias de hacer un do while, y asi ya puedes pasar
				 * despues a la siguiente fila*/
				/*while (resultado.next()) {
					System.out.println("Entra al while");
					
					System.out.println("Nombre: "+resultado.getString(1)
							+"\nPotencia: "+resultado.getString(2)
							+"\nDaño base: "+resultado.getDouble(3));

					System.out.println("------------------------------------------");
				}*/
				
				do{
					
					System.out.println("Nombre: "+resultado.getString(1)
							+"\nPotencia: "+resultado.getString(2)
							+"\nDaño base: "+resultado.getDouble(3));

					System.out.println("------------------------------------------");
				}while(resultado.next());
			}
			
			/*Al llamar a next(), el cursor se mueve a la siguiente fila.

			Si devuelve true, significa que hay datos y ya estás posicionado en la primera fila.

			Si devuelve false, significa que no hay más filas. Si esto pasa en la primera llamada, la consulta está vacía.*/
			
			//esto lo estoy usando para demostrar que el dato todavia no existe
			//o mejor dicho, por si el dato no existe
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//IMPORTANTE
	//necesito entender el try que pone una condicion o algo asi en brackets
	public static double llamarProcedimiento(Connection conexion, int id_ataque) {

            
        // 1. La llamada solo tiene un interrogante porque solo hay un parámetro IN
        String query = "CALL daño_basico_insert(?)";
        double danio=0;
        boolean validar=false;
        do {
        	try (CallableStatement cs = conexion.prepareCall(query)) {
                
                cs.setInt(1, id_ataque);
                
                // 3. Ejecutamos como query y guardamos el resultado en un ResultSet
                try (ResultSet rs = cs.executeQuery()) {
                    
                    // 4. Recorremos el ResultSet como si fuera un Statement normal aunque sea un procedimiento
                    while (rs.next()) {
                        danio = rs.getDouble(1);
                        validar=true;
                        System.out.println("Daño básico: "+danio);
                    }
                    
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }while(!validar);
        
        return danio;
	}
	
	
	
	
	//metodo para hacer un COUNT() de los objetos de una tabla (reutilizable)
	
	/**
	 * metodo para hacer un COUNT() de los objetos de una tabla
	 * @param conexion - es obligatorio en todos los metodos para establecer una conexion con la base de datos
	 * @param columna - la columna de datos de la que quieres que se cuente el numero de datos que hay dentro
	 * @param tabla - la tabla a la que pertenece esa columna
	 * @return
	 */
	public static int count(Connection conexion, String columna, String tabla) {
		
		String query = "SELECT COUNT("+columna+") FROM "+tabla;
		int numero=0;
		
		try {
			
			Statement comando = conexion.createStatement();

			ResultSet resultado = comando.executeQuery(query);
			
			if (!resultado.next()) {
				System.out.println("La consulta:\n"+query+"\nno se encuentra en la base de datos actualmente");
			}
			else {
				
				do{
					numero=resultado.getInt(1);
					System.out.println("Numero de ataques: "+resultado.getInt(1));

					System.out.println("------------------------------------------");
				}while(resultado.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(numero==0) {
			System.out.println("No hay datos en la consulta de la tabla que has seleccionado");
		}
		
		return numero;
	}
	
	
	
	
	
	/**
	 * Hace un count de la cantidad de ids que hay actualmente en ataque.
	 * 
	 * Luego por el numero de ataques que haya, va llamando al procedimiento daño_basico_insert que fue creado
	 * especificamente para devolver el daño calculado de un ataque en concreto.
	 * 
	 * Y por ultimo añade el daño_basico calculado con su id_ataque correspondiente
	 * @param conexion
	 */
	
	public static void daño_basico(Connection conexion) {
		
		int numero_ids=0;
		
		//recojo aqui cuantos ataques hay en la tabla ataque
		numero_ids = count(conexion,"id_ataque","ataques");
		
		int id_ataque=0;
        double danio=0;
		do {
			id_ataque+=1;
	        
			//recojo aqui cuanto daño hace cada ataque individualmente
	        danio=llamarProcedimiento(conexion,id_ataque);
			
			
			String query = "INSERT INTO daño_calculado (id_ataque, daño_basico) VALUES (?,?)";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(query);
				ps.setInt(1, id_ataque);
				ps.setDouble(2, danio);
				
				//executeUpdate devuelve un 1 si se inserta bien o un 2 si...
				int resultado = ps.executeUpdate();
				
				
				if (resultado > 0) {
					System.out.println("Dato insertado con éxito!!");
				} else {
					System.out.println("NO se ha insertado el dato");
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error en la consulta: "+query);
				break;
			}
			
		}while(numero_ids>id_ataque);
	}
	
	/*
	SOLUCION:
	vale ya lo entiendo, solo tienes que hacer el loop en tu cabeza, imagina que la condicion es numero_ids>=id_ataque, y id_ataque
	es 35, numero de ids es mayor o igual a 35? Si (numero_ids es 36), entonces entra en el loop de nuevo, suma 1, numero de ids es mayor o igual a 36? Si
	y vuelve a loopear (AHI ESTA EL PROBLEMA), numero de ids es mayor o igual a 37, no, y ahora sale, el problema es que no me saltaba nada de error
	
	Sin embargo, si pones numero_ids>id_ataque y planteamos la misma situacion, numero de ids (36) es mayor que 35? Si, loopea, suma 1,
	numero de ids es mayor que 36? NO. 36 no es mayor que 36. Y ahi se acaba, y has insertado todos los datos correctamente,
	de la otra manera se quedaba en ejecucion, no se porqué (solucionalo)*/
	
	
	
	
	/***
	 * metodo para borrar TODOS LOS DATOS de una tabla
	 * @param conexion
	 * @param tabla - tabla de la que quieres que se eliminen todos los datos
	 */
	
	public static void borrarTabla(Connection conexion, String tabla) {
		
		//voy ahora a insertar el dato que habia comprobado que no existia todavia con la query de mostrarCLientes
		String query = "DELETE FROM "+tabla;
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			//executeUpdate devuelve un 1 si se inserta bien o un 0 si no devuelve nada
			int resultado = ps.executeUpdate();
			
			
			if (resultado == 0) {
				
				System.out.println("NO se ha eliminado el dato");
			}
			
			
		} catch (SQLException e) {
			System.out.println("No existen esos datos para borrar de la base de datos");
			e.getMessage();
			
		}
		
	}
}
