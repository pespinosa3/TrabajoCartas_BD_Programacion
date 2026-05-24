package proyecto_cartas;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VBuscador extends JFrame {
	
    // 1. COMPONENTES COMO ATRIBUTOS
    private JTextField txtBusqueda;
    private JTextArea areaResultados; // Área grande para mostrar los resultados
    private Connection conexionActiva;
    
    private JButton botonAtras;
    private JButton btnBuscar;

    public VBuscador(Connection conexion) {
    	
        this.setVisible(false); // Nace oculta por defecto
        this.conexionActiva = conexion;
        
        // --- 1. CONFIGURACIÓN DEL FONDO ---
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/buscador.png"));
        Image imgFondo = iconoFondo.getImage();
        Image imgFondoRedimensionada = imgFondo.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        
        JLabel fondo = new JLabel(new ImageIcon(imgFondoRedimensionada));
        fondo.setSize(1920, 1080);
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK);
        fondo.setLayout(new BorderLayout()); 

        // --- 2. PANEL CENTRO ---
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100)); // Margen superior
        panelCentro.setOpaque(false);
        
        // --- 3. PANEL DEL FORMULARIO Y RESULTADOS ---
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BorderLayout(20, 30)); // Separación entre buscador y resultados
        panelContenedor.setOpaque(false);
        
        Font fuenteEtiquetas = new Font("Arial", Font.BOLD, 24);
        Font fuenteCampos = new Font("Arial", Font.PLAIN, 20);

        // --- SUB-PANEL ARRIBA (Buscador y Botones) ---
        JPanel panelArriba = new JPanel();
        panelArriba.setLayout(new GridLayout(2, 2, 20, 25)); 
        panelArriba.setOpaque(false);

        // Campo de búsqueda
        panelArriba.add(crearEtiqueta("Término a buscar:", fuenteEtiquetas));
        txtBusqueda = crearCampoTexto(fuenteCampos);
        panelArriba.add(txtBusqueda);

        // Botones 
        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 18));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botonAtras = new JButton("ATRÁS");
        botonAtras.setFont(new Font("Arial", Font.BOLD, 18));
        botonAtras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelArriba.add(botonAtras);
        panelArriba.add(btnBuscar);
        

        // --- SUB-PANEL ABAJO (Área de resultados) ---
        areaResultados = new JTextArea(15, 50); // 15 filas de alto, 50 columnas de ancho
        areaResultados.setFont(new Font("Consolas", Font.PLAIN, 18)); // Fuente monoespaciada para alinear bien
        areaResultados.setEditable(false); // No se puede escribir, solo leer
        areaResultados.setBackground(new Color(20, 20, 20, 200)); // Fondo oscuro semi-transparente
        areaResultados.setForeground(Color.WHITE); // Letra blanca
        areaResultados.setMargin(new Insets(15, 15, 15, 15)); // Margen interior

        // Le añadimos un Scroll por si hay muchos resultados
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setOpaque(false);
        scrollResultados.getViewport().setOpaque(false);

        // --- ENSAMBLAJE ---
        panelContenedor.add(panelArriba, BorderLayout.NORTH);
        panelContenedor.add(scrollResultados, BorderLayout.CENTER);

        panelCentro.add(panelContenedor); 
        fondo.add(panelCentro, BorderLayout.CENTER); 

        // --- 4. CONFIGURACIÓN DE LA VENTANA ---
        setTitle("Buscador de la Biblioteca");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Matrículas para tu Escuchador
        btnBuscar.setActionCommand("Ejecutar Busqueda"); 
        botonAtras.setActionCommand("atras_buscador");
        
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

    // --- MÉTODO PARA ASIGNAR ESCUCHADOR ---
    public void asignarEscuchador(Escuchador esc) { 
        btnBuscar.addActionListener(esc);
        botonAtras.addActionListener(esc);
    }

    // --- LÓGICA DEL BUSCADOR (TRADUCIDA A GUI) ---
    public void ejecutarBusqueda() {
        String termino = txtBusqueda.getText().trim();

        if (termino.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce un término para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder resultados = new StringBuilder(); // Iremos concatenando el texto aquí
        boolean encontrado = false;

        // Las tablas en las que buscar
        String[] tablas = {"personajes", "armas", "elementos", "ataques", "estados", "casas", "invocaciones"};

        // Iteramos por todas las tablas
        for (String tabla : tablas) {
            // Usamos LIKE ? para evitar inyección SQL y problemas de comillas
            String query = "SELECT nombre, descripcion FROM " + tabla + " WHERE nombre LIKE ?";
            
            
            try {
                PreparedStatement pst = conexionActiva.prepareStatement(query);
                pst.setString(1, "%" + termino + "%"); // Le añade los % automáticamente
                
                ResultSet resultado = pst.executeQuery(); // Usamos executeQuery() vacío porque es un PreparedStatement
                
                boolean cabeceraImpresa = false;

                while (resultado.next()) {
                    // Solo imprimimos el nombre de la tabla una vez si encuentra algo en ella
                    if (!cabeceraImpresa) {
                        resultados.append("=== Resultados en la tabla: ").append(tabla.toUpperCase()).append(" ===\n");
                        cabeceraImpresa = true;
                    }
                    
                    resultados.append("Nombre: ").append(resultado.getString(1)).append("\n");
                    resultados.append("Descripción: ").append(resultado.getString(2)).append("\n");
                    resultados.append("----------------------------------------------------\n");
                    
                    encontrado = true;
                }
                
                if (cabeceraImpresa) {
                    resultados.append("\n"); // Un salto de línea extra para separar tablas
                }
                
            } catch (SQLException e) {
                // Si una tabla no tiene la columna 'descripcion', capturamos el error pero permitimos
                // que el bucle for continúe buscando en las demás tablas.
                System.out.println("Error o falta de columnas en la tabla " + tabla + ": " + e.getMessage());
            }
        }

        // Si terminó de buscar y no encontró nada en absoluto
        if (!encontrado) {
            areaResultados.setText(""); // Limpiamos la pantalla
            JOptionPane.showMessageDialog(this, "No se ha encontrado ningún elemento que contenga '" + termino + "'.", "Sin resultados", JOptionPane.ERROR_MESSAGE);
        } else {
            // Mostramos todo el texto acumulado en nuestra área de resultados
            areaResultados.setText(resultados.toString());
            areaResultados.setCaretPosition(0); // Hacemos que el scroll vuelva arriba del todo
        }
    }
}