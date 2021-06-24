package es.um.tds.vista.paneles;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.um.tds.controlador.AppMusic;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;
import es.um.tds.vista.VentanaPrincipal;

public class PanelMasReproducidas extends JPanel {
	private static final long serialVersionUID = 1L;

	private AppMusic controlador;
	
	public PanelMasReproducidas() {
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
		VentanaPrincipal.fixedSize(panelVacio, 20, 20);
		panelCentral.add(panelVacio, BorderLayout.NORTH);
		
		JPanel panelTabla = new JPanel(new BorderLayout());
		VentanaPrincipal.fixedSize(panelTabla, 600, 200);
		panelCentral.add(panelTabla, BorderLayout.CENTER);
	    
		JTable tablaCanciones = new JTable(new ModeloTabla(new ListaCanciones("Canciones más reproducidas", 
				controlador.getCancionesMasReproducidas())));
		tablaCanciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaCanciones.setFillsViewportHeight(true);
		
	    JScrollPane scrollPane = new JScrollPane(tablaCanciones);
	    panelTabla.add(scrollPane);
	    
		return panelCentral;
	}
	
	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		
		Reproductor repr = new Reproductor();
		JPanel panelRepr = (JPanel) repr.getPanelReproductor();
		VentanaPrincipal.fixedSize(panelRepr, 250, 50);
		panelInferior.add(panelRepr);
		
		return panelInferior;
	}
}
