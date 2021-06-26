package es.um.tds.modelo.descuentos;

/**
 * Interfaz que implementarán los distintos descuentos de la app
 * 
 * @author Beatriz y Francisco
 */
public interface IDescuento {
	/**
	 * Calcula el descuento a aplicar en la tarifa de la app
	 * @return El porcentaje que se descuenta (de 0 a 100)
	 */
	public int getDescuento();
	
}
