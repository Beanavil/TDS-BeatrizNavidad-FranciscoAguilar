package es.um.tds.modelo.descuentos;

/**
 * Descuento que mantiene el precio normal de la tarifa de la app para los 
 * usuarios comunes.
 * 
 * @author Beatriz y Francisco
 */
public class DescuentoNulo implements IDescuento {

	@Override
	public int getDescuento() {
		return 0;
	}
}
