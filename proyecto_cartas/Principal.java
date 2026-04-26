
package proyecto_cartas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			
			
			//executeUpdate devuelve un 1 si se inserta bien o un 2 si...
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
	
	
	public static void conexion_bbdd() {
		
		
	}
	
	

	public static void main(String[] MySQLConnection) {
		
		MySQLConnection db = new MySQLConnection();
		Connection conexion = db.mySQLConnect();
		

		Consultas.ataques(conexion);
		insertarDatos(conexion);
		
		
		Consultas.ataques(conexion);
		//Connection conexion2 = db.mySQLConnect();
		
		eliminarDatos(conexion);
		
		
		System.out.println("\nTerminando conexión a la base de datos...");
		
		try {
			conexion.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
}