package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.utils.ComponentUtils;
import es.um.tds.vista.ModeloTablaReproducciones;
import es.um.tds.vista.Reproductor;

/**
 * Pestaña "Más reproducidas".
 * 
 * @author Beatriz y Francisco
 */
public class PanelMasReproducidas extends JPanel {
	private static final long serialVersionUID = 1L;

	private static AppMusic controlador;
	private static Reproductor repr;
	private static JTable tablaCanciones;
	private static List<Cancion> listaActual;
	
	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException
	 */
	public PanelMasReproducidas() throws BDException, DAOException {
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}
	
	/**
	 * Inicializa el panel.
	 */
	private void inicialize() {
		this.setLayout(new BorderLayout());
		
		// Inicializamos el reproductor
		try{
			repr = new Reproductor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error interno.\n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		// Parte central
		JPanel panelCentral =  crearPanelCentral();
		this.add(panelCentral, BorderLayout.CENTER);
		
		// Parte inferior
		JPanel panelInferior = crearPanelInferior();
		this.add(panelInferior, BorderLayout.SOUTH);
	}

	/**
	 * Crea el panel central que contiene la tabla con las canciones más reproducidas
	 */
	private JPanel crearPanelCentral() {
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
	    
		// Panel vacío para separar el panel central del borde superior
		JPanel panelVacio = new JPanel(new BorderLayout());
		ComponentUtils.fixedSize(panelVacio, 100, 100);
		panelCentral.add(panelVacio, BorderLayout.NORTH);
		
		// Panel que contiene la labla con las canciones
		JPanel panelTabla = new JPanel(new BorderLayout());
		ComponentUtils.fixedSize(panelTabla, 600, 182);
		panelCentral.add(panelTabla, BorderLayout.CENTER);
	    
		/*
		 * Le pedimos al controlador las canciones más reproducidas del usuario 
		 * y las metemos en la tabla
		 */
		listaActual = controlador.getCancionesMasReproducidas();
		tablaCanciones = new JTable(new ModeloTablaReproducciones(listaActual));
		tablaCanciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaCanciones.setFillsViewportHeight(true);
		/*
		 *  Definimos el comportamiento de la tabla al seleccionar una fila, que es reproducir 
		 *  la lista de canciones más reproducidas a partir de la canción seleccionada
		 */
		tablaCanciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				repr.setListaReproduccion(new ListaCanciones("Lista recientes", listaActual), 
						tablaCanciones.getSelectedRow());
	        }
		});
	    JScrollPane scrollPane = new JScrollPane(tablaCanciones);
	    panelTabla.add(scrollPane);
	    
		return panelCentral;
	}
	
	/**
	 * Crea el panel inferior, en el cual va el reproductor.
	 * @return
	 */
	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		// Añadimos el reproductor al panel
		JPanel panelRepr = (JPanel) repr.getPanelReproductor();
		panelInferior.add(panelRepr);
		return panelInferior;
	}
	
	/**
	 *  Refresca la pestaña de "Más reproducidas"
	 */
	public static void refrescar() {
		// Volvemos a cargar la lista de más reproducidas y refrescamos la tabla
		listaActual = controlador.getCancionesMasReproducidas();
		((ModeloTablaReproducciones)tablaCanciones.getModel()).setListaCanciones(listaActual);		
		tablaCanciones.repaint();
		tablaCanciones.revalidate();
	}
	
}
