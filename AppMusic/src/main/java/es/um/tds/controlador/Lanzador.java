package es.um.tds.controlador;

import java.io.IOException;

import es.um.tds.vista.VentanaLogin;

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
		//AppMusic.getUnicaInstancia().cargarCanciones("./xml/canciones.xml");
		//AppMusic.getUnicaInstancia().getCanciones().stream().forEach(c -> System.out.println(c.toString()));
	}
}
