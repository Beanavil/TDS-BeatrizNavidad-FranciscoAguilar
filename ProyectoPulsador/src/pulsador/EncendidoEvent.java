package pulsador;

import java.util.EventObject;

public class EncendidoEvent extends EventObject{
	
	private static final long serialVersionUID = 1L;
	protected boolean anterior, nuevo;
	
	public EncendidoEvent(Object fuente, boolean anterior, boolean nuevo) {
		super(fuente);
		this.anterior = anterior;
		this.nuevo = nuevo;
	}

	public boolean isAnterior() { return anterior; }
	public boolean isNuevo() { return nuevo; }
}