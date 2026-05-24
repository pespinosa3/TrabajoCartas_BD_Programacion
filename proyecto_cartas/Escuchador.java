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
    private VMostrar vm;
    private VEliminar ve;
    private VBorrar vb;
    private VBuscador vbu;
    
    // El constructor ahora recibe las tres ventanas
    public Escuchador(Menu v, VentanaCreacion vc, VentanaAtaques va, VentanaPersonajes vp, VMostrar vm, VEliminar ve , Connection conexion, 
    		VBorrar vb, VBuscador vbu) {
        this.v = v;
        this.vc = vc;
        this.va = va;
        this.conexionActiva = conexion;
        this.vp=vp;
        this.vm=vm;
        this.ve=ve;
        this.vb=vb;
        this.vbu=vbu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String comando = e.getActionCommand();
        
        switch(comando) {
            case "Nueva Partida":
                System.out.println("Nueva Partida iniciada");
                break;
                
            case "Mostrar Cartas":
                System.out.println("Entramos a la biblioteca de la sabiduria, donde se guarda toda la informacion del reino");
                this.vm.setVisible(true);
                this.v.setVisible(false);
                break;
                
            case "Añadir": // Del Menú a VentanaCreacion
                System.out.println("Elegiste la opcion de añadir a la base de datos");
                this.v.setVisible(false);
                this.vc.setVisible(true);
                break;
                
            case "ATRAS":
            	this.vc.setVisible(false);
            	this.vm.setVisible(false);
            	this.ve.setVisible(false);
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
                System.out.println("Elegiste la opcion de cambiar o borrar un dato");
                this.v.setVisible(false);
                this.ve.setVisible(true);
                break;
                
            case "SALIR":
                System.out.println("Saliendo del juego...");
                //esto va a hacer que en el main se cumpa la condicion y se pueda cerrar la base de datos
                Principal.corriendo(false);
                System.out.println("El juego ya no está corriendo.");
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
            case "atras(eliminar)": // Volver de VentanaBorrarDato a VEliminar
                this.vb.setVisible(false);
                this.ve.setVisible(true);
                break;
            case "modificar": 
                System.out.println("Aqui habria para poder modificar un dato");
                break;
            case "eliminar":
                this.ve.setVisible(false); 
                this.vb.setVisible(true);  // CORRECTO: Debe abrir VBorrar
                break;
            case "atras(borrarDato)": // Volver de VentanaBorrarDato a VEliminar
                this.vb.setVisible(false);
                this.ve.setVisible(true);
                break;
            case "Eliminar Registro": // El botón "ELIMINAR" dentro de la ventana de borrado
                System.out.println("Intentando eliminar un registro...");
                this.vb.ejecutarBorrado();
                break;
                
            case "buscar":
            	this.vbu.setVisible(true);
                this.vm.setVisible(false);
            	break;
            case "Ejecutar Busqueda": 
                System.out.println("Buscando...");
                this.vbu.ejecutarBusqueda();
                break;
            case "atras_buscador": // Volver de VentanaBorrarDato a VEliminar
                this.vbu.setVisible(false);
                this.vm.setVisible(true);
                break;
                
        }
    }
}