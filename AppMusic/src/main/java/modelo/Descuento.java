package modelo;

/**
 * Interfaz utilizada para calcular descuentos según el tipo 
 * de cuenta de usuario
 * 
 * @author Beatriz y Francisco
 */
public interface Descuento {
	/**
	 * Método para calcular el descuento a aplicar. 
	 * @param precio cantidad de la que calculamos el descuento
	 * @return descuento a aplicar
	 */
	public double calcDescuento(double precio);
}
