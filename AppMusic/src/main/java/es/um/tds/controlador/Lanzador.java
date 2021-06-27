package es.um.tds.controlador;

import java.io.IOException;

import es.um.tds.vista.VentanaLogin;

/**
 * Lanzador de la aplicación.
 * 
 * @author Beatriz y Francisco
 */
public class Lanzador {
	/**
	 * Método principal
	 * @param args
	 */
	public static void main( String[] args ) throws IOException
	{
		try {
			new VentanaLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
