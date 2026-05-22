package proyecto_cartas;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Menu(String p_fondo) {
    	
        // 1. EL FONDO 
        JLabel fondo = new JLabel(new ImageIcon(getClass().getResource(p_fondo)));
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK); 
        fondo.setLayout(new BorderLayout()); 

        // 2. PANEL DEL TÍTULO (Arriba y centrado)
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        panelTitulo.setOpaque(false); 
        
        // --- AQUÍ HACEMOS EL LOGO MÁS PEQUEÑO ---
        // Cargamos la imagen del logo original
        ImageIcon iconoLogoOriginal = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/logo.png"));
        Image imgLogo = iconoLogoOriginal.getImage();
        // Lo redimensionamos a un tamaño estándar más pequeño (350 de ancho por 90 de alto)
        Image imgLogoRedimensionada = imgLogo.getScaledInstance(600, 200, Image.SCALE_SMOOTH);
        JLabel labelTitulo = new JLabel(new ImageIcon(imgLogoRedimensionada));
        
        panelTitulo.add(labelTitulo);
        fondo.add(panelTitulo, BorderLayout.NORTH); 

        // 3. PANEL CONTENEDOR DE LAS OPCIONES (Mover hacia arriba)
        JPanel panelCentro = new JPanel();
        // ¡CAMBIO CLAVE!: Hemos bajado el margen vertical de 150 a 30. Esto sube los botones inmediatamente.
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); 
        panelCentro.setOpaque(false);
        
        JPanel panelMenu = new JPanel();
        // Mantenemos las 5 opciones en 1 columna, bajando a 15px la separación para asegurar espacio
        panelMenu.setLayout(new GridLayout(5, 1, 0, 15)); 
        panelMenu.setOpaque(false); 
        
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
        Escuchador miEscuchador = new Escuchador(this); 
        
        btnNuevaCampana.addActionListener(miEscuchador);
        btnCargar.addActionListener(miEscuchador);
        btnOpciones.addActionListener(miEscuchador);
        btnMultijugador.addActionListener(miEscuchador);
        btnSalir.addActionListener(miEscuchador);

        // Añadimos los botones al panel del menú
        panelMenu.add(btnNuevaCampana);
        panelMenu.add(btnCargar);
        panelMenu.add(btnOpciones);
        panelMenu.add(btnMultijugador);
        panelMenu.add(btnSalir);
        
        // Metemos el panel de los botones en el panel central
        panelCentro.add(panelMenu);
        
        // Añadimos el panel central al fondo de la ventana
        fondo.add(panelCentro, BorderLayout.CENTER); 

        // 5. PANEL DE CRÉDITOS (Abajo a la derecha)
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
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Menu("/img/fondo bueno.png");
    }
    
    // Método para crear botones con tamaño estándar
    private JButton crearBoton(String rutaImagen) {
        JButton boton = new JButton();
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
        Image imagen = iconoOriginal.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(300, 80, java.awt.Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenRedimensionada));
        
        boton.setPreferredSize(new Dimension(300, 80));
        
        boton.setOpaque(true);
        boton.setBackground(Color.DARK_GRAY);
        boton.setBorderPainted(false); 
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
}