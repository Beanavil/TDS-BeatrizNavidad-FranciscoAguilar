package es.um.tds.persistencia;

import java.util.List;

import es.um.tds.modelo.Usuario;

/**
 * Interfaz que implementarán los adaptadores de Usuario.
 * 
 * @author Beatriz y Francisco
 */
public interface UsuarioDAO {
	/**
	 * Crea una entidad con los datos de un usuario y la guarda en la BD.
	 * @param usuario usuario del que queremos obtener la entidad
	 */
	void store(Usuario usuario);
	
	/**
	 * Elimina un usuario de la BD.
	 * @param usuario usuario a eliminar
	 * @return si se ha eliminado o no
	 */
	boolean delete(Usuario usuario);
	
	/**
	 * Actualiza los datos de un usuario (email, nombreUsuario, contraseña y lista de listas de canciones). 
	 * @param usuario usuario cuyos datos queremos modificar
	 */
	void update(Usuario usuario);
	
	/**
	 * Devuelve el usuario con el id 'id'.
	 * @param id id del usuario que queremos obtener
	 * @return usuario buscado o null si no está en la BD
	 */
	Usuario get(int id);
	
	/**
	 * Devuelve todos los usuarios registrados.
	 * @return lista con los usuarios
	 */
	List<Usuario> getAll();
}
