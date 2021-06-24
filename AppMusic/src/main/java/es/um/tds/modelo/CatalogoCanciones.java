package es.um.tds.modelo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.um.tds.persistencia.CancionDAO;
import es.um.tds.persistencia.DAOException;
import es.um.tds.persistencia.FactoriaDAO;
import es.um.tds.utils.StringUtils;

/**
 * Catálogo (repositorio) de canciones de la app
 * 
 * @author Beatriz y Francisco
 */
public class CatalogoCanciones {
	private static CatalogoCanciones unicaInstancia;
	private Map<Integer, Cancion> canciones;
	private FactoriaDAO factoria;
	private CancionDAO adaptadorCancion;
	
	private CatalogoCanciones() throws InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, 
	ClassNotFoundException, DAOException {
		
		canciones = new HashMap<>();
		factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		adaptadorCancion = factoria.getCancionDAO();
		this.cargarCatalogo();
	}
	
	/**
	 * Método para obtener la única instancia del catálogo
	 * @return instancia
	 * @throws DAOException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static CatalogoCanciones getUnicaInstancia() throws InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, 
	ClassNotFoundException, DAOException {
		
		if(unicaInstancia == null)
			unicaInstancia = new CatalogoCanciones();
		return unicaInstancia;
	}
	
	
	/**
	 * Método para añadir una canción al catálogo
	 * @param cancion Canción a añadir
	 */
	public void addCancion(Cancion cancion) {
		canciones.put(cancion.getId(), cancion);
	}
	
	
	/**
	 * Método para eliminar una canción del catálogo
	 * @param cancion Canción a eliminar
	 */
	public void removeCancion(Cancion cancion) {
		canciones.remove(cancion.getId());
	}
	
	
	/**
	 * Método para recuperar una canción del catálogo
	 * @param id Id de la canción que se quiere recuperar
	 * @return Canción o null si no está en el catálogo
	 */
	public Cancion getCancion(int id) {
		return canciones.get(id);
	}
	
	
	/**
	 * Método para obtener todas las canciones del catálogo
	 * @return Lista con las canciones del catálogo
	 */
	public List<Cancion> getAll() {
		List<Cancion> lista = new ArrayList<>();
		canciones.values().stream().forEach(c -> lista.add(c));
		return lista;
	}
	
	
	/**
	 * Método para obtener todas las canciones del catálogo de un
	 * artista determinado
	 * @param artista Artista del que queremos obtener las canciones
	 * @return Lista con las canciones del catálogo del artista
	 */
	public List<Cancion> getAllArtist(String artista) {
		List<Cancion> lista = new ArrayList<>();
		canciones.values().stream()
		.filter(c -> StringUtils.containsIgnoreCase(c.getInterprete(), artista)) 
		.forEach(c -> lista.add(c));
		return lista;
	}
	
	
	/**
	 * Método para obtener todas las canciones del catálogo de un
	 * estilo determinado
	 * @param estilo Estilo del que queremos obtener las canciones
	 * @return Lista con las canciones del catálogo de ese estilo
	 */
	public List<Cancion> getAllStyle(String estilo) {
		List<Cancion> lista = new ArrayList<>();
		canciones.values().stream()
		.filter(c -> StringUtils.containsIgnoreCase(c.getInterprete(), estilo)) 
		.forEach(c -> lista.add(c));
		return lista;
	}
	
	
	/**
	 * Recupera todas las canciones de la bbdd para trabajar con ellas en memoria
	 * @throws DAOException
	 */
	private void cargarCatalogo() throws DAOException {
		List<Cancion> cancionesBD = adaptadorCancion.getAll();
		cancionesBD.stream().forEach(c -> canciones.put(c.getId(), c));
	}
}
