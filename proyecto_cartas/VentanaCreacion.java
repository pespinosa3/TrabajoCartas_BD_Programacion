package proyecto_cartas;

import javax.swing.*;
import java.awt.*;

public class VentanaCreacion extends JFrame {
	
    // Ponemos los botones como atributos para poder acceder a ellos desde el método asignarEscuchador
    private JButton btnAtras;
    private JButton btnPersonaje;
    private JButton btnAtaque;
	
    public VentanaCreacion() {
		
        this.setVisible(false); // Nace oculta
        
     // 1. EL FONDO (Escalado correctamente a 1920x1080)
    	ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/fondo princiapal-clean.png"));
    	Image imgFondo = iconoFondo.getImage();
    	// Forzamos a la imagen a medir 1920x1080 con renderizado suave
    	Image imgFondoRedimensionada = imgFondo.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        
        
        
        JLabel fondo = new JLabel(new ImageIcon(imgFondoRedimensionada));
        fondo.setSize(1920,1080);
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK); 
        fondo.setLayout(new BorderLayout()); 

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        panelTitulo.setOpaque(false); 
        
        ImageIcon iconoLogoOriginal = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/menu de creacion.png"));
        Image imgLogo = iconoLogoOriginal.getImage();
        Image imgLogoRedimensionada = imgLogo.getScaledInstance(210, 210, Image.SCALE_SMOOTH);
        JLabel labelTitulo = new JLabel(new ImageIcon(imgLogoRedimensionada));
        
        panelTitulo.add(labelTitulo);
        fondo.add(panelTitulo, BorderLayout.NORTH); 

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100)); 
        panelCentro.setOpaque(false);
        
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(1, 3, 50, 40)); 
        panelMenu.setOpaque(false); 
        
        btnAtras = crearBoton("/img/gemini cartas imagenes/atras.png");
        btnPersonaje = crearBoton("/img/gemini cartas imagenes/personaje.png");
        btnAtaque = crearBoton("/img/gemini cartas imagenes/ataque.png");

        btnAtras.setActionCommand("ATRAS");
        btnPersonaje.setActionCommand("personaje");
        btnAtaque.setActionCommand("ataque");

        // NO AÑADIMOS EL ESCUCHADOR AQUÍ. Lo añadiremos con el método de abajo.
        
        panelMenu.add(btnAtras);
        panelMenu.add(btnPersonaje);
        panelMenu.add(btnAtaque);
        
        panelCentro.add(panelMenu);
        fondo.add(panelCentro, BorderLayout.CENTER); 

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
    }
    
    // --- ESTE MÉTODO ES LA CLAVE ---
    // Recibe el escuchador que crea Menu.java y se lo pone a los botones
    public void asignarEscuchador(Escuchador esc) {
        btnAtras.addActionListener(esc);
        btnPersonaje.addActionListener(esc);
        btnAtaque.addActionListener(esc);
    }

    public JButton crearBoton(String rutaImagen) {
        JButton boton = new JButton();
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
        Image imagen = iconoOriginal.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenRedimensionada));
        
        boton.setPreferredSize(new Dimension(150, 150));
        
        boton.setOpaque(true);
        boton.setBackground(Color.DARK_GRAY);
        //boton.setBorderPainted(false); 
        //boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
}