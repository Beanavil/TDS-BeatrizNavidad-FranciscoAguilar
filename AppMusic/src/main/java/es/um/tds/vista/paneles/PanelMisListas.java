package es.um.tds.vista.paneles;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.um.tds.utils.ComponentUtils;
import es.um.tds.vista.ModeloLista;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;

public class PanelMisListas extends JPanel{

	private static final long serialVersionUID = 1L;

	public PanelMisListas() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panelSuperior = crearPanelSuperior();
		JPanel panelInferior = crearPanelInferior();
		
		this.add(panelSuperior);
		this.add(panelInferior);
	}
	
	/**
	 * Crea el panel que contendrá la lista de las PlayLists del usuario y la tabla con las
	 * canciones que componen cada una de estas listas
	 */
	private JPanel crearPanelSuperior() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		JList<String> lista = new JList<String>(new ModeloLista());
		
		JTable tabla = new JTable(new ModeloTabla());
		tabla.setPreferredScrollableViewportSize(new Dimension(350,70));
		tabla.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tabla);
		
		panel.add(lista);
		panel.add(scrollPane);
		return panel;
	}
	
	/**
	 * Crea el panel que contendrá el reproductor
	 */
	private JPanel crearPanelInferior() {
		Reproductor repr = null;
		try{
			repr = new Reproductor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error interno.\n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		JPanel panel = (JPanel) repr.getPanelReproductor();
		ComponentUtils.fixedSize(panel, 250, 50);
		
		return panel;
	}
}
