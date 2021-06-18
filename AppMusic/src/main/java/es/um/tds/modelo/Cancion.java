package es.um.tds.modelo;

/**
 * Representa una canción.
 * 
 * @author Beatriz y Francisco
 */
public class Cancion {
	private String titulo;
	private String estilo;
	private String interprete;
	private String rutaFichero;
	private int numReproducciones;
	private int id;
	
	/**
	 * Constructor
	 * @param titulo título de la canción
	 * @param estilo estilo musical
	 * @param interprete cantante que interpreta la canción
	 * @param rutaFichero ruta al fichero correspondiente a la canción
	 * @param numReproducciones número de veces que se ha reproducido en la aplicación
	 */
	public Cancion (String titulo, String interprete, String estilo, 
				String rutaFichero, int numReproducciones) {
		this.titulo = titulo;
		this.interprete = interprete;
		this.estilo = estilo;
		this.rutaFichero = rutaFichero;
		this.numReproducciones = numReproducciones;
	}
	
	/**
	 * Sobrecarga del constructor para cuando no se conoce el número de reproducciones
	 * @param titulo titulo de la canción
	 * @param estilo estilo musical
	 * @param interprete cantante que interpreta la canción
	 * @param rutaFichero ruta al fichero correspondiente a la canción
	 */
	public Cancion (String titulo, String interprete, String estilo, 
			String rutaFichero) {
		this(titulo, interprete, estilo, rutaFichero, 0);
	}
	
	/**
	 * Sobrecarga del constructor para cuando solo se conoce el título y el fichero de la canción
	 * @param titulo titulo de la canción
	 * @param rutaFichero ruta al fichero correspondiente a la canción
	 */
	public Cancion (String titulo, String rutaFichero) {
		this(titulo, "", "", rutaFichero);
	}
	
	// Getters
	public String getTitulo() {
		return titulo;
	}
	
	public String getInterprete() {
		return interprete;
	}
	
	public String getEstilo() {
		return estilo;
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
	
	
	// Setters
	// TODO ver si algunos tienen que ser privados
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setInterprete(String interprete) {
		this.interprete = interprete;
	}
	
	public void setEstilo(String estilo) {
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
	public String toString() {
		return "Cancion [título=" + titulo + ", intérprete= " + interprete + ", estilo= " + estilo + 
			   ", número de reproducciones= " + numReproducciones + ", id= " + id + "]";
	}
}
