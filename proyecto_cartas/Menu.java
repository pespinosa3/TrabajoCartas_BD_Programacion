package proyecto_cartas;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
	
    private JButton btnNuevaPartida;
    private JButton btnMostrar;
    private JButton btnMenuCreacion;
    private JButton btnBorrar;
    private JButton btnSalir;

    public Menu() {
    	
    	Principal.corriendo(true);
    	
    	// EL FONDO
    	ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/img/fondo bueno.png"));
    	Image imgFondo = iconoFondo.getImage();
    	Image imgFondoRedimensionada = imgFondo.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
    	
        JLabel fondo = new JLabel(new ImageIcon(imgFondoRedimensionada));
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK); 
        fondo.setLayout(new BorderLayout()); 

        // PANEL DEL TÍTULO
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        panelTitulo.setOpaque(false); 
        
        ImageIcon iconoLogoOriginal = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/logo.png"));
        Image imgLogo = iconoLogoOriginal.getImage();
        Image imgLogoRedimensionada = imgLogo.getScaledInstance(600, 200, Image.SCALE_SMOOTH);
        JLabel labelTitulo = new JLabel(new ImageIcon(imgLogoRedimensionada));
        
        panelTitulo.add(labelTitulo);
        fondo.add(panelTitulo, BorderLayout.NORTH); 

        // PANEL CONTENEDOR DE LAS OPCIONES
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); 
        panelCentro.setOpaque(false);
        
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(4, 2, 20, 20)); 
        panelMenu.setOpaque(false); 
        
        // INICIALIZACIÓN DE LOS BOTONES
        btnNuevaPartida = crearBoton("/img/gemini cartas imagenes/nueva partida.png");
        btnMostrar = crearBoton("/img/gemini cartas imagenes/mostrar cartas.png");
        btnMenuCreacion = crearBoton("/img/gemini cartas imagenes/crear.png");
        btnBorrar = crearBoton("/img/gemini cartas imagenes/eliminar.png");
        btnSalir = crearBoton("/img/gemini cartas imagenes/salir 1.png");
        
        btnNuevaPartida.setActionCommand("Nueva Partida");
        btnMostrar.setActionCommand("Mostrar Cartas");
        btnMenuCreacion.setActionCommand("Añadir");
        btnBorrar.setActionCommand("Cambiar / Borrar");
        btnSalir.setActionCommand("SALIR");

        // ¡ATENCIÓN! AQUÍ YA NO CREAMOS EL ESCUCHADOR NI LA VENTANA CREACIÓN.
        // Solo añadimos los botones al panel.

        panelMenu.add(btnNuevaPartida);
        panelMenu.add(btnMostrar);
        panelMenu.add(btnMenuCreacion);
        panelMenu.add(btnBorrar);
        panelMenu.add(btnSalir);
        
        panelCentro.add(panelMenu);
        fondo.add(panelCentro, BorderLayout.CENTER);

        // PANEL DE CRÉDITOS
        JPanel panelCreditos = new JPanel();
        panelCreditos.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        panelCreditos.setOpaque(false);
        
        JLabel labelCreditos = new JLabel("Créditos: Pablo Espinosa / Jaime Maestre");
        labelCreditos.setForeground(Color.LIGHT_GRAY);
        panelCreditos.add(labelCreditos);
        
        fondo.add(panelCreditos, BorderLayout.SOUTH); 

        this.setContentPane(fondo); 
        this.setSize(1920, 1080); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Tampoco lo hacemos visible aquí. Lo hará Principal.
    }

    public void asignarEscuchador(Escuchador esc) {
        btnNuevaPartida.addActionListener(esc);
        btnMostrar.addActionListener(esc);
        btnMenuCreacion.addActionListener(esc);
        btnBorrar.addActionListener(esc);
        btnSalir.addActionListener(esc);
    }

    public JButton crearBoton(String rutaImagen) {
        JButton boton = new JButton();
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
        Image imagen = iconoOriginal.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(160, 160, java.awt.Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenRedimensionada));
        boton.setPreferredSize(new Dimension(160, 160));
        boton.setOpaque(true);
        boton.setBackground(Color.BLACK);
        //boton.setBorderPainted(false); 
        //boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
    
}