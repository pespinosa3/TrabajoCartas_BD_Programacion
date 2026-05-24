package proyecto_cartas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class VentanaPersonajes extends JFrame {
	
    // 1. COMPONENTES COMO ATRIBUTOS
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtVida;
    private JTextField txtMultiplicador;
    private JComboBox<String> comboCasa;
    private JComboBox<String> comboElemento;
    
    private Connection conexionActiva;
    
    private JButton botonAtras;
    private JButton btnGuardar;

    public VentanaPersonajes(Connection conexion) {
    	
        this.setVisible(false); // Nace oculta por defecto
        this.conexionActiva = conexion;
        
        // --- 1. CONFIGURACIÓN DEL FONDO ---
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/fondo princiapal-clean.png"));
        Image imgFondo = iconoFondo.getImage();
        Image imgFondoRedimensionada = imgFondo.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        
        JLabel fondo = new JLabel(new ImageIcon(imgFondoRedimensionada));
        fondo.setSize(1920, 1080);
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK);
        fondo.setLayout(new BorderLayout()); 

        // --- 2. PANEL CENTRO ---
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 150)); 
        panelCentro.setOpaque(false);
        
        // --- 3. PANEL DEL FORMULARIO ---
        // GridLayout de 7 filas (6 de datos + 1 de botones) y 2 columnas
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(7, 2, 20, 25)); 
        panelFormulario.setOpaque(false);
        
        Font fuenteEtiquetas = new Font("Arial", Font.BOLD, 24);
        Font fuenteCampos = new Font("Arial", Font.PLAIN, 20);

        // Nombre
        panelFormulario.add(crearEtiqueta("Nombre:", fuenteEtiquetas));
        txtNombre = crearCampoTexto(fuenteCampos);
        panelFormulario.add(txtNombre);

        // Descripción
        panelFormulario.add(crearEtiqueta("Descripción:", fuenteEtiquetas));
        txtDescripcion = crearCampoTexto(fuenteCampos);
        panelFormulario.add(txtDescripcion);
        
        // Vida
        panelFormulario.add(crearEtiqueta("Vida (750 - 1350):", fuenteEtiquetas));
        txtVida = crearCampoTexto(fuenteCampos);
        panelFormulario.add(txtVida);

        // Multiplicador
        panelFormulario.add(crearEtiqueta("Mult. Daño (0.7 - 1.2):", fuenteEtiquetas));
        txtMultiplicador = crearCampoTexto(fuenteCampos);
        panelFormulario.add(txtMultiplicador);

        // Casa
        panelFormulario.add(crearEtiqueta("Casa:", fuenteEtiquetas));
        String[] opcionesCasa = {"Legión Administrativa", "Orden del Caos", "Lannister", "Marea Eterna"};
        comboCasa = new JComboBox<>(opcionesCasa);
        comboCasa.setFont(fuenteCampos);
        panelFormulario.add(comboCasa);
        
        // Elemento
        panelFormulario.add(crearEtiqueta("Elemento:", fuenteEtiquetas));
        String[] opcionesElemento = {"Vacío", "Electricidad", "Solar", "Planta", "Mineral", "Líquido", "Admin"};
        comboElemento = new JComboBox<>(opcionesElemento);
        comboElemento.setFont(fuenteCampos);
        panelFormulario.add(comboElemento);

        // Botones 
        btnGuardar = new JButton("CREAR PERSONAJE");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 18));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botonAtras = new JButton("ATRÁS");
        botonAtras.setFont(new Font("Arial", Font.BOLD, 18));
        botonAtras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelFormulario.add(btnGuardar);
        panelFormulario.add(botonAtras);

        // --- 4. ENSAMBLAJE JERÁRQUICO ---
        panelCentro.add(panelFormulario); 
        fondo.add(panelCentro, BorderLayout.CENTER); 

        // --- 5. CONFIGURACIÓN DE LA VENTANA ---
        setTitle("Insertar Nuevo Personaje");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btnGuardar.setActionCommand("Crear Personaje"); 
        botonAtras.setActionCommand("atras_crear");
        
        this.setContentPane(fondo);
    }
    
    // --- MÉTODOS AUXILIARES ---
    private JLabel crearEtiqueta(String texto, Font fuente) {
        JLabel label = new JLabel(texto);
        label.setFont(fuente);
        label.setForeground(Color.WHITE); 
        return label;
    }
    
    private JTextField crearCampoTexto(Font fuente) {
        JTextField textField = new JTextField();
        textField.setFont(fuente);
        textField.setPreferredSize(new Dimension(300, 40)); 
        return textField;
    }

    // 2. MÉTODO ASIGNAR ESCUCHADOR
    public void asignarEscuchador(Escuchador esc) { // Asegúrate de que tienes la clase 'Escuchador' creada
        btnGuardar.addActionListener(esc);
        botonAtras.addActionListener(esc);
    }

    // 3. MÉTODO DE GUARDADO CON VALIDACIONES
    public void guardarPersonaje() {
        try {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre y descripción no pueden estar vacíos.");
                return;
            }
            if (nombre.length() > 50) {
                JOptionPane.showMessageDialog(this, "El nombre no puede tener más de 50 caracteres.");
                return;
            }

            // --- Validación de Vida ---
            int vida = Integer.parseInt(txtVida.getText().trim());
            if (vida < 750 || vida > 1350) {
                JOptionPane.showMessageDialog(this, "La vida debe estar comprendida entre 750 y 1350.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- Validación de Multiplicador ---
            // Reemplazamos coma por punto por si el usuario escribe 1,2 en lugar de 1.2
            double multiplicador = Double.parseDouble(txtMultiplicador.getText().trim().replace(",", "."));
            if (multiplicador < 0.7 || multiplicador > 1.2) {
                JOptionPane.showMessageDialog(this, "El multiplicador de daño debe estar entre 0.7 y 1.2.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- Lógica de IDs para la base de datos ---
            // Los ComboBox devuelven índices que empiezan en 0. Sumamos 1 para que encaje con la BD.
            int idCasa = comboCasa.getSelectedIndex() + 1; 
            int idElemento = comboElemento.getSelectedIndex() + 1; 
            
            int idComandante = 0;
            switch(idCasa) {
                case 1: idComandante = 11; break; // Legión
                case 2: idComandante = 12; break; // Caos
                case 3: idComandante = 2;  break; // Lannister
                case 4: idComandante = 4;  break; // Marea
            }

            // Llamada al método que ahora sí es apto para GUI
            boolean exito = PedirDatos.insertarPersonajeGUI(conexionActiva, nombre, descripcion, vida, multiplicador, idCasa, idComandante, idElemento);

            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Personaje guardado con éxito!");
                // Limpiar campos
                txtNombre.setText("");
                txtDescripcion.setText("");
                txtVida.setText("");
                txtMultiplicador.setText("");
                comboCasa.setSelectedIndex(0);
                comboElemento.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Asegúrate de introducir números válidos en 'Vida' y 'Multiplicador'.\nEjemplo multiplicador: 1.1", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}