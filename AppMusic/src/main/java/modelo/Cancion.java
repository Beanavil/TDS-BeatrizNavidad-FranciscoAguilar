package modelo;

public class Cancion {
	private String titulo;
	private String estilo;
	private String interprete;
	private String rutaFichero;
	private int numReproducciones;
	
	public Cancion (String titulo, String estilo, String interprete, 
				String rutaFichero) {
		this.titulo = titulo;
		this.estilo = estilo;
		this.interprete = interprete;
		this.rutaFichero = rutaFichero;
		this.numReproducciones = 0;
	}
	
	public Cancion (String titulo, String rutaFichero) {
		this(titulo, "", "", rutaFichero);
	}
	
	// Getters
	public String getTitulo() {
		return titulo;
	}
	public String getEstilo() {
		return estilo;
	}
	public String getInterprete() {
		return interprete;
	}
	public String getRutaFichero() {
		return rutaFichero;
	}
	public int getNumReproducciones() {
		return numReproducciones;
	}
	
	// Setters
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	public void setInterprete(String interprete) {
		this.interprete = interprete;
	}
	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}
	private void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}
	
}
