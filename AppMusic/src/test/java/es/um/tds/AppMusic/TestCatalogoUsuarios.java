package es.um.tds.AppMusic;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.CatalogoUsuarios;
import es.um.tds.modelo.Usuario;

public class TestCatalogoUsuarios {

	private CatalogoUsuarios catalogoUsuarios;
	private Usuario pruebo;
	
	@Before
	public void inicializar() throws BDException, DAOException {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		pruebo = new Usuario("Pruebo", "Pruebinez", "01/01/01", "pruebamail", "pruebo", "1234");
	}
	
	@Test
	public void testAddUsuario() {
		boolean resultadoEsperado1 = true;
		Usuario resultadoEsperado2 = pruebo;
		
		catalogoUsuarios.addUsuario(pruebo);
		List<Usuario> lista = catalogoUsuarios.getAll();
			
		assertEquals("Resultado testAddUsuario1",resultadoEsperado1, catalogoUsuarios.isUsuario(pruebo));
		assertEquals("Resultado testAddUsuario1",resultadoEsperado2, lista.get(0));
	}

	@Test
	public void testRemoveUsuario() {
		boolean resultadoEsperado1 = false;
		boolean resultadoEsperado2 = true;
		
		catalogoUsuarios.addUsuario(pruebo);
		catalogoUsuarios.removeUsuario(pruebo);
		List<Usuario> lista = catalogoUsuarios.getAll();
		
		assertEquals("Resultado testAddUsuario1",resultadoEsperado1, catalogoUsuarios.isUsuario(pruebo));
		assertEquals("Resultado testAddUsuario1",resultadoEsperado2, lista.isEmpty());
	}

	@Test
	public void testGetUsuarioString() {
		Usuario resultadoEsperado1 = pruebo;
		
		assertEquals("Resultado testAddUsuario1",resultadoEsperado1, catalogoUsuarios.getUsuario("Pruebo"));
	}
}
