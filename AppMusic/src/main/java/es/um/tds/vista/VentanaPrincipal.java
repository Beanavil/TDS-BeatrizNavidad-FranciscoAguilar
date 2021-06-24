package es.um.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import es.um.tds.controlador.AppMusic;
import es.um.tds.persistencia.DAOException;
import es.um.tds.vista.paneles.PanelExplorar;
import es.um.tds.vista.paneles.PanelMasReproducidas;
import es.um.tds.vista.paneles.PanelMisListas;
import es.um.tds.vista.paneles.PanelNuevaLista;
import es.um.tds.vista.paneles.PanelPulsador;
import es.um.tds.vista.paneles.PanelRecientes;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class VentanaPrincipal {

	private AppMusic controlador;
	private JFrame frmVentanaPrincipal;
	private JPanel panelExplorar;
	private JPanel panelNuevaLista;
	private JPanel panelRecientes;
	private JPanel panelMisListas;
	private JPanel panelPulsador;
	private JPanel panelMasReproducidas;
	
	/**
	 * Constructor
	 * @throws DAOException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public VentanaPrincipal() throws InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, 
	ClassNotFoundException, DAOException { 
		// TODO saber el usuario actual
		controlador = AppMusic.getUnicaInstancia();
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
		tabbedPane.setUI(new TabsColoresUI());
		panelCentral.add(tabbedPane);
		
		BufferedImage iconE = null;
		BufferedImage iconNL = null;
		BufferedImage iconR = null;
		BufferedImage iconML = null;
		BufferedImage iconAC = null;
		BufferedImage iconMR = null;
		try {
			iconE = ImageIO.read(new File("./resources/explorar-icon.png")); 
			iconNL = ImageIO.read(new File("./resources/nueva-lista-icon.png")); 
			iconR = ImageIO.read(new File("./resources/recientes-icon.png")); 
			iconML = ImageIO.read(new File("./resources/mis-listas-icon.png")); 
			iconAC = ImageIO.read(new File("./resources/add-canciones-icon.png"));
			iconMR = ImageIO.read(new File("./resources/most-repr-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Pestañas con los iconos correspondientes
		try {
			panelRecientes = new PanelRecientes();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Error interno. \n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		ImageIcon tabIcon = new ImageIcon(iconR);
		Image image = tabIcon.getImage();
		Image scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Recientes", tabIcon, panelRecientes);
		
		try {
			panelExplorar = new PanelExplorar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Error interno. \n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
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
		
		panelMisListas = new PanelMisListas();
		tabIcon = new ImageIcon(iconML);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Mis listas", tabIcon, panelMisListas);

		try {
			panelMasReproducidas = new PanelMasReproducidas();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelCentral, "Error interno.\n", "Error", JOptionPane.ERROR_MESSAGE);
		}
		tabIcon = new ImageIcon(iconMR);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 35, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Más reproducidas", tabIcon, panelMasReproducidas);
		
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
		
		// Botón mejora
		JButton btnUpgrade = new JButton("Hazte premium");
		panel.add(btnUpgrade);
		
		BufferedImage icon = null;
		try {
			icon = ImageIO.read(new File("./resources/premium-icon.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageIcon tabIcon = new ImageIcon(icon);
		Image image = tabIcon.getImage();
		Image scaledimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		btnUpgrade.setIcon(tabIcon);
		btnUpgrade.setBorder(new LineBorder(new Color(164, 230, 246)));
		btnUpgrade.setOpaque(false);
		btnUpgrade.setContentAreaFilled(false);
		btnUpgrade.setBorderPainted(false);
		
		btnUpgrade.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent evt) {
	        	 btnUpgrade.setOpaque(true);
	     		 btnUpgrade.setContentAreaFilled(true);
	     		 btnUpgrade.setBorderPainted(true);
	     		 btnUpgrade.setBackground(new Color(164, 230, 246));
	         }
	         public void mouseExited(MouseEvent evt) {
	        	 btnUpgrade.setOpaque(false);
	     		 btnUpgrade.setContentAreaFilled(false);
	     		 btnUpgrade.setBorderPainted(false);
	          }
		});
		
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		// Botón logout
		JButton btnLogout = new JButton("Logout");
		panel.add(btnLogout);
		
		icon = null;
		try {
			icon = ImageIO.read(new File("./resources/logout-icon.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tabIcon = new ImageIcon(icon);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		btnLogout.setIcon(tabIcon);
		btnLogout.setBorder(new LineBorder(new Color(164, 230, 246)));
		btnLogout.setOpaque(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBorderPainted(false);
		
		btnLogout.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent evt) {
	        	 btnLogout.setOpaque(true);
	        	 btnLogout.setContentAreaFilled(true);
	        	 btnLogout.setBorderPainted(true);
	        	 btnLogout.setBackground(new Color(164, 230, 246));
	         }
	         public void mouseExited(MouseEvent evt) {
	        	 btnLogout.setOpaque(false);
	     		 btnLogout.setContentAreaFilled(false);
	     		 btnLogout.setBorderPainted(false);
	          }
		});
		
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		// Manejadores
		crearManejadorBotonMejora(btnUpgrade);
		crearManejadorBotonLogout(btnLogout);
		
		return panel;
	}
	
	
	/**
	 * Crea manejador para el botón de logout
	 */
	private void crearManejadorBotonLogout(JButton btnLogout) {
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				int result = JOptionPane.showOptionDialog(frmVentanaPrincipal, 
						"¿Está seguro de que desea cerrar sesión?", "Confirmar logout",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String[]{"Sí", "No"}, "default");
				if (result == JOptionPane.YES_OPTION) {
					controlador.setUsuarioActual(null);
					VentanaLogin ventanaLogin;
					try {
						ventanaLogin = new VentanaLogin();
						ventanaLogin.mostrarVentana();
						frmVentanaPrincipal.dispose();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frmVentanaPrincipal, "Error interno.\n",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
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
	
}
