package es.um.tds.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.um.tds.persistencia.UsuarioDAO;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.persistencia.FactoriaDAO;

/**
 * Catálogo (repositorio) de usuarios de la app.
 * 
 * @author Beatriz y Francisco
 */
public class CatalogoUsuarios {
	private Map<Integer, Usuario> usuariosID;
	private Map<String, Usuario> usuariosNombreUsuario;
	private static CatalogoUsuarios unicaInstancia;
	private FactoriaDAO factoria;
	private UsuarioDAO adaptadorUsuario;
	
	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException
	 */
	private CatalogoUsuarios() throws BDException, DAOException {		
		usuariosID = new HashMap<Integer, Usuario>();
		usuariosNombreUsuario = new HashMap<String, Usuario>();
		factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		adaptadorUsuario = factoria.getUsuarioDAO();
		this.cargarCatalogo();
	}
	
	/**
	 * Devuelve la única instancia del catálogo.
	 * @return Instancia del catálogo
	 * @throws BDException
	 * @throws DAOException 
	 */
	public static CatalogoUsuarios getUnicaInstancia() throws BDException, DAOException {	
		if (unicaInstancia == null) 
			unicaInstancia = new CatalogoUsuarios();
		return unicaInstancia;
	}
	
	/**
	 * Añade un usuario al catálogo.
	 * @param usuario Usuario a añadir
	 */
	public void addUsuario(Usuario usuario) {
		usuariosID.put(usuario.getId(), usuario);
		usuariosNombreUsuario.put(usuario.getLogin(), usuario);
	}
	
	/**
	 * Elimina un usuario del catálogo.
	 * @param usuario Usuario a eliminar
	 */
	public void removeUsuario(Usuario usuario) {
		usuariosID.remove(usuario.getId());
		usuariosNombreUsuario.remove(usuario.getLogin());
	}
	
	/**
	 * Recupera un usuario del catálogo por su id.
	 * @param id Id del usuario que se quiere recuperar
	 * @return Usuario o null si no está en el catálogo
	 */
	public Usuario getUsuario(int id) {
		return usuariosID.get(id);
	}
	
	/**
	 * Recupera un usuario del catálogo por su nombre de usuario.
	 * @param login Nombre de usuario del usuario que se quiere recuperar
	 * @return Usuario o null si no está en el catálogo
	 */
	public Usuario getUsuario(String login) {
		return usuariosNombreUsuario.get(login);
	}
	
	/**
	 * Indica si un usuario está en el catálogo.
	 * @param usuario Usuario del que se quiere saber esta info
	 * @return True si está o false si no está en el catálogo
	 */
	public boolean isUsuario(Usuario usuario) {
		return usuariosNombreUsuario.containsValue(usuario);
	}
	
	/**
	 * Devuelve todos los usuarios del catálogo.
	 * @return Lista con los usuarios del catálogo
	 */
	public List<Usuario> getAll() {
		List<Usuario> lista = new ArrayList<>();
		usuariosID.values().stream().forEach(u -> lista.add(u));
		return lista;
	}
	
	/**
	 * Recupera todos los usuarios de la bd para trabajar con ellos en memoria.
	 * @throws DAOException
	 */
	private void cargarCatalogo() throws DAOException {
		List<Usuario> usuariosBD = adaptadorUsuario.getAll();
		usuariosBD.stream()
				  .forEach(u -> {  
								   usuariosID.put(u.getId(), u);
								   usuariosNombreUsuario.put(u.getLogin(), u);
								});
	}
}
