package proyecto_cartas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class VBorrar extends JFrame {
	
    // 1. COMPONENTES COMO ATRIBUTOS
    private JTextField txtTabla;
    private JTextField txtColumna;
    private JTextField txtDato;
    private Connection conexionActiva;
    
    private JButton botonAtras;
    private JButton btnEliminar;

    /**
     * @param conexion La conexión a la base de datos
     * @param tablaDestino El nombre de la tabla (ej. "ataques" o "personajes"). Si le pasas un String, se autocompleta.
     */
    public VBorrar(Connection conexion, String tablaDestino) {
    	
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
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 200)); // Margen superior para centrar
        panelCentro.setOpaque(false);
        
        // --- 3. PANEL DEL FORMULARIO ---
        // GridLayout de 4 filas (3 de datos + 1 de botones) y 2 columnas
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 20, 25)); 
        panelFormulario.setOpaque(false);
        
        Font fuenteEtiquetas = new Font("Arial", Font.BOLD, 24);
        Font fuenteCampos = new Font("Arial", Font.PLAIN, 20);

        // Tabla
        JLabel lblTabla = crearEtiqueta("Tabla:", fuenteEtiquetas);
        txtTabla = crearCampoTexto(fuenteCampos);
        if(tablaDestino != null && !tablaDestino.isEmpty()) {
            txtTabla.setText(tablaDestino);
            txtTabla.setEditable(false); // Bloqueamos para que el usuario no se equivoque
            txtTabla.setBackground(Color.LIGHT_GRAY);
        }
        panelFormulario.add(lblTabla); panelFormulario.add(txtTabla);

        // Columna (ej. id_ataque, nombre)
        JLabel lblColumna = crearEtiqueta("Columna (ej. id_ataque):", fuenteEtiquetas);
        txtColumna = crearCampoTexto(fuenteCampos);
        panelFormulario.add(lblColumna); panelFormulario.add(txtColumna);
        
        // Dato a borrar
        JLabel lblDato = crearEtiqueta("Dato a eliminar:", fuenteEtiquetas);
        txtDato = crearCampoTexto(fuenteCampos);
        panelFormulario.add(lblDato); panelFormulario.add(txtDato);

        // Botones
        btnEliminar = new JButton("ELIMINAR DATO");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 18));
        btnEliminar.setBackground(new Color(200, 50, 50)); // Un rojo sutil para indicar "borrar"
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botonAtras = new JButton("ATRÁS");
        botonAtras.setFont(new Font("Arial", Font.BOLD, 18));
        botonAtras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelFormulario.add(btnEliminar);
        panelFormulario.add(botonAtras);

        // --- 4. ENSAMBLAJE ---
        panelCentro.add(panelFormulario); 
        fondo.add(panelCentro, BorderLayout.CENTER); 

        // --- 5. CONFIGURACIÓN DE LA VENTANA ---
        setTitle("Eliminar Dato de " + (tablaDestino != null ? tablaDestino.toUpperCase() : "la Base de Datos"));
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Comandos de acción
        btnEliminar.setActionCommand("Eliminar Registro"); 
        botonAtras.setActionCommand("atras(borrarDato)"); // Distínguelo de tu botón atrás normal si lo necesitas
        
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
        btnEliminar.addActionListener(esc);
        botonAtras.addActionListener(esc);
    }

    // MÉTODO DE ELIMINADO CON LA LÓGICA DE CONSULTAS
    public void ejecutarBorrado() {
        String tabla = txtTabla.getText().trim();
        String columna = txtColumna.getText().trim();
        String dato = txtDato.getText().trim();

        if (tabla.isEmpty() || columna.isEmpty() || dato.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Doble confirmación por seguridad antes de borrar
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que deseas eliminar el registro donde " + columna + " = " + dato + "?\nEsta acción no se puede deshacer.", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            
            // Llamamos a nuestro nuevo método booleano
            boolean exito = Consultas.borrarDatoGUI(conexionActiva, tabla, columna, dato);

            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Dato eliminado correctamente de la tabla " + tabla + "!");
                txtDato.setText(""); // Limpiamos solo el dato para que pueda borrar varios seguidos si quiere
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el dato.\nComprueba que el dato existe o que el nombre de la columna sea correcto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}