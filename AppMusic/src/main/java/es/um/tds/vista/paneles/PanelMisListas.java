package es.um.tds.vista.paneles;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.utils.ComponentUtils;
import es.um.tds.utils.GeneradorPDF;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import es.um.tds.vista.ModeloLista;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;
import es.um.tds.vista.TabsColoresUI;

/**
 * Pestaña "Mis listas".
 * 
 * @author Beatriz y Francisco
 */
public class PanelMisListas extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private static AppMusic controlador;
	private Reproductor repr;
	
	private JPanel panelSuperior;
	private JPanel panelInferior;

	private JButton btnPDF;
	
	private static JList<String> lista;
	
	private static JTable tabla;

	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException
	 */
	public PanelMisListas() throws BDException, DAOException {
		super();
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}

	/**
	 * Inicializa el panel.
	 * @throws BDException
	 * @throws DAOException
	 */
	private void inicialize() throws BDException, DAOException {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Creamos el reproductor
		try{
            repr = new Reproductor();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelInferior, "Error interno.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
		
		// Creamos los paneles que componen la pestaña y los añadimos a esta
		panelSuperior = crearPanelSuperior();
		crearPanelInferior();
		
		this.add(panelSuperior);
		this.add(panelInferior);
	}

	/**
	 * Crea el panel que contendrá la lista de las listas del usuario y la tabla con
	 * las canciones que componen cada una de estas listas.
	 */
	private JPanel crearPanelSuperior() {
		// Creamos el panel superior y el panel que contiene la lista
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		JPanel panelLista = new JPanel();
		panel.add(panelLista);
		panelLista.setBorder(new TitledBorder(null, "Mis listas.",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//Inicializamos la tabla scrolleable y definimos su comportamiento al seleccionar una fila
		tabla = new JTable(new ModeloTabla());
		tabla.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tabla);
		panel.add(scrollPane);
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				ListaCanciones listaRep = new
						ListaCanciones("actual",((ModeloTabla)tabla.getModel()).getListaCanciones());
				repr.setListaReproduccion(listaRep, tabla.getSelectedRow());
	        }
		}); 
		
		/* 
		 * Inicializamos la lista con las listas del usuario actual y definimos su comportamiento
		 * al seleccionar un elemento
		 */
		lista = new JList<String>(new ModeloLista(controlador.getListasUsuario()));
		panelLista.add(lista);
		lista.addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent evento) {
						if (!evento.getValueIsAdjusting()) {
							@SuppressWarnings("unchecked")
							JList<String> fuente = (JList<String>)evento.getSource();
							String nombreLista = fuente.getSelectedValue().toString();
							((ModeloTabla)tabla.getModel()).setListaCanciones(controlador
									.getListaCanciones(nombreLista).getCanciones());
							tabla.revalidate();
							tabla.repaint();
						}
					}
				});
		return panel;
	}
	
	/**
	 * Crea el panel inferior que contendrá el reproductor.
	 */
	private JPanel crearPanelInferior() {
		// Inicializamos el panel
        panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
        
        // Botón de generar pdf
        
 		JPanel panelPDF = new JPanel(new BorderLayout());
		ComponentUtils.fixedSize(panelPDF, 65, 65);
 		panelInferior.add(panelPDF, BorderLayout.WEST);
 		
 		// Cargamos el icono del botón 
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
 		// Creamos el botón y le asignamos el icono
 		btnPDF = new JButton();
 		btnPDF.setIcon(pdfIcon);
 		customizarBotonPDF(); // cambia algunas propiedades del botón
 		panelPDF.add(btnPDF);
 		// Creamos su manejador
     	crearManejadorBotonPDF();
     		
     	// Reproductor
     	
        JPanel panelRepr = (JPanel) repr.getPanelReproductor();
        panelInferior.add(panelRepr, BorderLayout.CENTER);

        return panelInferior;
    }

	/**
	 * Altera algunas propiedades del botón de PDF.
	 */
	private void customizarBotonPDF() {
		// Le quitamos el fondo
		btnPDF.setOpaque(false);
 		btnPDF.setContentAreaFilled(false);
 		btnPDF.setBorderPainted(false);
 		// Definimos el comportamiento cuando el ratón pasa por encima
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
	}
	
	/**
	 * Crea manejador del botón de generar pdf.
	 */
	private void crearManejadorBotonPDF() {
		btnPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				int result;
				// Si es premium, generamos el pdf
				if (controlador.isUsuarioPremium()) {
					result = JOptionPane.showOptionDialog(panelInferior, 
							"¿Quiere generar un pdf con todas sus listas de canciones?", "Confirmar generar pdf",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[]{"Sí", "No"}, "default");
					if (result == JOptionPane.YES_OPTION) {
						GeneradorPDF generador = new GeneradorPDF();
						try {
							generador.generarPDF(controlador.getUsuarioActual());
							JOptionPane.showMessageDialog(panelInferior, 
									"Pdf generado con éxito.\n", "", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(panelInferior, 
									"Error generando pdf.\n", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				// En caso contrario, informamos al usuario de que debe ser premium
				} else {
					JOptionPane.showMessageDialog(panelInferior, 
							"Debe ser un usuario premium para poder utilizar esta herramienta.\n", 
							"Usuario no permitido", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * Refresca la pestaña de "Mis listas".
	 */
	public static void refrescar() {
		// Volvemos a cargar la lista de listas de canciones y refrescamos
 		List<ListaCanciones> listas = controlador.getListasUsuario();	
		((ModeloLista)lista.getModel()).setListaPlaylists(listas);
		lista.updateUI();
		/*
		 * Si había alguna lista seleccionada anteriormente, refrescamos la tabla de la derecha 
		 * que contiene las canciones de esa lista
		 */
		int indice = lista.getSelectedIndex();
		if (indice >= 0) {
			indice = indice % lista.getModel().getSize();
			((ModeloTabla)tabla.getModel()).setListaCanciones(listas.get(indice).getCanciones());
		}
		tabla.repaint();
		tabla.revalidate();
	}
}
