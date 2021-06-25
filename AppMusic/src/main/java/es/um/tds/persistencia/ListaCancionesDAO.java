package es.um.tds.persistencia;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import es.um.tds.modelo.ListaCanciones;

public interface ListaCancionesDAO {
	/**
	 * Crea una entidad con los datos de una lista de canciones y la guarda en la BD.
	 * @param lista lista de canciones a registrar.
	 */
	public void store(ListaCanciones lista);
	
	/**
	 * Elimina una lista de canciones  de la BD.
	 * @param lista lista de canciones a eliminar.
	 * @return si se ha eliminado o no.
	 */
	public boolean delete(ListaCanciones lista);
	
	/**
	 * Modifica los datos de una lista de canciones (lista de canciones y nombre) de la BD.
	 * @param lista lista de canciones que se quiere modificar.
	 */
	public void update(ListaCanciones lista);
	
	/**
	 * Obtiene la lista de canciones con el id 'id'.
	 * @param id id de la lista que se quiere obtener.
	 * @return lista de canciones buscada o null si no está en la BD.
	 * @throws DAOException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NullPointerException 
	 */
	public ListaCanciones get(int id) throws NullPointerException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, DAOException;
	
	
	/**
	 * Obtiene todos las listas de canciones registradas.
	 * @return lista con las listas de canciones.
	 */
	public List<ListaCanciones> getAll();
}
