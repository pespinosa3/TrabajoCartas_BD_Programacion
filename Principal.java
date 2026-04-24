
package proyecto_cartas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Principal {
	
	public static void mostrarClientes(Connection conexion) {
		
		String query = "SELECT nombre, potencia, daño_base FROM ataques WHERE nombre='Eco Nulo'; ";
		
		try {
			Statement comando = conexion.createStatement();

			ResultSet resultado = comando.executeQuery(query);
			
			/* Se imprimen los registros que estén guardados en la base de datos */
			while (resultado.next()) {
				System.out.println("Nombre: "+resultado.getString(1)
						+"\nPotencia: "+resultado.getString(2)
						+"\nDaño base: "+resultado.getDouble(3));

				System.out.println("------------------------------------------");
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] MySQLConnection) {
		
		MySQLConnection db = new MySQLConnection();
		Connection conexion = db.mySQLConnect();

		mostrarClientes(conexion);
		
		System.out.println("Fin - Cerramos conexión");
		
		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}