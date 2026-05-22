package proyecto_cartas;


import javax.swing.*;
import java.awt.*;

import java.awt.event.*;

public class Menu extends JFrame {

    public Menu(String p_fondo) {
        //setTitle("TOTAL WAR: PAIME");

        // 1. EL FONDO (Con fondo oscuro temporal)
        // Usamos un JLabel como contenedor principal.
        JLabel fondo = new JLabel(new ImageIcon(getClass().getResource(p_fondo)));
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK); // Fondos oscuros 
        fondo.setLayout(new BorderLayout()); // Dividimos la pantalla en zonas

        // 2. PANEL DEL TÍTULO (Arriba y centrado)
        // Aquí es donde luego insertarás la imagen con el nombre del juego
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        panelTitulo.setOpaque(false); // Transparente para que se vea el fondo
        
        // Texto temporal hasta que pongas la imagen
        JLabel labelTitulo = new JLabel(new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/logo.png")));
        //labelTitutlo.setSize();
        labelTitulo.setFont(new Font("Serif", Font.BOLD, 40));
        labelTitulo.setForeground(Color.WHITE);
        panelTitulo.add(labelTitulo);
        
        fondo.add(panelTitulo, BorderLayout.NORTH); // Lo anclamos arriba

        // 3. PANEL CONTENEDOR DE LAS OPCIONES (En el centro absoluto)
        // Usamos un FlowLayout para que el menú no se estire y conserve su centro
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 150)); // Margen superior de 150px
        panelCentro.setOpaque(false);
        
        
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1, 0, 20)); // 5 opciones, 1 columna
        panelMenu.setOpaque(false); 
        
        /*// 4. CREACIÓN Y AÑADIDO DE BOTONES CENTRADOS
        panelMenu.add(Boton("Nueva Partida"));
        panelMenu.add(Boton("Mostrar Cartas"));
        panelMenu.add(Boton("Añadir/Borrar Personaje/Ataque"));
        panelMenu.add(Boton("Cambiar un Ataque o Personaje"));
        */
        //ImageIcon imagenCierre = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/hydra.png"));
        
        // 4. CREACIÓN DE BOTONES
        JButton btnNuevaCampana = crearBoton("/img/gemini cartas imagenes/flora.png");
        JButton btnCargar = crearBoton("/img/gemini cartas imagenes/gondalf.png");
        JButton btnOpciones = crearBoton("/img/gemini cartas imagenes/fran.png");
        JButton btnMultijugador = crearBoton("/img/gemini cartas imagenes/santa claus.png");
        JButton btnSalir = crearBoton("/img/gemini cartas imagenes/terrax.png");

        // --- ASIGNAMOS LA "ETIQUETA SECRETA" (ActionCommand) ---
        btnNuevaCampana.setActionCommand("Nueva Partida");
        btnCargar.setActionCommand("Mostrar Cartas");
        btnOpciones.setActionCommand("Añadir/Borrar Personaje/Ataque");
        btnMultijugador.setActionCommand("Cambiar un Ataque o Personaje");
        btnSalir.setActionCommand("SALIR");

        // --- AÑADIMOS TU ESCUCHADOR A TODOS LOS BOTONES ---
        // Le pasamos 'this' (esta ventana) por parámetro como tú querías
        Escuchador miEscuchador = new Escuchador(this); 
        
        btnNuevaCampana.addActionListener(miEscuchador);
        btnCargar.addActionListener(miEscuchador);
        btnOpciones.addActionListener(miEscuchador);
        btnMultijugador.addActionListener(miEscuchador);
        btnSalir.addActionListener(miEscuchador);

        // Añadimos los botones al panel
        panelMenu.add(btnNuevaCampana);
        panelMenu.add(btnCargar);
        panelMenu.add(btnOpciones);
        panelMenu.add(btnMultijugador);
        panelMenu.add(btnSalir);
        
        
        
        
        
        
        JButton botonCierre = crearBoton("/img/gemini cartas imagenes/hydra.png");
        botonCierre.setName("system exit");
        
        
        
        //botonCierre.setIcon(imagenCierre);
        //botonCierre.setSize(20,20);
        botonCierre.addActionListener(new Escuchador(this));
        
        //panelMenu.add(botonCierre);
        
        //botonCierre.addActionListener(new Escuchador(this));
        
        /*JButton botonCerrar = new JButton("Cerrar Aplicación");

        // 2. Instanciamos nuestra clase escuchador y se la añadimos al botón
        EscuchadorCerrar escuchador = new EscuchadorCerrar();
        botonCerrar.addActionListener(escuchador);

        // Añadimos el botón al panel y el panel a la ventana
        panelMenu.add(botonCerrar);*/
        
        
        //panelMenu.add(Boton("Salir"));
        //panelMenu.add(crearBotonEstilo("Créditos"));

        panelCentro.add(panelMenu);
        fondo.add(panelCentro, BorderLayout.CENTER); // Centrado en la pantalla

        // 5. PANEL DE CRÉDITOS (Abajo a la derecha)
        JPanel panelCreditos = new JPanel();
        panelCreditos.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Lo empuja a la derecha
        panelCreditos.setOpaque(false);
        
        JLabel labelCreditos = new JLabel("Créditos: Pablo Espinosa / Jaime Maestre");
        labelCreditos.setForeground(Color.LIGHT_GRAY);
        panelCreditos.add(labelCreditos);
        
        fondo.add(panelCreditos, BorderLayout.SOUTH); // Lo anclamos abajo

        // 6. CONFIGURACIÓN DEL MARCO
        this.setContentPane(fondo); 
        // Tamaño de ventana ampliada estándar (Full HD)
        this.setSize(1920, 1080); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private JButton Boton(String referencia) {
    	JButton boton = new JButton();
    	
    	return boton;
    }
    

    public static void main(String[] args) {
        new Menu("/img/fondo bueno.png");
    }
    
    
    
    
    
    private JButton crearBoton( String rutaImagen) {
        JButton boton = new JButton();
        boton.setFont(new Font("Serif", Font.BOLD, 24));
        boton.setForeground(Color.WHITE);
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Añadimos la imagen
        boton.setIcon(new ImageIcon(getClass().getResource(rutaImagen)));
        
        // Configuración de colores oscuros fijos
        boton.setOpaque(true);
        boton.setBackground(Color.DARK_GRAY);
        boton.setBorderPainted(false); 
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hemos eliminado todo el bloque del MouseListener
        return boton;
    }
}