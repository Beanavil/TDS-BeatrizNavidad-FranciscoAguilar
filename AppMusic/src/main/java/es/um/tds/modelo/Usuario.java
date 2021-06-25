package es.um.tds.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Representa un usuario de la aplicación.
 * 
 * @author Beatriz y Francisco
 */
public class Usuario implements Descuento {
	public static final String LISTA_RECIENTES = "Canciones recientes";
	public static final int NUM_RECIENTES = 10;
	
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String email;
	private String login;
	private String password;
	private boolean premium;
	private List<ListaCanciones> listasCanciones;
	private ListaCanciones cancionesRecientes;
	private Map<Cancion, Integer> cancionesMasReproducidas;
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "ES"));
	private int id;
	
	
	/**
	 * Constructor.
	 * @param nombre nombre real.
	 * @param apellidos apellidos.
	 * @param fechaNacimiento fecha de nacimiento en formato "dd/MM/yyyy"
	 * @param email dirección de correo electrónico.
	 * @param login nombre de usuario en la app.
	 * @param password contraseña de la cuenta.
	 * @param premium si es premium o no.
	 * @param listasCanciones listas de canciones del usuario.
	 * @param cancionesRecientes Lista de canciones reproducidas recientemente
	 * @param cancionesMasReproducidas Lista de canciones más reproducidas
	 */
	public Usuario (String nombre, String apellidos, String fechaNacimiento, 
			String email, String nombreUsuario, String contrasena, boolean premium, 
			List<ListaCanciones> listasCanciones, ListaCanciones cancionesRecientes, 
			Map<Cancion, Integer> cancionesMasReproducidas) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		// TODO implementar bien la lectura de fecha de nacimiento de un usuario en la vista para que esto no pueda lanzar excepción
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento, formatter); 
		this.email = email;
		this.login = nombreUsuario;
		this.password = contrasena;
		this.premium = premium;
		this.listasCanciones = new ArrayList<>(listasCanciones);
		if (cancionesRecientes.getNumCanciones() > 10)
			this.cancionesRecientes = new ListaCanciones(LISTA_RECIENTES, cancionesRecientes.getCanciones().subList(0, 10));
		else {
			cancionesRecientes.setNombre(LISTA_RECIENTES);
			this.cancionesRecientes = cancionesRecientes;
		}
		this.cancionesMasReproducidas =  new TreeMap<>(cancionesMasReproducidas); // TODO tener en cuenta orden ascendente para la vista
		this.id = -1;
	}
	
	/**
	 * Constructor sin recientes ni más reproducidas.
	 * @param nombre nombre real.
	 * @param apellidos apellidos.
	 * @param fechaNacimiento fecha de nacimiento en formato "dd/MM/yyyy"
	 * @param email dirección de correo electrónico.
	 * @param login nombre de usuario en la app.
	 * @param password contraseña de la cuenta.
	 * @param premium si es premium o no.
	 * @param listasCanciones listas de canciones del usuario.
	 */
	public Usuario (String nombre, String apellidos, String fechaNacimiento, 
			String email, String nombreUsuario, String contrasena, boolean premium, 
			List<ListaCanciones> listasCanciones) {
		this(nombre, apellidos, fechaNacimiento, email, nombreUsuario, contrasena, premium, listasCanciones, 
				new ListaCanciones(LISTA_RECIENTES), new TreeMap<Cancion, Integer>()); 
	}
	
	/**
	 * Constructor sin premium ni listas ni recientes ni más reproducidas.
	 * @param nombre nombre real.
	 * @param apellidos apellidos.
	 * @param fechaNacimiento fecha de nacimiento en formato "dd/MM/yyyy"
	 * @param email dirección de correo electrónico.
	 * @param login nombre de usuario en la app.
	 * @param password contraseña de la cuenta.
	 */
	public Usuario (String nombre, String apellidos, String fechaNacimiento, 
			String email, String nombreUsuario, String contrasena) {
		
		this(nombre, apellidos, fechaNacimiento, email, nombreUsuario, contrasena, false, new ArrayList<ListaCanciones>());
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

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public boolean isPremium() {
		return premium;
	}
	
	public List<ListaCanciones> getListasCanciones() {
		return new ArrayList<>(listasCanciones);
	}
	
	public void addListaCanciones(ListaCanciones lista) {
		listasCanciones.add(lista); 
	}
	
	public ListaCanciones getListaRecientes() {
		return cancionesRecientes;
	}
	
	public void addCancionReciente(Cancion cancion) {
		if (!this.cancionesRecientes.getCanciones().contains(cancion)) {
			if (this.cancionesRecientes.getNumCanciones() < NUM_RECIENTES)
				cancionesRecientes.addCancion(cancion);
			else {
				cancionesRecientes.removeFirst();
				cancionesRecientes.addCancion(cancion);
			}
		}
	}
	
	public void removeCancionReciente(Cancion cancion) {
		cancionesRecientes.removeCancion(cancion);
	}
	
	public Map<Cancion, Integer> getMasReproducidas() {
		return cancionesMasReproducidas;
	}
	
	public void addMasReproducida(Cancion cancion) {
		cancionesMasReproducidas.replace(cancion, cancion.getNumReproducciones());
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
		this.email = email;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		// TODO: comprobar el formato de la password en la vista
		this.password = password;
	}
	
	public void setListasCanciones(List<ListaCanciones> listasCanciones) {
		this.listasCanciones = new ArrayList<>(listasCanciones);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Usuario [nombre= " + nombre + ", apellidos= " + apellidos + ", mail=" + email +
			   "fecha de nacimiento= " + fechaNacimiento.format(formatter) + ", usuario=" + login +
			   ", contraseña=" + password + ", id=" + id + "]";
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
	public double calcDescuento(double precio) { // TODO descuentos
		return 0;
	}

}
