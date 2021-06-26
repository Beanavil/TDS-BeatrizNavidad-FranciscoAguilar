package es.um.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Usuario;
import es.um.tds.vista.VentanaLogin;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;


/**
 * Ventana de registro de AppMusic
 * 
 * @author Beatriz y Francisco
 */
public class VentanaRegistro {
	private AppMusic controlador;
	// Ventanas
	private JFrame frmRegistro;
	
	// Labels
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblFechaNacimiento;
	private JLabel lblEmail;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JLabel lblPasswordChk;
	
	// Campos de texto
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtEmail;
	private JTextField txtUsuario;
	
	// Campos de contraseña
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordChk;
	
	// Botones
	private JButton btnRegistrar;
	private JButton btnCancelar;
	
	// Labels de error
	private JLabel lblNombreError;
	private JLabel lblApellidosError;
	private JLabel lblFechaNacimientoError;
	private JLabel lblEmailError;
	private JLabel lblUsuarioError;
	private JLabel lblPasswordError;
	private JPanel panelCampoNombre;
	private JPanel panel;
	private JPanel panelCampoApellidos;
	private JPanel panelCamposEmail;
	private JPanel panelCamposUsuario;
	private JPanel panelCamposFechaNacimiento;
	
	// Seleccionador de fecha
	private JDateChooser dateChooser;

	/**
	 * Constructor
	 * @throws DAOException 
	 * @throws BDException
	 */
	public VentanaRegistro() throws BDException, DAOException {		
		controlador = AppMusic.getUnicaInstancia();
		initialize();
	}
	
	
	/**
	 * Muestra la ventana de registro
	 */
	public void mostrarVentana() {
		frmRegistro.setLocationRelativeTo(null);
		frmRegistro.setVisible(true);
	}

	
	/**
	 * Inicializar la ventana
	 */
	public void initialize() {
		frmRegistro = new JFrame();
		frmRegistro.setTitle("Registro AppMusic");
		frmRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		Container contentPane = frmRegistro.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel datosPersonales = new JPanel();
		contentPane.add(datosPersonales, BorderLayout.CENTER);
		datosPersonales.setBorder(new TitledBorder(null, "Introduce tus datos.", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		datosPersonales.setLayout(new BoxLayout(datosPersonales, BoxLayout.Y_AXIS));

		datosPersonales.add(crearLineaNombre());
		datosPersonales.add(crearLineaApellidos());
		datosPersonales.add(crearLineaFechaNacimiento());
		datosPersonales.add(crearLineaEmail());
		datosPersonales.add(crearLineaUsuario());
		datosPersonales.add(crearLineaPassword());
		
		// Botones y sus manejadores
		crearPanelBotones();

		// Ocultar mensajes de error
		ocultarErrores();
		
		frmRegistro.revalidate();
		frmRegistro.pack();
		
		mostrarVentana();
	}

	
	/**
	 * Crea el panel con el campo para introducir el nombre
	 */
	private JPanel crearLineaNombre() {
		JPanel lineaNombre = new JPanel();
		lineaNombre.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaNombre.setLayout(new BorderLayout());
		
		panelCampoNombre = new JPanel();
		lineaNombre.add(panelCampoNombre, BorderLayout.CENTER);
		
		lblNombre = new JLabel("Nombre: ", JLabel.RIGHT);
		panelCampoNombre.add(lblNombre);
		fixedSize(lblNombre, 160, 20);
		txtNombre = new JTextField();
		panelCampoNombre.add(txtNombre);
		fixedSize(txtNombre, 285, 20);
		
		lblNombreError = new JLabel("El campo 'Nombre' es obligatorio", SwingConstants.CENTER);
		lineaNombre.add(lblNombreError, BorderLayout.SOUTH);
		fixedSize(lblNombreError, 224, 15);
		lblNombreError.setForeground(Color.RED);
		
		return lineaNombre;
	}

	
	/**
	 * Crea el panel con el campo para introducir los apellidos
	 */
	private JPanel crearLineaApellidos() {
		JPanel lineaApellidos = new JPanel();
		lineaApellidos.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaApellidos.setLayout(new BorderLayout());
		
		panelCampoApellidos = new JPanel();
		lineaApellidos.add(panelCampoApellidos);
		
		lblApellidos = new JLabel("Apellidos: ", JLabel.RIGHT);
		panelCampoApellidos.add(lblApellidos);
		fixedSize(lblApellidos, 160, 20);
		txtApellidos = new JTextField();
		panelCampoApellidos.add(txtApellidos);
		fixedSize(txtApellidos, 285, 20);

		
		lblApellidosError = new JLabel("El campo 'Apellidos' es obligatorio", SwingConstants.CENTER);
		lineaApellidos.add(lblApellidosError, BorderLayout.SOUTH);
		fixedSize(lblApellidosError, 255, 15);
		lblApellidosError.setForeground(Color.RED);
		
		return lineaApellidos;
	}
	
	
	/**
	 * Crea el panel con el campo para introducir la fecha de nacimiento
	 */
	private JPanel crearLineaFechaNacimiento() {
		JPanel lineaFechaNacimiento = new JPanel();
		lineaFechaNacimiento.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaFechaNacimiento.setLayout(new BorderLayout());
		
		panelCamposFechaNacimiento = new JPanel();
		lineaFechaNacimiento.add(panelCamposFechaNacimiento, BorderLayout.CENTER);
		
		lblFechaNacimiento = new JLabel("Fecha de Nacimiento: ", JLabel.RIGHT);
		panelCamposFechaNacimiento.add(lblFechaNacimiento);
		fixedSize(lblFechaNacimiento, 160, 20);
		
		dateChooser = new JDateChooser();
		panelCamposFechaNacimiento.add(dateChooser);
		fixedSize(dateChooser, 285, 20);
		lblFechaNacimientoError = new JLabel("Introduce la fecha de nacimiento", SwingConstants.CENTER);
		fixedSize(lblFechaNacimientoError, 150, 15);
		lblFechaNacimientoError.setForeground(Color.RED);
		lineaFechaNacimiento.add(lblFechaNacimientoError, BorderLayout.SOUTH);
		
		return lineaFechaNacimiento;
	}

	
	/**
	 * Crea el panel con el campo para introducir el email
	 */
	private JPanel crearLineaEmail() {
		JPanel lineaEmail = new JPanel();
		lineaEmail.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaEmail.setLayout(new BorderLayout());
		
		panelCamposEmail = new JPanel();
		lineaEmail.add(panelCamposEmail, BorderLayout.CENTER);
		
		lblEmail = new JLabel("Email: ", JLabel.RIGHT);
		panelCamposEmail.add(lblEmail);
		fixedSize(lblEmail, 160, 20);
		txtEmail = new JTextField();
		panelCamposEmail.add(txtEmail);
		fixedSize(txtEmail, 285, 20);
		lblEmailError = new JLabel("El campo 'Email' es obligatorio", SwingConstants.CENTER);
		fixedSize(lblEmailError, 150, 15);
		lblEmailError.setForeground(Color.RED);
		lineaEmail.add(lblEmailError, BorderLayout.SOUTH);
		
		return lineaEmail;
	}

	
	/**
	 * Crea el panel con el campo para introducir el nombre de usuario
	 */
	private JPanel crearLineaUsuario() {
		JPanel lineaUsuario = new JPanel();
		lineaUsuario.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaUsuario.setLayout(new BorderLayout());
		
		panelCamposUsuario = new JPanel();
		lineaUsuario.add(panelCamposUsuario, BorderLayout.CENTER);
		
		lblUsuario = new JLabel("Usuario: ", JLabel.RIGHT);
		panelCamposUsuario.add(lblUsuario);
		fixedSize(lblUsuario, 160, 20);
		txtUsuario = new JTextField();
		panelCamposUsuario.add(txtUsuario);
		fixedSize(txtUsuario, 285, 20);
		lblUsuarioError = new JLabel("El usuario ya existe", SwingConstants.CENTER);
		fixedSize(lblUsuarioError, 150, 15);
		lblUsuarioError.setForeground(Color.RED);
		lineaUsuario.add(lblUsuarioError, BorderLayout.SOUTH);
		
		return lineaUsuario;
	}

	
	/**
	 * Crea el panel con los campos de contraseñas
	 */
	private JPanel crearLineaPassword() {
		JPanel lineaPassword = new JPanel();
		lineaPassword.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaPassword.setLayout(new BorderLayout());
		
		panel = new JPanel();
		lineaPassword.add(panel, BorderLayout.CENTER);
		
		lblPassword = new JLabel("Password: ", JLabel.RIGHT);
		panel.add(lblPassword);
		fixedSize(lblPassword,80, 20);
		txtPassword = new JPasswordField();
		panel.add(txtPassword);
		fixedSize(txtPassword, 142, 20);
		lblPasswordChk = new JLabel("Otra vez:", JLabel.RIGHT);
		panel.add(lblPasswordChk);
		fixedSize(lblPasswordChk, 70, 20);
		txtPasswordChk = new JPasswordField();
		panel.add(txtPasswordChk);
		fixedSize(txtPasswordChk, 142, 20);

		lblPasswordError = new JLabel("Las contraseñas no coinciden", JLabel.CENTER);
		lineaPassword.add(lblPasswordError, BorderLayout.SOUTH);
		lblPasswordError.setForeground(Color.RED);
		
		return lineaPassword;
	}

	
	/**
	 * Crea el panel que contiene los botones de 'registrar' y 
	 * 'cancelar' y definir sus manejadores
	 */
	private void crearPanelBotones() {
		JPanel lineaBotones = new JPanel(); 
		frmRegistro.getContentPane().add(lineaBotones, BorderLayout.SOUTH);
		lineaBotones.setBorder(new EmptyBorder(5, 0, 0, 0));
		lineaBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		btnRegistrar = new JButton("Aceptar");
		lineaBotones.add(btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		lineaBotones.add(btnCancelar);

		crearManejadorBotonRegistar();
		crearManejadorBotonCancelar();
	}

	
	/**
	 * Define un manejador de eventos para el botón de registrar
	 */
	private void crearManejadorBotonRegistar() {
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				boolean OK = false;
				OK = checkFields();
				if (OK) {
					LocalDate date = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					String fecha = date.format(Usuario.formatter);
					try {
						controlador.registrarUsuario(txtNombre.getText(),
								txtApellidos.getText(), fecha, txtEmail.getText(), txtUsuario.getText(),
								new String(txtPassword.getPassword()));
						
						JOptionPane.showMessageDialog(frmRegistro, "Usuario registrado correctamente.", "Registro",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frmRegistro, "No se ha podido llevar a cabo el registro.\n",
								"Registro", JOptionPane.ERROR_MESSAGE);
					} 
					
					try {
						VentanaLogin ventanaLogin = new VentanaLogin();
						ventanaLogin.mostrarVentana();
						frmRegistro.dispose();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frmRegistro, "Error interno.\n",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	
	/**
	 * Define un manejador de eventos para el botón de cancelar
	 */
	private void crearManejadorBotonCancelar() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				int result = JOptionPane.showOptionDialog(frmRegistro, 
						"¿Está seguro de que desea salir del registro?", "Confirmar salida de registro",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String[]{"Sí", "No"}, "default");
				if (result == JOptionPane.YES_OPTION) {
					try {
						VentanaLogin ventanaLogin = new VentanaLogin();
						ventanaLogin.mostrarVentana();
						frmRegistro.dispose();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frmRegistro, "Error interno.\n",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	/**
	 * Comprueba que los campos de registro están bien
	 */
	private boolean checkFields() {
		boolean salida = true;
		/* borrar todos los errores en pantalla */
		ocultarErrores();
		if (txtNombre.getText().trim().isEmpty()) {
			lblNombreError.setVisible(true);
			lblNombre.setForeground(Color.RED);
			txtNombre.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtApellidos.getText().trim().isEmpty()) {
			lblApellidosError.setVisible(true);
			lblApellidos.setForeground(Color.RED);
			txtApellidos.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if(!pat.matcher(txtEmail.getText().trim()).matches()) {
			lblEmailError.setText("Introduce una dirección de correo electrónico válida: ejemplo@gmail.com");
			lblEmailError.setVisible(true);
			lblEmail.setForeground(Color.RED);
			txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtEmail.getText().trim().isEmpty()) {
			lblEmailError.setText("El campo 'Email' es obligatorio");
			lblEmailError.setVisible(true);
			lblEmail.setForeground(Color.RED);
			txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtUsuario.getText().trim().isEmpty()) {
			lblUsuarioError.setText("Introduce un nombre de usuario");
			lblUsuarioError.setVisible(true);
			lblUsuario.setForeground(Color.RED);
			txtUsuario.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		// Comprobar que no exista otro usuario con igual login 
		try {
			if (!lblUsuarioError.getText().isEmpty() && controlador.esUsuarioRegistrado(txtUsuario.getText())) {
				lblUsuarioError.setText("Usuario ya registrado");
				lblUsuarioError.setVisible(true);
				lblUsuario.setForeground(Color.RED);
				txtUsuario.setBorder(BorderFactory.createLineBorder(Color.RED));
				salida = false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmRegistro, "No se ha podido llevar a cabo el registro.\n",
					"Registro", JOptionPane.ERROR_MESSAGE);
			frmRegistro.setTitle("Login Gestor Eventos");
		}
		String password = new String(txtPassword.getPassword());
		String password2 = new String(txtPasswordChk.getPassword());
		if (password.isEmpty() || password2.isEmpty()) {
			lblPasswordError.setText("Los campos de contraseña no pueden estar vacíos");
			lblPasswordError.setVisible(true);
			lblPassword.setForeground(Color.RED);
			txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
			lblPasswordChk.setForeground(Color.RED);
			txtPasswordChk.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		} 
		if (!password.equals(password2)) {
			lblPasswordError.setText("Los contraseñas no coinciden");
			lblPasswordError.setVisible(true);
			lblPassword.setForeground(Color.RED);
			lblPasswordChk.setForeground(Color.RED);
			txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
			txtPasswordChk.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (dateChooser.getDate() == null) { 
			lblFechaNacimientoError.setVisible(true);
			lblFechaNacimiento.setForeground(Color.RED);
			dateChooser.setBorder(BorderFactory.createLineBorder(Color.RED)); 
			salida = false;
		} else {
			LocalDate date = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate today = LocalDate.now();
			if (date.isAfter(today)) {
				lblFechaNacimientoError.setText("Fecha de nacimiento no válida");
				lblFechaNacimientoError.setVisible(true);
				lblFechaNacimiento.setForeground(Color.RED);
				dateChooser.setBorder(BorderFactory.createLineBorder(Color.RED));
				salida = false;
			}
		}
		
		frmRegistro.revalidate();
		frmRegistro.pack();
		
		return salida;
	}

	/**
	 * Oculta todos los errores que pueda haber en la pantalla
	 */
	private void ocultarErrores() {
		lblNombreError.setVisible(false);
		lblApellidosError.setVisible(false);
		lblFechaNacimientoError.setVisible(false);
		lblEmailError.setVisible(false);
		lblUsuarioError.setVisible(false);
		lblPasswordError.setVisible(false);
		lblFechaNacimientoError.setVisible(false);
		
		Border border = new JTextField().getBorder();
		txtNombre.setBorder(border);
		txtApellidos.setBorder(border);
		txtEmail.setBorder(border);
		txtUsuario.setBorder(border);
		txtPassword.setBorder(border);
		txtPasswordChk.setBorder(border);
		txtPassword.setBorder(border);
		txtPasswordChk.setBorder(border);
		txtUsuario.setBorder(border);
		dateChooser.setBorder(border);
		
		lblNombre.setForeground(Color.BLACK);
		lblApellidos.setForeground(Color.BLACK);
		lblEmail.setForeground(Color.BLACK);
		lblUsuario.setForeground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);
		lblPasswordChk.setForeground(Color.BLACK);
		lblFechaNacimiento.setForeground(Color.BLACK);
		dateChooser.setForeground(Color.BLACK);
	}

	/**
	 * Fija el tamaño de un componente
	 */
	private void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
	}
}
