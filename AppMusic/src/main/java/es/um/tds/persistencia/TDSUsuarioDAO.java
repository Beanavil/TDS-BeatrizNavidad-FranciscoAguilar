package es.um.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import beans.Entidad;
import beans.Propiedad;
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
	private static final String NOMBREUSUARIO = "nombreUsuario";
	private static final String CONTRASENA = "contrasena";
	private static final String PREMIUM = "premium";
	private static final String LISTASCANCIONES = "listasCanciones";
	private ServicioPersistencia servPersistencia;
	private static TDSUsuarioDAO unicaInstancia;

	/**
	 * Constructor.
	 */
	private TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	/**
	 * Crea una instancia del adaptador o devuelve la que ya haya creada.
	 * @return única instancia del adaptador de Usuario.
	 */
	public static TDSUsuarioDAO getUnicaInstancia() {
		if (unicaInstancia == null) 
			return new TDSUsuarioDAO(); 
		return unicaInstancia;       
	} 
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(Usuario usuario) {
		Entidad eUsuario;
		boolean registrada = true;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		} 
		catch (NullPointerException e) {
			registrada = false;
			System.err.println("Entity already registered. ");
		}
		if(registrada) return;
		TDSListaCancionesDAO adaptadorListaCanciones = TDSListaCancionesDAO.getUnicaInstancia();
		usuario.getListasCanciones().stream().forEach(lc -> adaptadorListaCanciones.store(lc));
		eUsuario = ListaCancionesToEntidad(usuario);
		servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		} 
		catch (NullPointerException e) {
			System.err.println("Entity not registered yet. " + e);
			return false;
		}
		TDSListaCancionesDAO adaptadorListaCanciones = TDSListaCancionesDAO.getUnicaInstancia();
		usuario.getListasCanciones().stream().forEach(lc -> adaptadorListaCanciones.delete(lc));
		return servPersistencia.borrarEntidad(eUsuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		} 
		catch (NullPointerException e) {
			System.err.println("Entity not registered yet. " + e);
			return;
		}
		servPersistencia.eliminarPropiedadEntidad(eUsuario, EMAIL);
		servPersistencia.anadirPropiedadEntidad(eUsuario, EMAIL, usuario.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, NOMBREUSUARIO);
		servPersistencia.anadirPropiedadEntidad(eUsuario, NOMBREUSUARIO, usuario.getNombreUsuario());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, CONTRASENA);
		servPersistencia.anadirPropiedadEntidad(eUsuario, CONTRASENA, usuario.getContrasena());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PREMIUM);
		servPersistencia.anadirPropiedadEntidad(eUsuario, PREMIUM, String.valueOf(usuario.isPremium()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, LISTASCANCIONES);
		servPersistencia.anadirPropiedadEntidad(eUsuario, LISTASCANCIONES, getIdsFromListasCanciones(usuario.getListasCanciones()));	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usuario get(int id) {
		Entidad eUsuario = new Entidad();
		try {
			eUsuario = servPersistencia.recuperarEntidad(id);
		} 
		catch (NullPointerException e) {
			System.err.println("Entity not registered yet. " + e);
			return null;
		}
		return entidadToListaCanciones(eUsuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Usuario> getAll() {
		List<Usuario> usuarios = new ArrayList<>();
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades(USUARIO);
		eUsuarios.stream().forEach(e -> usuarios.add(get(e.getId())));
		return usuarios;
	}

	// Métodos auxiliares
	// TODO ¿necesarios?
	
	/**
	 * Método auxiliar que convierte una entidad en un objeto de tipo Usuario.
	 * @param eUsuario entidad con los datos de una instancia de Usuario.
	 * @return objeto ListaCanciones correspondiente.
	 */
	private Usuario entidadToListaCanciones(Entidad eUsuario) {
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, APELLIDOS);
		String fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHANACIMIENTO);
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		String nombreUsuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBREUSUARIO);
		String contrasena = servPersistencia.recuperarPropiedadEntidad(eUsuario, CONTRASENA);
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));
		
		List<ListaCanciones> listas = getListasCancionesFromIds(servPersistencia.recuperarPropiedadEntidad(eUsuario, LISTASCANCIONES));
		Usuario usuario = new Usuario(nombre, apellidos, fechaNacimiento, email, nombreUsuario, contrasena, premium, listas);
		usuario.setId(eUsuario.getId());
		return usuario;
	}
	
	
	/**
	 * Método auxiliar que convierte un usuario en un objeto de tipo Entidad equivalente.
	 * @param usuario objeto de tipo Usuario a transformar en Entidad.
	 * @return entidad con los datos correspondientes al usuario.
	 */
	private Entidad ListaCancionesToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(APELLIDOS, usuario.getApellidos()),
				new Propiedad(FECHANACIMIENTO, usuario.getFechaNacimiento().format(Usuario.formatter)),
				new Propiedad(EMAIL, usuario.getNombre()),
				new Propiedad(NOMBREUSUARIO, usuario.getNombre()),
				new Propiedad(CONTRASENA, usuario.getNombre()),
				new Propiedad(PREMIUM, usuario.getNombre()),
				new Propiedad(LISTASCANCIONES, getIdsFromListasCanciones(usuario.getListasCanciones())))));
		return eUsuario;
	}
	
	/**
	 * Método auxiliar para guardar como cadena de caracteres los ids de las listas de canciones
	 * de un usuario.
	 * @param listasCanciones lista de listas de canciones.
	 * @return string con los ids.
	 */
	private String getIdsFromListasCanciones(List<ListaCanciones> listasCanciones) {
		String ids = "";
		listasCanciones.stream().forEach(lc -> ids.concat(String.valueOf(lc.getId()).concat(" ")));
		return ids;
	}
	
	/**
	 * Método auxiliar para extraer los objetos ListaCanciones asociados a los ids guardados como String.
	 * @param ids String con los ids.
	 * @return lista de listas de canciones.
	 */
	private List<ListaCanciones> getListasCancionesFromIds(String ids) {
		ArrayList<ListaCanciones> listasCanciones = new ArrayList<>();
		TDSListaCancionesDAO adaptadorListaCanciones = TDSListaCancionesDAO.getUnicaInstancia();
		StringTokenizer tok = new StringTokenizer(ids, " ");
		while(tok.hasMoreTokens()) {
			try {
				listasCanciones.add(adaptadorListaCanciones.get(Integer.valueOf((String)tok.nextElement())));
			} 
			catch (NumberFormatException e) { 
				System.err.println("Unable to get id. " + e);
			}
		}
		return listasCanciones;
	}
}
