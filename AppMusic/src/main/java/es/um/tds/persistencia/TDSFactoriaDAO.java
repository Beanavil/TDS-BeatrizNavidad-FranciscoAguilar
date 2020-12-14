package es.um.tds.persistencia;

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
	 */
	@Override
	public TDSCancionDAO getCancionDAO() {	
		return TDSCancionDAO.getUnicaInstancia(); 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDSListaCancionesDAO getListaCancionesDAO() {	
		return TDSListaCancionesDAO.getUnicaInstancia(); 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDSUsuarioDAO getUsuarioDAO() {	
		return TDSUsuarioDAO.getUnicaInstancia(); 
	}
}
