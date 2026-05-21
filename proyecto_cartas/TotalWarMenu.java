package proyecto_cartas;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TotalWarMenu extends JFrame {

    public TotalWarMenu() {
        //setTitle("TOTAL WAR: PAIME");

        // 1. EL FONDO (Con fondo oscuro temporal)
        // Usamos un JLabel como contenedor principal.
        JLabel fondo = new JLabel(new ImageIcon(getClass().getResource("/img/fondo bueno.png")));
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

        // 4. CREACIÓN Y AÑADIDO DE BOTONES CENTRADOS
        panelMenu.add(crearBotonEstilo("Nueva Partida"));
        panelMenu.add(crearBotonEstilo("Mostrar Cartas"));
        panelMenu.add(crearBotonEstilo("Añadir/Borrar Personaje"));
        panelMenu.add(crearBotonEstilo("Añadir/Borrar Ataque"));
        panelMenu.add(crearBotonEstilo("Cambiar un Ataque o Personaje"));
        //panelMenu.add(crearBotonEstilo("Créditos"));

        panelCentro.add(panelMenu);
        fondo.add(panelCentro, BorderLayout.CENTER); // Centrado en la pantalla

        // 5. PANEL DE CRÉDITOS (Abajo a la derecha)
        JPanel panelCreditos = new JPanel();
        panelCreditos.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Lo empuja a la derecha
        panelCreditos.setOpaque(false);
        
        JLabel labelCreditos = new JLabel("Créditos: Tu Nombre / Tu Equipo");
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

    // Método que crea los botones y les añade los eventos del ratón
    private JButton crearBotonEstilo(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Serif", Font.BOLD, 24));
        boton.setForeground(Color.WHITE);
        boton.setHorizontalAlignment(SwingConstants.CENTER); // Texto centrado
        
        // Configuramos el botón para que parezca un panel oscuro
        boton.setOpaque(true);
        boton.setBackground(Color.DARK_GRAY);
        boton.setBorderPainted(false); // Sin bordes
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Evento para el Highlight (MouseListener)
        boton.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Se resalta el panel al pasar por encima
                boton.setBackground(Color.GRAY);
                boton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Vuelve a la normalidad al quitar el ratón
                boton.setBackground(Color.DARK_GRAY);
                boton.setForeground(Color.WHITE);
            }

            // Es obligatorio declarar los otros métodos de la interfaz aunque no se usen
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
        });

        return boton;
    }

    public static void main(String[] args) {
        new TotalWarMenu();
    }
}