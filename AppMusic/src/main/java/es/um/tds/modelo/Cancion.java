package es.um.tds.modelo;

import java.lang.Comparable;

/**
 * Representa una canción.
 * 
 * @author Beatriz y Francisco
 */
public class Cancion implements Comparable<Cancion> {
	private String titulo;
	private Estilo estilo;
	private String interprete;
	private String rutaFichero;
	private int numReproducciones;
	private int id;
	
	/**
	 * Constructor.
	 * @param titulo Título de la canción
	 * @param estilo Estilo musical
	 * @param interprete Cantante que interpreta la canción
	 * @param rutaFichero Ruta al fichero correspondiente a la canción
	 * @param numReproducciones Número de veces que se ha reproducido en la aplicación
	 */
	public Cancion (String titulo, String interprete, Estilo estilo, 
					String rutaFichero, int numReproducciones) {
		this.titulo = titulo;
		this.interprete = interprete;
		this.estilo = estilo;
		this.rutaFichero = rutaFichero;
		this.numReproducciones = numReproducciones;
		this.id = -1;
	}
	
	/**
	 * Sobrecarga del constructor para cuando no se conoce el número de reproducciones.
	 * @param titulo Título de la canción
	 * @param estilo Estilo musical
	 * @param interprete Cantante que interpreta la canción
	 * @param rutaFichero Ruta al fichero correspondiente a la canción
	 */
	public Cancion (String titulo, String interprete, Estilo estilo, 
			String rutaFichero) {
		this(titulo, interprete, estilo, rutaFichero, 0);
	}
	
	/**
	 * Sobrecarga del constructor para cuando solo se conoce el título y el fichero de la canción.
	 * @param titulo Título de la canción
	 * @param rutaFichero Ruta al fichero correspondiente a la canción
	 */
	public Cancion (String titulo, String rutaFichero) {
		this(titulo, "", Estilo.UNKNOWN, rutaFichero);
	}
	
	
	// GETTERS
	
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getInterprete() {
		return interprete;
	}
	
	public Estilo getEstilo() {
		return estilo;
	}
	
	public String getNombreEstilo() {
		return estilo.getNombre();
	}
	
	public String getRutaFichero() {
		return rutaFichero;
	}
	
	public int getNumReproducciones() {
		return numReproducciones;
	}
	
	public int getId() {
		return id;
	}
	
	
	// SETTERS
	

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setInterprete(String interprete) {
		this.interprete = interprete;
	}
	
	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}
	
	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}
	
	public void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Cancion c) {
        return this.getTitulo().compareTo(c.getTitulo());
    }
}
