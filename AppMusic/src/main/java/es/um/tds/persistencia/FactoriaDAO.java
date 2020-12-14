package es.um.tds.persistencia;

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
	 * @throws DAOException.
	 */
	public static FactoriaDAO getInstancia(String tipo) throws DAOException {
		if (unicaInstancia == null)
			try { 
				unicaInstancia = (FactoriaDAO)Class.forName(tipo).getDeclaredConstructor().newInstance();
			} catch (Exception e) {	
				throw new DAOException(e.getMessage());
		} 
		return unicaInstancia;
	}
	
	/**
	 * Crea un tipo de factoria DAO (TDSFactoriaDAO) o devuelve el que ya hay creado.
	 * @return Única instancia de la factoría.
	 * @throws DAOException.
	 */
	public static FactoriaDAO getInstancia() throws DAOException {
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
