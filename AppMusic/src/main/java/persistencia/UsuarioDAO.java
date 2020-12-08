package persistencia;

import java.util.List;
import modelo.Usuario;

public interface UsuarioDAO {
	/**
	 * Crea un usuario
	 * @param u
	 */
	void create(Usuario u);
	
	/**
	 * Elimina un usuario
	 * @param u
	 * @return si se ha eliminado o no
	 */
	boolean delete(Usuario u);
	
	/**
	 * Modifica los datos de un usuario
	 * @param u
	 */
	void updatePerfil(Usuario u);
	
	/**
	 * Obtiene el usuario con el id 'id'
	 * @param id
	 * @return usuario buscado
	 */
	Usuario get(int id);
	
	/**
	 * Obtiene todos los usuarios registrados
	 * @return lista con los usuarios
	 */
	List<Usuario> getAll();
}
