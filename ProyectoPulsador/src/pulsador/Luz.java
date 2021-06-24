package pulsador;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;
import java.util.Vector;

public class Luz extends Canvas implements Serializable {

	private static final long serialVersionUID = 1L;

	// Propiedades
	private boolean encendido = false; // propiedad ligada
	private String nombre; // identificador del pulsador

	private Color colorEncendido; // color de la luz
	private Color colorApagado; // color apagado

	private Color colorInferior = new Color(160, 160, 160);
	private Color colorSuperior = new Color(200, 200, 200);

	// Atributos
	private boolean bPulsado = false; // botón presionado o no

	private Vector<IEncendidoListener> oyentes;
	
	
	/**
	 * Constructor
	 */
	public Luz() {
		setSize(30, 30); // tamaño inicial por defecto del pulsador
		setMinimumSize(new Dimension(30, 30));
		repaint();
		// Añadir eventos de ratón
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				luzPressed(e);
			}

			public void mouseReleased(MouseEvent e) {
				luzReleased(e);
			}
		});
		
		oyentes = new Vector<>();
	}


 	// Getters y setters
 	
	public Color getColorEncendido() {
		return colorEncendido;
	}

	public void setColorEncendido(Color colorEncendido) {
		this.colorEncendido = colorEncendido;
		repaint();
	}

	public Color getColorApagado() {
		return colorApagado;
	}

	public void setColorApagado(Color colorApagado) {
		this.colorApagado = colorApagado;
		repaint();
	}

	public Color getColorInferior() {
		return colorInferior;
	}

	public void setColorInferior(Color colorInferior) {
		this.colorInferior = colorInferior;
		repaint();
	}

	public Color getColorSuperior() {
		return colorSuperior;
	}

	public void setColorSuperior(Color colorSuperior) {
		this.colorSuperior = colorSuperior;
		repaint();
	}

	public boolean isEncendido() {
		return encendido;
	}

	public void setEncendido(boolean nuevo) {
		boolean anterior = this.encendido;
		encendido = nuevo;
		
		// si cambia el estado del pulsador, notificar a oyentes
		if (anterior != nuevo) {
			EventObject o = new EncendidoEvent(this, anterior, nuevo);
			notificarOyentes(o);
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Dimension getPreferredSize() {
		return new Dimension(30, 30);
	}

	// deprecated
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	
	// Funcionalidad
	
	
	/**
	 * {@inheritDoc}
	 */
 	public void paint(Graphics g) {
		// Obtener el tamaño del pulsador
		int ancho = getSize().width;
		int alto = getSize().height;

		// Bloquear relación de aspecto
		if (ancho != alto) {
			if (ancho < alto) {
				alto = ancho;
			} else {
				ancho = alto;
			}
			setSize(ancho, alto);
			invalidate();
		}

		int grosor = 3; // grosor del botón
		int anchuraBoton = ancho - grosor;
		int bordeBoton = anchuraBoton / 5;
		int anchuraLuz = anchuraBoton - 2 * bordeBoton;

		int x = 0; // desplazamiento;
		if (!bPulsado) {
			x = 0;
		} else {
			x = grosor;
		}

		g.setColor(colorInferior);
		g.fillOval(grosor, grosor, anchuraBoton, anchuraBoton); // dibujar grosor
		g.setColor(Color.BLACK); // dibujar circulos negros
		g.drawOval(grosor, grosor, anchuraBoton - 1, anchuraBoton - 1);
		g.setColor(colorSuperior);
		g.fillOval(x, x, anchuraBoton, anchuraBoton); // dibujar tapa

		if (encendido) {
			g.setColor(colorEncendido);
		} else {
			g.setColor(colorApagado);
		}

		g.fillOval(x + bordeBoton, x + bordeBoton, anchuraLuz, anchuraLuz);
		// Dibujar luz
		g.setColor(Color.BLACK); // dibujar círculos negros
		g.drawOval(x, x, anchuraBoton - 1, anchuraBoton - 1);
		g.drawOval(x + bordeBoton, x + bordeBoton, anchuraLuz - 1, anchuraLuz - 1);
		g.setColor(getForeground()); // restituir color
	}
 	
 	
 	/**
 	 * Notificar evento en el botón a los oyentes
 	 * @param e Evento a notificar
 	 */
 	@SuppressWarnings("unchecked")
 	public void notificarOyentes(EventObject e) {
 		Vector<IEncendidoListener> copia;
 		synchronized (this) {
 			copia = (Vector<IEncendidoListener>) oyentes.clone();
 		}
 		//copia.stream().forEach(o -> o.enteradoCambioEncendido(e));
 		copia.forEach(o -> o.enteradoCambioEncendido(e)); // TODO ¿quién define este método?
 	}
 	
 	
	/**
	 * Botón pulsado con ratón
	 * @param e Evento de ratón pulsando botón
	 */
	public void luzPressed(MouseEvent e) {
		bPulsado = true;
		repaint();
	}
	
	
	/**
	 * Botón que estaba siendo pulsado es liberado
	 * @param e Evento de ratón dejando de pulsar botón
	 */
	public void luzReleased(MouseEvent e) {
		if (bPulsado) {
			bPulsado = false;
			setEncendido(!encendido); // TODO if (encendido){setEncendido(false);}else{seEncendido(true);}
			repaint();
		}
	}
	
	
	/**
	 * Añadir oyente 
	 * @param oyente
	 */
	public synchronized void addEncendidoListener(IEncendidoListener oyente) {
		oyentes.add(oyente);
	}
	
	
	/**
	 * Eliminar oyente
	 * @param oyente
	 */
	public synchronized void removeEncendidoListener(IEncendidoListener oyente) {
		oyentes.removeElement(oyente);
	}
}