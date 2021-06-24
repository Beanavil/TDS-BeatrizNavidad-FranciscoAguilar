package es.um.tds.persistencia;

import java.lang.reflect.InvocationTargetException;

import es.um.tds.persistencia.DAOException;
import es.um.tds.persistencia.FactoriaDAO;

/**
 * Factoría abstracta DAO.
 * 
 * @author Beatriz y Francisco
 */
public abstract class FactoriaDAO {
	public static final String DAO_TDS = "es.um.tds.persistencia.TDSFactoriaDAO"; // TODO cambiar por TDSFactoriaDAO.class.getName()?
	private static FactoriaDAO unicaInstancia = null;
	
	/**
	 * Constructor
	 */
	protected FactoriaDAO (){}

	/** 
	 * Crea una instancia de un tipo de factoría (solo existe TDSFactoriaDAO) o devuelve la que ya hay.
	 * @param tipo nombre de la clase del tipo de factoría a crear.
	 * @return única instancia de la clase FactoriaDAO.
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws DAOException.
	 */

	public static FactoriaDAO getInstancia(String tipo) throws DAOException, 
	InstantiationException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		if (unicaInstancia == null) {
			unicaInstancia = (FactoriaDAO)Class.forName(tipo).getDeclaredConstructor().newInstance();
		} 
		return unicaInstancia;
	}
	
	/**
	 * Crea un tipo de factoria DAO (TDSFactoriaDAO) o devuelve el que ya hay creado.
	 * @return Única instancia de la factoría.
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws DAOException.
	 */
	public static FactoriaDAO getInstancia() throws DAOException, 
	InstantiationException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		
		return getInstancia(FactoriaDAO.DAO_TDS);
	}

	
	
	// Metodos factoria para obtener adaptadores
	
	/**
	 * Método factoría para obtener el adaptador de Cancion.
	 * @return adaptador TDSCancionDAO.
	 */
	public abstract CancionDAO getCancionDAO();
	
	/**
	 * Método factoría para obtener el adaptador de ListaCanciones.
	 * @return adaptador TDSListaCancionesDAO.
	 */
	public abstract ListaCancionesDAO getListaCancionesDAO();
	
	/**
	 * Método factoría para obtener el adaptador de Usuario.
	 * @return adaptador TDSUsuarioDAO.
	 */
	public abstract UsuarioDAO getUsuarioDAO();	
}
