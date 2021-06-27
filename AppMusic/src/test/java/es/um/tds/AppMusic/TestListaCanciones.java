package es.um.tds.AppMusic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;

public class TestListaCanciones {

	private ListaCanciones lista;
	private Cancion cancion;
	
	@Before
	public void inicializar() {
		lista = new ListaCanciones("Lista de prueba");
		cancion = new Cancion("Cancion de prueba", "./");
		lista.addCancion(cancion);
	}
	
	@Test
	public void testIsCancionEnLista() {
		boolean resultadoEsperado1 = true;
		
		assertEquals("Resultado testIsCancionEnLista1",resultadoEsperado1, lista.isCancionEnLista(cancion));
	}

	@Test
	public void testRemoveCancion() {
		boolean resultadoEsperado1 = false;
		
		lista.removeCancion(cancion);
		assertEquals("Resultado testRemoveCancion1",resultadoEsperado1, lista.isCancionEnLista(cancion));
	}

}
