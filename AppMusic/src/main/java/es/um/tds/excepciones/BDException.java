package es.um.tds.excepciones;

public class BDException extends Exception {
	private static final long serialVersionUID = 1L;

	public BDException (String mensaje) {
        super(mensaje);
    }
}
