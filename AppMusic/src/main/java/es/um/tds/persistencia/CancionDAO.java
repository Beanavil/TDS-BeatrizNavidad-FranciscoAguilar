package es.um.tds.persistencia;

import java.util.List;

import es.um.tds.modelo.Cancion;

/**
 * Interfaz que implementarán los adaptadores de Cancion.
 * 
 * @author Beatriz y Francisco
 */
public interface CancionDAO {
	/**
	 * Crea una entidad con los datos de una canción y la guarda en la BD.
	 * @param cancion canción que queremos guardar en la BD.
	 */
	public void store(Cancion cancion);
	
	/**
	 * Elimina una canción de la BD.
	 * @param cancion canción a eliminar.
	 * @return si se ha eliminado o no.
	 */
	public boolean delete(Cancion cancion);
	
	/**
	 * Actualiza los datos de una canción (ruta del fichero que la contiene).
	 * @param cancion canción que se quiere modificar.
	 */
	public void update(Cancion cancion);
	
	/**
	 * Devuelve la canción con el id 'id'.
	 * @param id.
	 * @return canción buscada o null si no está en la BD.
	 */
	public Cancion get(int id);
	
	/**
	 * Devuelve todos las canciones de un determinado estilo.
	 * @return lista con las canciones.
	 */
	public List<Cancion> getAllStyle(String estilo);
	
	/**
	 * Devuelve todos las canciones de un determinado intérprete.
	 * @return lista con las canciones.
	 */
	public List<Cancion> getAllArtist(String artista);
	
	/**
	 * Devuelve todos las canciones de un determinado intérprete y estilo.
	 * @return lista con las canciones.
	 */
	public List<Cancion> getAllArtistStyle(String artista, String estilo);
	
	/**
	 * Devuelve todos las canciones registradas.
	 * @return lista con las canciones.
	 */
	public List<Cancion> getAll();
}
