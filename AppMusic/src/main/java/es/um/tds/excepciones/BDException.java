package es.um.tds.excepciones;

/**
 * Excepción para agrupar las excepciones lanzadas por la bd.
 * 
 * @author Beatriz y Francisco
 */
public class BDException extends Exception {
	private static final long serialVersionUID = 1L;

	public BDException (String mensaje) {
        super(mensaje);
    }
}
