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

public class PanelMasReproducidas extends JPanel {
	private static final long serialVersionUID = 1L;

	private static AppMusic controlador;
	private static JTable tablaCanciones;
	private static List<Cancion> listaActual;
	private static Reproductor repr;
	
	public PanelMasReproducidas() throws BDException, DAOException {
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}
	
	private void inicialize() {
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		
		// Parte central
		JPanel panelCentral =  crearPanelCentral();
		this.add(panelCentral, BorderLayout.CENTER);
		
		// Parte inferior
		JPanel panelInferior = crearPanelInferior();
		this.add(panelInferior, BorderLayout.SOUTH);
	}

	private JPanel crearPanelCentral() {
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
	    
		JPanel panelVacio = new JPanel(new BorderLayout());
		ComponentUtils.fixedSize(panelVacio, 100, 100);
		panelCentral.add(panelVacio, BorderLayout.NORTH);
		
		JPanel panelTabla = new JPanel(new BorderLayout());
		ComponentUtils.fixedSize(panelTabla, 600, 182);
		panelCentral.add(panelTabla, BorderLayout.CENTER);
	    
		listaActual = controlador.getCancionesMasReproducidas();
		tablaCanciones = new JTable(new ModeloTablaReproducciones(listaActual));
		tablaCanciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaCanciones.setFillsViewportHeight(true);
		
	    JScrollPane scrollPane = new JScrollPane(tablaCanciones);
	    panelTabla.add(scrollPane);
	    
		return panelCentral;
	}
	
	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		
		try{
			repr = new Reproductor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelInferior, "Error interno.\n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		JPanel panelRepr = (JPanel) repr.getPanelReproductor();
		panelInferior.add(panelRepr);
		
		tablaCanciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				repr.setListaReproduccion(new ListaCanciones("Lista recientes", listaActual), 
						tablaCanciones.getSelectedRow());
	        }
		});
		return panelInferior;
	}
	
	// Refrescar tabla de mas reproducidas
	public static void refrescar() {
		listaActual = controlador.getCancionesMasReproducidas();
		((ModeloTablaReproducciones)tablaCanciones.getModel()).setListaCanciones(listaActual);		
		tablaCanciones.repaint();
		tablaCanciones.revalidate();
	}
}
