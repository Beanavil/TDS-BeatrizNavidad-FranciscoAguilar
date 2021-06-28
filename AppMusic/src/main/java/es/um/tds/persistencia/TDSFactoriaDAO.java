package es.um.tds.persistencia;

import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.persistencia.TDSUsuarioDAO;

/**
 * Factoría particular de nuestro proyecto.
 * 
 * @author Beatriz y Francisco
 */
public final class TDSFactoriaDAO extends FactoriaDAO {
	
	public TDSFactoriaDAO() {}
	
	/**
	 * {@inheritDoc}
	 * @throws BDException
	 * @throws DAOException 
	 */
	@Override
	public TDSCancionDAO getCancionDAO() throws BDException, DAOException {	
		return TDSCancionDAO.getUnicaInstancia(); 
	}
	
	/**
	 * {@inheritDoc}
	 * @throws BDException
	 * @throws DAOException 
	 */
	@Override
	public TDSListaCancionesDAO getListaCancionesDAO() throws BDException, DAOException {	
		return TDSListaCancionesDAO.getUnicaInstancia(); 
	}
	
	/**
	 * {@inheritDoc}
	 * @throws BDException
	 * @throws DAOException 
	 */
	@Override
	public TDSUsuarioDAO getUsuarioDAO() throws BDException, DAOException {		
		return TDSUsuarioDAO.getUnicaInstancia(); 
	}
}
