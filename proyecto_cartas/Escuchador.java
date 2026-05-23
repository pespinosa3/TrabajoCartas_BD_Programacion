package proyecto_cartas;

import java.awt.event.*;
import javax.swing.JButton;

public class Escuchador implements ActionListener {

    private Menu v;
    private VentanaCreacion vc;
    
    public Escuchador(Menu v, VentanaCreacion vc) {
        super();
        this.v = v;
        this.vc=vc;
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
                
            case "Añadir":
                System.out.println("Abriendo menú de opciones...");
                
                this.v.setVisible(false);
                this.vc.setVisible(true);
                
                break;
                
            case "Cambiar / Borrar":
                System.out.println("Buscando servidores...");
                
                break;
                
            case "SALIR":
                System.out.println("Saliendo del juego. ¡Hasta pronto!");
                System.exit(0); 
                break;
                
            case "ATRAS":
            	this.vc.setVisible(false);
            	this.v.setVisible(true);
            	break;
        }
        
        // Comprobación segura por si añades botones con nombre más adelante
        JButton boton = (JButton) e.getSource();
        if (boton.getName() != null && boton.getName().equalsIgnoreCase("system exit")) {
            System.out.println("Cerrando la aplicación...");
            System.exit(0);
        }
    }
}
