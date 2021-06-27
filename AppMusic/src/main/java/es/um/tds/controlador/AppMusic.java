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
 * Clase controlador de AppMusic
 * 
 * @author Beatriz y Francisco
 */
public final class AppMusic implements ICargadoListener{
	private static AppMusic unicaInstancia;

	private UsuarioDAO adaptadorUsuario;
	private CancionDAO adaptadorCancion;
	private ListaCancionesDAO adaptadorListaCanciones;
	
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoCanciones catalogoCanciones;
	
	private static Usuario usuarioActual;
	

	
	/**
	 * Constructor 
	 * @throws DAOException 
	 * @throws BDException
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
	 * Método para inicializar los adaptadores de las clases persistentes
	 * @throws DAOException
	 * @throws BDException 
	 */
	private void inicializarAdaptadores() throws BDException, DAOException {
			FactoriaDAO factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = factoria.getUsuarioDAO();
			adaptadorCancion = factoria.getCancionDAO();
			adaptadorListaCanciones = factoria.getListaCancionesDAO();
	}
	
	
	/**
	 * Método para inicializar los catálogos
	 * @throws DAOException 
	 * @throws BDException
	 */
	private void inicializarCatalogos() throws BDException, DAOException {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoCanciones = CatalogoCanciones.getUnicaInstancia();
	}
	
	
	// Funcionalidad usuarios
    
    /**
     * Método para comprobar si un usuario está registrado
     * @param login Nickname del usuario en cuestión
     * @return True si está registrado, false si no
     */
    public boolean esUsuarioRegistrado(String login) {
    	return catalogoUsuarios.getUsuario(login) != null;
    }
    
    
    /**
     * Método para registrar un nuevo usuario, por defecto se asume que no es premium y no tiene listas
     * de canciones
     * @param nombre 
     * @param apellidos
     * @param fechaNacimiento
     * @param email
     * @param login
     * @param password
     * @return 
     */
    public boolean registrarUsuario(String nombre, String apellidos, String fechaNacimiento, String email,
    		String login, String password) {
    	
    	// En la ventana de registro ya se comprueba que no haya otro usuario con el mismo login
    	Usuario usuario = new Usuario(nombre, apellidos, fechaNacimiento, email, login, password);
    	adaptadorUsuario.store(usuario);
    	catalogoUsuarios.addUsuario(usuario);
    	return true;
    }
    
    
    /**
     * Método para eliminar un usuario
     * @param usuario Usuario a eliminar
     * @return True si es eliminado, false si no
     */
    public boolean eliminarUsuario(Usuario usuario) {
    	// Si el usuario no está registrado, no hacemos nada
    	if (!esUsuarioRegistrado(usuario.getLogin()))
    		return false;
    	
    	// Si está registrado, lo eliminamos de la bbdd y del catálogo
    	adaptadorUsuario.delete(usuario);
    	catalogoUsuarios.removeUsuario(usuario);
    	return true;
    }
    
    /**
     * Método para hacer el login de un usuario
     * @param login Nickname del usuario
     * @param password Contraseña del usuario
     * @return False si el usuario no está registrado o la contraseña no
     * coincide, true en caso contrario
     */
    public boolean loginUsuario(String login, String password) {
    	if(!esUsuarioRegistrado(login))
    		return false;
    	Usuario usuario = catalogoUsuarios.getUsuario(login);
    	if (usuario.getPassword().equals(password)) {
    		this.setUsuarioActual(usuario);
    		return true;
    	}
    	return false;
    }
    
    /**
     * Retorna las listas de canciones del usuario actual //TODO No sé si hay que comprobar
     * que el usuario actual sea distinto de null
     * @return
     */
    public List<ListaCanciones> usuarioGetListas() {
    		List<ListaCanciones> lista = usuarioActual.getListasCanciones();
    		return lista;
    }
    
    // Funcionalidad canción
    
    /**
     * Añade una canción a la bd y al catálogo
     * @param titulo 
     * @param interprete
     * @param estilo
     * @param url
     * @param numReproducciones
     */
    public void registrarCancion(String titulo, String interprete, Estilo estilo, String url,
    		int numReproducciones) {
    	Cancion cancion = new Cancion(titulo, interprete, estilo, url, numReproducciones);
    	if (catalogoCanciones.isCancion(cancion))
    		return;
    	adaptadorCancion.store(cancion);
    	catalogoCanciones.addCancion(cancion);
    }
    
    
    /**
     * Método para eliminar una canción
     * @param cancion Canción a eliminar
     * @return True si es eliminada, false si no
     */
    public boolean eliminarCancion(Cancion cancion) {
    	// Si la canción no está en la bd, no hacemos nada
    	if (cancion.getId() < 0)
    		return false;
    	
    	// Si está registrada, la eliminamos de la bbdd y del catálogo
    	adaptadorCancion.delete(cancion);
    	catalogoCanciones.removeCancion(cancion);
    	return true;
    }
    
    
    /**
     * Retorna canciones filtradas por un estilo concreto
     * @param estilo
     */
    public List<Cancion> buscarPorEstilo(String estilo) {
    	return catalogoCanciones.getAllStyle(estilo);
    }
    
    
    /**
     * Retorna canciones filtradas por un intérprete concreto
     * @param interprete
     */
    public List<Cancion> buscarPorInterprete(String interprete) {
    	return catalogoCanciones.getAllArtist(interprete);
    }
    
    
    /**
     * Retorna canciones filtradas por un intérprete y estilo concretos
     * @param interprete
     * @param estilo
     * @return
     */
    public List<Cancion> buscarPorInterpreteEstilo(String interprete, String estilo) {
    	return catalogoCanciones.getAllArtistStyle(interprete, estilo);
    }
    
    
    /**
     * Retorna todas las canciones del catálogo
     */
    public List<Cancion> obtenerCanciones() {
    	return catalogoCanciones.getAll();
    }
    
    /**
     * Aumenta el número de reproducciones de una canción en 1 unidad
     * @param cancion
     */
    public void actualizarNumReproducciones(Cancion cancion) {
    	// Actualizamos reproducciones de cancion
    	cancion.setNumReproducciones(cancion.getNumReproducciones() + 1);
    	adaptadorCancion.update(cancion);
    	
    	// Actualizamos más reproducidas del usuario
    	usuarioActual.addMasReproducida(cancion);
    	adaptadorUsuario.update(usuarioActual);
    }
    
    
    /**
     * Utiliza el componente CargarCanciones para cargar canciones desde un
     * archivo xml
     * @param fichero Ruta del fichero xml 
     */
    public void cargarCanciones(String fichero) {
    	CargadorCanciones c = new CargadorCanciones();
    	c.addOyente(this);
    	c.setArchivoCanciones(fichero);
    }
    
    
    /**
     * El cargador de canciones lanza un evento que captura el controlador
     * y con este método lo maneja para añadir las canciones cargadas a la bd
     */
    @Override
    public void enteradoCarga(CancionesEvent cEvent) {
    	cEvent.getCanciones().getCancion().stream()
    	.forEach(
    			c -> {
    					this.registrarCancion(c.getTitulo(), c.getInterprete(), 
    							Estilo.valor(c.getEstilo()), c.getURL(), 0);
    				 });
    } 
    
    
    /**
     * Añade una canción reciente a la lista de canciones recientes del usuario
     * @param cancion
     */
    public void addReciente(Cancion cancion) {
    	// Añadir canción a la lista de recientes del usuario actual
    	usuarioActual.addCancionReciente(cancion);
    	// Actualiza la bd
    	adaptadorUsuario.update(usuarioActual);
    }
    
    // Funcionalidad lista de canciones
    
    /**
     * @param nombreLista Nombre de la lista a comprobar existencia
     * @return True si la lista ya pertenecía a las listas del usuario
     * false en otro caso
     */
    public boolean existeLista(String nombreLista) {
    	if (usuarioActual != null) {
    		boolean existe = usuarioActual.getListasCanciones().stream()
    				.anyMatch(lc -> lc.getNombre().equals(nombreLista));
    		return existe;
    	}
    	return false;
    }
    
    public void crearLista(String nombre) {
    	if (usuarioActual != null) {
    		ListaCanciones lista = new ListaCanciones(nombre);
    		usuarioActual.addListaCanciones(lista);
    		adaptadorListaCanciones.store(lista);
    		adaptadorUsuario.update(usuarioActual);
    	}
    }
    
    /**
     * Elimina una lista de canciones dada de la BD //TODO
     * @param lista
     */
    public void eliminarLista(ListaCanciones lista) {
    	adaptadorListaCanciones.delete(lista);
    	usuarioActual.removeListaCanciones(lista);
    	adaptadorUsuario.update(usuarioActual);
    }
    
    /**
     * Recupera una lista de canciones por su nombre //TODO No creo que sea la mejor forma de hacerlo
     * @param nombreLista
     * @return
     */
    public ListaCanciones getListaCanciones(String nombreLista) {
    	if (usuarioActual != null) {
    		ListaCanciones lista = usuarioActual.getListasCanciones().stream()
    				.filter(lc -> lc.getNombre().equals(nombreLista))
    				.findAny()
    				.orElse(null);
    	return lista;
    	}
    	return null;
    }
    
    
    public void addCancionToLista (ListaCanciones lista, Cancion cancion) {
    	if (usuarioActual != null) {
    		if (!lista.isCancionEnLista(cancion)) {
    			lista.addCancion(cancion);
        		adaptadorListaCanciones.update(lista);
        		//TODO Esto es necesario??
        		adaptadorUsuario.update(usuarioActual);
    		}
    	}
    }
    
    
    public void eliminarCancionFromLista(ListaCanciones lista, Cancion cancion) {
    	if (usuarioActual != null) {
    		lista.removeCancion(cancion);
    		adaptadorListaCanciones.update(lista);
    		//TODO Esto es necesario??
    		adaptadorUsuario.update(usuarioActual);
    	}
    }
    
    public List<Cancion> getCancionesRecientes() {
		List<Cancion> recientes = new ArrayList<>();
		if (usuarioActual != null) {
			ListaCanciones listaRecientes = usuarioActual.getListaRecientes();
			recientes = (List<Cancion>) listaRecientes.getCanciones();
		}
		return recientes;
	}


	public List<Cancion> getCancionesMasReproducidas() {
		List<Cancion> masReproducidas = new ArrayList<>();
		if (usuarioActual != null)
			masReproducidas = usuarioActual.getMasReproducidas().keySet().stream()
			.collect(Collectors.toList());
		return masReproducidas;
	}
    
    // Getters
    
    /**
     * Método para obtener la única instancia del controlador
     * @return Única instancia controlador
     * @throws DAOException 
	 * @throws BDException
     */
    public static AppMusic getUnicaInstancia() throws BDException , DAOException {
    	if (unicaInstancia == null)
    		unicaInstancia = new AppMusic();
    	return unicaInstancia;
    }
    
    
    public Usuario getUsuarioActual() {
    	return usuarioActual;
    }
    
    
    public List<Usuario> getUsuarios() {
    	return catalogoUsuarios.getAll();
    }
    
    
    public List<Cancion> getCanciones() {
    	return catalogoCanciones.getAll();
    }
    
    public void setUsuarioActual(Usuario usuario) {
    	usuarioActual = usuario;
    }
    
    public void upgradeUsuarioActual() {
    	if (usuarioActual != null)
    		usuarioActual.setPremium(true);
    }
    
    public void degradeUsuarioActual() {
    	if (usuarioActual != null)
    		usuarioActual.setPremium(false);
    }
    
    public boolean isUsuarioPremium() {
    	if (usuarioActual != null)
    		return usuarioActual.isPremium();
    	return false;
    }

}
