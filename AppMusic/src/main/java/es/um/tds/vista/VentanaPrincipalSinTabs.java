package es.um.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import pulsador.Luz;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class VentanaPrincipalSinTabs {
private JFrame frmVentanaPrincipal;
	
	/**
	 * Constructor
	 */
	public VentanaPrincipalSinTabs() { // TODO saber el usuario actual
		initialize();
	}

	
	/**
	 * Muestra la ventana principal
	 */
	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
	}
	
	
	/**
	 * Inicializa la ventana principal
	 */
	public void initialize() {
		frmVentanaPrincipal = new JFrame();
		frmVentanaPrincipal.setTitle("AppMusic");
		frmVentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVentanaPrincipal.setSize(700, 500);
		frmVentanaPrincipal.setMinimumSize(new Dimension(700, 500));
		
		//Parte superior
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BorderLayout());
		
		JPanel panelSuperior_2 = new JPanel();
		panelSuperior_2.setLayout(new BoxLayout(panelSuperior_2, BoxLayout.X_AXIS));
		panelSuperior_2.add(Box.createRigidArea(new Dimension(105,40)));
		panelSuperior_2.add(new JLabel("Hola usuario"));
		panelSuperior_2.add(Box.createRigidArea(new Dimension(10,10)));
		panelSuperior_2.add(new JButton("Mejora tu cuenta"));
		panelSuperior_2.add(Box.createRigidArea(new Dimension(10,10)));
		panelSuperior_2.add(new JButton("Logout"));
		panelSuperior_2.add(Box.createRigidArea(new Dimension(10,10)));
		
		panelSuperior.add(panelSuperior_2, BorderLayout.EAST);
		
		
		// Parte izquierda
		
		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		JPanel panelIzquierdo_2 = new JPanel();
		panelIzquierdo_2.setLayout(new BoxLayout(panelIzquierdo_2, BoxLayout.Y_AXIS));
		JTabbedPane tabPane = new JTabbedPane(JTabbedPane.VERTICAL, JTabbedPane.WRAP_TAB_LAYOUT);
		
		// Paneles para cada pestaña
		JPanel panelExplorar = new JPanel(new BorderLayout());
		panelExplorar.add(new JTextArea(20,60));
		JPanel panelNuevaLista = new JPanel(new BorderLayout());
		panelNuevaLista.add(new JTextArea(30,30));
		JPanel panelReciente = new JPanel(new BorderLayout());
		panelReciente.add(new JTextArea(30,30));
		JPanel panelMisListas = new JPanel(new BorderLayout());
		panelMisListas.add(new JTextArea(30,30));
		
		// Añadimos las pestañas
		tabPane.addTab("Mis listas", null, panelReciente, null);
		tabPane.addTab("Reciente", null, panelReciente, null);
		tabPane.addTab("Nueva lista", null, panelNuevaLista, null);
		tabPane.addTab("Explorar", null, panelExplorar, null);
		
		
		// Lista visible de las listas de canciones del usuario
//		DefaultListModel<String> model = new DefaultListModel<String>(); 
//		model.add(0, "Lista 1");
//		model.add(1, "Lista 2");
//		JList<String> list = new JList<String>(model); 
//		list.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
		
		panelIzquierdo_2.add(tabPane);
		panelIzquierdo.add(panelIzquierdo_2, BorderLayout.NORTH);
		//panelIzquierdo.add(list, BorderLayout.CENTER);
		
		// Parte central
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		
		// Parte derecha
		JPanel panelDerecho = new JPanel();
		panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
				
		// Parte inferior
		JPanel panelInferior = new JPanel(new FlowLayout());
		
		// Panel principal
		JPanel panelPrincipal = (JPanel) frmVentanaPrincipal.getContentPane();
		panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelPrincipal.setLayout(new BorderLayout());
	
		panelPrincipal.add(panelSuperior,BorderLayout.NORTH);
		panelPrincipal.add(panelInferior,BorderLayout.SOUTH);
		panelPrincipal.add(panelIzquierdo,BorderLayout.WEST);
		panelPrincipal.add(panelDerecho,BorderLayout.EAST);
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);


		frmVentanaPrincipal.pack();
		
		mostrarVentana();
	}
}
