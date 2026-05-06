
package proyecto_cartas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class Principal {
	
	
	
	public static void insertarDatos(Connection conexion) {
		
		//voy ahora a insertar el dato que habia comprobado que no existia todavia con la query de mostrarCLientes
		String query = "INSERT INTO ataques (id_ataque, nombre, descripcion, potencia, daño_base, coste_mana) VALUES (?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, 37);
			ps.setString(2, "Paliza");
			ps.setString(3, "Llama a todos sus primos para ayudarle con el ataque");
			ps.setString(4, "potente");
			ps.setInt(5, 100);
			ps.setInt(6, 11);
			
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
		
	}
	
	
	public static void eliminarDatos(Connection conexion) {
		
		//voy ahora a insertar el dato que habia comprobado que no existia todavia con la query de mostrarCLientes
		String query = "DELETE FROM ataques WHERE nombre=?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, "Paliza");
			
			
			//executeUpdate devuelve un 1 si se inserta bien o un 0 si no devuelve nada
			int resultado = ps.executeUpdate();
			
			
			if (resultado > 0) {
				System.out.println("Dato:\n"+query.substring(0, query.length()-1)+"Paliza"
						+ "\nborrado con éxito!!");
			} else {
				System.out.println("NO se ha eliminado el dato");
			}
			
			
		} catch (SQLException e) {
			System.out.println("No existe ese/esos dato/s para borrar de la base de datos");
			e.printStackTrace();
			
		}
		
	}
	
	//PREPARE CALL BARRIO DEL CARMEN
	//PENDIENTE TERMINAR!!!!!!!!!
	public static void llamarProcedimiento(Connection conexion) {
		
		//voy ahora a insertar el dato que habia comprobado que no existia todavia con la query de mostrarCLientes
		String query = "CALL daño_basico_insert(?)";
		
		try (CallableStatement cs = conexion.prepareCall(query)) {
			//CallableStatement cs = conexion.prepareCall(query);
			
			cs.setInt(1, 33);
			
			//execute ejecuta el preparedStatement, en este caso CallableStatement
			//devuelve true si encuentra un resultSet (resultado) y false si no lo encuentra
			boolean resultado = cs.execute();
			
			double tumadre = cs.getDouble(1);
			
			System.out.println("Prueba: "+tumadre);
			
			if (resultado) {
				System.out.println("Resultado:\n");
			} else {
				
				System.out.println("NO hay mas datos que devolver");
			}
			
			
		} catch (SQLException e) {
			//comprobar el error con un sysout del stacktrace
			//molaria hacer un custom error con el signal de mysql y que lo printeara el stacktrace
			e.printStackTrace();
			System.out.println("Error en la query...");
		}
		
	}
	
	

	/*public static void procedimiento() {
	        
            
            
            String sql = "CALL ObtenerNombreEmpleado(?, ?)}";
            
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                
                // 2. Pasamos los parámetros de entrada (IN)
                // El primer "?" corresponde a p_id
                stmt.setInt(1, 105); 
                
                // 3. Registramos los parámetros de salida (OUT)
                // El segundo "?" corresponde a p_nombre. Debemos decirle a Java qué tipo de dato esperar.
                stmt.registerOutParameter(2, Types.VARCHAR);
                
                // 4. Ejecutamos el procedimiento
                stmt.execute();
                
                // 5. Recogemos el valor que devolvió el procedimiento
                String nombreDevuelto = stmt.getString(2);
                
                System.out.println("El empleado encontrado es: " + nombreDevuelto);
                
            }
            
        
	    }
	}*/
	
	
	
	
	public static void conexion_bbdd() {
		
		
	}
	
	
	//si te das cuenta, no es args, es MySQLConnection
	public static void main(String[] MySQLConnection) {
		
		MySQLConnection db = new MySQLConnection();
		Connection conexion = db.mySQLConnect();
		

		/*Consultas.ataques(conexion);
		insertarDatos(conexion);
		
		Consultas.ataques(conexion);
		
		Consultas.ataques(conexion);
		//Connection conexion2 = db.mySQLConnect();
		
		eliminarDatos(conexion);*/
		
		//llamarProcedimiento(conexion);
		
		//Consultas.llamarProcedimiento(conexion);
		
		Consultas.borrarTabla(conexion,"daño_calculado");
		
		Consultas.daño_basico(conexion);
		
		
		
		System.out.println("\nTerminando conexión a la base de datos...");
		
		try {
			conexion.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}