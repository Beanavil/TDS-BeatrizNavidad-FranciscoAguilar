package es.um.tds.controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.CatalogoCanciones;
import es.um.tds.modelo.CatalogoUsuarios;
import es.um.tds.modelo.Estilo;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.modelo.Usuario;
import es.um.tds.persistencia.CancionDAO;
import es.um.tds.persistencia.DAOException;
import es.um.tds.persistencia.FactoriaDAO;
import es.um.tds.persistencia.ListaCancionesDAO;
import es.um.tds.persistencia.UsuarioDAO;
import es.um.tds.vista.VentanaLogin;
import es.um.tds.vista.VentanaPrincipal;
import es.um.tds.vista.VentanaRegistro;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import umu.tds.componente.Canciones;
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
	
	private static String binPath;
	private static String tempPath;
	private static MediaPlayer mediaPlayer;
	
	
	/**
	 * Método principal
	 * @param args
	 */
	public static void main( String[] args ) throws IOException
	{
		//VentanaLogin vl = new VentanaLogin();
		//VentanaRegistro vr = new VentanaRegistro();
		AppMusic.getUnicaInstancia().setUsuarioActual(new Usuario("bea", "navidad", "25/11/1999", "beatriznavidad@yahoo.es", "bea", "123"));
		VentanaPrincipal vp = new VentanaPrincipal();
	}

	
	/**
	 * Constructor 
	 */
	private AppMusic() {
		inicializarAdaptadores();
		inicializarCatalogos();
		binPath = AppMusic.class.getClassLoader().getResource(".").getPath();
		tempPath = binPath.replace("/bin", "/temp");
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
    	List<Cancion> canciones = adaptadorCancion.getAllStyle(estilo);
    	if (canciones == null)
    		return new ArrayList<Cancion>();
    	return canciones;
    }
    
    
    /**
     * Retorna canciones filtradas por un intérprete concreto
     * @param interprete
     */
    public List<Cancion> buscarPorInterprete(String interprete) {
    	List<Cancion> canciones = adaptadorCancion.getAllArtist(interprete);
    	if (canciones == null)
    		return new ArrayList<Cancion>();
    	return canciones;
    }
    
    
    /**
     * Retorna canciones filtradas por un intérprete y estilo concretos
     * @param interprete
     * @param estilo
     * @return
     */
    public List<Cancion> buscarPorInterpreteEstilo(String interprete, String estilo) {
    	List<Cancion> canciones = adaptadorCancion.getAllArtistStyle(interprete, estilo);
    	if (canciones == null)
    		return new ArrayList<Cancion>();
    	return canciones;
    }
    
    
    /**
     * Retorna todas las canciones de la bd
     */
    public List<Cancion> obtenerCanciones() {
    	List<Cancion> canciones = adaptadorCancion.getAll();
    	if (canciones == null)
    		return new ArrayList<Cancion>();
    	return canciones;
    }
    
    /**
     * Aumenta el número de reproducciones de una canción en 1 unidad
     * @param cancion
     */
    public void actualizarNumReproducciones(Cancion cancion) {
    	catalogoCanciones.removeCancion(cancion); 
    	cancion.setNumReproducciones(cancion.getNumReproducciones() + 1);
    	adaptadorCancion.update(cancion);
    	catalogoCanciones.addCancion(cancion);
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
    public void enteradoCarga(EventObject arg0) {
    	CancionesEvent cEvent = (CancionesEvent) arg0;
    	cEvent.getCanciones().getCancion().stream()
    	.forEach(c -> {this.registrarCancion(c.getTitulo(), c.getInterprete(), Estilo.valor(c.getEstilo()), c.getURL(), 0);});
    } 
    
    
    /**
     * Reproduce una canción 
     * @param url URL de la ubicación de la canción
     */
    public void playCancion(String url) {
		URL uri = null;
		try {
			com.sun.javafx.application.PlatformImpl.startup(() -> {
			});

			uri = new URL(url);

			System.setProperty("java.io.tmpdir", tempPath);
			Path mp3 = Files.createTempFile("now-playing", ".mp3");

			System.out.println(mp3.getFileName());
			try (InputStream stream = uri.openStream()) {
				Files.copy(stream, mp3, StandardCopyOption.REPLACE_EXISTING);
			}
			System.out.println("finished-copy: " + mp3.getFileName());

			Media media = new Media(mp3.toFile().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			mediaPlayer.play();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
    
    
    /**
     * Para una canción en reproducción
     */
    public void stopCancion() {
		if (mediaPlayer != null) mediaPlayer.stop();
		File directorio = new File(tempPath);
		String[] files = directorio.list();
		for (String archivo : files) {
			File fichero = new File(tempPath + File.separator + archivo);
			fichero.delete();
		}
	}
    
    
    // Funcionalidad lista de canciones
    
    public void crearLista(String nombre) {
    	if (usuarioActual != null) {
    		ListaCanciones lista = new ListaCanciones(nombre);
    		catalogoUsuarios.removeUsuario(usuarioActual);
    		usuarioActual.addListaCanciones(lista);
    		adaptadorUsuario.update(usuarioActual);
    		catalogoUsuarios.addUsuario(usuarioActual); // TODO ver lo del update en el catálogo
    	}
    }
    
    
    public void addCancion(ListaCanciones lista, List<Cancion> cancion) {
    	
    }
    
    
    public void removeCancion(ListaCanciones lista, List<Cancion> cancion) {
    	
    }
    
    /**
	 * Método auxiliar para ver si un string contiene a otro como subcadena sin tener en 
	 * cuenta las mayúsculas.
	 * @param str String en el que buscamos la subcadena
	 * @param subStr Posible subcadena de str
	 * @return Si subStr está contenido en str, case insensitive
	 */
	public static boolean containsIgnoreCase(String str, String subStr) {
        return str.toLowerCase().contains(subStr.toLowerCase());
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
    
    public void setUsuarioActual(Usuario usuario) {
    	this.usuarioActual = usuario;
    }
}
