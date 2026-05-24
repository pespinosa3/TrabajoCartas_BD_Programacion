
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
		
		//Consultas.borrarTabla(conexion,"daño_calculado");
		
		//PedirDatos.insertarAtaque(conexion);
		
		//Consultas.buscador(conexion);
		
		//PedirDatos.insertarPersonaje(conexion);
		
		//Consultas.daño_basico(conexion);
		
		//VentanaCreacion vc = new VentanaCreacion("/img/gemini cartas imagenes/fondo princiapal.png");
		
		Consultas.borrarDato(conexion, "ataques", "id", "37");
		
		// 2. CREAMOS TODAS LAS VENTANAS (Nacen invisibles por defecto)
		//creamos un objeto de todas las ventanas, que empezaran en invisible
		Menu v = new Menu();
		VentanaCreacion vc = new VentanaCreacion();
		VentanaAtaques va = new VentanaAtaques(conexion);
		VentanaPersonajes vp=new VentanaPersonajes(conexion);
		VMostrar vm=new VMostrar();
		VEliminar ve=new VEliminar();
		VBorrar vb=new VBorrar(conexion,"a");
		
		// 3. CREAMOS EL ÚNICO ESCUCHADOR (Le pasamos las 3 ventanas para que las controle)
		//creamos un solo escuchador general para todas las ventanas
		Escuchador esc = new Escuchador(v, vc, va, vp, vm, ve, conexion, vb);
		
		// 4. REPARTIMOS EL ESCUCHADOR (Le decimos a cada ventana: "Este es el tío que vigila tus botones")
		//asignamos el escuchador a todas las ventanas
		v.asignarEscuchador(esc);
		vc.asignarEscuchador(esc);
		va.asignarEscuchador(esc);
		vp.asignarEscuchador(esc);
		vm.asignarEscuchador(esc);
		ve.asignarEscuchador(esc);
		vb.asignarEscuchador(esc);
		
		// 5. ¡QUE EMPIECE EL JUEGO! Solo hacemos visible el menú principal
		//hacemos visible el menu para que empiece la interfaz
		v.setVisible(true);
		
		
		System.out.println("\nTerminando conexión a la base de datos...");
		
		//comporbacion de si el juego sigue corriendo para poder cerrar la conexion a la base de datos
		if(corriendo(false)) {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//Este metodo lo he tenido que crear para que yo elija cuando se cierra la aplicacion, porque sino se cerraba antes de tiempo ya que entraba al try
	//catch del cierre de conexion despues de correr las ventanas porque no habia nadie vigilando que la aplicacion seguia corriendo
	public static boolean corriendo(boolean corriendo) {
		return corriendo;
    }
}