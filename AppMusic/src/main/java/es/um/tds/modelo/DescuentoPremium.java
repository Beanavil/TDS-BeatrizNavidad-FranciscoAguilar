package es.um.tds.modelo;

/**
 * Clase que representa un descuento aplicado en la tarifa premium (30%).
 * 
 * @author Beatriz y Francisco
 */
public class DescuentoPremium implements Descuento {
	/**
	 * Método para calcular el descuento a aplicar. 
	 * @param precio cantidad de la que calculamos el descuento
	 * @return descuento a aplicar
	 */
	public double calcDescuento(double precio) {
		return 0.3*precio;
	}
}
