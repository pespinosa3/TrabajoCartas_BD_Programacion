package proyecto_cartas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaCreacion extends JFrame {
	
    // Ponemos los botones como atributos para poder acceder a ellos desde el método asignarEscuchador
    private JButton btnAtras;
    private JButton btnCrear;
	
    public VentanaCreacion() {
		
        this.setVisible(false); // Nace oculta
        
        JLabel fondo = new JLabel(new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/fondo princiapal.png")));
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK); 
        fondo.setLayout(new BorderLayout()); 

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        panelTitulo.setOpaque(false); 
        
        ImageIcon iconoLogoOriginal = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/logo.png"));
        Image imgLogo = iconoLogoOriginal.getImage();
        Image imgLogoRedimensionada = imgLogo.getScaledInstance(600, 200, Image.SCALE_SMOOTH);
        JLabel labelTitulo = new JLabel(new ImageIcon(imgLogoRedimensionada));
        
        panelTitulo.add(labelTitulo);
        fondo.add(panelTitulo, BorderLayout.NORTH); 

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100)); 
        panelCentro.setOpaque(false);
        
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(2, 1, 0, 40)); 
        panelMenu.setOpaque(false); 
        
        btnAtras = crearBoton("/img/gemini cartas imagenes/gondalf.png");
        btnCrear = crearBoton("/img/gemini cartas imagenes/flora.png");

        btnAtras.setActionCommand("ATRAS");
        btnCrear.setActionCommand("AÑADIR");

        // NO AÑADIMOS EL ESCUCHADOR AQUÍ. Lo añadiremos con el método de abajo.
        
        panelMenu.add(btnAtras);
        panelMenu.add(btnCrear);
        
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
        btnCrear.addActionListener(esc);
    }

    public JButton crearBoton(String rutaImagen) {
        JButton boton = new JButton();
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
        Image imagen = iconoOriginal.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(500, 300, java.awt.Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenRedimensionada));
        
        boton.setPreferredSize(new Dimension(500, 300));
        
        boton.setOpaque(true);
        boton.setBackground(Color.DARK_GRAY);
        boton.setBorderPainted(false); 
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }
}