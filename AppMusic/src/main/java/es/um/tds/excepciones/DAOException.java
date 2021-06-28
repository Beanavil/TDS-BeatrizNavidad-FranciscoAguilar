package es.um.tds.excepciones;

/**
 * Excepción del DAO.
 * 
 * @author Beatriz y Francisco
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = 1L; 
	
	public DAOException(final String mensaje) {
		super(mensaje);
	}
}
