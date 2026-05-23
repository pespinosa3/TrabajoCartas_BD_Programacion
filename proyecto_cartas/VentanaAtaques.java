package proyecto_cartas;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.Connection;

public class VentanaAtaques extends JFrame {
	
    // 1. COMPONENTES COMO ATRIBUTOS (Para que todo el archivo los vea)
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
        
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/img/gemini cartas imagenes/fondo princiapal-clean.png"));
    	Image imgFondo = iconoFondo.getImage();
    	Image imgFondoRedimensionada = imgFondo.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        
        JLabel fondo = new JLabel(new ImageIcon(imgFondoRedimensionada));
        fondo.setOpaque(true);
        fondo.setBackground(Color.BLACK);
        
        
        setTitle("Insertar Nuevo Ataque");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); // Tu diseño absoluto

        // --- CAMPOS DE TEXTO (Tu código original) ---
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(150, 30, 200, 25);
        add(lblNombre); add(txtNombre);
        
        fondo.add(lblNombre);

        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setBounds(30, 70, 100, 25);
        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(150, 70, 200, 25);
        add(lblDesc); add(txtDescripcion);
        
        fondo.add(lblDesc);
        
        JLabel lblPotencia = new JLabel("Potencia:");
        lblPotencia.setBounds(30, 110, 100, 25);
        String[] opcionesPotencia = {"Ligero", "Normal", "Potente"};
        comboPotencia = new JComboBox<>(opcionesPotencia);
        comboPotencia.setBounds(150, 110, 200, 25);
        add(lblPotencia); add(comboPotencia);
        
        fondo.add(lblPotencia);
        
        JLabel lblDano = new JLabel("Daño Base:");
        lblDano.setBounds(30, 150, 100, 25);
        txtDano = new JTextField();
        txtDano.setBounds(150, 150, 200, 25);
        add(lblDano); add(txtDano);

        fondo.add(lblDano);
        
        JLabel lblMana = new JLabel("Coste Maná:");
        lblMana.setBounds(30, 190, 100, 25);
        txtMana = new JTextField();
        txtMana.setBounds(150, 190, 200, 25);
        add(lblMana); add(txtMana);

        fondo.add(lblMana);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 2, 20, 20)); 
        panelBotones.setOpaque(false);
        
        btnGuardar = new JButton("CREAR ATAQUE");
        btnGuardar.setBounds(20, 250, 150, 40);
        
        //fondo.add(btnGuardar);
        
        botonAtras = new JButton("ATRÁS");
        botonAtras.setBounds(180, 250, 150, 40);
        
        //fondo.add(botonAtras);
        
        fondo.add(panelBotones);
        
        // Le ponemos la "matrícula" al botón para que el Escuchador sepa quién habla
        btnGuardar.setActionCommand("Crear Ataque"); 
        botonAtras.setActionCommand("atras_crear");
        
        add(btnGuardar);
        add(botonAtras);
        
        this.setContentPane(fondo);
    }
    
    // 2. EL NUEVO MÉTODO QUE LE FALTABA A ESTA VENTANA
    // Cuando el Jefe (Principal.java) le pase el escuchador único, se lo enganchamos al botón
    public void asignarEscuchador(Escuchador esc) {
        btnGuardar.addActionListener(esc);
        botonAtras.addActionListener(esc);
    }

    // 3. TU MÉTODO DE GUARDADO (Ahora público para que el Escuchador pueda ejecutarlo)
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
