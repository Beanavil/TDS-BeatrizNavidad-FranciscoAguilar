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
	 * Constructor.
	 * @param nombre Nombre de la lista
	 * @param canciones Lista de canciones
	 */
	public ListaCanciones(String nombre, List<Cancion> canciones) {
		this.nombre = nombre;
		this.canciones = new ArrayList<>(canciones);
		this.numCanciones = canciones.size();
		this.id = -1;
	}
	
	/**
	 * Constructor para una lista que no tiene canciones aún.
	 * @param nombre Nombre de la lista
	 */
	public ListaCanciones(String nombre) {
		this(nombre, new ArrayList<>());
	}
	
	
	//GETTERS
	
	
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

	
	// SETTERS
	

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = new ArrayList<>(canciones);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	// FUNCIONALIDAD
	
	
	/**
	 * Indica si una canción está en la lista.
	 * @param cancion Canción en cuestión
	 * @return True si está, false si no
	 */
	public boolean isCancionEnLista(Cancion cancion) {
		return this.getCanciones()
				   .stream()
				   .anyMatch(c -> c.getTitulo().equals(cancion.getTitulo()));
	}
	
	/**
	 * Añade una canción a la lista.
	 * @param cancion Canción en cuestión
	 */
	public void addCancion(Cancion cancion) {
		this.canciones.add(cancion);
	}
	
	/**
	 * Elimina una canción de la lista.
	 * @param cancion Canción en cuestión
	 */
	public void removeCancion(Cancion cancion) {
		this.canciones.remove(cancion);
	}
	
	/**
	 * Elimina la primera canción de la lista, si se puede.
	 */
	public void removeFirst() {
		if(this.canciones.size() > 0)
			this.canciones.remove(0);
	}
}
