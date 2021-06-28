package es.um.tds.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
	
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String email;
	private String login;
	private String password;
	private boolean premium;
	private IDescuento descuento;
	private List<ListaCanciones> listas;
	private ListaCanciones recientes;
	private int id;
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "ES"));
	public static final String LISTA_RECIENTES = "Canciones recientes";
	public static final int NUM_RECIENTES = 10;
	
	/**
	 * Constructor.
	 * @param nombre Nombre real.
	 * @param apellidos Apellidos.
	 * @param fechaNacimiento Fecha de nacimiento en formato "dd/MM/yyyy"
	 * @param email Dirección de correo electrónico.
	 * @param login Nombre de usuario en la app.
	 * @param password Contraseña de la cuenta.
	 * @param premium Si es premium o no.
	 * @param listas Listas de canciones del usuario.
	 * @param recientes Lista de canciones reproducidas recientemente
	 */
	public Usuario (String nombre, String apellidos, String fechaNacimiento, String email, String nombreUsuario, 
					String contrasena, boolean premium, List<ListaCanciones> listas, ListaCanciones recientes) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento, formatter); 
		this.email = email;
		this.login = nombreUsuario;
		this.password = contrasena;
		this.premium = premium;
		this.descuento = calcDescuento();
		this.listas = new ArrayList<>(listas);
		
		if (recientes.getNumCanciones() > 10)
			this.recientes = new ListaCanciones(LISTA_RECIENTES, recientes.getCanciones().subList(0, 10));
		else {
			recientes.setNombre(LISTA_RECIENTES);
			this.recientes = recientes;
		}
		
		this.id = -1;
	}
	
	/**
	 * Constructor sin recientes.
	 * @param nombre Nombre real.
	 * @param apellidos Apellidos.
	 * @param fechaNacimiento Fecha de nacimiento en formato "dd/MM/yyyy"
	 * @param email Dirección de correo electrónico.
	 * @param login Nombre de usuario en la app.
	 * @param password Contraseña de la cuenta.
	 * @param premium Si es premium o no.
	 * @param listas Listas de canciones del usuario.
	 */
	public Usuario (String nombre, String apellidos, String fechaNacimiento, String email, String nombreUsuario, 
					String contrasena, boolean premium, List<ListaCanciones> listas) {
		this(nombre, apellidos, fechaNacimiento, email, nombreUsuario, contrasena, premium, listas, 
				new ListaCanciones(LISTA_RECIENTES)); 
	}
	
	/**
	 * Constructor sin premium ni listas ni recientes.
	 * @param nombre Nombre real.
	 * @param apellidos Apellidos.
	 * @param fechaNacimiento Fecha de nacimiento en formato "dd/MM/yyyy"
	 * @param email Dirección de correo electrónico.
	 * @param login Nombre de usuario en la app.
	 * @param password Contraseña de la cuenta.
	 */
	public Usuario (String nombre, String apellidos, String fechaNacimiento, 
					String email, String nombreUsuario, String contrasena) {
		this(nombre, apellidos, fechaNacimiento, email, nombreUsuario, contrasena, 
				false, new ArrayList<ListaCanciones>());
	}

	
	// GETTERS
	
	
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
	
	public List<ListaCanciones> getListas() {
		return new ArrayList<>(listas);
	}
	
	public ListaCanciones getRecientes() {
		return recientes;
	}
	
	public int getId() {
		return id;
	}
	
	
	// SETTERS	
	
	
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
		this.listas = new ArrayList<>(listasCanciones);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	// FUNCIONALIDAD
	
	
	/**
	 * Indica si una cancion está en recientes o no
	 * @param cancion
	 * @return
	 */
	public boolean isReciente(Cancion cancion) {
		return recientes.getCanciones().stream()		
				.anyMatch(c -> c.getTitulo().equals(cancion.getTitulo()));
	}
	
	/**
	 * Añade una canción a la lista de recientes. Si ya hay 10 (el máximo)
	 * elimina la menos reciente (la primera añadida)
	 * @param cancion Canción a añadir a recientes
	 */
	public void addCancionReciente(Cancion cancion) {
		if (!this.isReciente(cancion)) {
			if (this.recientes.getNumCanciones() < NUM_RECIENTES)
				recientes.addCancion(cancion);
			else {
				recientes.removeFirst();
				recientes.addCancion(cancion);
			}
		}
	}
	
	/**
	 * Elimina una canción de recientes.
	 * @param cancion Canción a eliminar de recientes
	 */
	public void removeCancionReciente(Cancion cancion) {
		recientes.removeCancion(cancion);
	}
	
	/**
	 * Añade una lista de canciones al usuario.
	 * @param lista Lista a añadir
	 */
	public void addListaCanciones(ListaCanciones lista) {
		listas.add(lista); 
	}
	
	/**
	 * Elimina una lista de las listas del usuario.
	 * @param lista Lista a eliminar
	 */
	public void removeListaCanciones(ListaCanciones lista) {
		listas.remove(lista);
	}

	/**
	 * Calcula el descuento a aplicar en función del tipo de cuenta del usuario.
	 * @return Descuento a aplicar
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
