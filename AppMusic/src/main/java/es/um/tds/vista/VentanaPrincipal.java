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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
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
	
	private JButton btnUpgrade;
	
	private ImageIcon upgIcon;
	private ImageIcon degIcon;
	
	private JPanel panelExplorar;
	private JPanel panelNuevaLista;
	private JPanel panelRecientes;
	private JPanel panelMisListas;
	private JPanel panelPulsador;
	private JPanel panelMasReproducidas;
	
	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException 
	 */
	public VentanaPrincipal() throws BDException, DAOException { 
		controlador = AppMusic.getUnicaInstancia();
		initialize();
	}
	
	/**
	 * Muestra la ventana principal.
	 */
	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
	}
	
	/**
	 * Inicializa la ventana principal.
	 */
	public void initialize() {
		frmVentanaPrincipal = new JFrame();
		frmVentanaPrincipal.setTitle("AppMusic");
		frmVentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Parte superior
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
		
		// Cargamos los iconos
		BufferedImage iconExplorar = null;
		BufferedImage iconNuevaLista = null;
		BufferedImage iconRecientes = null;
		BufferedImage iconMisListas = null;
		BufferedImage iconCargarCanciones = null;
		BufferedImage iconMasRepr = null;
		try {
			iconExplorar = ImageIO.read(new File("./resources/explorar-icon.png")); 
			iconNuevaLista = ImageIO.read(new File("./resources/nueva-lista-icon.png")); 
			iconRecientes = ImageIO.read(new File("./resources/recientes-icon.png")); 
			iconMisListas = ImageIO.read(new File("./resources/mis-listas-icon.png")); 
			iconCargarCanciones = ImageIO.read(new File("./resources/add-canciones-icon.png"));
			iconMasRepr = ImageIO.read(new File("./resources/most-repr-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Añadimos las pestañas con los iconos correspondientes
		// Pestaña "Recientes"
		try {
			panelRecientes = new PanelRecientes();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Error interno. \n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		ImageIcon tabIcon = new ImageIcon(iconRecientes);
		Image image = tabIcon.getImage();
		Image scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Recientes", tabIcon, panelRecientes);
		
		// Pestaña "Explorar"
		try {
			panelExplorar = new PanelExplorar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Error interno. \n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		tabIcon = new ImageIcon(iconExplorar);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Explorar", tabIcon, panelExplorar);
		
		// Pestaña "Cargar canciones"
		try {
			panelPulsador = new PanelPulsador();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Error interno. \n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		tabIcon = new ImageIcon(iconCargarCanciones);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Cargar canciones", tabIcon, panelPulsador);
		
		// Pestaña "Nueva lista"
		try {
			panelNuevaLista = new PanelNuevaLista();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Error interno. \n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		tabIcon = new ImageIcon(iconNuevaLista);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Nueva lista", tabIcon, panelNuevaLista);
		
		// Pestaña "Mis listas"
		try {
			panelMisListas = new PanelMisListas();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmVentanaPrincipal, "Error interno. \n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		tabIcon = new ImageIcon(iconMisListas);
		image = tabIcon.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		tabIcon = new ImageIcon(scaledimage);
		tabbedPane.addTab("Mis listas", tabIcon, panelMisListas);

		// Pestaña "Más reproducidas"
		try {
			panelMasReproducidas = new PanelMasReproducidas();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panelCentral, "Error interno.\n", "Error", JOptionPane.ERROR_MESSAGE);
		}
		tabIcon = new ImageIcon(iconMasRepr);
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
	 * Crea botones superiores (mejorar cuenta y logout).
	 */
	public JPanel crearPanelBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		panel.add(Box.createRigidArea(new Dimension(105,40)));
		
		panel.add(new JLabel("Hola, " + controlador.getUsuarioActual().getLogin()));
		
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		// Botón mejora
		btnUpgrade = new JButton("Hazte premium");
		panel.add(btnUpgrade);
		
		BufferedImage iconUpgrade = null; 
		BufferedImage iconDegrade = null;
		try {
			iconUpgrade = ImageIO.read(new File("./resources/premium-icon.png")); 
			iconDegrade = ImageIO.read(new File("./resources/poor-icon.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Icono upgrade
		upgIcon = new ImageIcon(iconUpgrade);
		Image image = upgIcon.getImage();
		Image scaledimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		upgIcon = new ImageIcon(scaledimage);
		
		// Icono degrade
		degIcon = new ImageIcon(iconDegrade);
		image = degIcon.getImage();
		scaledimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		degIcon = new ImageIcon(scaledimage);
		
		// Al inicio el botón tiene el icono upgrade
		btnUpgrade.setIcon(upgIcon);
		customizarBoton(btnUpgrade);
		
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		// Botón logout
		JButton btnLogout = new JButton("Logout");
		panel.add(btnLogout);
		
		iconUpgrade = null;
		try {
			iconUpgrade = ImageIO.read(new File("./resources/logout-icon.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageIcon logoutIcon = new ImageIcon(iconUpgrade);
		image = logoutIcon.getImage();
		scaledimage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		logoutIcon = new ImageIcon(scaledimage);
		btnLogout.setIcon(logoutIcon);
		customizarBoton(btnLogout);
		
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		// Creamos los manejadores de los botones
		crearManejadorBotonMejora(btnUpgrade);
		crearManejadorBotonLogout(btnLogout);
		
		return panel;
	}

	/**
	 * Altera algunas propiedades de un botón.
	 */
	private void customizarBoton(JButton btn) {
		// Le quitamos el fondo
		btn.setBorder(new LineBorder(new Color(164, 230, 246)));
		btn.setOpaque(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		// Definimos su comportamiento cuando pasa el ratón por encima
		btn.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent evt) {
	        	 btn.setOpaque(true);
	        	 btn.setContentAreaFilled(true);
	        	 btn.setBorderPainted(true);
	        	 btn.setBackground(new Color(164, 230, 246));
	         }
	         public void mouseExited(MouseEvent evt) {
	        	 btn.setOpaque(false);
	     		 btn.setContentAreaFilled(false);
	     		 btn.setBorderPainted(false);
	          }
		});
	}

	/**
	 * Crea manejador para el botón de logout.
	 */
	private void crearManejadorBotonLogout(JButton btnLogout) {
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				int result = JOptionPane.showOptionDialog(frmVentanaPrincipal, 
							"¿Está seguro de que desea cerrar sesión?", "Confirmar logout",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[]{"Sí", "No"}, "default");
				if (result == JOptionPane.YES_OPTION) {
					// Eliminamos el usuario actual
					controlador.setUsuarioActual(null);
					VentanaLogin ventanaLogin;
					try {
						// Reconducimos a la ventana de login
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
				int result;
				if (controlador.isUsuarioPremium()) {
					result = JOptionPane.showOptionDialog(frmVentanaPrincipal, 
							"¿Quiere dejar de ser premium?", "Confirmar dejar premium",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[]{"Sí", "No"}, "default");
					if (result == JOptionPane.YES_OPTION) {
						// Hacer al usuario no-premium
						controlador.degradeUsuarioActual();
						// Cambiar botón
						btnUpgrade.setText("Hazte premium");
						btnUpgrade.setIcon(upgIcon);
					}
				} else {
					result = JOptionPane.showOptionDialog(frmVentanaPrincipal, 
							"¿Quiere hacer su cuenta premium?", "Confirmar pasar a premium",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[]{"Sí", "No"}, "default");
					if (result == JOptionPane.YES_OPTION) {
						// Hacer al usuario premium
						controlador.upgradeUsuarioActual();
						// Cambiar botón
						btnUpgrade.setText("Dejar de ser premium");
						btnUpgrade.setIcon(degIcon);
					}
				}
			}
		});
	}
}
