package es.um.tds.modelo.descuentos;

/**
 * Descuento para los usuarios mayores de 65 años
 * 
 * @author Beatriz y Francisco
 */
public class Descuento65 implements IDescuento {

	@Override
	public int getDescuento() {
		return 25;
	}

}
