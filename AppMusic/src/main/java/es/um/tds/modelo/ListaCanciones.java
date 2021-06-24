package es.um.tds.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una lista de canciones.
 * 
 * @author Beatriz y Francisco
 */
public class ListaCanciones {
	private String nombre;
	private List<Cancion> canciones;
	private int numCanciones;
	private int id;

	/**
	 * Constructor
	 * @param nombre nombre de la lista
	 * @param canciones lista de canciones
	 */
	public ListaCanciones(String nombre, List<Cancion> canciones) {
		this.nombre = nombre;
		this.canciones = new ArrayList<>(canciones);
		this.numCanciones = canciones.size();
	}
	
	/**
	 * Constructor para una lista que no tiene canciones aún
	 * @param nombre nombre de la lista
	 */
	public ListaCanciones(String nombre) {
		this(nombre, new ArrayList<>());
	}
	
	// Getters
	public String getNombre() {
		return nombre;
	}
	
	public List<Cancion> getCanciones() {
		return new ArrayList<>(canciones);
	}
	
	public Cancion getCancion(int indice) {
		return this.canciones.get(indice);
	}
	
	public int getId() {
		return id;
	}
	
	public int getNumCanciones() {
		return numCanciones;
	}

	// Setters
	// TODO ver si algunos tienen que ser privados
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = new ArrayList<>(canciones);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNumCanciones(int numCanciones) {
		// TODO ¿esto hace falta?
		if(numCanciones == canciones.size())
			this.numCanciones = numCanciones;
	}
	
	
	public void addCancion(Cancion cancion) {
		this.canciones.add(cancion);
	}
	
	
	public void removeCancion(Cancion cancion) {
		this.canciones.remove(cancion);
	}
	
	
	public void removeFirst() {
		if(this.canciones.size() > 0)
			this.canciones.remove(0);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final String s = "ListaCanciones [nombre= " + nombre + ", lista de canciones=" ;
		this.canciones.stream().forEach(c -> s.concat("\n  "+c.toString()));
		return s.concat("]");
	}
}
