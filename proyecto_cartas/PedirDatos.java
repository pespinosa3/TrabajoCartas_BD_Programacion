package proyecto_cartas;
import java.util.InputMismatchException;
import java.util.Scanner;


public class PedirDatos {
	public static String pedirString() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
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
	
}
