package umu.tds.componente;

import java.util.EventObject;

public class CancionesEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	protected Canciones canciones;
	
	public CancionesEvent(Object fuente, Canciones canciones) {
		super(fuente);
		this.canciones = canciones;
	}

	public Canciones getCanciones() { return canciones; }
}
