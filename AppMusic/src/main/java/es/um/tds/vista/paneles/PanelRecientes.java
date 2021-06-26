package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.utils.ComponentUtils;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;
import es.um.tds.vista.TabsColoresUI;

public class PanelRecientes extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static AppMusic controlador;
	private static JTable tablaCanciones;
	private static List<Cancion> listaActual;
	private static Reproductor repr;
	
	private JButton btnPDF;

	public PanelRecientes() throws BDException, DAOException {
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}
	
	private void inicialize() {
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		
		// Reproductor
		try{
			repr = new Reproductor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error interno.\n",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		// Parte central
		JPanel panelCentral =  crearPanelCentral();
		this.add(panelCentral, BorderLayout.CENTER);
		
		// Parte inferior
		JPanel panelInferior = crearPanelInferior();
		this.add(panelInferior, BorderLayout.SOUTH);
	}

	private JPanel crearPanelCentral() {
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
	    
		JPanel panelVacio = new JPanel(new BorderLayout());
		ComponentUtils.fixedSize(panelVacio, 100, 100);
		panelCentral.add(panelVacio, BorderLayout.NORTH);
		
		JPanel panelTabla = new JPanel(new BorderLayout());
		ComponentUtils.fixedSize(panelTabla, 600, 182);
		panelCentral.add(panelTabla, BorderLayout.CENTER);
		
		listaActual = controlador.getCancionesRecientes();
		tablaCanciones = new JTable(new ModeloTabla(listaActual));
		tablaCanciones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaCanciones.setFillsViewportHeight(true);
		
		// Definimos el comportamiento de la app cuando se selecciona un elemento (canción) de la tabla:
		tablaCanciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				repr.setListaReproduccion(new ListaCanciones("Lista recientes", listaActual), 
						tablaCanciones.getSelectedRow());
	        }
		});
	    JScrollPane scrollPane = new JScrollPane(tablaCanciones);
	    panelTabla.add(scrollPane, BorderLayout.CENTER);
	    
		return panelCentral;
	}
	
	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel(new BorderLayout());
		//panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
		
		// Botón de generar pdf
		JPanel panelPDF = new JPanel();
		panelPDF.setLayout(new BoxLayout(panelPDF, BoxLayout.X_AXIS));
		panelInferior.add(panelPDF, BorderLayout.WEST);
		
		BufferedImage iconPDF = null;
		try {
			iconPDF = ImageIO.read(new File("./resources/pdf-icon.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon pdfIcon = new ImageIcon(iconPDF);
		Image image = pdfIcon.getImage();
		Image scaledimage = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		pdfIcon = new ImageIcon(scaledimage);
		btnPDF = new JButton();
		btnPDF.setIcon(pdfIcon);
		btnPDF.setOpaque(false);
		btnPDF.setContentAreaFilled(false);
		btnPDF.setBorderPainted(false);
		btnPDF.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent evt) {
	        	 btnPDF.setOpaque(true);
	        	 btnPDF.setContentAreaFilled(true);
	        	 btnPDF.setBorderPainted(true);
	        	 btnPDF.setBorder(new LineBorder(TabsColoresUI.COLOR_AZUL));
	        	 btnPDF.setBackground(TabsColoresUI.COLOR_AZUL);
	         }
	         public void mouseExited(MouseEvent evt) {
	        	 btnPDF.setOpaque(false);
	        	 btnPDF.setContentAreaFilled(false);
	        	 btnPDF.setBorderPainted(false);
	          }
		});
		
		panelPDF.add(btnPDF);
		
		
		crearManejadorBotonPDF();
		
		// Reproductor
		JPanel panelRepr = (JPanel) repr.getPanelReproductor();
		panelInferior.add(panelRepr, BorderLayout.CENTER);
		
		return panelInferior;
	}
	
	
	/**
	 * Crea manejador del botón de generar pdf
	 */
	private void crearManejadorBotonPDF() {
		// TODO 
		
	}

	// Refrescar tabla de recientes
	public static void refresh() {
		listaActual = controlador.getCancionesRecientes();
		tablaCanciones.setModel(new ModeloTabla(listaActual));
		tablaCanciones.repaint();
		tablaCanciones.revalidate();
	}
}
