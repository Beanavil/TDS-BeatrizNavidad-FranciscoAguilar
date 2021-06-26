package es.um.tds.persistencia;


import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
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
	 * @throws BDException 
	 */

	public static FactoriaDAO getInstancia(String tipo) throws BDException {
		try {
			if (unicaInstancia == null) {
				unicaInstancia = (FactoriaDAO)Class.forName(tipo).getDeclaredConstructor().newInstance();
			} 
			return unicaInstancia;
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}
	}
	
	/**
	 * Crea un tipo de factoria DAO (TDSFactoriaDAO) o devuelve el que ya hay creado.
	 * @return Única instancia de la factoría.
	 * @throws DAOException.
	 * @throws BDException 
	 */
	public static FactoriaDAO getInstancia() throws DAOException, BDException {
		return getInstancia(FactoriaDAO.DAO_TDS);
	}

	
	
	// Metodos factoria para obtener adaptadores
	
	/**
	 * Método factoría para obtener el adaptador de Cancion.
	 * @return adaptador TDSCancionDAO.
	 * @throws DAOException 
	 * @throws BDException
	 */
	public abstract CancionDAO getCancionDAO() throws BDException, DAOException;
	
	/**
	 * Método factoría para obtener el adaptador de ListaCanciones.
	 * @return adaptador TDSListaCancionesDAO.
	 * @throws DAOException 
	 * @throws BDException
	 */
	public abstract ListaCancionesDAO getListaCancionesDAO() throws BDException, DAOException;
	
	/**
	 * Método factoría para obtener el adaptador de Usuario.
	 * @return adaptador TDSUsuarioDAO.
	 * @throws DAOException 
	 * @throws BDException
	 */
	public abstract UsuarioDAO getUsuarioDAO() throws BDException, DAOException;	
}
