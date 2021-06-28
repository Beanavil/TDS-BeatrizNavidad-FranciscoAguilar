package es.um.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

/**
 * Adaptador del DAO para la clase Usuario.
 * 
 * @author Beatriz y Francisco
 */
public class TDSUsuarioDAO implements UsuarioDAO {
	private static final String USUARIO = "Usuario";
	private static final String NOMBRE = "nombre";
	private static final String APELLIDOS = "apellidos";
	private static final String FECHANACIMIENTO = "fechaNacimiento";
	private static final String EMAIL = "email";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String PREMIUM = "premium";
	private static final String LISTASCANCIONES = "listasCanciones";
	private static final String CANCIONESRECIENTES = "cancionesRecientes";
	private ServicioPersistencia servPersistencia;
	private static TDSUsuarioDAO unicaInstancia;
	private TDSListaCancionesDAO adaptadorListaCanciones;  

	/**
	 * Constructor.
	 * @throws BDException 
	 * @throws DAOException 
	 */
	private TDSUsuarioDAO() throws BDException, DAOException {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		adaptadorListaCanciones = TDSListaCancionesDAO.getUnicaInstancia();
	}
	
	/**
	 * Crea una instancia del adaptador o devuelve la que ya haya creada.
	 * @return única instancia del adaptador de Usuario
	 * @throws BDException
	 * @throws DAOException 
	 */
	public static TDSUsuarioDAO getUnicaInstancia() throws BDException, DAOException {
		if (unicaInstancia == null) 
			unicaInstancia = new TDSUsuarioDAO(); 
		return unicaInstancia;       
	} 
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(Usuario usuario)  {
		// Comprobamos primero que no esté ya registrado
		if (usuario.getId() >= 0)
			return;
		// Guardamos las listas
		usuario.getListas().stream().forEach(lc -> adaptadorListaCanciones.store(lc));
		// Guardamos la lista de recientes
		adaptadorListaCanciones.store(usuario.getRecientes());

		Entidad eUsuario = UsuarioToEntidad(usuario);
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(Usuario usuario) {
		// Si no está no podemos borrar al usuario
		if (usuario.getId() < 0)
			return false;
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		// Eliminamos las listas
		usuario.getListas().stream()
						   .forEach(lc -> adaptadorListaCanciones.delete(lc));
		// Eliminamos la lista de recientes
		adaptadorListaCanciones.delete(usuario.getRecientes());
		
		return servPersistencia.borrarEntidad(eUsuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Usuario usuario) {
		// Si no está no podemos actualizar los datos del usuario
		if (usuario.getId() < 0)
			return;
		
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBRE);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBRE, usuario.getNombre());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, APELLIDOS);
		servPersistencia.anadirPropiedadEntidad(eUsuario, APELLIDOS, usuario.getApellidos());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, FECHANACIMIENTO);
		servPersistencia.anadirPropiedadEntidad(eUsuario, FECHANACIMIENTO, usuario.getFechaNacimiento().format(Usuario.formatter));
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, EMAIL);
		servPersistencia.anadirPropiedadEntidad(eUsuario, EMAIL, usuario.getEmail());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, LOGIN);
		servPersistencia.anadirPropiedadEntidad(eUsuario, LOGIN, usuario.getLogin());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PASSWORD);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PASSWORD, usuario.getPassword());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PREMIUM);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PREMIUM, String.valueOf(usuario.isPremium()));
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, LISTASCANCIONES);
		servPersistencia.anadirPropiedadEntidad(eUsuario, LISTASCANCIONES, getIdsFromListasCanciones(usuario.getListas()));
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, CANCIONESRECIENTES);
		servPersistencia.anadirPropiedadEntidad(eUsuario, CANCIONESRECIENTES, 
				adaptadorListaCanciones.getIdsFromCanciones(usuario.getRecientes().getCanciones()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usuario get(int id) {
		// Si no está devolvemos null
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		if(eUsuario == null)
			return null;
		return entidadToUsuario(eUsuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Usuario> getAll() {
		List<Usuario> usuarios = new ArrayList<>();
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades(USUARIO); 
		if (eUsuarios != null)
			eUsuarios.stream()
					 .forEach(e -> usuarios.add(get(e.getId())));
		return usuarios;
		
	}

	
	// MÉTODOS AUXILIARES
	
	
	/**
	 * Convierte una entidad en un objeto de tipo Usuario.
	 * @param eUsuario entidad con los datos de una instancia de Usuario
	 * @return objeto ListaCanciones correspondiente
	 */
	private Usuario entidadToUsuario(Entidad eUsuario) {
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, APELLIDOS);
		String fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHANACIMIENTO);
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		String nombreUsuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, LOGIN);
		String contrasena = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));
		
		List<ListaCanciones> listas = getListasCancionesFromIds(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, LISTASCANCIONES));
		
		ListaCanciones cancionesRecientes = new ListaCanciones(Usuario.LISTA_RECIENTES, 
				adaptadorListaCanciones.getCancionesFromIds(
						servPersistencia.recuperarPropiedadEntidad(eUsuario, CANCIONESRECIENTES)));
		
		Usuario usuario = new Usuario(nombre, apellidos, fechaNacimiento, email, nombreUsuario, contrasena, 
									  premium, listas, cancionesRecientes);
		usuario.setId(eUsuario.getId());
		return usuario;
	}
	
	/**
	 * Convierte un usuario en un objeto de tipo Entidad equivalente.
	 * @param usuario objeto de tipo Usuario a transformar en Entidad
	 * @return entidad con los datos correspondientes al usuario
	 */
	private Entidad UsuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(APELLIDOS, usuario.getApellidos()),
				new Propiedad(FECHANACIMIENTO, usuario.getFechaNacimiento().format(Usuario.formatter)),
				new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(LOGIN, usuario.getLogin()),
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(LISTASCANCIONES, getIdsFromListasCanciones(usuario.getListas())),
				new Propiedad(CANCIONESRECIENTES, 
						adaptadorListaCanciones.getIdsFromCanciones(usuario.getRecientes().getCanciones())))));
		return eUsuario;
	}
	
	/**
	 * Guarda como cadena de caracteres los ids de las listas de canciones
	 * de un usuario.
	 * @param listasCanciones Lista de listas de canciones
	 * @return String con los ids separados por espacios
	 */
	private String getIdsFromListasCanciones(List<ListaCanciones> listasCanciones) {
		String ids = listasCanciones.stream()
									.map(lc -> String.valueOf(lc.getId()))
									.collect(Collectors.joining(" "));
		return ids;
	}
	
	/**
	 * Extrae los objetos ListaCanciones asociados a los ids guardados como String.
	 * @param ids String con los ids
	 * @return Lista de listas de canciones correspondientes
	 */
	private List<ListaCanciones> getListasCancionesFromIds(String ids) {
		if (ids.trim().isEmpty())
			return new ArrayList<ListaCanciones>();
		List<ListaCanciones> listasCanciones = Arrays.stream(ids.split(" "))
									                 .map(id -> adaptadorListaCanciones.get(Integer.valueOf(id)))
									                 .collect(Collectors.toList());
		return listasCanciones;
	}
}
