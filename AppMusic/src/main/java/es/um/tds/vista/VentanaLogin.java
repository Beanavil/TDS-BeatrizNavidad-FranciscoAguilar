package es.um.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import es.um.tds.controlador.AppMusic;
import es.um.tds.vista.VentanaRegistro;
import es.um.tds.vista.VentanaLogin;

import javax.swing.border.EmptyBorder;


/**
 * Ventana de login de AppMusic
 * 
 * @author Beatriz y Francisco
 */
public class VentanaLogin {
	private JFrame frmLogin;
	private JTextField textUsuario;
	private JPasswordField textPassword;

	/**
	 * Constructor
	 */
	public VentanaLogin() {
		initialize();
	}

	
	/**
	 * Muestra la ventana de login
	 */
	public void mostrarVentana() {
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setVisible(true);
	}
	
	
	/**
	 * Inicializa la ventana de login
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(new BorderLayout());

		crearPanelTitulo();
		crearPanelLogin();

		frmLogin.setResizable(false);
		frmLogin.pack();
		
		mostrarVentana();
	}

	
	/**
	 * Crea el panel con el título (nombre) de la app
	 */
	private void crearPanelTitulo() {
		JPanel panel_Norte = new JPanel();
		frmLogin.getContentPane().add(panel_Norte, BorderLayout.NORTH);
		panel_Norte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));

		// Icono de AppMusic
		BufferedImage logo = null;
		try {
			logo = ImageIO.read(new File("./resources/appmusic.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon imageIcon = new ImageIcon(logo);
		Image image = imageIcon.getImage();
		Image scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(scaledimage);
		panel_Norte.add(new JLabel(imageIcon));
		
		// Nombre
		JLabel lblTitulo = new JLabel("AppMusic");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setForeground(Color.DARK_GRAY);
		panel_Norte.add(lblTitulo);
	}

	
	/**
	 * Crea el panel que contendrá los campos del login y la contraseña
	 */
	private void crearPanelLogin() {
		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmLogin.getContentPane().add(panelLogin, BorderLayout.CENTER);
		panelLogin.setLayout(new BorderLayout(0, 0));

		panelLogin.add(crearPanelCampos(), BorderLayout.NORTH);
		panelLogin.add(crearPanelBotones(), BorderLayout.SOUTH);
	}

	
	/**
	 * Crea el panel con los campos del login y la contraseña
	 */
	private JPanel crearPanelCampos() {
		JPanel panelCampos = new JPanel();
		panelCampos.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

		// Panel campo login
		JPanel panelCampoUsuario = new JPanel();
		panelCampos.add(panelCampoUsuario);
		panelCampoUsuario.setLayout(new BorderLayout(0, 0));

		JLabel lblUsuario = new JLabel("Usuario: ");
		panelCampoUsuario.add(lblUsuario);
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));

		textUsuario = new JTextField();
		panelCampoUsuario.add(textUsuario, BorderLayout.EAST);
		textUsuario.setColumns(15);

		// Panel campo contraseña
		JPanel panelCampoPassword = new JPanel();
		panelCampos.add(panelCampoPassword);
		panelCampoPassword.setLayout(new BorderLayout(0, 0));

		JLabel lblPassword = new JLabel("Contraseña: "); 
		panelCampoPassword.add(lblPassword);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));

		textPassword = new JPasswordField();
		panelCampoPassword.add(textPassword, BorderLayout.EAST);
		textPassword.setColumns(15);
		
		return panelCampos;
	}

	
	/**
	 * Crea el panel con los botones de login, registro y salir
	 */
	private JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new EmptyBorder(5, 0, 5, 0));
		panelBotones.setLayout(new BorderLayout(0, 0));

		JPanel panelBotonesLoginRegistro = new JPanel();
		panelBotones.add(panelBotonesLoginRegistro, BorderLayout.WEST);

		JButton btnLogin = new JButton("Login");
		panelBotonesLoginRegistro.add(btnLogin);
		btnLogin.setVerticalAlignment(SwingConstants.BOTTOM);

		JButton btnRegistro = new JButton("Registro");
		panelBotonesLoginRegistro.add(btnRegistro);
		btnRegistro.setVerticalAlignment(SwingConstants.BOTTOM);

		JPanel panelBotonSalir = new JPanel();
		panelBotones.add(panelBotonSalir, BorderLayout.EAST);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setVerticalAlignment(SwingConstants.BOTTOM);
		panelBotonSalir.add(btnSalir);

		crearManejadorBotonLogin(btnLogin);
		crearManejadorBotonRegistro(btnRegistro);
		crearManejadorBotonSalir(btnSalir);
		
		return panelBotones;
	}
	
	
	/**
	 * Crea un manejador del boton de login
	 */
	private void crearManejadorBotonLogin(JButton btnLogin) {
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean login = AppMusic.getUnicaInstancia().loginUsuario(textUsuario.getText(),
						new String(textPassword.getPassword()));
				
				if (login) {
					VentanaPrincipal window = new VentanaPrincipal();
					window.mostrarVentana();
					frmLogin.dispose();
				} else
					JOptionPane.showMessageDialog(frmLogin, "Nombre de usuario o contraseña no válido",
							"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	
	/**
	 * Crea un manejador del boton de registro
	 */
	private void crearManejadorBotonRegistro(JButton btnRegistro) {
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegistro ventanaRegistro = new VentanaRegistro();
				ventanaRegistro.mostrarVentana();
				frmLogin.dispose();
			}
		});
	}
	
	
	/**
	 * Crea un manejador del boton de salir
	 */
	private void crearManejadorBotonSalir(JButton btnSalir) {
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLogin.dispose();
				System.exit(0);
			}
		});
	}


}
