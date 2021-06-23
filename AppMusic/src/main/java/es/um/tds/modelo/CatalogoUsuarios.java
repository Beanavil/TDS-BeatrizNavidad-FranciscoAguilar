package es.um.tds.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.um.tds.persistencia.UsuarioDAO;
import es.um.tds.persistencia.DAOException;
import es.um.tds.persistencia.FactoriaDAO;

/**
 * Catálogo (repositorio) de usuarios de la app
 * 
 * @author Beatriz y Francisco
 */
public class CatalogoUsuarios {
	private Map<Integer, Usuario> usuariosID;
	private Map<String, Usuario> usuariosNombreUsuario; // TODO ¿Hace falta?
	private static CatalogoUsuarios unicaInstancia;
	private FactoriaDAO factoria;
	private UsuarioDAO adaptadorUsuario;
	
	private CatalogoUsuarios() {
		usuariosID = new HashMap<>();
		usuariosNombreUsuario = new HashMap<>();
		
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = factoria.getUsuarioDAO();
			this.cargarCatalogo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}
	
	/**
	 * Método para obtener la única instancia del catálogo
	 * @return instancia
	 */
	public static CatalogoUsuarios getUnicaInstancia() {
		if (unicaInstancia == null) 
			unicaInstancia = new CatalogoUsuarios();
		return unicaInstancia;
	}
	
	
	/**
	 * Método para añadir un usuario al catálogo
	 * @param usuario Usuario a añadir
	 */
	public void addUsuario(Usuario usuario) {
		usuariosID.put(usuario.getId(), usuario);
		usuariosNombreUsuario.put(usuario.getLogin(), usuario);
	}
	
	
	/**
	 * Método para eliminar un usuario del catálogo
	 * @param usuario Usuario a eliminar
	 */
	public void removeUsuario(Usuario usuario) {
		usuariosID.remove(usuario.getId());
		usuariosNombreUsuario.remove(usuario.getLogin());
	}
	
	/**
	 * Método para recuperar un usuario del catálogo por su id
	 * @param id Id del usuario que se quiere recuperar
	 * @return Usuario o null si no está en el catálogo
	 */
	public Usuario getUsuario(int id) {
		return usuariosID.get(id);
	}
	
	
	/**
	 * Método para recuperar un usuario del catálogo por su nombre de usuario
	 * @param login Nombre de usuario del usuario que se quiere recuperar
	 * @return Usuario o null si no está en el catálogo
	 */
	public Usuario getUsuario(String login) {
		return usuariosNombreUsuario.get(login);
	}
	
	
	/**
	 * Método para obtener todos los usuarios del catálogo
	 * @return Lista con los usuarios del catálogo
	 */
	public List<Usuario> getAll() {
		ArrayList<Usuario> lista = new ArrayList<>();
		usuariosID.values().stream().forEach(u -> lista.add(u));
		return lista;
	}
	
	
	/**
	 * Recupera todos los usuarios de la bbdd para trabajar con ellos en memoria
	 * @throws DAOException
	 */
	private void cargarCatalogo() throws DAOException {
		List<Usuario> usuariosBD = adaptadorUsuario.getAll();
		usuariosBD.stream().forEach(u -> {  
											usuariosID.put(u.getId(), u);
											usuariosNombreUsuario.put(u.getLogin(), u);
										 });
	}
}
