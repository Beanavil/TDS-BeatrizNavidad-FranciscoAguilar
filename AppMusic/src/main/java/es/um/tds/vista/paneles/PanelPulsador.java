package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.utils.ComponentUtils;
import es.um.tds.vista.Reproductor;
import pulsador.Luz;
import pulsador.IEncendidoListener;

public class PanelPulsador extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private AppMusic controlador;
	private JPanel panelCentral;

	public PanelPulsador() throws BDException, DAOException {
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}
	
	private void inicialize() {
		this.setLayout(new BorderLayout());
		
		// Parte norte
		JPanel panelNorte = crearPanelNorte();
		this.add(panelNorte, BorderLayout.NORTH);
		// Parte central
		crearPanelCentral();
		this.add(panelCentral, BorderLayout.CENTER);
		
//		// Parte inferior
//		JPanel panelInferior = crearPanelInferior();
//		this.add(panelInferior, BorderLayout.SOUTH);
	}
	

	private JPanel crearPanelNorte() {
		JPanel panelNorte = new JPanel();
		ComponentUtils.fixedSize(panelNorte, 60, 60);
		return panelNorte;
	}

	/** 
	 * Panel donde va el jfilechooser y la caja de texto para buscar url
	 */
	private void crearPanelCentral() {
		panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		
		JPanel panelLuz = new JPanel();
		panelLuz.setLayout(new BoxLayout(panelLuz, BoxLayout.X_AXIS));
		panelCentral.add(panelLuz);
		
		JLabel luzLabel = new JLabel("Pulsa para cargar un archivo XML de canciones:");//		
		Luz luz = new Luz();
		luz.setMaximumSize(new Dimension(30, 30));
		luz.setColorEncendido(Color.BLUE);
		panelLuz.add(luzLabel, BorderLayout.NORTH);
		panelLuz.add(luz, BorderLayout.NORTH);
		
		luz.addEncendidoListener(new IEncendidoListener() {
			@Override
			public void enteradoCambioEncendido(EventObject arg0) {
				if (luz.isEncendido()) {
					JFileChooser jfc = new JFileChooser(new File("./xml")); //FileSystemView.getFileSystemView().getHomeDirectory()
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
		
//		JPanel panelVacio = new JPanel();
//		ComponentUtils.fixedSize(panelVacio, 20, 20);
//		panelCentral.add(panelVacio);
//		
//		JPanel panelURL = new JPanel();
//		panelURL.setLayout(new BoxLayout(panelURL, BoxLayout.X_AXIS));
//		panelCentral.add(panelURL);
//		
//		JLabel urlLabel = new JLabel("O introduce la URL de una canción:");//		
//		JTextField urlField = new JTextField();
//		urlField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//		ComponentUtils.fixedSize(urlField, 500, 20);
//		
//		panelURL.add(urlLabel, BorderLayout.CENTER);
//		panelURL.add(urlField, BorderLayout.CENTER);
		
	}
	
}
