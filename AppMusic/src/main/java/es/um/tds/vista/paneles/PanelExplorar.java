package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import es.um.tds.controlador.AppMusic;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.Estilo;
import es.um.tds.persistencia.DAOException;
import es.um.tds.utils.ComponentUtils;
import es.um.tds.utils.StringUtils;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;

public class PanelExplorar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private AppMusic controlador;
	
	private JPanel panelCentral;
	private JPanel panelCampos;
	private JPanel panelInferior;
	
	private JTextField campoTitulo;
	private JTextField campoInterprete;
	
	private final JLabel lblTitulo = new JLabel("Título");
	private final JLabel lblInterprete = new JLabel("Intérprete");
	private final JLabel lblEstilo = new JLabel("Estilo");
	
	private JComboBox<Estilo> boxEstilo;

	
	private JButton btnBuscar;
	private JButton btnCancelar;
	
	
	public PanelExplorar() throws InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, 
	ClassNotFoundException, DAOException {
		
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}
	
	private void inicialize() {
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		
		// Parte superior
		JPanel panelSuperior = crearPanelSuperior();
		this.add(panelSuperior, BorderLayout.NORTH);
		
		// Parte central
		crearPanelCentral();
		this.add(panelCentral, BorderLayout.CENTER);
		
		// Parte inferior
		crearPanelInferior();
		this.add(panelInferior, BorderLayout.SOUTH);
	}
	
	
	/**
	 * Crea el panel superior
	 */
	public JPanel crearPanelSuperior() {
		JPanel panelSuperior = new JPanel();
		crearPanelCampos();
		JPanel panelBotones = crearPanelBotones();
		
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
		panelSuperior.add(Box.createRigidArea(new Dimension(20, 20)));
		panelSuperior.add(panelCampos);
		panelSuperior.add(Box.createRigidArea(new Dimension(20, 20)));
		panelSuperior.add(panelBotones);
		
		return panelSuperior;
	}
	
	
	/**
	 * Crea el panel central
	 */
	public void crearPanelCentral() {
		panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.setVisible(false);
	}
	
	
	/**
	 * Crea el panel inferior (reproductor)
	 */
	public void crearPanelInferior() {
		panelInferior = new JPanel();
		panelInferior.setVisible(false);
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		
		Reproductor repr = null;
		try{
			repr = new Reproductor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelInferior, "Error interno.\n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		JPanel panelRepr = (JPanel) repr.getPanelReproductor();
		//VentanaPrincipal.fixedSize(panelRepr, 250, 50);
		panelInferior.add(panelRepr);
	}
	
	
	/**
	 * Crea panel de campos de texto del panel superior
	 */
	public void crearPanelCampos() {
		panelCampos = new JPanel();
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.X_AXIS));
		
		JPanel panelTitulo = new JPanel(new FlowLayout());
		JPanel panelInterprete = new JPanel(new FlowLayout());
		JPanel panelEstilo = new JPanel(new FlowLayout());
		
		ComponentUtils.fixedSize(panelTitulo, 300, 30);
		ComponentUtils.fixedSize(panelInterprete, 300, 30);
		ComponentUtils.fixedSize(panelEstilo, 250, 30);
		
		panelCampos.add(panelTitulo);
		panelCampos.add(panelInterprete);
		panelCampos.add(panelEstilo);
		
		// Campo título
		campoTitulo = new JTextField();
		ComponentUtils.fixedSize(campoTitulo, 200, 20);
		panelTitulo.add(lblTitulo);
		lblTitulo.setLabelFor(campoTitulo);
		panelTitulo.add(campoTitulo);
			
		// Campo intérprete
		campoInterprete = new JTextField();
		ComponentUtils.fixedSize(campoInterprete, 200, 20);
		panelInterprete.add(lblInterprete);
		lblInterprete.setLabelFor(campoInterprete);
		panelInterprete.add(campoInterprete);
		
		// ComboBox estilo
		boxEstilo = new JComboBox<Estilo>();
		boxEstilo.setBackground(new Color(255,255,255));
		lblEstilo.setLabelFor(boxEstilo);
		ComponentUtils.fixedSize(boxEstilo, 150, 20);
		
		for (Estilo e : Estilo.values()) {
			boxEstilo.addItem(e);
		}
		boxEstilo.addItem(null);
		boxEstilo.setSelectedItem(null);
		
		panelEstilo.add(lblEstilo);
		panelEstilo.add(boxEstilo);
	}
	
	
	/**
	 * Crea panel de botones del panel superior
	 */
	private JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		
		JPanel panelBuscar = new JPanel(new FlowLayout());
		JPanel panelCancelar = new JPanel(new FlowLayout());
		
		ComponentUtils.fixedSize(panelBuscar, 100, 60);
		ComponentUtils.fixedSize(panelCancelar, 100, 60);
		
		panelBotones.add(panelBuscar);
		panelBotones.add(panelCancelar);
		
		btnBuscar = new JButton("Buscar");
		panelBuscar.add(btnBuscar);
		
		btnCancelar = new JButton("Cancelar");
		panelCancelar.add(btnCancelar);
		
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
				List<Cancion> canciones;
				if (interprete.trim().isEmpty()) {
					Estilo estilo = (boxEstilo.getSelectedItem() == null) ? null : (Estilo)boxEstilo.getSelectedItem();
					canciones = (boxEstilo.getSelectedItem() == null) ? new ArrayList<>(controlador.getCanciones()) : 
						new ArrayList<>(controlador.buscarPorEstilo(estilo.getNombre()));
				} else {
					Estilo estilo = (boxEstilo.getSelectedItem() == null) ? null : (Estilo)boxEstilo.getSelectedItem();
					canciones = (boxEstilo.getSelectedItem() == null) ? new ArrayList<>(controlador.buscarPorInterprete(interprete)) : 
						new ArrayList<>(controlador.buscarPorInterpreteEstilo(interprete, estilo.getNombre()));
				}
				// Si hay título, filtramos las canciones anteriores por título
				if (!titulo.trim().isEmpty()) {
					canciones.stream().filter(c -> StringUtils.containsIgnoreCase(c.getTitulo(), titulo));
				}
				
				// Añadimos un nuevo panel con la tabla de las canciones encontradas al panel central y lo hacemos visible
				JPanel panelTabla = new JPanel(new BorderLayout());
				//ComponentUtils.fixedSize(panelTabla, 600, 200);
			    
				JTable tablaCanciones = new JTable(new ModeloTabla(canciones));
				tablaCanciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				tablaCanciones.setFillsViewportHeight(true);
				
			    JScrollPane scrollPane = new JScrollPane(tablaCanciones);
			    panelTabla.add(scrollPane, BorderLayout.CENTER);
			    
			    panelCentral.add(panelTabla);
				panelCentral.setVisible(true);
				
				// Hacemos visible el panel inferior(reproductor)
				panelInferior.setVisible(true);
			}
		});
	}
	
	/**
	 * Crea manejador para el botón de cancelar
	 */
	private void crearManejadorBotonCancelar() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(panelCampos, 
						"¿Está seguro de que desea cancelar la búsqueda?", "Confirmar cancelar búsqueda",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					campoTitulo.setText("");
					campoInterprete.setText("");
					boxEstilo.setSelectedItem(null);
					panelCentral.removeAll();
					panelCentral.setVisible(false);
					panelInferior.setVisible(false);
				}
			}
		});
	}
}
