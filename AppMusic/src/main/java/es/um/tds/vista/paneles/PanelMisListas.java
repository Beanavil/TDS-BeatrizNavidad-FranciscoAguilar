package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.um.tds.modelo.ListaCanciones;
import es.um.tds.vista.ModeloLista;
import es.um.tds.vista.ModeloTabla;

public class PanelMisListas extends JPanel{

	private static final long serialVersionUID = 1L;

	public PanelMisListas() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panelSuperior = crearPanelSuperior();
		//Puede ser que esto haya que hacerlo con el reproductor
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
	
// Lista visible de las listas de canciones del usuario
//  DefaultListModel<String> model = new DefaultListModel<String>(); 
//  model.add(0, "Lista 1");
//  model.add(1, "Lista 2");
//  JList<String> list = new JList<String>(model); 
//  list.setBorder(BorderFactory.createLineBorder(Color.GRAY));
}
