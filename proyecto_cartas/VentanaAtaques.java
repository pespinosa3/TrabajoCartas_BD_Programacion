package proyecto_cartas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class VentanaAtaques extends JFrame {
	
    // 1. COMPONENTES COMO ATRIBUTOS
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JComboBox<String> comboPotencia;
    private JTextField txtDano;
    private JTextField txtMana;
    private Connection conexionActiva;
    
    private JButton botonAtras;
    private JButton btnGuardar;

    public VentanaAtaques(Connection conexion) {
    	
        this.setVisible(false); // Nace oculta por defecto
        this.conexionActiva = conexion;
        
        // --- 1. CONFIGURACIÓN DEL FONDO (Igual que VentanaCreacion) ---
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/fondo princiapal-clean.png"));
        Image imgFondo = iconoFondo.getImage();
        Image imgFondoRedimensionada = imgFondo.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        
        JLabel fondo = new JLabel(new ImageIcon(imgFondoRedimensionada));
        fondo.setSize(1920, 1080);
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK);
        fondo.setLayout(new BorderLayout()); 

        // --- 2. PANEL CENTRO (Copiando la estrategia exacta de VentanaCreacion) ---
        // Usamos FlowLayout con una separación vertical (por ejemplo, 150 píxeles) para bajar el formulario
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 150)); 
        panelCentro.setOpaque(false);
        
        // --- 3. PANEL DEL FORMULARIO ---
        // Un GridLayout de 6 filas (5 de datos + 1 de botones) y 2 columnas
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2, 20, 25)); 
        panelFormulario.setOpaque(false);
        
        // Fuentes aumentadas para que se lean de forma óptima a 1920x1080
        Font fuenteEtiquetas = new Font("Arial", Font.BOLD, 24);
        Font fuenteCampos = new Font("Arial", Font.PLAIN, 20);

        // Nombre
        JLabel lblNombre = crearEtiqueta("Nombre:", fuenteEtiquetas);
        txtNombre = crearCampoTexto(fuenteCampos);
        panelFormulario.add(lblNombre); panelFormulario.add(txtNombre);

        // Descripción
        JLabel lblDesc = crearEtiqueta("Descripción:", fuenteEtiquetas);
        txtDescripcion = crearCampoTexto(fuenteCampos);
        panelFormulario.add(lblDesc); panelFormulario.add(txtDescripcion);
        
        // Potencia
        JLabel lblPotencia = crearEtiqueta("Potencia:", fuenteEtiquetas);
        String[] opcionesPotencia = {"Ligero", "Normal", "Potente"};
        comboPotencia = new JComboBox<>(opcionesPotencia);
        comboPotencia.setFont(fuenteCampos);
        panelFormulario.add(lblPotencia); panelFormulario.add(comboPotencia);
        
        // Daño Base
        JLabel lblDano = crearEtiqueta("Daño Base:", fuenteEtiquetas);
        txtDano = crearCampoTexto(fuenteCampos);
        panelFormulario.add(lblDano); panelFormulario.add(txtDano);

        // Coste Maná
        JLabel lblMana = crearEtiqueta("Coste Maná:", fuenteEtiquetas);
        txtMana = crearCampoTexto(fuenteCampos);
        panelFormulario.add(lblMana); panelFormulario.add(txtMana);

        // Botones (Ocupan la última fila del GridLayout automáticamente)
        btnGuardar = new JButton("CREAR ATAQUE");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 18));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botonAtras = new JButton("ATRÁS");
        botonAtras.setFont(new Font("Arial", Font.BOLD, 18));
        botonAtras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelFormulario.add(botonAtras);
        panelFormulario.add(btnGuardar);
        

        // --- 4. ENSAMBLAJE JERÁRQUICO ---
        panelCentro.add(panelFormulario); // Metemos el formulario en el panel con FlowLayout
        fondo.add(panelCentro, BorderLayout.CENTER); // Metemos todo al centro del fondo

        // --- 5. CONFIGURACIÓN DE LA VENTANA ---
        setTitle("Insertar Nuevo Ataque");
        setSize(1920, 1080);
        setLocationRelativeTo(null); // Centra la ventana en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Matrículas para el Escuchador
        btnGuardar.setActionCommand("Crear Personaje"); 
        botonAtras.setActionCommand("atras_crear");
        
        this.setContentPane(fondo);
    }
    
    // --- MÉTODOS AUXILIARES PARA LIMPIAR EL CONSTRUCTOR ---
    
    private JLabel crearEtiqueta(String texto, Font fuente) {
        JLabel label = new JLabel(texto);
        label.setFont(fuente);
        label.setForeground(Color.WHITE); // Texto blanco para que resalte sobre el fondo oscuro
        return label;
    }
    
    private JTextField crearCampoTexto(Font fuente) {
        JTextField textField = new JTextField();
        textField.setFont(fuente);
        textField.setPreferredSize(new Dimension(300, 40)); // Dimensiones base idóneas
        return textField;
    }

    // 2. MÉTODO ASIGNAR ESCUCHADOR (Mantenido intacto)
    public void asignarEscuchador(Escuchador esc) {
        btnGuardar.addActionListener(esc);
        botonAtras.addActionListener(esc);
    }

    // 3. MÉTODO DE GUARDADO (Mantenido intacto)
    public void guardarAtaque() {
        try {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String potencia = comboPotencia.getSelectedItem().toString().toLowerCase(); 

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre y descripción no pueden estar vacíos.");
                return;
            }
            if (nombre.length() > 50) {
                JOptionPane.showMessageDialog(this, "El nombre no puede tener más de 50 caracteres.");
                return;
            }

            int dano = Integer.parseInt(txtDano.getText().trim());
            int mana = Integer.parseInt(txtMana.getText().trim());

            boolean exito = PedirDatos.insertarAtaqueGUI(conexionActiva, nombre, descripcion, potencia, dano, mana);

            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Ataque guardado con éxito!");
                txtNombre.setText("");
                txtDescripcion.setText("");
                txtDano.setText("");
                txtMana.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce números enteros válidos en Daño y Maná.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        }
    }
}
