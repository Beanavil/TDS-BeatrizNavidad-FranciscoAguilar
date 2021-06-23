package es.um.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import beans.Entidad;
import beans.Propiedad;
import es.um.tds.controlador.AppMusic;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.Estilo;
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
	 * Constructor
	 */
	private TDSCancionDAO () {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	/**
	 * Crea una instancia del adaptador o devuelve la que ya haya creada.
	 * @return única instancia del adaptador de Cancion.
	 */
	public static TDSCancionDAO getUnicaInstancia() {
		if (unicaInstancia == null) 
			return new TDSCancionDAO(); 
		return unicaInstancia;       
	} 
	
	// TODO ver si hace falta tanto manejo de excepciones
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(Cancion cancion) {
		Entidad eCancion;
		boolean registrada = true;
		try {
			eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		} 
		catch (NullPointerException e) {
			registrada = false;
			System.err.println("Entity already registered. ");
		}
		if(registrada) return;
		eCancion = cancionToEntidad(cancion);
		eCancion = servPersistencia.registrarEntidad(eCancion);
		cancion.setId(eCancion.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	// TODO ¿mostrar en el mensaje de error la lista o el mensaje de excepción?
	@Override
	public boolean delete(Cancion cancion) {
		Entidad eCancion = new Entidad();
		try {
			eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		} 
		catch (NullPointerException e) {
			System.err.println("Entity not registered yet. " + e);
			return false;
		}
		return servPersistencia.borrarEntidad(eCancion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Cancion cancion) {
		Entidad eCancion = new Entidad();
		try {
			eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		} 
		catch (NullPointerException e) {
			System.err.println("Entity not registered yet. " + e);
			return;
		}
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
		Entidad eCancion = new Entidad();
		try {
			eCancion = servPersistencia.recuperarEntidad(id);
		} 
		catch (NullPointerException e) {
			System.err.println("Entity not registered yet. " + e);
			return null;
		}
		return entidadToCancion(eCancion);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cancion> getAll() {
		List<Cancion> canciones = new ArrayList<Cancion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION);
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
		entidades.stream()
		// estilo can be a substring of a song' style or the whole string
		.filter(e -> AppMusic.containsIgnoreCase(e.getPropiedades().get(2).getValor(), estilo)) 
		.forEach(e -> canciones.add(entidadToCancion(e)));
		return canciones;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cancion> getAllArtist(String artista) {
		List<Cancion> canciones = new ArrayList<Cancion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION);
		entidades.stream()
		.filter(e -> AppMusic.containsIgnoreCase(e.getPropiedades().get(1).getValor(), artista)) 
		.forEach(e -> canciones.add(entidadToCancion(e)));
		return canciones;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cancion> getAllArtistStyle(String artista, String estilo) {
		List<Cancion> canciones = new ArrayList<Cancion>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades(CANCION);
		entidades.stream()
		.filter(e -> AppMusic.containsIgnoreCase(e.getPropiedades().get(1).getValor(), artista)) 
		.filter(e -> AppMusic.containsIgnoreCase(e.getPropiedades().get(2).getValor(), estilo)) 
		.forEach(e -> canciones.add(entidadToCancion(e)));
		return canciones;
	}
	
	// Métodos auxiliares

	/**
	 * Método auxiliar que convierte una entidad en un objeto de tipo Cancion.
	 * @param eCancion entidad con los datos de una instancia de Cancion.
	 * @return objeto Cancion correspondiente.
	 */
	private Cancion entidadToCancion(Entidad eCancion) {
		String titulo = servPersistencia.recuperarPropiedadEntidad(eCancion, TITULO);
		String interprete = servPersistencia.recuperarPropiedadEntidad(eCancion, INTERPRETE);
		String estilo = servPersistencia.recuperarPropiedadEntidad(eCancion, ESTILO);
		String rutaFichero = servPersistencia.recuperarPropiedadEntidad(eCancion, RUTAFICHERO);
		int numReproducciones = 0;
		try {
			numReproducciones = Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eCancion, REPRODUCCIONES));
		} 
		catch (NumberFormatException e) {
			System.out.println("Unable to get value of 'numReproducciones'. " + e);
		} 
		Cancion cancion = new Cancion(titulo, interprete, Estilo.valor(estilo), rutaFichero, numReproducciones);
		cancion.setId(eCancion.getId());
		return cancion;
	}
	
	
	/**
	 * Método auxiliar que convierte una canción en un objeto de tipo Entidad equivalente.
	 * @param cancion objeto de tipo Cancion a transformar en Entidad.
	 * @return entidad con los datos correspondientes a la canción.
	 */
	private Entidad cancionToEntidad(Cancion cancion) {
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
