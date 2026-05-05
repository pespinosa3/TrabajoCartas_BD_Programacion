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
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void daño_basico(Connection conexion) {
		
		String query = "SELECT COUNT(id_ataque) FROM ataques; ";
		int numero=0;
		//int contador=0;
		
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
		
		
		
		int id_ataque=0;
        double danio=0;
		do {
			id_ataque+=1;
			/*query = "CALL daño_basico_insert(?)";
			
			id_ataque+=1;
	        try (CallableStatement cs = conexion.prepareCall(query)) {
	            
	            cs.setInt(1, id_ataque);
	            
	            // 3. Ejecutamos como query y guardamos el resultado en un ResultSet
	            try (ResultSet rs = cs.executeQuery()) {
	                
	            	
	                // 4. Recorremos el ResultSet como si fuera un Statement normal aunque sea un procedimiento
	                while (rs.next()) {
	                    danio = rs.getDouble(1);
	                    
	                    
	                    
	                    System.out.println("Daño básico: "+danio);
	                }
	                
	            }
	        } catch (SQLException e) {
	            System.err.println("Error: " + e.getMessage());
	        }*/
	        
	        danio=llamarProcedimiento(conexion,id_ataque);
			
			
			//voy ahora a insertar el dato que habia comprobado que no existia todavia con la query de mostrarCLientes
			query = "INSERT INTO daño_calculado (id_ataque, daño_basico) VALUES (?,?)";
			
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
				//comprobar el error con un sysout del stacktrace
				//molaria hacer un custom error con el signal de mysql y que lo printeara el stacktrace
				e.printStackTrace();
				System.out.println("Error en la query...");
			}
		}while(numero!=id_ataque);
	}
	
	
	
	
	
	public static void ñ(Connection conexion) {
		
		//voy ahora a insertar el dato que habia comprobado que no existia todavia con la query de mostrarCLientes
		String query = "DELETE FROM daño_calculado";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			//executeUpdate devuelve un 1 si se inserta bien o un 0 si no devuelve nada
			int resultado = ps.executeUpdate();
			
			
			if (resultado == 0) {
				
				System.out.println("NO se ha eliminado el dato");
			}
			
			
		} catch (SQLException e) {
			System.out.println("No existe ese/esos dato/s para borrar de la base de datos");
			e.getMessage();
			
		}
		
	}
}
