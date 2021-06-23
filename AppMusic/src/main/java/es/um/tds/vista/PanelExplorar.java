package es.um.tds.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.um.tds.controlador.AppMusic;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.Estilo;
import es.um.tds.modelo.Usuario;

public class PanelExplorar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private AppMusic controlador = AppMusic.getUnicaInstancia();
	
	private JPanel panelCampos;
	private JPanel panelCentral;
	
	private JTextField campoTitulo;
	private JTextField campoInterprete;
	
	private final JLabel lblTitulo = new JLabel("Título");
	private final JLabel lblInterprete = new JLabel("Interprete");
	private final JLabel lblEstilo = new JLabel("Estilo");
	
	private JComboBox<Estilo> boxEstilo;
	
	private JButton btnBuscar;
	private JButton btnCancelar;
	
	private JFrame frmVentanaPrincipal;
	
	
	public PanelExplorar(JFrame frmVentanaPrincipal) {
		super();
		inicialize();
		this.frmVentanaPrincipal = frmVentanaPrincipal;
	}
	
	private void inicialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Parte superior
		JPanel panelSuperior = new JPanel();
		JPanel panelCampos = crearPanelCampos();
		JPanel panelBotones = crearPanelBotones();
		
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
		panelSuperior.add(Box.createRigidArea(new Dimension(60, 20)));
		panelSuperior.add(panelCampos);
		panelSuperior.add(Box.createRigidArea(new Dimension(60, 20)));
		panelSuperior.add(panelBotones);
		this.add(panelSuperior);
		
		// Parte central
		panelCentral = new JPanel();
		panelCentral.setVisible(false);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.X_AXIS));
		this.add(Box.createRigidArea(new Dimension(20, 20)));
		this.add(panelCentral);
	}
	
	
	/**
	 * Crea panel de campos de texto
	 */
	public JPanel crearPanelCampos() {
		panelCampos = new JPanel();
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.X_AXIS));
		
		campoTitulo = new JTextField();
		VentanaPrincipal.fixedSize(campoTitulo, 200, 20);
		panelCampos.add(lblTitulo);
		lblTitulo.setLabelFor(campoTitulo);
		panelCampos.add(campoTitulo);
		panelCampos.add(Box.createRigidArea(new Dimension(60, 20)));
		
		
		campoInterprete = new JTextField();
		VentanaPrincipal.fixedSize(campoInterprete, 200, 20);
		panelCampos.add(lblInterprete);
		lblInterprete.setLabelFor(campoInterprete);
		panelCampos.add(campoInterprete);
		panelCampos.add(Box.createRigidArea(new Dimension(60, 20)));
		
		
		boxEstilo = new JComboBox<Estilo>();
		VentanaPrincipal.fixedSize(boxEstilo, 200, 20);
		boxEstilo.setBackground(new Color(255,255,255));
		panelCampos.add(lblEstilo);
		lblEstilo.setLabelFor(boxEstilo);
		
		for (Estilo e : Estilo.values()) {
			boxEstilo.addItem(e);
		}
		boxEstilo.addItem(null);
		boxEstilo.setSelectedItem(null);
		panelCampos.add(boxEstilo);
		
		return panelCampos;
	}
	
	
	/**
	 * Crea panel de botones
	 */
	private JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		
		btnBuscar = new JButton("Buscar");
		panelBotones.add(btnBuscar);
		
		btnCancelar = new JButton("Cancelar");
		panelBotones.add(btnCancelar);
		
		crearManejadorBotonBuscar();
		crearManejadorBotonCancelar();
		
		return panelBotones;
	}

	
	/**
	 * Crea manejador para el botón de buscar
	 */
	private void crearManejadorBotonBuscar() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titulo = campoTitulo.getText();
				String interprete = campoInterprete.getText();
				ArrayList<Cancion> canciones;
				if (interprete.trim().isEmpty()) {
					Estilo estilo = (boxEstilo.getSelectedItem() == null) ? null : (Estilo)boxEstilo.getSelectedItem();
					canciones = (boxEstilo.getSelectedItem() == null) ? new ArrayList<>(controlador.getCanciones()) : 
						new ArrayList<>(controlador.buscarPorEstilo(estilo.getNombre()));
				} else {
					Estilo estilo = (boxEstilo.getSelectedItem() == null) ? null : (Estilo)boxEstilo.getSelectedItem();
					canciones = (boxEstilo.getSelectedItem() == null) ? new ArrayList<>(controlador.buscarPorInterprete(interprete)) : 
						new ArrayList<>(controlador.buscarPorInterpreteEstilo(interprete, estilo.getNombre()));
				}
				// Si hay título, filtramos las canciones anteriores por título TODO ¿implementar métodos en TDSCancionDAO?
				if (!titulo.trim().isEmpty()) {
					canciones.stream().filter(c -> AppMusic.containsIgnoreCase(c.getTitulo(), titulo));
				}
				
				// Añadimos la tabla con las canciones encontradas al panel central y lo hacemos visible
				JTable tablaCanciones = new JTable(new ModeloDefinido());
			    tablaCanciones.setPreferredScrollableViewportSize(new Dimension(500,70));
			    tablaCanciones.setFillsViewportHeight(true);
			    
			    JScrollPane scrollPane = new JScrollPane(tablaCanciones);
			    panelCentral.add(scrollPane);
				panelCentral.setVisible(true);
			}
		});
	}
	
	
	/**
	 * Crea manejador para el botón de cancelar
	 */
	private void crearManejadorBotonCancelar() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frmVentanaPrincipal, 
						"¿Está seguro de que desea cancelar la búsqueda?", "Confirmar cancelar búsqueda",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					campoTitulo.setText("");
					campoInterprete.setText("");
					boxEstilo.setSelectedItem(null);
					panelCentral.setVisible(false);
				}
			}
		});
	}
}
