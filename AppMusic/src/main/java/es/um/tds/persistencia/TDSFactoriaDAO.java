package es.um.tds.persistencia;

import java.lang.reflect.InvocationTargetException;

import es.um.tds.persistencia.TDSUsuarioDAO;

/**
 * Tipo de factoría que hereda de FactoriaDAO.
 * 
 * @author Beatriz y Francisco
 */
public final class TDSFactoriaDAO extends FactoriaDAO{
	
	public TDSFactoriaDAO() {	}
	
	/**
	 * {@inheritDoc}
	 * @throws DAOException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Override
	public TDSCancionDAO getCancionDAO() throws InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, 
	ClassNotFoundException, DAOException {	
		return TDSCancionDAO.getUnicaInstancia(); 
	}
	
	/**
	 * {@inheritDoc}
	 * @throws DAOException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Override
	public TDSListaCancionesDAO getListaCancionesDAO() throws InstantiationException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 
	SecurityException, ClassNotFoundException, DAOException {	
		
		return TDSListaCancionesDAO.getUnicaInstancia(); 
	}
	
	/**
	 * {@inheritDoc}
	 * @throws DAOException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Override
	public TDSUsuarioDAO getUsuarioDAO() throws InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, 
	ClassNotFoundException, DAOException {	
		
		return TDSUsuarioDAO.getUnicaInstancia(); 
	}
}
