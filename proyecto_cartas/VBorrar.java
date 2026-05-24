package proyecto_cartas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class VBorrar extends JFrame {
	
    // 1. COMPONENTES COMO ATRIBUTOS
    private JComboBox<String> comboTabla;
    private JTextField txtColumna;
    private JTextField txtDato;
    private Connection conexionActiva;
    
    private JButton botonAtras;
    private JButton btnBorrar;

    // El nombre del constructor coincide con tu clase VBorrar
    public VBorrar(Connection conexion) {
    	
        this.setVisible(false); // Nace oculta por defecto
        this.conexionActiva = conexion;
        
        // --- 1. CONFIGURACIÓN DEL FONDO ---
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/mazmorra.png"));
        Image imgFondo = iconoFondo.getImage();
        Image imgFondoRedimensionada = imgFondo.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        
        JLabel fondo = new JLabel(new ImageIcon(imgFondoRedimensionada));
        fondo.setSize(1920, 1080);
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK);
        fondo.setLayout(new BorderLayout()); 

        // --- 2. PANEL CENTRO ---
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 250)); 
        panelCentro.setOpaque(false);
        
        // --- 3. PANEL DEL FORMULARIO ---
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 20, 25)); 
        panelFormulario.setOpaque(false);
        
        Font fuenteEtiquetas = new Font("Arial", Font.BOLD, 24);
        Font fuenteCampos = new Font("Arial", Font.PLAIN, 20);

        // Tabla
        panelFormulario.add(crearEtiqueta("Selecciona la Tabla:", fuenteEtiquetas));
        String[] opcionesTablas = {"personajes", "armas", "elementos", "ataques", "estados", "casas", "invocaciones"};
        comboTabla = new JComboBox<>(opcionesTablas);
        comboTabla.setFont(fuenteCampos);
        panelFormulario.add(comboTabla);

        // Columna 
        panelFormulario.add(crearEtiqueta("Columna de referencia:", fuenteEtiquetas));
        txtColumna = crearCampoTexto(fuenteCampos);
        panelFormulario.add(txtColumna);
        
        // Dato a borrar 
        panelFormulario.add(crearEtiqueta("Dato a eliminar:", fuenteEtiquetas));
        txtDato = crearCampoTexto(fuenteCampos);
        panelFormulario.add(txtDato);

        // Botones 
        btnBorrar = new JButton("ELIMINAR DATO");
        btnBorrar.setFont(new Font("Arial", Font.BOLD, 18));
        btnBorrar.setBackground(new Color(200, 50, 50)); 
        btnBorrar.setForeground(Color.WHITE);
        btnBorrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botonAtras = new JButton("ATRÁS");
        botonAtras.setFont(new Font("Arial", Font.BOLD, 18));
        botonAtras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelFormulario.add(botonAtras);
        panelFormulario.add(btnBorrar);
        

        // --- 4. ENSAMBLAJE JERÁRQUICO ---
        panelCentro.add(panelFormulario); 
        fondo.add(panelCentro, BorderLayout.CENTER); 

        // --- 5. CONFIGURACIÓN DE LA VENTANA ---
        setTitle("Eliminar Registro");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // MATRÍCULAS ADAPTADAS A TU ESCUCHADOR
        btnBorrar.setActionCommand("Eliminar Registro"); 
        botonAtras.setActionCommand("atras(borrarDato)");
        
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

    // MÉTODO ASIGNAR ESCUCHADOR
    public void asignarEscuchador(Escuchador esc) { 
        btnBorrar.addActionListener(esc);
        botonAtras.addActionListener(esc);
    }

    // MÉTODO RENOMBRADO PARA ENCAJAR CON TU ESCUCHADOR ("this.vb.ejecutarBorrado();")
    public void ejecutarBorrado() {
        String tabla = comboTabla.getSelectedItem().toString();
        String columna = txtColumna.getText().trim();
        String dato = txtDato.getText().trim();

        if (columna.isEmpty() || dato.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La columna y el dato no pueden estar vacíos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                "¿Estás seguro de que deseas eliminar el registro de la tabla '" + tabla + "' \ndonde '" + columna + "' sea igual a '" + dato + "'?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Llama a tu método en Consultas.java
            boolean exito = Consultas.borrarDatoGUI(conexionActiva, tabla, columna, dato);

            if (exito) {
                JOptionPane.showMessageDialog(this, "¡El dato ha sido eliminado con éxito de la base de datos!");
                txtColumna.setText("");
                txtDato.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar. Es posible que el dato no exista o la columna esté mal escrita.", "Error al borrar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}