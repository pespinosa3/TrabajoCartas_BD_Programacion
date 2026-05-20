package proyecto_cartas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class PedirDatos {
	
	
	/**
	 * Variable que te pide un string por parametro y te lo devuelve si es valido y entra dentro del rango aceptado de caracteres
	 * @param char_max - numero maximo de caracteres que acepta el VARCHAR en mysql de la variable que quieras introducir
	 * @return - el texto que tu le has metido por scanner
	 */
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
	
	
	/**
	 * variable que te pide un numero por parametro, no se sale hasta que no le insertes un numero
	 * entero valido
	 * @return - te lo devuelve si es valido
	 */
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
		
	}
		
		
		public static void insertarPersonaje(Connection conexion) {
			
			//8 interrogaciones, id_comandante va a ser obligatorio aqui, que sino habria que crear una maldita casa entera
			// id_elemento 2 deberia ser null tambien para facilitarnos la vida
			String query = "INSERT INTO personajes (nombre, descripcion, vida, multiplicador_ataque, id_casa, id_comandante,"
					+ "id_elemento_1) VALUES (?,?,?,?,?,?,?)";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(query);
				
				System.out.println("¿Que nombre quieres que tenga?");
				ps.setString(1, pedirString(50));
				
				System.out.println("Descripcion de tu personaje: ");
				ps.setString(2, pedirString(300));
				
				int vida=0;
				do {
					System.out.println("Cuanta vida va a tener? (Entre 750 y 1350)");
					
					vida=pedirInt();
					
				}while(vida<750 || vida>1350);
				
				ps.setInt(3, vida);
				
				
				double multiplicador=0;
				do {
					System.out.println("¿Cuánto quieres que tenga de multiplicador de daño? (introduce un numero entre 0.7 y 1.2)");
					multiplicador=pedirDouble();
					
				}while(multiplicador<0.7 || multiplicador>1.2);
				
				ps.setDouble(4, multiplicador);
				
				
				int id_casa=0;
				do {
					System.out.println("Las casas son: Legión Administrativa, Orden del Caos, Lannister y Marea Eterna.");
					System.out.println("¿Cual será el ID de tu casa? (del 1 al 4)");
					id_casa=pedirInt();
					
				}while(id_casa>4 || id_casa<1);
				
				ps.setInt(5, id_casa);
				
				
				switch(id_casa) {
				case 1:
					ps.setInt(6, 11);
					break;
				case 2:
					ps.setInt(6, 12);
					break;
				case 3:
					ps.setInt(6, 2);
					break;
				case 4:
					ps.setInt(6, 4);
					break;
				}
				
				/*if(id_casa==1){
					ps.setInt(6, 11);
				}else if(id_casa==2) {
					ps.setInt(6, 12);
				}else if(id_casa==3) {
					ps.setInt(6, 2);
				}else if(id_casa==4) {
					ps.setInt(6, 4);
				}*/
				
				
				int id_elemento=0;
				do {
					System.out.println("Los elementos son: Vacío, Electricidad, Solar, Planta, Mineral, Líquido, Admin.");
					System.out.println("¿Cual será el ID de tu elemento? (del 1 al 7)");
					id_elemento=pedirInt();
					
				}while(id_elemento>7 || id_elemento<1);
				
				ps.setInt(7, id_elemento);
				
				
				
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
