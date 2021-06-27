package es.um.tds.AppMusic;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFileChooser;

import org.junit.Before;
import org.junit.Test;

import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.Usuario;

public class TestUsuario {
	
	private static final int PRIMERA = 0;
	private AppMusic controlador;
	private Usuario pruebo;
	private Cancion cancion;
	JFileChooser jfc;
	
	@Before
	public void inicializar() {
		LocalDate date = LocalDate.now();
		String fecha = date.format(Usuario.formatter);
		pruebo = new Usuario("Pruebo", "Pruebinez", fecha, "pruebamail", "pruebo", "1234");
		cancion = new Cancion("Cancion de prueba", "./");
	}
	
	@Test
	public void testAddCancionReciente() {
		boolean resultadoEsperado1 = true;

		pruebo.addCancionReciente(cancion);
			
		assertEquals("Resultado testAddCancionReciente1",resultadoEsperado1, pruebo.isReciente(cancion));
	}

	@Test
	public void testIsMasReproducida() throws BDException, DAOException {
		boolean resultadoEsperado1 = false;
		boolean resultadoEsperado2 = true;
		List<Cancion> lista;
		
		controlador = AppMusic.getUnicaInstancia();
		jfc = new JFileChooser(new File("./xml"));
		controlador.cargarCanciones("./xml/canciones.xml");
		
		controlador.setUsuarioActual(pruebo);
		cancion = controlador.getCanciones().get(PRIMERA);
		lista = controlador.getCancionesMasReproducidas();
		assertEquals("Resultado testIsMasReproducidass1",resultadoEsperado1, lista.contains(cancion));
		
		cancion.setNumReproducciones(100);
		controlador.actualizarNumReproducciones(cancion);
		lista = controlador.getCancionesMasReproducidas();
		assertEquals("Resultado testIsMasReproducidass2",resultadoEsperado2, lista.contains(cancion));
	}

}
