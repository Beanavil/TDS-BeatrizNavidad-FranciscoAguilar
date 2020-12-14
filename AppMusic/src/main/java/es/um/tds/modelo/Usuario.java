package es.um.tds.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Representa un usuario de la aplicación.
 * 
 * @author Beatriz y Francisco
 */
public class Usuario implements RolUsuario, Descuento {
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String email;
	private String nombreUsuario;
	private String contrasena;
	private boolean premium;
	private List<ListaCanciones> listasCanciones;
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "ES"));
	private int id;
	
	/**
	 * Constructor.
	 * @param nombre nombre real.
	 * @param apellidos apellidos.
	 * @param fechaNacimiento fecha de nacimiento en formato "dd/MM/yyyy"
	 * @param email dirección de correo electrónico.
	 * @param nombreUsuario nombre de usuario en la app.
	 * @param contrasena contraseña de la cuenta.
	 * @param premium si es premium o no.
	 * @param listasCanciones listas de canciones del usuario.
	 */
	public Usuario (String nombre, String apellidos, String fechaNacimiento, 
			String email, String nombreUsuario, String contrasena, boolean premium, 
			List<ListaCanciones> listasCanciones) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		// TODO implementar bien la lectura de fecha de nacimiento de un usuario en la vista para que esto no pueda lanzar excepción
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento, formatter); 
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.premium = premium;
		this.listasCanciones = new ArrayList<>(listasCanciones);
	}

	// Getters
	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	// No necesaria copia pq LocalDate es inmutable
	public LocalDate getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}
	
	public boolean isPremium() {
		return premium;
	}
	
	public List<ListaCanciones> getListasCanciones() {
		return new ArrayList<>(listasCanciones);
	}
	
	public int getId() {
		return id;
	}

	
	// Setters 	
	// TODO ver si algunos tienen que ser privados
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento, formatter);
	}

	public void setEmail(String email) {
		// TODO: comprobar el formato del mail en la vista o aquí?
		this.email = email;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void setContrasena(String contrasena) {
		// TODO: comprobar el formato de la contrasena en la vista o aquí?
		this.contrasena = contrasena;
	}
	
	public void setListasCanciones(ArrayList<ListaCanciones> listasCanciones) {
		this.listasCanciones = new ArrayList<>(listasCanciones);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * {@inheritDoc}
	 */
	// TODO ¿incluir listas de cantiones en el toString?
	@Override
	public String toString() {
		return "Usuario [nombre= " + nombre + ", apellidos= " + apellidos + ", mail=" + email +
			   "fecha de nacimiento= " + fechaNacimiento.format(formatter) + ", usuario=" + nombreUsuario +
			   ", contraseña=" + contrasena + ", id=" + id + "]";
	}

	/**
	 * Método para realizar un pago
	 */
	public void realizarPago() {
		// TODO xd
	}
	
	/**
	 * Método para calcular el descuento a aplicar en función 
	 * del tipo de cuenta.
	 * @param precio cantidad de la que calculamos el descuento
	 * @return descuento a aplicar
	 */
	public double calcDescuento(double precio) {
		return 0;
	}
}
