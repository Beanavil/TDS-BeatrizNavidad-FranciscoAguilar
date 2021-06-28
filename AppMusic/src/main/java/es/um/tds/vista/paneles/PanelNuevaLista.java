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
import javax.swing.border.TitledBorder;

import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.Estilo;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.utils.ComponentUtils;
import es.um.tds.utils.StringUtils;
import es.um.tds.vista.ModeloTabla;

/**
 * Pestaña "Nueva lista".
 * 
 * @author Beatriz y Francisco
 */
public class PanelNuevaLista extends JPanel {
	private static final long serialVersionUID = 1L;
	private AppMusic controlador;
	private String nombreLista;
		
	private JButton btnCrear;
	private JButton btnEliminar;
	private JButton btnBuscar;
	private JButton btnAdd;
	private JButton btnRetirar;
	private JButton btnFinalizar;
	
	private JTextField campoCrear;
	private JTextField campoInterprete;
	private JTextField campoTitulo;
	
	private JTable tablaIzq;
	private JTable tablaDer;

	private JPanel panelTablaDer;
	private JPanel panelInvisible;
	
	private JComboBox<Estilo> boxEstilo;
	
	private static final int TAM_CUADRO_TEXTO = 10;	
	
	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException 
	 */
	public PanelNuevaLista() throws BDException, DAOException{
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}

	/**
	 * Inicializa el panel.
	 */
	private void inicialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel panelCrear = crearPanelCrear();
		
		panelInvisible = new JPanel();
		panelInvisible.setLayout(new BorderLayout());
		panelInvisible.setVisible(false);
		
		JPanel panelBuscar = crearPanelBuscar();
		JPanel panelTablas = crearPanelTablas();
		JPanel panelFinalizar = crearPanelFinalizar();
		
		panelInvisible.add(panelBuscar, BorderLayout.NORTH);
		panelInvisible.add(panelTablas, BorderLayout.CENTER);
		panelInvisible.add(panelFinalizar, BorderLayout.SOUTH);
		
		this.add(panelCrear);
		this.add(panelInvisible);
	}
	
	/**
	 * Crea el panel con los elementos para crear una nueva lista.
	 */
	private JPanel crearPanelCrear() {
		JPanel panel = new JPanel();
		
		// Botón crear
		JLabel lblCrear = new JLabel("Nombre");
		campoCrear = new JTextField(TAM_CUADRO_TEXTO);
		lblCrear.setLabelFor(campoCrear);
		btnCrear = new JButton("Crear");
		
		// Botón eliminar
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setVisible(false);

		// Añadimos los elementos al panel
		panel.add(lblCrear);
		panel.add(campoCrear);
		panel.add(btnCrear);
		panel.add(btnEliminar);

		//Manejadores de los botones
		crearManejadorBotonCrear();
		crearManejadorBotonEliminar();
		
		return panel;
	}
	
	/**
	 * Crea el panel con los objetos para buscar canciones según intérprete, título y estilo.
	 */
	private JPanel crearPanelBuscar() {
		// Definición de los objetos necesarios
		JPanel panel = new JPanel();
		JLabel lblTitulo = new JLabel("Título");
		JLabel lblInterprete = new JLabel("Intérprete");
		JLabel lblEstilo = new JLabel("Estilo");
		
		campoTitulo = new JTextField(TAM_CUADRO_TEXTO);
		campoInterprete = new JTextField(TAM_CUADRO_TEXTO);
		boxEstilo = new JComboBox<Estilo>();
		btnBuscar = new JButton("Buscar");
		
		// Campo título
		panel.add(lblTitulo);
		lblTitulo.setLabelFor(campoTitulo);
		panel.add(campoTitulo);
		
		// Campo intérprete
		panel.add(lblInterprete);
		lblInterprete.setLabelFor(campoInterprete);
		panel.add(campoInterprete);
		
		// Campo estilo
		boxEstilo.setBackground(new Color(255,255,255));
		panel.add(lblEstilo);
		lblEstilo.setLabelFor(boxEstilo);
		// Añadimos los elementos del ComboBox
		for (Estilo e : Estilo.values()) {
			boxEstilo.addItem(e);
		}
		boxEstilo.addItem(null);
		boxEstilo.setSelectedItem(null);
		panel.add(boxEstilo);
		
		panel.add(btnBuscar);
		crearManejadorBotonBuscar();
		
		return panel;
	}

	/**
	 * Crea el panel que contiene las tablas con las canciones de la bd y las de la lista.
	 */
	private JPanel crearPanelTablas() {
		// Inicializamos el panel
		JPanel panel = new JPanel();
		ComponentUtils.fixedSize(panel, 350, 250);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		// Tabla de canciones de la bd
		tablaIzq = new JTable(new ModeloTabla());
		tablaIzq.setPreferredScrollableViewportSize(new Dimension(350,70));
		tablaIzq.setFillsViewportHeight(true);
		JScrollPane scrollPaneIzq = new JScrollPane(tablaIzq);
		panel.add(scrollPaneIzq);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		// Panel de botones para añadir o eliminar canciones de la lista
		JPanel panelBtn = crearPanelBtn();
		panel.add(panelBtn);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		// Tabla de canciones de la lista en creación
		panelTablaDer = new JPanel();
		panelTablaDer.setBorder(new TitledBorder(null, "PlayList", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tablaDer = new JTable(new ModeloTabla());
		tablaDer.setPreferredScrollableViewportSize(new Dimension(350,200));
		tablaDer.setFillsViewportHeight(true);
		JScrollPane scrollPaneDer = new JScrollPane(tablaDer);
		panelTablaDer.add(scrollPaneDer);
		panel.add(panelTablaDer);
		
		return panel;
	}
	
	/**
	 * Crea el panel con el botón de finalizar.
	 */
	private JPanel crearPanelFinalizar() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
		
		btnFinalizar = new JButton("Finalizar");
		panel.add(btnFinalizar);
		
		crearManejadorBotonFinalizar();

		return panel;
	}
	
	/**
	 * Crea el panel que contendrá los botones añadir y eliminar canciones de la lista.
	 * @return Panel
	 */
	private JPanel crearPanelBtn() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// Botón añadir
		btnAdd = new JButton(">>");
		panel.add(btnAdd);
		//Botón eliminar
		btnRetirar = new JButton("<<");
		panel.add(btnRetirar);
		
		crearManejadorBotonAdd();
		crearManejadorBotonRetirar();
		
		return panel;
	}
	
	/**
	 * Crear manejador para el botón de crear una lista.
	 */
	private void crearManejadorBotonCrear() {
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cargamos las canciones del catálogo en la tabla
				((ModeloTabla)tablaIzq.getModel()).setListaCanciones(controlador.getCanciones());
				// Obtenemos el nombre de la lista a crear/modificar
				nombreLista = campoCrear.getText();
				
				// Si la lista no existe, se crea
				if (!controlador.existeLista(nombreLista)) {
					int result = JOptionPane.showOptionDialog(panelInvisible, 
								"¿Desea crear una nueva lista?", "Crear nueva Lista",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								new String[]{"Sí", "No"}, "default");
					
					if (result == JOptionPane.YES_OPTION) {
						// Creamos la lista
						controlador.crearLista(nombreLista);
						// La cargamos en la tabla de la derecha
						ListaCanciones lista = controlador.getListaCanciones(nombreLista);
						((ModeloTabla)tablaDer.getModel()).setListaCanciones(lista.getCanciones());
						// Hacemos visible el panel y refrescamos la tabla derecha
						panelInvisible.setVisible(true);
						btnEliminar.setVisible(false);
						tablaDer.revalidate();
						tablaDer.repaint();
					} else return;
				}
				
				// Si la lista existe, se da la opción de modificar
				else {
					int result = JOptionPane.showOptionDialog(panelInvisible, 
								"¿Desea modificarla?", "Ya existe una lista con ese nombre",
								JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,
								new String[]{"Sí", "No"}, "default");
						if (result == JOptionPane.YES_OPTION) {
							// La cargamos en la tabla de la derecha
							ListaCanciones lista = controlador.getListaCanciones(nombreLista);
							((ModeloTabla)tablaDer.getModel()).setListaCanciones(lista.getCanciones());
							// Hacemos visible el panel y refrescamos la tabla derecha
							panelInvisible.setVisible(true);
							btnEliminar.setVisible(true);
							tablaDer.revalidate();
							tablaDer.repaint();	
						}
				}
				PanelMisListas.refrescar(); // refrescamos la pestaña de "Mis listas"
			}
		});
	}
	
	/**
	 * Crear manejador para el botón "Eliminar".
	 */
	private void crearManejadorBotonEliminar() {
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showOptionDialog(panelInvisible, 
							"¿Está seguro de que desea eliminar la lista?", "Confirmar eliminar lista",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[]{"Sí", "No"}, "default");
				if (result == JOptionPane.YES_OPTION) {
					// Eliminamos la lista
					ListaCanciones lista = controlador.getListaCanciones(nombreLista);
					controlador.eliminarLista(lista);
					// Vaciamos los campos y hacemos invisibles los elementos necesarios
					campoCrear.setText("");
					campoTitulo.setText("");
					campoInterprete.setText("");
					boxEstilo.setSelectedItem(null);
					panelInvisible.setVisible(false);
					btnEliminar.setVisible(false);
					PanelMisListas.refrescar();
				}
			}
		});
	}
	
	/**
	 * Crea manejador para el botón de buscar.
	 */
	private void crearManejadorBotonBuscar() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				// Actualizamos la tabla izquierda
				((ModeloTabla)tablaIzq.getModel()).setListaCanciones(cancionesEncontradas);
				panelInvisible.updateUI();
			}
		});
	}
	
	/**
	 * Crea manejador para el botón "Finalizar".
	 */
	private void crearManejadorBotonFinalizar() {
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showOptionDialog(panelInvisible, 
							"¿Está seguro de que desea finalizar la creación?", "Confirmar finalizar creación",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[]{"Sí", "No"}, "default");
				if (result == JOptionPane.YES_OPTION) {
					// Vaciamos los campos y hacemos invisibles los elementos necesarios
					campoCrear.setText("");
					campoTitulo.setText("");
					campoInterprete.setText("");
					boxEstilo.setSelectedItem(null);
					btnEliminar.setVisible(false);
					panelInvisible.setVisible(false);
				}
			}
		});
	}

	/**
	 * Crea el manejador para el boton "Añadir".
	 */
	private void crearManejadorBotonAdd() {
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 // Si no hay ninguna canción seleccionada, no hacemos nada
				 int fila = tablaIzq.getSelectedRow();
				 if (fila < 0) 
					 return;
				 // En caso contrario, añadimos la canción a la lista
				 ListaCanciones lista = controlador.getListaCanciones(nombreLista);
				 Cancion cancion = ((ModeloTabla)tablaIzq.getModel()).getListaCanciones().get(fila);
				 controlador.addCancionToLista(lista, cancion);
				 // La mostramos en la tabla de la derecha
				 ((ModeloTabla)tablaDer.getModel()).setListaCanciones(lista.getCanciones());
				 // Refrescamos la tabla derecha y la pestaña "Mis listas"
				 tablaDer.revalidate();
				 tablaDer.repaint();	
				 PanelMisListas.refrescar();
			}
		});
	}
	
	/**
	 * Crea el manejador para el boton "Retirar".
	 */
	private void crearManejadorBotonRetirar() {
		btnRetirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 // Si no hay ninguna canción seleccionada, no hacemos nada
				 int fila = tablaDer.getSelectedRow();
				 if (fila < 0) 
					 return;
				 // En caso contrario, la eliminamos la lista
				 ListaCanciones lista = controlador.getListaCanciones(nombreLista);
				 Cancion cancion = ((ModeloTabla)tablaDer.getModel()).getListaCanciones().get(fila);
				 controlador.eliminarCancionFromLista(lista, cancion);
				 // La eliminamos de la tabla de la derecha
				 ((ModeloTabla)tablaDer.getModel()).setListaCanciones(lista.getCanciones());
				 // Refrescamos la tabla derecha y la pestaña "Mis listas"
				 tablaDer.revalidate();
				 tablaDer.repaint(); 
				 PanelMisListas.refrescar();
			}
		});
	}
}
