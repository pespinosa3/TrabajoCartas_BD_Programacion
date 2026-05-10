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
	
	
	
	
	
	
	public static void insertarAtaque(Connection conexion) {
		
		//voy ahora a insertar el dato que habia comprobado que no existia todavia con la query de mostrarCLientes
		String query = "INSERT INTO ataques (nombre, descripcion, potencia, daño_base, coste_mana) VALUES (?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			System.out.println("¿Que nombre quieres qe tenga?");
			ps.setString(2, pedirString(50));
			
			System.out.println("Descripcion de tu personaje: ");
			ps.setString(3, pedirString(300));
			
			
			String potencia=null;
			Scanner sc = new Scanner(System.in);
			boolean condicion = false;
			
			do {
				System.out.println("Elige la potencia del ataque (ligero / normal /potente): ");
				
				potencia=sc.nextLine();
				if(potencia.equalsIgnoreCase("ligero") || potencia.equalsIgnoreCase("normal") || potencia.equalsIgnoreCase("potente")) {
					condicion=true;
				}
			}while(!condicion);
			
			ps.setString(4, potencia);
			
			System.out.println("¿Cuánto daño quieres que haga el ataque de base? (introduce un numero)");
			ps.setInt(5, pedirInt());
			
			System.out.println("¿Cuánto maná va a consumir tu ataque? ");
			ps.setInt(6, pedirInt());
			
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
}
