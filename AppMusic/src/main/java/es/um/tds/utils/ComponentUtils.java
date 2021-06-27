package es.um.tds.utils;

import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * Funciones para aplicar a objetos de tipo Component.
 * 
 * @author Beatriz y Francisco
 */
public class ComponentUtils {

	/**
	 * Fija el tamaño de un componente
	 */
	public static void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
	}

}
