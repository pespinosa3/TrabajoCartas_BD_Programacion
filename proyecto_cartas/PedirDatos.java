package proyecto_cartas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class PedirDatos {
	
	
	
	public static String pedirString(int char_max) {
		
		Scanner sc = new Scanner(System.in);
		
		String texto=null;
		
		boolean condicion = false;
		
		do {
			texto=sc.nextLine();
			
			if (texto.length()>char_max) {
				System.out.println("No puedes introducir una linea de texto con mas de "+char_max+" caracteres");
			}
			else {
				condicion = true;
			}
		}while(!condicion);
		
		return texto;
	}
	
	
	
	public static int pedirInt() {
		boolean validar=false;
		int i=0;
		
		while(!validar) {
			try {
				Scanner sc = new Scanner(System.in);
				i=sc.nextInt();
				validar=true;
				
			}catch(InputMismatchException e) {
				System.out.println("Debes de insertar un numero entero");
			}
		}
		
		return i;
	}
	
	
	public static double pedirDouble() {
		boolean validar=false;
		double i=0;
		
		while(!validar) {
			try {
				Scanner sc = new Scanner(System.in);
				i=sc.nextDouble();
				validar=true;
				
			}catch(InputMismatchException e) {
				System.out.println("Debes de insertar un numero. Puede ser decimal");
			}
		}
		
		return i;
	}
	
	
	//HAZ CONSULTA GENERAL YA
	
	public static void insertarAtaqueElemento(Connection conexion) {
		
		String query = "INSERT INTO ataque_elemento (id_elemento, id_estado) VALUES (?,?) WHERE ";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			System.out.println("¿Que nombre quieres qe tenga?");
			ps.setString(1, pedirString(50));
			
			System.out.println("Descripcion de tu personaje: ");
			ps.setString(2, pedirString(300));
			
			
			String potencia=null;
			Scanner sc = new Scanner(System.in);
			boolean condicion = false;
			
			do {
				System.out.println("Elige la potencia del ataque (ligero / normal /potente): ");
				
				potencia=sc.nextLine();
				if(potencia.equalsIgnoreCase("ligero") || 
						potencia.equalsIgnoreCase("normal") || 
						potencia.equalsIgnoreCase("potente")) {
					condicion=true;
				}
			}while(!condicion);
			
			ps.setString(3, potencia);
			
			System.out.println("¿Cuánto daño quieres que haga el ataque de base? (introduce un numero)");
			ps.setInt(4, pedirInt());
			
			System.out.println("¿Cuánto maná va a consumir tu ataque? ");
			ps.setInt(5, pedirInt());
			
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
	
	
	public static void insertarAtaque(Connection conexion) {
		
		//voy ahora a insertar el dato que habia comprobado que no existia todavia con la query de mostrarCLientes
		String query = "INSERT INTO ataques (nombre, descripcion, potencia, daño_base, coste_mana) VALUES (?,?,?,?,?)";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			System.out.println("¿Que nombre quieres que tenga?");
			ps.setString(1, pedirString(50));
			
			System.out.println("Descripcion de tu personaje: ");
			ps.setString(2, pedirString(300));
			
			
			String potencia=null;
			Scanner sc = new Scanner(System.in);
			boolean condicion = false;
			
			do {
				System.out.println("Elige la potencia del ataque (ligero / normal /potente): ");
				
				potencia=sc.nextLine();
				if(potencia.equalsIgnoreCase("ligero") || 
						potencia.equalsIgnoreCase("normal") || 
						potencia.equalsIgnoreCase("potente")) {
					condicion=true;
				}
			}while(!condicion);
			
			ps.setString(3, potencia);
			
			System.out.println("¿Cuánto daño quieres que haga el ataque de base? (introduce un numero)");
			ps.setInt(4, pedirInt());
			
			System.out.println("¿Cuánto maná va a consumir tu ataque? ");
			ps.setInt(5, pedirInt());
			
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
		
		//HAZ UN MAX SOBRE LA TABLA ATAQUES PARA AVERIGUAR EL ID DEL ULTIMO ATAQUE INSERTADO Y ASI PODER INSERTARLO A LA TABLA ATAQUE_ELEMENTO
		
		
		
		/*PARA EL BUSCADOR GENERAL : SELECT * FROM tabla WHERE nombre = "lo que ha buscado" 
		 * o SELECT * FROM tabla WHERE nombre LIKE "%termino_de_busqueda%" 
		 * si devuelve el resultset 0, significa que no hay datos, y por lo tanto puedes pasar a la siguiente tabla, si entra a una tabla, se cumplirá
		 * una condicion que sea para checkear si ha entrado a algunas de las tablas, si no devuelve nada significa que la variable booleana será false
		 * y el termino de busqueda (el nombre) no se encuentra en la base de datos.
		 * Si lo quisieras hacer de descripciones tambien, se complicaria, porque harian falta otra consulta mas para cada tabla, O
		 * puedes hacer un cursor doble en el que recoge varibles de cada campo (nombre, descripcion) y luego revisa si está en cada tabla. El problema sería
		 * la consulta del cursor todo el rato, pero en verdad ibas a hacer lo mismo en java. o tambien, aun mejor, una variable global
		 * en mysql que vaya cambiando de dato conforme recorra una de las tablas, y asi se puede reutilizar el procedimiento del cursor, en el que se introduzca
		 * el nombre de la varible, ostia noooo, solo necesitas un parametro de entrada con el IN y le cambias el nombre  a la tabla conforme las recorras. Porque
		 * tablas nuevas no vas a crear.
		 * 
		 */
	}
	
	
	
	
	
}
