package persistencia;

import persistencia.TDSUsuarioDAO;

public final class TDSFactoriaDAO extends FactoriaDAO{
	
	public TDSFactoriaDAO() {	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDSCancionDAO getCancionDAO() {	
		return new TDSCancionDAO(); 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDSListaCancionesDAO getListaCancionesDAO() {	
		return new TDSListaCancionesDAO(); 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TDSUsuarioDAO getUsuarioDAO() {	
		return new TDSUsuarioDAO(); 
	}
}
