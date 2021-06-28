package es.um.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import beans.Entidad;
import beans.Propiedad;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.Estilo;
import es.um.tds.utils.StringUtils;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

/**
 * Adaptador del DAO para la clase Cancion.
 * 
 * @author Beatriz y Francisco
 */
public class TDSCancionDAO implements CancionDAO {

	private static final String CANCION = "Cancion";
	private static final String TITULO = "titulo";
	private static final String INTERPRETE = "interprete";
	private static final String ESTILO = "estilo";
	private static final String RUTAFICHERO = "rutaFichero";
	private static final String REPRODUCCIONES = "numReproducciones";
	private ServicioPersistencia servPersistencia;
	private static TDSCancionDAO unicaInstancia;

	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException 
	 */
	private TDSCancionDAO () throws BDException, DAOException {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	/**
	 * Crea una instancia del adaptador o devuelve la que ya haya creada.
	 * @return única instancia del adaptador de Cancion
	 * @throws BDException
	 * @throws DAOException 
	 */
	public static TDSCancionDAO getUnicaInstancia() throws BDException, DAOException {
		
		if (unicaInstancia == null) 
			unicaInstancia = new TDSCancionDAO(); 
		return unicaInstancia;       
	} 
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(Cancion cancion) {
		// Comprobamos primero que no esté ya registrada
		if (cancion.getId() >= 0)
			return;
		Entidad eCancion = CancionToEntidad(cancion);
		eCancion = servPersistencia.registrarEntidad(eCancion);
		cancion.setId(eCancion.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(Cancion cancion) {
		// Si no está no podemos borrarla
		if (cancion.getId() < 0)
			return false;
		Entidad eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		return servPersistencia.borrarEntidad(eCancion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Cancion cancion) {
		// Si no está no podemos actualizar sus datos
		if (cancion.getId() < 0)
			return;
		Entidad eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		
		servPersistencia.eliminarPropiedadEntidad(eCancion, TITULO);
		servPersistencia.anadirPropiedadEntidad(eCancion, TITULO, cancion.getTitulo());
		
		servPersistencia.eliminarPropiedadEntidad(eCancion, ESTILO);
		servPersistencia.anadirPropiedadEntidad(eCancion, ESTILO, cancion.getEstilo().getNombre());
		
		servPersistencia.eliminarPropiedadEntidad(eCancion, INTERPRETE);
		servPersistencia.anadirPropiedadEntidad(eCancion, INTERPRETE, cancion.getInterprete());
		
		servPersistencia.eliminarPropiedadEntidad(eCancion, RUTAFICHERO);
		servPersistencia.anadirPropiedadEntidad(eCancion, RUTAFICHERO, cancion.getRutaFichero());
		
		servPersistencia.eliminarPropiedadEntidad(eCancion, REPRODUCCIONES);
		servPersistencia.anadirPropiedadEntidad(eCancion, REPRODUCCIONES, String.valueOf(cancion.getNumReproducciones()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cancion get(int id) {
		// Si no está devolvemos null
		Entidad eCancion = servPersistencia.recuperarEntidad(id);
		if (eCancion == null)
			return null;
		return entidadToCancion(eCancion);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cancion> getAll() {
		List<Cancion> canciones = new ArrayList<Cancion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION);
		if (entidades != null)
			entidades.stream()
					 .forEach(e -> canciones.add(entidadToCancion(e)));
		return canciones;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cancion> getAllStyle(String estilo) {
		List<Cancion> canciones = new ArrayList<Cancion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION); 
		if (entidades != null) {
			entidades.stream()
					 // estilo puede ser una subcadena de algún otro estilo en la bd
					 .filter(e -> StringUtils.containsIgnoreCase(e.getPropiedades().get(2).getValor(), estilo)) 
					 .forEach(e -> canciones.add(entidadToCancion(e)));
		}
		return canciones;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cancion> getAllArtist(String artista) {
		List<Cancion> canciones = new ArrayList<Cancion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION); 
		if (entidades != null) {
			entidades.stream()
					 .filter(e -> StringUtils.containsIgnoreCase(e.getPropiedades().get(1).getValor(), artista)) 
					 .forEach(e -> canciones.add(entidadToCancion(e)));
		}
		return canciones;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cancion> getAllArtistStyle(String artista, String estilo) {
		List<Cancion> canciones = new ArrayList<Cancion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION); 
		if (entidades != null) {
			entidades.stream()
					 .filter(e -> StringUtils.containsIgnoreCase(e.getPropiedades().get(1).getValor(), artista)) 
					 .filter(e -> StringUtils.containsIgnoreCase(e.getPropiedades().get(2).getValor(), estilo)) 
					 .forEach(e -> canciones.add(entidadToCancion(e)));
		}
		return canciones;
	}
	
	
	// MÉTODOS AUXILIARES

	
	/**
	 * Convierte una entidad en un objeto de tipo Cancion.
	 * @param eCancion entidad con los datos de una instancia de Cancion
	 * @return objeto Cancion correspondiente
	 */
	private Cancion entidadToCancion(Entidad eCancion) throws NumberFormatException {
		String titulo = servPersistencia.recuperarPropiedadEntidad(eCancion, TITULO);
		String interprete = servPersistencia.recuperarPropiedadEntidad(eCancion, INTERPRETE);
		String estilo = servPersistencia.recuperarPropiedadEntidad(eCancion, ESTILO);
		String rutaFichero = servPersistencia.recuperarPropiedadEntidad(eCancion, RUTAFICHERO);
		int numReproducciones = Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eCancion, REPRODUCCIONES));
		Cancion cancion = new Cancion(titulo, interprete, Estilo.valor(estilo), rutaFichero, numReproducciones);
		cancion.setId(eCancion.getId());
		return cancion;
	}
	
	/**
	 * Convierte una canción en un objeto de tipo Entidad equivalente.
	 * @param cancion objeto de tipo Cancion a transformar en Entidad
	 * @return entidad con los datos correspondientes a la canción
	 */
	private Entidad CancionToEntidad(Cancion cancion) {
		Entidad eCancion = new Entidad();
		eCancion.setNombre(CANCION);
		eCancion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TITULO, cancion.getTitulo()),
				new Propiedad(INTERPRETE, cancion.getInterprete()),
				new Propiedad(ESTILO, cancion.getEstilo().getNombre()), 
				new Propiedad(RUTAFICHERO, cancion.getRutaFichero()), 
				new Propiedad(REPRODUCCIONES, String.valueOf(cancion.getNumReproducciones())))));
		return eCancion;
	}
}
