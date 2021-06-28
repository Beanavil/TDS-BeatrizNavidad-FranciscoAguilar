package es.um.tds.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.CatalogoCanciones;
import es.um.tds.modelo.CatalogoUsuarios;
import es.um.tds.modelo.Estilo;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.modelo.Usuario;
import es.um.tds.persistencia.CancionDAO;
import es.um.tds.persistencia.FactoriaDAO;
import es.um.tds.persistencia.ListaCancionesDAO;
import es.um.tds.persistencia.UsuarioDAO;
import umu.tds.componente.CancionesEvent;
import umu.tds.componente.CargadorCanciones;
import umu.tds.componente.ICargadoListener;

/**
 * Clase controlador de AppMusic.
 * 
 * @author Beatriz y Francisco
 */
public final class AppMusic implements ICargadoListener {
	private static AppMusic unicaInstancia;

	private UsuarioDAO adaptadorUsuario;
	private CancionDAO adaptadorCancion;
	private ListaCancionesDAO adaptadorListaCanciones;
	
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoCanciones catalogoCanciones;
	
	private static Usuario usuarioActual;
	
	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException 
	 */
	private AppMusic() throws BDException, DAOException {
		try {
			inicializarAdaptadores();
			inicializarCatalogos();
		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}
	}
	
	/**
	 * Devuelve la única instancia del controlador.
	 * @return Única instancia controlador
	 * @throws BDException
	 * @throws DAOException 
	 */
	public static AppMusic getUnicaInstancia() throws BDException , DAOException {
		if (unicaInstancia == null)
			unicaInstancia = new AppMusic();
		return unicaInstancia;
	}
	
	/**
	 * Inicializa los adaptadores de las clases persistentes.
	 * @throws BDException 
	 * @throws DAOException
	 */
	private void inicializarAdaptadores() throws BDException, DAOException {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = factoria.getUsuarioDAO();
			adaptadorCancion = factoria.getCancionDAO();
			adaptadorListaCanciones = factoria.getListaCancionesDAO();
	}
	
	/**
	 * Inicializa los catálogos.
	 * @throws BDException
	 * @throws DAOException 
	 */
	private void inicializarCatalogos() throws BDException, DAOException {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoCanciones = CatalogoCanciones.getUnicaInstancia();
	}
	
	
	// FUNCIONALIDAD USUARIOS
    
	
    /**
     * Método para comprobar si un usuario está registrado.
     * @param login Nickname del usuario en cuestión
     * @return True si está registrado, false si no
     */
    public boolean esUsuarioRegistrado(String login) {
    	return catalogoUsuarios.getUsuario(login) != null;
    }
    
    /**
     * Método para registrar un nuevo usuario, por defecto se asume que no es premium y no tiene listas
     * de canciones.
     * @param nombre 
     * @param apellidos
     * @param fechaNacimiento
     * @param email
     * @param login
     * @param password
     * @return 
     */
    public boolean registrarUsuario(String nombre, String apellidos, String fechaNacimiento, 
    								String email, String login, String password) {
    	// En la ventana de registro ya se comprueba que no haya otro usuario con el mismo login
    	Usuario usuario = new Usuario(nombre, apellidos, fechaNacimiento, email, login, password);
    	adaptadorUsuario.store(usuario);
    	catalogoUsuarios.addUsuario(usuario);
    	return true;
    }
    
    /**
     * Método para eliminar un usuario.
     * @param usuario Usuario a eliminar
     */
    public void eliminarUsuario(Usuario usuario) {
    	adaptadorUsuario.delete(usuario);
    	catalogoUsuarios.removeUsuario(usuario);
    }
    
    /**
     * Método para hacer el login de un usuario.
     * @param login Nickname del usuario
     * @param password Contraseña del usuario
     * @return False si el usuario no está registrado o la contraseña no
     * coincide, true en caso contrario
     */
    public boolean loginUsuario(String login, String password) {
    	// Si no está registrado no puede hacer login
    	if(!esUsuarioRegistrado(login))
    		return false;
    	// En caso contrario, se recupera el usuario y se comprueba la contraseña
    	Usuario usuario = catalogoUsuarios.getUsuario(login);
    	if (usuario.getPassword().equals(password)) {
    		this.setUsuarioActual(usuario);
    		return true;
    	}
    	return false;
    }
    
    /**
     * Devuelve las listas de canciones del usuario actual. 
     * @return Listas de canciones
     */
    public List<ListaCanciones> getListasUsuario() {
    	List<ListaCanciones> lista = new ArrayList<ListaCanciones>();
    	if (usuarioActual != null)
    		 lista = usuarioActual.getListas();
    	return lista;
    }
    
    /**
     * Devuelve el usuario loggeado actualmente.
     * @return Usuario
     */
    public Usuario getUsuarioActual() {
    	return usuarioActual;
    }
    
    /**
     * Setter de usuarioActual.
     * @param usuario Nuevo usuarioActual
     */
    public void setUsuarioActual(Usuario usuario) {
    	usuarioActual = usuario;
    }
    
    /**
     * Establece al usuario actual como premium.
     */
    public void upgradeUsuarioActual() {
    	if (usuarioActual != null)
    		usuarioActual.setPremium(true);
    }
    
    /**
     * Devuelve al usuario actual a no-premium.
     */
    public void degradeUsuarioActual() {
    	if (usuarioActual != null)
    		usuarioActual.setPremium(false);
    }
    
    /**
     * Indica si el usuario actual es premium.
     * @return
     */
    public boolean isUsuarioPremium() {
    	if (usuarioActual != null)
    		return usuarioActual.isPremium();
    	return false;
    }
    
    /**
     * Devuelve todos los usuarios del catálogo.
     * @return LIsta con todos los usuarios
     */
    public List<Usuario> getUsuarios() {
    	return catalogoUsuarios.getAll();
    }
    
    
    // FUNCIONALIDAD CANCIONES
    
    
    /**
     * Añade una canción a la bd y al catálogo.
     * @param titulo 
     * @param interprete
     * @param estilo
     * @param url
     * @param numReproducciones
     */
    public void registrarCancion(String titulo, String interprete, Estilo estilo, String url,
    								int numReproducciones) {
    	Cancion cancion = new Cancion(titulo, interprete, estilo, url, numReproducciones);
    	// Si ya está en el catálogo (y, por tanto, en la bd) no la volvemos a guardar
    	if (catalogoCanciones.isCancion(cancion))
    		return;
    	adaptadorCancion.store(cancion);
    	catalogoCanciones.addCancion(cancion);
    }
    
    /**
     * Elimina una canción.
     * @param cancion Canción a eliminar
     */
    public void eliminarCancion(Cancion cancion) {
    	adaptadorCancion.delete(cancion);
    	catalogoCanciones.removeCancion(cancion);
    }
    
    /**
     * Añade una canción reciente a la lista de canciones recientes del usuario.
     * @param cancion
     */
    public void addReciente(Cancion cancion) {
    	// Añadir canción a la lista de recientes del usuario actual
    	usuarioActual.addCancionReciente(cancion);
    	// Actualiza la bd
    	adaptadorUsuario.update(usuarioActual);
    }
    
    /**
     * Aumenta el número de reproducciones de una canción en 1 unidad.
     * @param cancion
     */
    public void actualizarNumReproducciones(Cancion cancion) {
    	// Actualizamos reproducciones de la canción
    	cancion.setNumReproducciones(cancion.getNumReproducciones() + 1);
    	adaptadorCancion.update(cancion);
    	// Actualizamos más reproducidas del usuario
    	usuarioActual.addMasReproducida(cancion);
    	adaptadorUsuario.update(usuarioActual);
    }
    
    /**
     * Utiliza el componente CargardorCanciones para cargar canciones desde un
     * archivo xml.
     * @param fichero Ruta del fichero xml 
     */
    public void cargarCanciones(String fichero) {
    	CargadorCanciones c = new CargadorCanciones();
    	c.addOyente(this);
    	c.setArchivoCanciones(fichero);
    }
    
    /**
     * El cargador de canciones lanza un evento que captura el controlador
     * y con este método lo maneja para añadir las canciones cargadas a la bd.
     */
    @Override
    public void enteradoCarga(CancionesEvent cEvent) {
    	cEvent.getCanciones().getCancion()
    	.stream()
    	.forEach(c -> {
    					this.registrarCancion(c.getTitulo(), c.getInterprete(), 
    							Estilo.valor(c.getEstilo()), c.getURL(), 0);
    				  });
    } 
    
    /**
     * Devuelve todas las canciones del catálogo.
     * @return Lista con todas las canciones
     */
    public List<Cancion> getCanciones() {
    	return catalogoCanciones.getAll();
    }
    
    /**
     * Devuelve canciones filtradas por un estilo concreto.
     * @param estilo
     * @return Lista de canciones encontradas
     */
    public List<Cancion> buscarPorEstilo(String estilo) {
    	return catalogoCanciones.getAllStyle(estilo);
    }
    
    /**
     * Devuelve canciones filtradas por un intérprete concreto.
     * @param interprete
     * @return Lista de canciones encontradas
     */
    public List<Cancion> buscarPorInterprete(String interprete) {
    	return catalogoCanciones.getAllArtist(interprete);
    }
    
    /**
     * Devuelve canciones filtradas por un intérprete y estilo concretos.
     * @param interprete
     * @param estilo
     * @return Lista de canciones encontradas
     */
    public List<Cancion> buscarPorInterpreteEstilo(String interprete, String estilo) {
    	return catalogoCanciones.getAllArtistStyle(interprete, estilo);
    }
    
    
    // FUNCIONALIDAD LISTAS DE CANCIONES
    
    
    /**
     * Indica si una lista de canciones está en la bd.
     * @param nombre Nombre de la lista
     * @return True si la lista está en la bd, false en caso contrario
     */
    public boolean existeLista(String nombre) {
    	if (usuarioActual != null) {
    		boolean existe = usuarioActual.getListas()
							.stream()
							.anyMatch(lc -> lc.getNombre().equals(nombre));
    		return existe;
    	}
    	return false;
    }
    
    /**
     * Crea una lista de canciones para el usuario actual.
     * @param nombre Nombre de la lista a crear
     */
    public void crearLista(String nombre) {
    	if (usuarioActual != null) {
    		// Creamos y añadimos la lista a las listas del usuario
    		ListaCanciones lista = new ListaCanciones(nombre);
    		usuarioActual.addListaCanciones(lista);
    		// Actualizamos la bd
    		adaptadorListaCanciones.store(lista);
    		adaptadorUsuario.update(usuarioActual);
    	}
    }
    
    /**
     * Elimina una lista de canciones del usuario actual.
     * @param lista Lista a borrar
     */
    public void eliminarLista(ListaCanciones lista) {
    	// Eliminamos primero la lista de las listas del usuario
    	usuarioActual.removeListaCanciones(lista);
    	// Actualizamos el usuario en la bd
    	adaptadorUsuario.update(usuarioActual);
    	// Eliminamos la lista de la bd
    	adaptadorListaCanciones.delete(lista);
    }
    
    /**
     * Recupera una lista de canciones por su nombre.
     * @param nombre Nombre de la lista.
     * @return Lista si existe o null si no existe
     */
    public ListaCanciones getListaCanciones(String nombre) {
    	if (usuarioActual != null) {
    		ListaCanciones lista = usuarioActual.getListas()
    							   .stream()
				    			   .filter(lc -> lc.getNombre().equals(nombre))
				    			   .findAny()
				    			   .orElse(null);
    		return lista;
    	}
    	return null;
    }
    
    /**
     * Añade una canción a una lista de canciones.
     * @param lista Lista a la que añadir la canción
     * @param cancion Canción a añadir
     */
    public void addCancionToLista (ListaCanciones lista, Cancion cancion) {
    	if (usuarioActual != null && !lista.isCancionEnLista(cancion)) {
    			lista.addCancion(cancion);
        		adaptadorListaCanciones.update(lista);
    	}
    }
 
    /**
     * Elimina una canción de una lista.
     * @param lista Lista de la que eliminar la canción
     * @param cancion Canción a eliminar
     */
    public void eliminarCancionFromLista(ListaCanciones lista, Cancion cancion) {
    	if (usuarioActual != null) {
    		lista.removeCancion(cancion);
    		adaptadorListaCanciones.update(lista);
    	}
    }
    
    /**
     * Devuelve la lista de canciones recientes del usuario actual.
     * @return Lista de recientes
     */
    public List<Cancion> getCancionesRecientes() {
		List<Cancion> recientes = new ArrayList<>();
		if (usuarioActual != null) {
			ListaCanciones listaRecientes = usuarioActual.getRecientes();
			recientes = (List<Cancion>)listaRecientes.getCanciones();
		}
		return recientes;
	}

    /**
     * Devuelve las canciones más reproducidas por el usuario actual.
     * @return Lista de más reproducidas
     */
	public List<Cancion> getCancionesMasReproducidas() {
		List<Cancion> masReproducidas = new ArrayList<>();
		if (usuarioActual != null)
			masReproducidas = usuarioActual.getMasReproducidas().keySet()
							  .stream()
							  .collect(Collectors.toList());
		return masReproducidas;
	}
}
