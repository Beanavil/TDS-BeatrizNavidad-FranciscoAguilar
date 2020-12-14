package es.um.tds.persistencia;

/**
 * Excepción
 * 
 * @author Beatriz y Francisco
 */
public class DAOException extends Exception {
	// TODO ¿serialVersionUID hace falta?
	private static final long serialVersionUID = 1L; // default serialVersionUID
	public DAOException(final String mensaje) {
		super(mensaje);
	}
}
