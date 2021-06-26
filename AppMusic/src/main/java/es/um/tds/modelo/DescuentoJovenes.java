 package es.um.tds.modelo;

/**
 * Clase que representa un descuento aplicado en la tarifa jóvenes (10%).
 * 
 * @author Beatriz y Francisco
 */
public class DescuentoJovenes implements Descuento {
	/**
	 * Método para calcular el descuento a aplicar. 
	 * @param precio cantidad de la que calculamos el descuento
	 * @return descuento a aplicar
	 */
	public double calcDescuento(double precio) {
		return 0.1*precio;
	}
}
