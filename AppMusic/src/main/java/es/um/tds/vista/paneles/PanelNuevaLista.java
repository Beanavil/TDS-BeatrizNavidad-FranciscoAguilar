package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import es.um.tds.modelo.Estilo;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.VentanaPrincipal;

public class PanelNuevaLista extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final int TAM_CUADRO_TEXTO = 10;
	
	private AppMusic controlador;
		
	private JTextField txtCrear;
	private JButton btnCrear;
	private JButton btnEliminar;
	
	private JTextField txtInterprete;
	private JTextField txtTitulo;
	private JComboBox<Estilo> boxEstilo;
	private JButton btnBuscar;
	
	private JTable tablaIzq;
	private JTable tablaDer;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	//TODO ¿Es necesario añadir los paneles como atributos de la clase?
	private JPanel panelInv;
	
	/**
	 * Constructor de la clase //TODO pasar todo a método initialize();
	 */
	public PanelNuevaLista() {
		super();
		controlador = AppMusic.getUnicaInstancia();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel panel1 = crearPanel1();
		
		panelInv = new JPanel();
		panelInv.setLayout(new BorderLayout());
		panelInv.setVisible(false);
		
		JPanel panel2 = crearPanel2();
		JPanel panel3 = crearPanel3();
		JPanel panel4 = crearPanel4();
		
		panelInv.add(panel2, BorderLayout.NORTH);
		panelInv.add(panel3, BorderLayout.CENTER);
		panelInv.add(panel4, BorderLayout.SOUTH);
		
		this.add(panel1);
		this.add(panelInv);
	}
	
	/**
	 * Crea el panel con los objetos para crear una nueva playlist
	 */
	private JPanel crearPanel1() {
		JPanel panel = new JPanel();
		JLabel lblCrear = new JLabel("Nombre");
		txtCrear = new JTextField(TAM_CUADRO_TEXTO);
		btnCrear = new JButton("Crear");
		btnEliminar = new JButton("Eliminar");
		
		panel.add(lblCrear);
		lblCrear.setLabelFor(txtCrear);
		panel.add(txtCrear);
		panel.add(btnCrear);
		panel.add(btnEliminar);
		btnEliminar.setVisible(false);
		
		crearManejadorBotonCrear();
		return panel;
	}
	
	/**
	 * Crea el panel con los objetos para buscar canciones según intérprete
	 * título y estilo.
	 */
	private JPanel crearPanel2() {
		//Definición de los objetos necesarios
		JPanel panel = new JPanel();
		JLabel lblInterprete = new JLabel("Intérprete");
		JLabel lblTitulo = new JLabel("Título");
		JLabel lblEstilo = new JLabel("Estilo");
		txtInterprete = new JTextField(TAM_CUADRO_TEXTO);
		txtTitulo = new JTextField(TAM_CUADRO_TEXTO);
		boxEstilo = new JComboBox<Estilo>();
		btnBuscar = new JButton("Buscar");
		
		
		panel.add(lblInterprete);
		lblInterprete.setLabelFor(txtInterprete);
		panel.add(txtInterprete);
		
		panel.add(lblTitulo);
		lblTitulo.setLabelFor(txtTitulo);
		panel.add(txtTitulo);
		
		boxEstilo.setBackground(new Color(255,255,255));
		panel.add(lblEstilo);
		lblEstilo.setLabelFor(boxEstilo);
		
		for (Estilo e : Estilo.values()) {
			boxEstilo.addItem(e);
		}
		boxEstilo.addItem(null);
		boxEstilo.setSelectedItem(null);
		panel.add(boxEstilo);
	
		
		panel.add(btnBuscar);
		return panel;
	}

	/**
	 * Crea el panel que contiene ambas tablas
	 */
	private JPanel crearPanel3() {
		JPanel panel = new JPanel();
		VentanaPrincipal.fixedSize(panel, 350, 250);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		tablaIzq = new JTable(new ModeloTabla());
		tablaIzq.setPreferredScrollableViewportSize(new Dimension(350,70));
		tablaIzq.setFillsViewportHeight(true);
		
		JScrollPane scrollPaneIzq = new JScrollPane(tablaIzq);
		panel.add(scrollPaneIzq);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		JPanel panelBtn = crearPanelBtn();
		panel.add(panelBtn);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		JPanel panelTablaDer = new JPanel();
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
	 * Crea el panel con los botones Aceptar y Cancelar 
	 */
	private JPanel crearPanel4() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
		btnAceptar = new JButton("Aceptar");
		btnCancelar = new JButton("Cancelar");
		
		crearManejadorBotonCancelar();
		
		panel.add(btnAceptar);
		panel.add(btnCancelar);
		return panel;
	}
	
	private JPanel crearPanelBtn() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JButton btnañadir = new JButton(">>");
		JButton btnretirar = new JButton("<<");
		panel.add(btnañadir);
		panel.add(btnretirar);
		return panel;
	}
	
	/**
	 * Crear manejador para el botón "Crear"
	 */
	private void crearManejadorBotonCrear() {
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreLista = txtCrear.getText();
				if (! controlador.existeLista(nombreLista)) {
					controlador.crearLista(nombreLista);
				}
				//TODO Distinguir si existe la lista o no y proceder
				
				panelInv.setVisible(true);
			}
		});
	}
	
	/**
	 * Crear manejador para el botón "Eliminar" //TODO
	 */
	private void crearManejadorBotonEliminar() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(panelInv, 
						"¿Está seguro de que desea cancelar la búsqueda?", "Confirmar cancelar búsqueda",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					txtCrear.setText("");
					txtTitulo.setText("");
					txtInterprete.setText("");
					boxEstilo.setSelectedItem(null);
					panelInv.setVisible(false);
				}
			}
		});
	}
	
	/**
	 * Crear manejador para el botón "Buscar"
	 */
	//TODO es básicamente como el de PanelExplorar, una vez desarrollado ese copiar
//	private void crearManejadorBotonBuscar() {
//		
//	}
	
	/**
	 * Crea manejador para el botón "Cancelar"
	 */
	private void crearManejadorBotonCancelar() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(panelInv, 
						"¿Está seguro de que desea cancelar la búsqueda?", "Confirmar cancelar búsqueda",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					txtCrear.setText("");
					txtTitulo.setText("");
					txtInterprete.setText("");
					boxEstilo.setSelectedItem(null);
					panelInv.setVisible(false);
				}
			}
		});
	}
}
