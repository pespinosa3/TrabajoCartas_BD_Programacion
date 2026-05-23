package proyecto_cartas;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
	
    public Menu() {
        // 1. EL FONDO 
        JLabel fondo = new JLabel(new ImageIcon(getClass().getResource("/img/fondo bueno.png")));
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK); 
        fondo.setLayout(new BorderLayout()); 

        // 2. PANEL DEL TÍTULO
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        panelTitulo.setOpaque(false); 
        
        ImageIcon iconoLogoOriginal = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/logo.png"));
        Image imgLogo = iconoLogoOriginal.getImage();
        Image imgLogoRedimensionada = imgLogo.getScaledInstance(600, 200, Image.SCALE_SMOOTH);
        JLabel labelTitulo = new JLabel(new ImageIcon(imgLogoRedimensionada));
        
        panelTitulo.add(labelTitulo);
        fondo.add(panelTitulo, BorderLayout.NORTH); 

        // 3. PANEL CONTENEDOR DE LAS OPCIONES
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); 
        panelCentro.setOpaque(false);
        
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1, 0, 12)); 
        panelMenu.setOpaque(false); 
        
        // 4. CREACIÓN DE BOTONES
        JButton btnNuevaCampana = crearBoton("/img/gemini cartas imagenes/flora.png");
        JButton btnCargar = crearBoton("/img/gemini cartas imagenes/gondalf.png");
        JButton btnOpciones = crearBoton("/img/gemini cartas imagenes/fran.png");
        JButton btnBorrar = crearBoton("/img/gemini cartas imagenes/menu de creacion.png");
        JButton btnSalir = crearBoton("/img/gemini cartas imagenes/terrax.png");

        btnNuevaCampana.setActionCommand("Nueva Partida");
        btnCargar.setActionCommand("Mostrar Cartas");
        btnOpciones.setActionCommand("Añadir");
        btnBorrar.setActionCommand("Cambiar / Borrar");
        btnSalir.setActionCommand("SALIR");

        // --- LA MAGIA ESTÁ AQUÍ ---
        // 1. Creamos la ventana de creación SIN mostrarla aún
        VentanaCreacion vc = new VentanaCreacion();
        
        // 2. Creamos EL ÚNICO escuchador y le pasamos el Menu (this) y la VentanaCreacion (vc)
        Escuchador miEscuchador = new Escuchador(this, vc);
        
        // 3. Añadimos el escuchador a los botones del menú
        btnNuevaCampana.addActionListener(miEscuchador);
        btnCargar.addActionListener(miEscuchador);
        btnOpciones.addActionListener(miEscuchador);
        btnBorrar.addActionListener(miEscuchador);
        btnSalir.addActionListener(miEscuchador);
        
        // 4. LE PASAMOS el escuchador a la ventana de creación para que lo usen sus botones
        vc.asignarEscuchador(miEscuchador);
        // ---------------------------

        panelMenu.add(btnNuevaCampana);
        panelMenu.add(btnCargar);
        panelMenu.add(btnOpciones);
        panelMenu.add(btnBorrar);
        panelMenu.add(btnSalir);
        
        panelCentro.add(panelMenu);
        fondo.add(panelCentro, BorderLayout.CENTER); 

        // 5. PANEL DE CRÉDITOS
        JPanel panelCreditos = new JPanel();
        panelCreditos.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        panelCreditos.setOpaque(false);
        
        JLabel labelCreditos = new JLabel("Créditos: Pablo Espinosa / Jaime Maestre");
        labelCreditos.setForeground(Color.LIGHT_GRAY);
        panelCreditos.add(labelCreditos);
        
        fondo.add(panelCreditos, BorderLayout.SOUTH); 

        // 6. CONFIGURACIÓN DEL MARCO
        this.setContentPane(fondo); 
        this.setSize(1920, 1080); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true); // El menú sí se hace visible al arrancar
    }

    public static void main(String[] args) {
        new Menu();
    }
    
    public JButton crearBoton(String rutaImagen) {
        JButton boton = new JButton();
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
        Image imagen = iconoOriginal.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(300, 100, java.awt.Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenRedimensionada));
        
        boton.setPreferredSize(new Dimension(300, 100));
        
        boton.setOpaque(true);
        boton.setBackground(Color.DARK_GRAY);
        boton.setBorderPainted(false); 
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
}