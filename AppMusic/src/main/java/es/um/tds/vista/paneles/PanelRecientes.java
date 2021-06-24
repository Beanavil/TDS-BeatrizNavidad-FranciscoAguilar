package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.um.tds.controlador.AppMusic;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.persistencia.DAOException;
import es.um.tds.utils.ComponentUtils;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;

public class PanelRecientes extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private AppMusic controlador;

	public PanelRecientes() throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, 
	DAOException {
		
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
		ComponentUtils.fixedSize(panelVacio, 20, 20);
		panelCentral.add(panelVacio, BorderLayout.NORTH);
		
		JPanel panelTabla = new JPanel(new BorderLayout());
		//ComponentUtils.fixedSize(panelTabla, 600, 200);
		panelCentral.add(panelTabla, BorderLayout.CENTER);
		
		JTable tablaCanciones = new JTable(new ModeloTabla(new ListaCanciones("Canciones recientes", 
				controlador.getCancionesRecientes())));
		tablaCanciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaCanciones.setFillsViewportHeight(true);
		
	    JScrollPane scrollPane = new JScrollPane(tablaCanciones);
	    panelTabla.add(scrollPane, BorderLayout.CENTER);
	    
		return panelCentral;
	}
	
	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		Reproductor repr = null;
		try{
			repr = new Reproductor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelInferior, "Error interno.\n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		JPanel panelRepr = (JPanel) repr.getPanelReproductor();
		//ComponentUtils.fixedSize(panelRepr, 250, 50);
		panelInferior.add(panelRepr);
		
		return panelInferior;
	}
}
