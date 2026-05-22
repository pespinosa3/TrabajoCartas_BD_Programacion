package proyecto_cartas;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.*;

import javax.swing.JButton;

public class Escuchador implements ActionListener{

	private Menu v;
	
	
	/**
	 * 
	 */
	public Escuchador(Menu v) {
		super();
		this.v=v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		
		switch(comando) {
		case "Nueva Partida":
            System.out.println("Iniciando una nueva campaña...");
            // Ejemplo: new VentanaJuego();
            // menuPrincipal.dispose(); // Cierra el menú principal
            break;
            
        case "Mostrar Cartas":
            System.out.println("Abriendo partidas guardadas...");
            break;
            
        case "Añadir/Borrar Personaje/Ataque":
            System.out.println("Abriendo menú de opciones...");
            break;
            
        case "Cambiar un Ataque o Personaje":
            System.out.println("Buscando servidores...");
            break;
            
        case "SALIR":
            System.out.println("Saliendo del juego. ¡Hasta pronto!");
            System.exit(0); // Cierra el programa
            break;
		}
		
		JButton boton = (JButton)e.getSource();
		
		if (boton.getName().equalsIgnoreCase("system exit")) {
			System.out.println("Cerrando la aplicación...");
	        //Esta linea cierra Java
	        System.exit(0);
		}
		
        
	}

}
