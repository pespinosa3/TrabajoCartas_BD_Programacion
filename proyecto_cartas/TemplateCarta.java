package proyecto_cartas;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder; // Clase externa que necesitaríamos importar

public class TemplateCarta extends JFrame {

    public TemplateCarta() {
        setTitle("Template Carta");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Fondo de la ventana (Lienzo principal)
        JPanel panelFondoVentana = new JPanel();
        panelFondoVentana.setLayout(null);
        panelFondoVentana.setBackground(new Color(200, 200, 200));
        setContentPane(panelFondoVentana);

        // --- EL PANEL DE LA CARTA ---
        JPanel cartaPanel = new JPanel();
        cartaPanel.setLayout(null); 
        cartaPanel.setBounds(10, 10, 360, 500); 
        cartaPanel.setBackground(Color.WHITE); // Color de fondo de la carta
        
        // AQUÍ EL TRUCO: Aplicamos un borde de línea negro, de 3 píxeles de grosor y con el hito 'true' (redondeado)
        cartaPanel.setBorder(new LineBorder(Color.BLACK, 3, true));

        // --- 1. CABECERA ---
        JLabel lblNombre = new JLabel("NOMBRE");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setBounds(20, 20, 200, 20); 
        cartaPanel.add(lblNombre);

        JLabel lblElemento = new JLabel("ELEM-ENTO", SwingConstants.RIGHT);
        lblElemento.setFont(new Font("Arial", Font.BOLD, 12));
        lblElemento.setBounds(260, 20, 80, 20);
        cartaPanel.add(lblElemento);

        // --- 2. ZONA CENTRAL ---
        JPanel imagenPanel = new JPanel();
        imagenPanel.setBackground(new Color(230, 230, 230));
        imagenPanel.setBounds(20, 50, 260, 180);
        imagenPanel.add(new JLabel("Imagen"));
        cartaPanel.add(imagenPanel);

        JPanel armaPanel = new JPanel();
        armaPanel.setBounds(290, 50, 60, 180);
        armaPanel.add(new JLabel("<html>A<br>R<br>M<br>A</html>"));
        cartaPanel.add(armaPanel);

        // --- 3. SUB-ETIQUETAS ---
        JPanel casaPanel = new JPanel();
        casaPanel.setBounds(20, 240, 125, 30);
        casaPanel.add(new JLabel("CASA"));
        cartaPanel.add(casaPanel);

        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(155, 240, 125, 30);
        logoPanel.add(new JLabel("LOGO INVOCACION"));
        cartaPanel.add(logoPanel);

        // --- 4. CUADRO GRANDE DE TEXTOS ---
        JPanel panelTextos = new JPanel();
        panelTextos.setLayout(null);
        panelTextos.setBounds(20, 280, 330, 210);
        panelTextos.setOpaque(false);

        JLabel tituloAtaques = new JLabel("ATAQUES");
        tituloAtaques.setBounds(0, 0, 100, 20);
        panelTextos.add(tituloAtaques);

        JLabel ataque1 = new JLabel("1. Ataque rápido: 2 de Daño.");
        ataque1.setBounds(0, 20, 300, 20);
        panelTextos.add(ataque1);

        JLabel tituloDesc = new JLabel("DESCRIPCIÓN");
        tituloDesc.setBounds(0, 90, 100, 20);
        panelTextos.add(tituloDesc);

        JTextArea txtDescripcion = new JTextArea("Aquí va el lore del personaje...");
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setOpaque(false);
        txtDescripcion.setEditable(false);
        txtDescripcion.setBounds(0, 115, 330, 80);
        panelTextos.add(txtDescripcion);

        cartaPanel.add(panelTextos);

        // --- 5. ID ---
        JLabel lblId = new JLabel("ID: 001");
        lblId.setFont(new Font("Arial", Font.PLAIN, 10));
        lblId.setBounds(20, 480, 100, 20);
        cartaPanel.add(lblId);

        // Añadimos la carta al fondo de la ventana
        panelFondoVentana.add(cartaPanel);
    }

    public static void main(String[] args) {
        TemplateCarta ventana = new TemplateCarta();
        ventana.setVisible(true);
    }
}