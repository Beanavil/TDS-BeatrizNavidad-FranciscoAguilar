package persistencia;

import java.util.List;

import modelo.Cancion;

public interface CancionDAO {
	/**
	 * Crea una canción en la BD
	 * @param c
	 */
	void create(Cancion c);
	
	/**
	 * Elimina una canción de la BD
	 * @param c
	 * @return si se ha eliminado o no
	 */
	boolean delete(Cancion c);
	
	/**
	 * Modifica los datos de una canción
	 * @param c
	 */
	void updateData(Cancion c);
	
	/**
	 * Obtiene la canción con el id 'id'
	 * @param id
	 * @return canción buscada
	 */
	Cancion get(int id);
	
	
	/**
	 * Obtiene todos las canciones de un determinado estilo
	 * @return lista con las canciones
	 */
	List<Cancion> getAllStyle(String estilo);
	
	/**
	 * Obtiene todos las canciones de un determinado intérprete
	 * @return lista con las canciones
	 */
	List<Cancion> getAllArtist(String artista);
	
	/**
	 * Obtiene todos las canciones registradas
	 * @return lista con las canciones
	 */
	List<Cancion> getAll();
}
