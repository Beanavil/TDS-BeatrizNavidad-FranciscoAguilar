package es.um.tds.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import es.um.tds.controlador.AppMusic;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

public class VentanaPrincipal {

	private AppMusic controlador = AppMusic.getUnicaInstancia();
	private JFrame frmVentanaPrincipal;
	private JPanel panelExplorar;
	private JPanel panelNuevaLista;
	private JPanel panelRecientes;
	private JPanel panelMisListas;
	private JPanel panelPulsador;
	
	/**
	 * Constructor
	 */
	public VentanaPrincipal() { // TODO saber el usuario actual
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
		panelSuperior.add(crearPanelBotones(), BorderLayout.EAST);
		
		
		// Parte izquierda
		
		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		JPanel panelIzquierdo_2 = new JPanel();
		panelIzquierdo_2.setLayout(new BoxLayout(panelIzquierdo_2, BoxLayout.Y_AXIS));
		panelIzquierdo.add(panelIzquierdo_2, BorderLayout.NORTH);
		
		
		// Parte central
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setUI(new CustomTabbedPaneUI());
		panelCentral.add(tabbedPane);
		
//		// Pestañas
//		
//		panelExplorar = new JPanel();
//		tabbedPane.addTab("Explorar", null, panelExplorar, null);
//		
//		//Falta traerse aquí la declaración
//		panelNuevaLista = new PanelNuevaLista();
//		tabbedPane.addTab("Nueva lista", null, panelNuevaLista, null);
//		
//		panelRecientes = new JPanel();
//		tabbedPane.addTab("Reciente", null, panelRecientes, null);
//		
//		panelMisListas = new JPanel();
//		tabbedPane.addTab("Mis listas", null, panelMisListas, null);
		
		BufferedImage iconE = null;
		BufferedImage iconNL = null;
		BufferedImage iconR = null;
		BufferedImage iconML = null;
		BufferedImage iconAC = null;
		try {
			iconE = ImageIO.read(new File("./resources/explorar-icon.png")); 
			iconNL = ImageIO.read(new File("./resources/nueva-lista-icon.png")); 
			iconR = ImageIO.read(new File("./resources/recientes-icon.png")); 
			iconML = ImageIO.read(new File("./resources/mis-listas-icon.png")); 
			iconAC = ImageIO.read(new File("./resources/add-canciones-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Pestañas con los iconos correspondientes
		panelRecientes = new PanelRecientes();
		ImageIcon tabIcon = new ImageIcon(iconR);
		Image image = tabIcon.getImage();
		Image scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Recientes", tabIcon, panelRecientes);
		
		panelExplorar = new PanelExplorar(frmVentanaPrincipal);
		tabIcon = new ImageIcon(iconE);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Explorar", tabIcon, panelExplorar);
		
		panelPulsador = new PanelPulsador();
		tabIcon = new ImageIcon(iconAC);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Cargar canciones", tabIcon, panelPulsador);
		
		panelNuevaLista = new PanelNuevaLista();
		tabIcon = new ImageIcon(iconNL);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Nueva lista", tabIcon, panelNuevaLista);
		
		panelMisListas = new JPanel();
		tabIcon = new ImageIcon(iconML);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Mis listas", tabIcon, panelMisListas);
		
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


	/**
	 * Crea botones superiores (mejorar cuenta y logout)
	 */
	public JPanel crearPanelBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		panel.add(Box.createRigidArea(new Dimension(105,40)));
		
		panel.add(new JLabel("Hola, " + controlador.getUsuarioActual().getLogin()));
		
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		JButton btnMejora = new JButton("Mejora tu cuenta");
		panel.add(btnMejora);
		
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		JButton btnLogout = new JButton("Logout");
		panel.add(btnLogout);
		
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		crearManejadorBotonMejora(btnMejora);
		crearManejadorBotonLogout(btnLogout);
		
		return panel;
	}
	
	
	/**
	 * Crea manejador para el botón de logout
	 */
	private void crearManejadorBotonLogout(JButton btnLogout) {
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frmVentanaPrincipal, 
						"¿Está seguro de que desea cerrar sesión?", "Confirmar cerrar sesión",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					controlador.setUsuarioActual(null);
					VentanaLogin window = new VentanaLogin();
					window.mostrarVentana();
					frmVentanaPrincipal.dispose();
				}
			}
		});
	}
	
	/**
	 * Crea manejador para el botón de mejora de la cuenta
	 */
	private void crearManejadorBotonMejora(JButton btnMejora) {
		btnMejora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				int result = JOptionPane.showConfirmDialog(frmVentanaPrincipal, 
//						"¿Está seguro de que desea cerrar sesión?", "Confirmar cerrar sesión",
//						JOptionPane.YES_NO_OPTION);
//				if (result == JOptionPane.YES_OPTION) {
//					VentanaLogin window = new VentanaLogin();
//					window.mostrarVentana();
//					frmVentanaPrincipal.dispose();
//				}
			}
		});
	}

	
	/**
	 * Fija el tamaño de un componente
	 */
	public static void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
	}
	
}
