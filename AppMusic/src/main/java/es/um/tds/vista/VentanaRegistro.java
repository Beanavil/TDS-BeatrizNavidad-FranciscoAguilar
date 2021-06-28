package es.um.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import es.um.tds.utils.ComponentUtils;
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
	
	private JFrame frmRegistro;

	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblFechaNacimiento;
	private JLabel lblEmail;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JLabel lblPasswordChk;
	
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtEmail;
	private JTextField txtUsuario;
	
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordChk;

	private JButton btnRegistrar;
	private JButton btnCancelar;
	
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
	
	private JDateChooser dateChooser;

	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException 
	 */
	public VentanaRegistro() throws BDException, DAOException {		
		controlador = AppMusic.getUnicaInstancia();
		initialize();
	}
	
	/**
	 * Muestra la ventana de registro.
	 */
	public void mostrarVentana() {
		frmRegistro.setLocationRelativeTo(null);
		frmRegistro.setVisible(true);
	}
	
	/**
	 * Inicializa la ventana.
	 */
	public void initialize() {
		frmRegistro = new JFrame();
		frmRegistro.setTitle("Registro AppMusic");
		frmRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = frmRegistro.getContentPane();
		contentPane.setLayout(new BorderLayout());

		// Inicializamos el panel con los campos para los datos personales
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
		
		// Creamos los botones y sus manejadores
		crearPanelBotones();

		// Ocultamos mensajes de error
		ocultarErrores();
		
		frmRegistro.revalidate();
		frmRegistro.pack();
		
		mostrarVentana();
	}
	
	/**
	 * Crea el panel con el campo para introducir el nombre.
	 */
	private JPanel crearLineaNombre() {
		JPanel lineaNombre = new JPanel();
		lineaNombre.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaNombre.setLayout(new BorderLayout());
		
		panelCampoNombre = new JPanel();
		lineaNombre.add(panelCampoNombre, BorderLayout.CENTER);
		
		lblNombre = new JLabel("Nombre: ", JLabel.RIGHT);
		panelCampoNombre.add(lblNombre);
		ComponentUtils.fixedSize(lblNombre, 160, 20);
		txtNombre = new JTextField();
		panelCampoNombre.add(txtNombre);
		ComponentUtils.fixedSize(txtNombre, 285, 20);
		
		// Error de este campo
		lblNombreError = new JLabel("El campo 'Nombre' es obligatorio", SwingConstants.CENTER);
		lineaNombre.add(lblNombreError, BorderLayout.SOUTH);
		ComponentUtils.fixedSize(lblNombreError, 224, 15);
		lblNombreError.setForeground(Color.RED);
		
		return lineaNombre;
	}
	
	/**
	 * Crea el panel con el campo para introducir los apellidos.
	 */
	private JPanel crearLineaApellidos() {
		JPanel lineaApellidos = new JPanel();
		lineaApellidos.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaApellidos.setLayout(new BorderLayout());
		
		panelCampoApellidos = new JPanel();
		lineaApellidos.add(panelCampoApellidos);
		
		lblApellidos = new JLabel("Apellidos: ", JLabel.RIGHT);
		panelCampoApellidos.add(lblApellidos);
		ComponentUtils.fixedSize(lblApellidos, 160, 20);
		txtApellidos = new JTextField();
		panelCampoApellidos.add(txtApellidos);
		ComponentUtils.fixedSize(txtApellidos, 285, 20);

		// Error de este campo
		lblApellidosError = new JLabel("El campo 'Apellidos' es obligatorio", SwingConstants.CENTER);
		lineaApellidos.add(lblApellidosError, BorderLayout.SOUTH);
		ComponentUtils.fixedSize(lblApellidosError, 255, 15);
		lblApellidosError.setForeground(Color.RED);
		
		return lineaApellidos;
	}
	
	/**
	 * Crea el panel con el campo para introducir la fecha de nacimiento.
	 */
	private JPanel crearLineaFechaNacimiento() {
		JPanel lineaFechaNacimiento = new JPanel();
		lineaFechaNacimiento.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaFechaNacimiento.setLayout(new BorderLayout());
		
		panelCamposFechaNacimiento = new JPanel();
		lineaFechaNacimiento.add(panelCamposFechaNacimiento, BorderLayout.CENTER);
		
		lblFechaNacimiento = new JLabel("Fecha de Nacimiento: ", JLabel.RIGHT);
		panelCamposFechaNacimiento.add(lblFechaNacimiento);
		ComponentUtils.fixedSize(lblFechaNacimiento, 160, 20);
		
		dateChooser = new JDateChooser();
		panelCamposFechaNacimiento.add(dateChooser);
		ComponentUtils.fixedSize(dateChooser, 285, 20);
		
		// Error de este campo
		lblFechaNacimientoError = new JLabel("Introduce la fecha de nacimiento", SwingConstants.CENTER);
		ComponentUtils.fixedSize(lblFechaNacimientoError, 150, 15);
		lblFechaNacimientoError.setForeground(Color.RED);
		lineaFechaNacimiento.add(lblFechaNacimientoError, BorderLayout.SOUTH);
		
		return lineaFechaNacimiento;
	}
	
	/**
	 * Crea el panel con el campo para introducir el email.
	 */
	private JPanel crearLineaEmail() {
		JPanel lineaEmail = new JPanel();
		lineaEmail.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaEmail.setLayout(new BorderLayout());
		
		panelCamposEmail = new JPanel();
		lineaEmail.add(panelCamposEmail, BorderLayout.CENTER);
		
		lblEmail = new JLabel("Email: ", JLabel.RIGHT);
		panelCamposEmail.add(lblEmail);
		ComponentUtils.fixedSize(lblEmail, 160, 20);
		txtEmail = new JTextField();
		panelCamposEmail.add(txtEmail);
		ComponentUtils.fixedSize(txtEmail, 285, 20);
		
		// Error de este campo
		lblEmailError = new JLabel("El campo 'Email' es obligatorio", SwingConstants.CENTER);
		ComponentUtils.fixedSize(lblEmailError, 150, 15);
		lblEmailError.setForeground(Color.RED);
		lineaEmail.add(lblEmailError, BorderLayout.SOUTH);
		
		return lineaEmail;
	}

	/**
	 * Crea el panel con el campo para introducir el nombre de usuario.
	 */
	private JPanel crearLineaUsuario() {
		JPanel lineaUsuario = new JPanel();
		lineaUsuario.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaUsuario.setLayout(new BorderLayout());
		
		panelCamposUsuario = new JPanel();
		lineaUsuario.add(panelCamposUsuario, BorderLayout.CENTER);
		
		lblUsuario = new JLabel("Usuario: ", JLabel.RIGHT);
		panelCamposUsuario.add(lblUsuario);
		ComponentUtils.fixedSize(lblUsuario, 160, 20);
		txtUsuario = new JTextField();
		panelCamposUsuario.add(txtUsuario);
		ComponentUtils.fixedSize(txtUsuario, 285, 20);
		
		// Error de este campo
		lblUsuarioError = new JLabel("El usuario ya existe", SwingConstants.CENTER);
		ComponentUtils.fixedSize(lblUsuarioError, 150, 15);
		lblUsuarioError.setForeground(Color.RED);
		lineaUsuario.add(lblUsuarioError, BorderLayout.SOUTH);
		
		return lineaUsuario;
	}

	/**
	 * Crea el panel con los campos de contraseñas.
	 */
	private JPanel crearLineaPassword() {
		JPanel lineaPassword = new JPanel();
		lineaPassword.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaPassword.setLayout(new BorderLayout());
		
		panel = new JPanel();
		lineaPassword.add(panel, BorderLayout.CENTER);
		
		lblPassword = new JLabel("Password: ", JLabel.RIGHT);
		panel.add(lblPassword);
		ComponentUtils.fixedSize(lblPassword,80, 20);
		txtPassword = new JPasswordField();
		panel.add(txtPassword);
		ComponentUtils.fixedSize(txtPassword, 142, 20);
		lblPasswordChk = new JLabel("Otra vez:", JLabel.RIGHT);
		panel.add(lblPasswordChk);
		ComponentUtils.fixedSize(lblPasswordChk, 70, 20);
		txtPasswordChk = new JPasswordField();
		panel.add(txtPasswordChk);
		ComponentUtils.fixedSize(txtPasswordChk, 142, 20);

		// Error de este campo
		lblPasswordError = new JLabel("Las contraseñas no coinciden", JLabel.CENTER);
		lineaPassword.add(lblPasswordError, BorderLayout.SOUTH);
		lblPasswordError.setForeground(Color.RED);
		
		return lineaPassword;
	}

	/**
	 * Crea el panel que contiene los botones de 'registrar' y 
	 * 'cancelar' y define sus manejadores.
	 */
	private void crearPanelBotones() {
		JPanel lineaBotones = new JPanel(); 
		frmRegistro.getContentPane().add(lineaBotones, BorderLayout.SOUTH);
		lineaBotones.setBorder(new EmptyBorder(5, 0, 0, 0));
		lineaBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
		// Botón registrar
		btnRegistrar = new JButton("Aceptar");
		lineaBotones.add(btnRegistrar);
		// Botón cancelar
		btnCancelar = new JButton("Cancelar");
		lineaBotones.add(btnCancelar);

		crearManejadorBotonRegistar();
		crearManejadorBotonCancelar();
	}

	
	/**
	 * Define un manejador de eventos para el botón de registrar.
	 */
	private void crearManejadorBotonRegistar() {
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				boolean OK = false;
				// Comprobamos que los campos estén correctamente cumplimentados
				OK = checkFields();
				if (OK) {
					LocalDate date = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					String fecha = date.format(Usuario.formatter);
					try {
						// Pedimos al controlador que registre al usuario
						controlador.registrarUsuario(txtNombre.getText(),
								txtApellidos.getText(), fecha, txtEmail.getText(), txtUsuario.getText(),
								new String(txtPassword.getPassword()));
						
						JOptionPane.showMessageDialog(frmRegistro, "Usuario registrado correctamente.", "Registro",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frmRegistro, "No se ha podido llevar a cabo el registro.\n",
								"Registro", JOptionPane.ERROR_MESSAGE);
					} 
					// Redirigimos a la ventana de login
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
	 * Define un manejador de eventos para el botón de cancelar.
	 */
	private void crearManejadorBotonCancelar() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				// Preguntamos al usuario si quiere cancelar
				int result = JOptionPane.showOptionDialog(frmRegistro, 
						"¿Está seguro de que desea salir del registro?", "Confirmar salida de registro",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String[]{"Sí", "No"}, "default");
				if (result == JOptionPane.YES_OPTION) {
					// Redirigimos a la ventana de login
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
	 * Comprueba que los campos de registro están bien.
	 */
	private boolean checkFields() {
		boolean salida = true;
		// Ocultar todos los errores 
		ocultarErrores();
		// Comprobar cada campo y, si hay error, mostrarlo
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
	 * Oculta todos los errores que pueda haber en la pantalla.
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
}
