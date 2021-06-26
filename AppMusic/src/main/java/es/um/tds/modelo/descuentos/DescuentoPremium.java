package es.um.tds.modelo.descuentos;

/**
 * Descuento para los usuarios premium
 * 
 * @author Beatriz y Francisco
 */
public class DescuentoPremium implements IDescuento{

	@Override
	public int getDescuento() {
		return 10;
	}

}
