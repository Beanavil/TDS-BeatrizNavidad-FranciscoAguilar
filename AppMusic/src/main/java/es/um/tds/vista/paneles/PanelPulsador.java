package es.um.tds.vista.paneles;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class PanelPulsador extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelPulsador() {
		super();
		inicialize();
	}
	
	private void inicialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		
//		// Panel donde va el jfilechoose y la caja de texto para buscar url
//		JPanel panelSuperior = new JPanel();
//		pane.add(panelSuperior);
//		
//		Luz luz = new Luz();
//		luz.setColorEncendido(Color.BLUE);
//		panelSuperior.add(luz);
//		
//		luz.addEncendidoListener(new IEncendidoListener() {
//			@Override
//			public void enteradoCambioEncendido(EventObject arg0) {
//				if (luz.isEncendido()) {
//					JFileChooser jfc = new JFileChooser(); //FileSystemView.getFileSystemView().getHomeDirectory()
//					FileNameExtensionFilter filter = new FileNameExtensionFilter("Xml Song Files", "xml");
//					jfc.setFileFilter(filter);
//	
//			        int returnValue = jfc.showOpenDialog(frmVentanaPrincipal);
//	
//			        if (returnValue == JFileChooser.APPROVE_OPTION) {
//			        	Canciones canciones = MapperCancionesXMLtoJava.cargarCanciones(jfc.getSelectedFile().getAbsolutePath());
//			            canciones.getCancion().stream().forEach(c -> System.out.println(c.getTitulo()));
//			            //controlador.playCancion(canciones.getCancion().get(1).getURL());
//			            panelPulsador.revalidate();
//			        }
//				}
//				luz.setEncendido(false);
//			}
//		});
//		
//		JLabel lblPulsaParaSeleccionar = new JLabel("Pulsa para cargar un archivo XML de canciones o introduce la URL de una canción:");
//		panelSuperior.add(lblPulsaParaSeleccionar);
//		
//		JTextArea textArea = new JTextArea();
//		fixedSize(JTextArea, 400, 20);
//		textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//		panelSuperior.add(textArea);
//		
	}
}
