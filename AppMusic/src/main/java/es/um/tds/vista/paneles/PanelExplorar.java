package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.Estilo;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.utils.ComponentUtils;
import es.um.tds.utils.StringUtils;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;

/**
 * Pestaña "Explorar".
 * 
 * @author Beatriz y Francisco
 */
public class PanelExplorar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private AppMusic controlador;
	private ListaCanciones resultadoBusqueda;
	private Reproductor repr;
	
	private JPanel panelCentral;
	private JPanel panelCampos;
	private JPanel panelInferior;
	
	private final JLabel lblTitulo = new JLabel("Título");
	private final JLabel lblInterprete = new JLabel("Intérprete");
	private final JLabel lblEstilo = new JLabel("Estilo");
	
	private JTextField campoTitulo;
	private JTextField campoInterprete;
	
	private JButton btnBuscar;
	private JButton btnCancelar;
	
	private JComboBox<Estilo> boxEstilo;
	
	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException
	 */
	public PanelExplorar() throws BDException, DAOException {
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}
	
	/**
	 * Inicializa el panel.
	 */
	private void inicialize() {
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
	 * Crea el panel superior.
	 */
	public JPanel crearPanelSuperior() {
		// Inicializamos el panel
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
		// Creamos los paneles de los campos y los botones
		crearPanelCampos();
		JPanel panelBotones = crearPanelBotones();
		// Añadimos todo al panel
		panelSuperior.add(Box.createRigidArea(new Dimension(20, 20)));
		panelSuperior.add(panelCampos);
		panelSuperior.add(Box.createRigidArea(new Dimension(20, 20)));
		panelSuperior.add(panelBotones);
		
		return panelSuperior;
	}
	
	/**
	 * Crea el panel central.
	 */
	public void crearPanelCentral() {
		panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		// Ocultamos el panel porque solo es visible al hacer una búsqueda
		panelCentral.setVisible(false);
	}
	
	/**
	 * Crea el panel inferior (reproductor).
	 */
	public void crearPanelInferior() {
		panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		// Ocultamos el panel porque solo es visible al hacer una búsqueda
		panelInferior.setVisible(false);
		
		try{
			repr = new Reproductor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelInferior, "Error interno.\n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		// Creamos y añadimos el panel del reproductor
		JPanel panelRepr = (JPanel) repr.getPanelReproductor();
		panelInferior.add(panelRepr);
	}
	
	/**
	 * Crea panel de campos de texto del panel superior.
	 */
	public void crearPanelCampos() {
		panelCampos = new JPanel();
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.X_AXIS));
		
		// Inicializamos los paneles de cada sección y los añadimos al panel de campos
		JPanel panelTitulo = new JPanel(new FlowLayout());
		JPanel panelInterprete = new JPanel(new FlowLayout());
		JPanel panelEstilo = new JPanel(new FlowLayout());
		
		ComponentUtils.fixedSize(panelTitulo, 300, 30);
		ComponentUtils.fixedSize(panelInterprete, 300, 30);
		ComponentUtils.fixedSize(panelEstilo, 250, 30);
		
		panelCampos.add(panelTitulo);
		panelCampos.add(panelInterprete);
		panelCampos.add(panelEstilo);
		
		// Añadimos los componentes de cada sección
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
		// Añadimos los elementos del ComboBox y la opción de null (nada seleccionado)
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
		
		// Inicializamos los paneles de cada sección y los añadimos al panel de botones
		JPanel panelBuscar = new JPanel(new FlowLayout());
		JPanel panelCancelar = new JPanel(new FlowLayout());
		
		ComponentUtils.fixedSize(panelBuscar, 100, 60);
		ComponentUtils.fixedSize(panelCancelar, 100, 60);
		
		panelBotones.add(panelBuscar);
		panelBotones.add(panelCancelar);
		
		// Botón buscar
		btnBuscar = new JButton("Buscar");
		panelBuscar.add(btnBuscar);
		
		// Botón cancelar
		btnCancelar = new JButton("Cancelar");
		panelCancelar.add(btnCancelar);
		
		// Creamos los manejadores de los botones
		crearManejadorBotonBuscar();
		crearManejadorBotonCancelar();
		
		return panelBotones;
	}
	
	/**
	 * Crea manejador para el botón de buscar.
	 */
	private void crearManejadorBotonBuscar() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Eliminamos el contenido anterior
				panelCentral.removeAll();
				
				// Buscamos las canciones pedidas
				List<Cancion> cancionesEncontradas;
				String titulo = campoTitulo.getText();
				String interprete = campoInterprete.getText();
				Estilo estilo = (boxEstilo.getSelectedItem() == null) ? null : (Estilo)boxEstilo.getSelectedItem();
				
				if (interprete.trim().isEmpty()) {
					cancionesEncontradas = (estilo == null) ? new ArrayList<>(controlador.getCanciones()) : 
						new ArrayList<>(controlador.buscarPorEstilo(estilo.getNombre()));
				} else {
					cancionesEncontradas = (estilo == null) ? new ArrayList<>(controlador.buscarPorInterprete(interprete)) : 
						new ArrayList<>(controlador.buscarPorInterpreteEstilo(interprete, estilo.getNombre()));
				}
				// Si hay título, filtramos las canciones anteriores por título
				if (!titulo.trim().isEmpty()) {
					cancionesEncontradas = cancionesEncontradas.stream()
										 .filter(c -> StringUtils.containsIgnoreCase(c.getTitulo(), titulo))
										 .collect(Collectors.toList());
				}
				// Creamos un objeto ListaCanciones con las canciones encontradas
				resultadoBusqueda = new ListaCanciones("Resultado búsqueda", cancionesEncontradas);
				
				// Añadimos un nuevo panel con la tabla de las canciones encontradas al panel central y lo hacemos visible
				JPanel panelTabla = new JPanel(new BorderLayout());
				
				// Definimos la tabla scrolleable que contiene las canciones encontradas
				JTable tablaCanciones = new JTable(new ModeloTabla(cancionesEncontradas));
				tablaCanciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				tablaCanciones.setFillsViewportHeight(true);
				JScrollPane scrollPane = new JScrollPane(tablaCanciones);
				panelTabla.add(scrollPane, BorderLayout.CENTER);
				
				// Definimos el comportamiento de la app cuando se selecciona un elemento (canción) de la tabla:
				tablaCanciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						repr.setListaReproduccion(resultadoBusqueda, tablaCanciones.getSelectedRow());
			        }
				});
				
				// Añadimos la tabla al panel central y lo hacemos visible
			    panelCentral.revalidate();
			    panelCentral.add(panelTabla);
				panelCentral.setVisible(true);
				// Hacemos visible el panel reproductor
				panelInferior.setVisible(true);
			}
		});
	}
	
	/**
	 * Crea manejador para el botón de cancelar.
	 */
	private void crearManejadorBotonCancelar() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showOptionDialog(panelCampos, 
							"¿Está seguro de que desea cancelar la búsqueda?", "Confirmar cancelar búsqueda",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[]{"Sí", "No"}, "default");
				if (result == JOptionPane.YES_OPTION) {
					// Ocultamos el panel central y borramos los criterios de búsqueda
					panelCentral.removeAll();
					panelCentral.setVisible(false);
					panelInferior.setVisible(false);

					campoTitulo.setText("");
					campoInterprete.setText("");
					boxEstilo.setSelectedItem(null);
				}
			}
		});
	}
}
