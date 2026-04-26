package proyecto_cartas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    
	
	public Connection mySQLConnect() {
        
		String base_datos= "juego_cartas";
		
    	// Datos de conexión
        String url = "jdbc:mysql://localhost:3306/"+base_datos; // url bbdd
        String usuario = "root"; // Cambia por tu usuario de MySQL
        String password = "12345"; // Cambia por tu contraseña

        Connection conexion = null;
        
        try {
            // Cargar el driver de MySQL (opcional para versiones recientes de Java)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer la conexión
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión exitosa a la base de datos: "+base_datos+"\n");
            
            // Cerrar la conexión
            //conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de MySQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
    
        return conexion;
	}
}
