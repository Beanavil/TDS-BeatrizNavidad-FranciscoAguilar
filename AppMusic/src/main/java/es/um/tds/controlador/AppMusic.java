package es.um.tds.controlador;

import java.util.List;

import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.CatalogoCanciones;
import es.um.tds.modelo.CatalogoUsuarios;
import es.um.tds.modelo.Usuario;
import es.um.tds.persistencia.CancionDAO;
import es.um.tds.persistencia.DAOException;
import es.um.tds.persistencia.FactoriaDAO;
import es.um.tds.persistencia.ListaCancionesDAO;
import es.um.tds.persistencia.UsuarioDAO;

/**
 * Clase controlador de AppMusic
 * 
 * @author Beatriz y Francisco
 */
public final class AppMusic 
{
	private static AppMusic unicaInstancia;

	private UsuarioDAO adaptadorUsuario;
	private CancionDAO adaptadorCancion;
	private ListaCancionesDAO adaptadorListaCanciones;
	
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoCanciones catalogoCanciones;
	
	private Usuario usuarioActual;
	
	
	/**
	 * Método principal
	 * @param args
	 */
	public static void main( String[] args )
	{
		System.out.println( "Hello World!" );
	}

	
	/**
	 * Constructor 
	 */
	private AppMusic() {
		inicializarAdaptadores();
		inicializarCatalogos();
	}
	
	
	/**
	 * Método para inicializar los adaptadores de las clases persistentes
	 */
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorCancion = factoria.getCancionDAO();
		adaptadorListaCanciones = factoria.getListaCancionesDAO();
	}
	
	
	/**
	 * Método para inicializar los catálogos
	 */
	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoCanciones = CatalogoCanciones.getUnicaInstancia();
	}
	
	
	// Funcionalidad
    
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
     * @return True si se ha registrado y false si no
     */
    public boolean registrarUsuario(String nombre, String apellidos, String fechaNacimiento, String email,
    		String login, String password) {
    	
    	// Si el usuario ya está registrado, no hacemos nada
    	if (esUsuarioRegistrado(login))
    		return false;
    	
    	// Si no, se crea el usuario y se añade a la bbdd y al catálogo
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
     * Método para confirmar si un usuario puede hacer login
     * @param login Nickname del usuario
     * @param password Contraseña del usuario
     * @return False si el usuario no está registrado o la contraseña no
     * coincide, true en caso contrario
     */
    public boolean loginUsuario(String login, String password) {
    	if(!esUsuarioRegistrado(login))
    		return false;
    	Usuario usuario = catalogoUsuarios.getUsuario(login);
    	return usuario.getPassword().equals(password);
    }
    
    
    public void play() {
    	// TODO
    }
    
    public void crearLista() {
    	// TODO
    }
    
    
    // Getters
    
    /**
     * Método para obtener la única instancia del controlador
     * @return Única instancia controlador
     */
    public static AppMusic getUnicaInstancia() {
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
}
