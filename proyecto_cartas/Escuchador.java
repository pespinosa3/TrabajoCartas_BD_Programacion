package proyecto_cartas;

import java.awt.event.*;
import java.sql.Connection;

import javax.swing.JButton;

public class Escuchador implements ActionListener {

    private Menu v;
    private VentanaCreacion vc;
    private VentanaAtaques va;
    private Connection conexionActiva;
    private VentanaPersonajes vp;
    
    // El constructor ahora recibe las tres ventanas
    public Escuchador(Menu v, VentanaCreacion vc, VentanaAtaques va, VentanaPersonajes vp , Connection conexion) {
        this.v = v;
        this.vc = vc;
        this.va = va;
        this.conexionActiva = conexion;
        this.vp=vp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String comando = e.getActionCommand();
        
        switch(comando) {
            case "Nueva Partida":
                System.out.println("Iniciando una nueva campaña...");
                break;
                
            case "Mostrar Cartas":
                System.out.println("Abriendo partidas guardadas...");
                break;
                
            case "Añadir": // Del Menú a VentanaCreacion
                System.out.println("Abriendo menú de opciones...");
                this.v.setVisible(false);
                this.vc.setVisible(true);
                break;
                
            case "ATRAS": // De VentanaCreacion al Menú
            	this.vc.setVisible(false);
            	this.v.setVisible(true);
            	break;

            case "ataque": // De VentanaCreacion a VentanaAtaques (Pon el actionCommand que tengas en el botón de Crear Ataque)
                this.vc.setVisible(false); // Ocultamos la de creación
                this.va.setVisible(true);  // Mostramos la de ataques
                break;
                
            case "Crear Ataque": // El botón de dentro de VentanaAtaques
                System.out.println("Guardando el ataque en la Base de Datos...");
                this.va.guardarAtaque(); 
                break;
                
            case "Cambiar / Borrar":
                System.out.println("Buscando servidores...");
                break;
                
            case "SALIR":
                System.out.println("Saliendo del juego. ¡Hasta pronto!");
                //esto va a hacer que en el main se cumpa la condicion y se pueda cerrar la base de datos
                Principal.corriendo(false);
                System.out.println("El juego ya no está corriendo");
                System.exit(0); 
                break;
            case "atras_crear":
            	System.out.println("Volviendo a la ventana del menu de creación");
            	this.va.setVisible(false);
            	this.vp.setVisible(false);
            	this.vc.setVisible(true);
            	break;
            case "Crear Personaje":
                System.out.println("Guardando el personaje en la Base de Datos...");
                this.vp.guardarPersonaje(); 
                break;
            case "personaje": 
                this.vc.setVisible(false); // Ocultamos la de creación
                this.vp.setVisible(true);  // Mostramos la de personajes
                break;
        }
    }
}