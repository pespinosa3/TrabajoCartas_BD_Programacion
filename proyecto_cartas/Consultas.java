package proyecto_cartas;

import java.sql.Connection;
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
}
