package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.utils.ComponentUtils;
import pulsador.Luz;
import pulsador.IEncendidoListener;

/**
 * Pestaña "Cargar canciones".
 * 
 * @author Beatriz y Francisco
 */
public class PanelPulsador extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private AppMusic controlador;
	private JPanel panelCentral;

	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException
	 */
	public PanelPulsador() throws BDException, DAOException {
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}
	
	/**
	 * Inicializa el panel.
	 */
	private void inicialize() {
		this.setLayout(new BorderLayout());
		
		// Parte norte
		JPanel panelNorte = crearPanelNorte();
		this.add(panelNorte, BorderLayout.NORTH);
		// Parte central
		crearPanelCentral();
		this.add(panelCentral, BorderLayout.CENTER);
	}
	
	/**
	 * Crea un panel vacío para separar el panel central del borde superior.
	 */
	private JPanel crearPanelNorte() {
		JPanel panelNorte = new JPanel();
		ComponentUtils.fixedSize(panelNorte, 60, 60);
		return panelNorte;
	}

	/** 
	 * Crea el panel donde va el pulsador para cargar las canciones
	 */
	private void crearPanelCentral() {
		panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		
		// Panel donde ponemos el componente Luz
		JPanel panelLuz = new JPanel();
		panelLuz.setLayout(new BoxLayout(panelLuz, BoxLayout.X_AXIS));
		panelCentral.add(panelLuz);
		
		JLabel luzLabel = new JLabel("Pulsa para cargar un archivo XML de canciones:");//		
		Luz luz = new Luz();
		luz.setMaximumSize(new Dimension(30, 30));
		luz.setColorEncendido(Color.BLUE);
		panelLuz.add(luzLabel, BorderLayout.NORTH);
		panelLuz.add(luz, BorderLayout.NORTH);
		
		/* 
		 * Definimos el comportamiento del pulsador, que será llamar al controlador para cargar las canciones
		 * seleccionadas por el usuario
		 */
		luz.addEncendidoListener(new IEncendidoListener() {
			@Override
			public void enteradoCambioEncendido(EventObject arg0) {
				if (luz.isEncendido()) {
					JFileChooser jfc = new JFileChooser(new File("./xml"));
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Xml Song Files", "xml");
					jfc.setFileFilter(filter);
	
			        int returnValue = jfc.showOpenDialog(panelCentral);
	
			        if (returnValue == JFileChooser.APPROVE_OPTION) {
			        	controlador.cargarCanciones(jfc.getSelectedFile().getAbsolutePath());
			            panelCentral.revalidate();
			            
			            JOptionPane.showMessageDialog(panelCentral, 
			            		"Canciones cargadas con éxito.\n", "", JOptionPane.INFORMATION_MESSAGE);
			        }
			       luz.setEncendido(false);
				}
			}
		});
	}
}
