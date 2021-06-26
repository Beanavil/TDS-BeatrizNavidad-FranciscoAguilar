package es.um.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

/**
 * Adaptador del DAO para la clase ListaCanciones.
 * 
 * @author Beatriz y Francisco
 */
public class TDSListaCancionesDAO implements ListaCancionesDAO {
	private static final String LISTACANCIONES = "ListaCanciones";
	private static final String NOMBRE = "nombre";
	private static final String CANCIONES = "canciones";
	private ServicioPersistencia servPersistencia;
	private static TDSListaCancionesDAO unicaInstancia;
	private TDSCancionDAO adaptadorCancion;

	/**
	 * Constructor.
	 * @throws DAOException 
	 * @throws BDException
	 */
	private TDSListaCancionesDAO () throws BDException, DAOException {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		adaptadorCancion = TDSCancionDAO.getUnicaInstancia();
	}

	/**
	 * Crea una instancia del adaptador o devuelve la que ya haya creada.
	 * @return única instancia del adaptador de ListaCanciones.
	 * @throws DAOException 
	 * @throws BDException
	 */
	public static TDSListaCancionesDAO getUnicaInstancia() throws BDException, DAOException {	
		if (unicaInstancia == null) 
			unicaInstancia = new TDSListaCancionesDAO(); 
		return unicaInstancia;       
	} 
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(ListaCanciones lista) {
		// Comprobamos primero que no esté ya registrada
		if (lista.getId() >= 0)
			return;
		Entidad eLista = ListaCancionesToEntidad(lista);
		eLista = servPersistencia.registrarEntidad(eLista);
		lista.setId(eLista.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(ListaCanciones lista) {
		if (lista.getId() < 0)
			return false;
		Entidad eLista = servPersistencia.recuperarEntidad(lista.getId()); 
		return servPersistencia.borrarEntidad(eLista);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(ListaCanciones lista) {
		if (lista.getId() < 0)
			return;
		Entidad eLista = servPersistencia.recuperarEntidad(lista.getId());
		servPersistencia.eliminarPropiedadEntidad(eLista, NOMBRE);
		servPersistencia.anadirPropiedadEntidad(eLista, NOMBRE, lista.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eLista, CANCIONES);
		servPersistencia.anadirPropiedadEntidad(eLista, CANCIONES, getIdsFromCanciones(lista.getCanciones()));		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListaCanciones get(int id) {
		Entidad eLista = servPersistencia.recuperarEntidad(id);
		if (eLista == null)
			return null;
		return entidadToListaCanciones(eLista);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ListaCanciones> getAll() throws NullPointerException {
		List<ListaCanciones> listas = new ArrayList<>();
		List<Entidad> eListas = servPersistencia.recuperarEntidades(LISTACANCIONES); // puede ser null
		eListas.stream().forEach(e -> listas.add(get(e.getId())));
		return listas;
	}
	
	// Métodos auxiliares
	
	/**
	 * Método auxiliar que convierte una entidad en un objeto de tipo ListaCanciones.
	 * @param eLista entidad con los datos de una instancia de ListaCanciones.
	 * @return objeto ListaCanciones correspondiente.
	 */
	private ListaCanciones entidadToListaCanciones(Entidad eLista) {
		String nombre = servPersistencia.recuperarPropiedadEntidad(eLista, NOMBRE);
		List<Cancion> canciones = getCancionesFromIds(servPersistencia.recuperarPropiedadEntidad(eLista, CANCIONES));
		ListaCanciones lista = new ListaCanciones(nombre, canciones);
		lista.setId(eLista.getId());
		return lista;
	}
	
	
	/**
	 * Método auxiliar que convierte una lista de canciones en un objeto de tipo Entidad equivalente.
	 * @param lista objeto de tipo ListaCanciones a transformar en Entidad.
	 * @return entidad con los datos correspondientes a la lista.
	 */
	private Entidad ListaCancionesToEntidad(ListaCanciones lista) {
		Entidad eLista = new Entidad();
		eLista.setNombre(LISTACANCIONES);
		eLista.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE, lista.getNombre()),
				new Propiedad(CANCIONES, getIdsFromCanciones(lista.getCanciones())))));
		return eLista;
	}
	
	/**
	 * Método auxiliar para guardar como cadena de caracteres los ids de las canciones.
	 * de una lista de canciones.
	 * @param canciones lista de canciones.
	 * @return string con los ids.
	 */
	public String getIdsFromCanciones(List<Cancion> canciones) {
		String ids = canciones.stream()
				.map(c -> String.valueOf(c.getId()))
				.collect(Collectors.joining(" "));
		return ids;
	}
	
	/**
	 * Método auxiliar para extraer los objetos Cancion asociados a los ids guardados como String.
	 * @param ids String con los ids.
	 * @return lista de canciones.
	 */
	public List<Cancion> getCancionesFromIds(String ids) {
		if (ids.trim().isEmpty())
			return new ArrayList<Cancion>();
		List<Cancion> canciones = Arrays.stream(ids.split(" "))
                .map(id -> adaptadorCancion.get(Integer.valueOf(id)))
                .collect(Collectors.toList());
		return canciones;
	}
}
