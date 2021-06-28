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
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.vista.VentanaRegistro;
import es.um.tds.vista.VentanaLogin;

import javax.swing.border.EmptyBorder;


/**
 * Ventana de login de AppMusic.
 * 
 * @author Beatriz y Francisco
 */
public class VentanaLogin {
	private AppMusic controlador;
	private JFrame frmLogin;
	private JTextField textUsuario;
	private JPasswordField textPassword;

	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException 
	 */
	public VentanaLogin() throws BDException, DAOException {		
		controlador = AppMusic.getUnicaInstancia();
		initialize();
	}

	/**
	 * Muestra la ventana de login.
	 */
	public void mostrarVentana() {
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setVisible(true);
	}
	
	
	/**
	 * Inicializa la ventana de login.
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
	 * Crea el panel con el título (nombre) de la app.
	 */
	private void crearPanelTitulo() {
		JPanel panelTitulo = new JPanel();
		frmLogin.getContentPane().add(panelTitulo, BorderLayout.NORTH);
		panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));

		// Carga el icono de AppMusic y lo añade al panel
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
		panelTitulo.add(new JLabel(imageIcon));
		
		// Añade el nombre de la app
		JLabel lblTitulo = new JLabel("AppMusic");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setForeground(Color.DARK_GRAY);
		panelTitulo.add(lblTitulo);
	}

	/**
	 * Crea el panel que contendrá los campos del login y la contraseña.
	 */
	private void crearPanelLogin() {
		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmLogin.getContentPane().add(panelLogin, BorderLayout.CENTER);
		panelLogin.setLayout(new BorderLayout());

		panelLogin.add(crearPanelCampos(), BorderLayout.NORTH);
		panelLogin.add(crearPanelBotones(), BorderLayout.SOUTH);
	}

	
	/**
	 * Crea el panel con los campos del login y la contraseña.
	 */
	private JPanel crearPanelCampos() {
		JPanel panelCampos = new JPanel();
		panelCampos.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

		// Panel para el campo de login
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

		// Panel para el campo de contraseña
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
	 * Crea el panel con los botones de login, registro y salir.
	 */
	private JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new EmptyBorder(5, 0, 5, 0));
		panelBotones.setLayout(new BorderLayout(0, 0));

		// Panel de botones login y registro
		JPanel panelBotonesLoginRegistro = new JPanel();
		panelBotones.add(panelBotonesLoginRegistro, BorderLayout.WEST);
		// Botón login
		JButton btnLogin = new JButton("Login");
		panelBotonesLoginRegistro.add(btnLogin);
		btnLogin.setVerticalAlignment(SwingConstants.BOTTOM);
		//Botón registro
		JButton btnRegistro = new JButton("Registro");
		panelBotonesLoginRegistro.add(btnRegistro);
		btnRegistro.setVerticalAlignment(SwingConstants.BOTTOM);

		// Panel de botón salir
		JPanel panelBotonSalir = new JPanel();
		panelBotones.add(panelBotonSalir, BorderLayout.EAST);
		// Botón salir
		JButton btnSalir = new JButton("Salir");
		btnSalir.setVerticalAlignment(SwingConstants.BOTTOM);
		panelBotonSalir.add(btnSalir);

		// Creamos los manejadores de los botones
		crearManejadorBotonLogin(btnLogin);
		crearManejadorBotonRegistro(btnRegistro);
		crearManejadorBotonSalir(btnSalir);
		
		return panelBotones;
	}
	
	/**
	 * Crea un manejador del botón de login.
	 */
	private void crearManejadorBotonLogin(JButton btnLogin) {
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				try {
					// Preguntamos al controlador si el login es válido
					boolean login = controlador.loginUsuario(textUsuario.getText(),
							new String(textPassword.getPassword()));
					// Si lo es, entramos a la app
					if (login) {
							VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
							ventanaPrincipal.mostrarVentana();
							frmLogin.dispose();
					// Si no, informamos al usuario
					} else
						JOptionPane.showMessageDialog(frmLogin, "Nombre de usuario o contraseña no válido",
								"Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frmLogin, "Error interno.\n", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea un manejador del botón de registro.
	 */
	private void crearManejadorBotonRegistro(JButton btnRegistro) {
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				try {
					// Nos reconduce a la ventana de registro
					VentanaRegistro ventanaRegistro = new VentanaRegistro();
					ventanaRegistro.mostrarVentana();
					frmLogin.dispose();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frmLogin, "Error interno.\n","Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * Crea un manejador del botón de salir.
	 */
	private void crearManejadorBotonSalir(JButton btnSalir) {
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showOptionDialog(frmLogin, 
						"¿Está seguro de que desea salir de la aplicación?", "Confirmar salida",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String[]{"Sí", "No"}, "default");
				if (result == JOptionPane.YES_OPTION) {
					// Finalizamos el programa
					frmLogin.dispose();
					System.exit(0);
				}
			}
		});
	}
	
}
