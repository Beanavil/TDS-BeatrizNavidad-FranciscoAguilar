package es.um.tds.vista.paneles;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import es.um.tds.utils.ComponentUtils;
import es.um.tds.vista.ModeloLista;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;

public class PanelMisListas extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Vector<String> misListas; // TODO cambiar

	public PanelMisListas() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		misListas = new Vector<String>();
		
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
		
		JPanel panelLista = new JPanel();
		//panelLista.setMinimumSize(new Dimension(200, 100));
		panelLista.setBorder(new TitledBorder(null, "Mis listas.", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JList<String> lista = new JList<String>(new ModeloLista());
		lista.setListData(misListas);
		lista.setBackground(Color.WHITE);

		panel.add(panelLista);
		panelLista.add(lista);
		
		JPanel panelTabla = new JPanel();
		panel.add(panelTabla);
		
		JTable tabla = new JTable(new ModeloTabla());
		//tabla.setPreferredScrollableViewportSize(new Dimension(350,70));
		tabla.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tabla);
		
		panelTabla.add(scrollPane);
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
