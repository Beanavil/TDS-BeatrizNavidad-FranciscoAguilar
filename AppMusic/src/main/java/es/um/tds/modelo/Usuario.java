package es.um.tds.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import es.um.tds.modelo.descuentos.Descuento65;
import es.um.tds.modelo.descuentos.DescuentoNulo;
import es.um.tds.modelo.descuentos.DescuentoPremium;
import es.um.tds.modelo.descuentos.IDescuento;

/**
 * Representa un usuario de la aplicación.
 * 
 * @author Beatriz y Francisco
 */
public class Usuario {
	public static final String LISTA_RECIENTES = "Canciones recientes";
	public static final int NUM_RECIENTES = 10;
	
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String email;
	private String login;
	private String password;
	private boolean premium;
	private IDescuento descuento;
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
		this.descuento = calcDescuento();
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
	
	public IDescuento getDescuento() {
		return this.descuento;
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
	
	/**
	 * Añade una canción a la lista de recientes. Si ya hay 10 (el máximo)
	 * elimina la menos reciente (la primera añadida)
	 * @param cancion Canción a añadir a recientes
	 */
	public void addCancionReciente(Cancion cancion) {
		if (!this.isReciente(cancion)) {
			if (this.cancionesRecientes.getNumCanciones() < NUM_RECIENTES)
				cancionesRecientes.addCancion(cancion);
			else {
				cancionesRecientes.removeFirst();
				cancionesRecientes.addCancion(cancion);
			}
		}
	}
	
	/**
	 * Indica si una cancion está en recientes o no
	 * @param cancion
	 * @return
	 */
	public boolean isReciente(Cancion cancion) {
		return cancionesRecientes.getCanciones().stream()		
		.anyMatch(c -> c.getTitulo().equals(cancion.getTitulo()));
	}
	
	
	/**
	 * Indica si una cancion está en más reproducidas o no
	 * @param cancion
	 * @return
	 */
	public boolean isMasReproducida(Cancion cancion) {
		return cancionesMasReproducidas.keySet().stream()		
		.anyMatch(c -> c.getTitulo().equals(cancion.getTitulo()));
	}
	
	/**
	 * Elimina una canción de recientes
	 * @param cancion
	 */
	public void removeCancionReciente(Cancion cancion) {
		cancionesRecientes.removeCancion(cancion);
	}
	
	
	public Map<Cancion, Integer> getMasReproducidas() {
		return cancionesMasReproducidas;
	}
	
	
	public void addMasReproducida(Cancion cancion) {
		if (isMasReproducida(cancion))
			cancionesMasReproducidas.replace(cancion, cancion.getNumReproducciones());
		else
			cancionesMasReproducidas.put(cancion, cancion.getNumReproducciones());
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
		this.actualizaDescuento();
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
		this.password = password;
	}
	
	public void actualizaDescuento() {
		this.descuento = this.calcDescuento();
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
	 * Método para calcular el descuento a aplicar en función 
	 * del tipo de cuenta.
	 * @param precio cantidad de la que calculamos el descuento
	 * @return descuento a aplicar
	 */
	private IDescuento calcDescuento() { 
		LocalDate fechaActual = LocalDate.now();
		if (Period.between(fechaNacimiento, fechaActual).getYears() >= 65)
			return new Descuento65();
		else if (this.premium)
			return new DescuentoPremium();
		else 
			return new DescuentoNulo();		
	}
}
