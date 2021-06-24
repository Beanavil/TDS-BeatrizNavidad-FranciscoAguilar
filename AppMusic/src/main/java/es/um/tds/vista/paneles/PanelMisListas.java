package es.um.tds.vista.paneles;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.um.tds.vista.ModeloLista;
import es.um.tds.vista.ModeloTabla;

public class PanelMisListas extends JPanel{

	private static final long serialVersionUID = 1L;

	public PanelMisListas() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panelSuperior = crearPanelSuperior();
		//TODO Puede ser que esto haya que hacerlo con el reproductor
		//JPanel panelInferior = crearPanelInferior();
		
		this.add(panelSuperior);
	}
	
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
}
