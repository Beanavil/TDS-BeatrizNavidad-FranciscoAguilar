package es.um.tds.excepciones;

/**
 * Excepción
 * 
 * @author Beatriz y Francisco
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = 1L; // default serialVersionUID
	public DAOException(final String mensaje) {
		super(mensaje);
	}
}