package es.um.tds.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.persistencia.CancionDAO;
import es.um.tds.persistencia.FactoriaDAO;
import es.um.tds.utils.StringUtils;

/**
 * Catálogo (repositorio) de canciones de la app.
 * 
 * @author Beatriz y Francisco
 */
public class CatalogoCanciones {
	private static CatalogoCanciones unicaInstancia;
	private Map<Integer, Cancion> canciones;
	private Map<String, Cancion> cancionesTitulo;
	private FactoriaDAO factoria;
	private CancionDAO adaptadorCancion;
	
	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException
	 */
	private CatalogoCanciones() throws BDException, DAOException {
		canciones = new HashMap<>();
		cancionesTitulo = new HashMap<>();
		factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		adaptadorCancion = factoria.getCancionDAO();
		this.cargarCatalogo();
	}
	
	/**
	 * Devuelve la única instancia del catálogo.
	 * @return Instancia del catálogo
	 * @throws BDException
	 * @throws DAOException 
	 */
	public static CatalogoCanciones getUnicaInstancia() throws BDException, DAOException {	
		if(unicaInstancia == null)
			unicaInstancia = new CatalogoCanciones();
		return unicaInstancia;
	}
	
	/**
	 * Añade una canción al catálogo.
	 * @param cancion Canción a añadir
	 */
	public void addCancion(Cancion cancion) {
		canciones.put(cancion.getId(), cancion);
		cancionesTitulo.put(cancion.getTitulo(), cancion);
	}
	
	/**
	 * Elimina una canción del catálogo.
	 * @param cancion Canción a eliminar
	 */
	public void removeCancion(Cancion cancion) {
		canciones.remove(cancion.getId());
		cancionesTitulo.remove(cancion.getTitulo());
	}
	
	/**
	 * Indica si una canción está en el catálogo
	 * @param cancion Canción que se quiere saber si está
	 * @return True si la canción está o false si no está en el catálogo
	 */
	public boolean isCancion(Cancion cancion) {
		return (getCancion(cancion.getTitulo()) != null);
	}
	
	/**
	 * Recupera una canción del catálogo.
	 * @param id Id de la canción que se quiere recuperar
	 * @return Canción o null si no está en el catálogo
	 */
	public Cancion getCancion(int id) {
		return canciones.get(id);
	}
	
	/**
	 * Recupera una canción del catálogo por su título.
	 * @param titulo Título de la canción que se quiere recuperar
	 * @return Canción o null si no está en el catálogo
	 */
	public Cancion getCancion(String titulo) {
		return cancionesTitulo.get(titulo);
	}
	
	/**
	 * Devuelve todas las canciones del catálogo.
	 * @return Lista con las canciones del catálogo
	 */
	public List<Cancion> getAll() {
		List<Cancion> lista = new ArrayList<>();
		cancionesTitulo.values()
					   .stream().forEach(c -> lista.add(c));
		return lista;
	}
	
	/**
	 * Devuelve todas las canciones del catálogo de un artista determinado.
	 * @param interprete Intérprete del que queremos obtener las canciones
	 * @return Lista con las canciones del catálogo del artista
	 */
	public List<Cancion> getAllArtist(String interprete) {
		List<Cancion> lista = new ArrayList<>();
		cancionesTitulo.values()
					   .stream()
					   .filter(c -> StringUtils.containsIgnoreCase(c.getInterprete(), interprete)) 
					   .forEach(c -> lista.add(c));
		return lista;
	}
	
	/**
	 * Devuelve todas las canciones del catálogo de un estilo determinado.
	 * @param estilo Estilo del que queremos obtener las canciones
	 * @return Lista con las canciones del catálogo de ese estilo
	 */
	public List<Cancion> getAllStyle(String estilo) {
		List<Cancion> lista = new ArrayList<>();
		cancionesTitulo.values()
					   .stream()
					   .filter(c -> StringUtils.containsIgnoreCase(c.getEstilo().getNombre(), estilo)) 
					   .forEach(c -> lista.add(c));
		return lista;
	}
	
	/**
	 * Método para obtener todas las canciones del catálogo de un estilo e intérpretes determinados.
	 * @param interprete Intérprete del que queremos obtener las canciones
	 * @param estilo Estilo del que queremos obtener las canciones
	 * @return Lista con las canciones del catálogo de ese estilo e intérprete
	 */
	public List<Cancion> getAllArtistStyle(String interprete, String estilo) {
		List<Cancion> lista = new ArrayList<>();
		cancionesTitulo.values()
					   .stream()
					   .filter(c -> StringUtils.containsIgnoreCase(c.getInterprete(), interprete)) 
					   .filter(c -> StringUtils.containsIgnoreCase(c.getEstilo().getNombre(), estilo)) 
					   .forEach(c -> lista.add(c));
		return lista;
	}
	
	/**
	 * Recupera todas las canciones de la bbdd para trabajar con ellas en memoria.
	 * @throws DAOException
	 */
	private void cargarCatalogo() throws DAOException {
		List<Cancion> cancionesBD = adaptadorCancion.getAll();
		cancionesBD.stream()
				   .forEach(c -> {
									canciones.put(c.getId(), c);
									cancionesTitulo.put(c.getTitulo(), c);
								 });
	}
}
