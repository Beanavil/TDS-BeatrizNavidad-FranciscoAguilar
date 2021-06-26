package es.um.tds.vista.paneles;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
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

import javax.swing.border.TitledBorder;

import es.um.tds.vista.ModeloLista;
import es.um.tds.vista.ModeloTabla;
import es.um.tds.vista.Reproductor;

public class PanelMisListas extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private AppMusic controlador;
	
	private JPanel panelSuperior;
	private JPanel panelInferior;
	
	private JList<String> lista;
	private JTable tabla;

	public PanelMisListas() throws BDException, DAOException {
		super();
		this.controlador = AppMusic.getUnicaInstancia();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelSuperior = crearPanelSuperior();
		panelInferior = crearPanelInferior();
		
		this.add(panelSuperior);
		this.add(panelInferior);
	}

	/**
	 * Crea el panel que contendrá la lista de las PlayLists del usuario y la tabla con las
	 * canciones que componen cada una de estas listas
	 */
	private JPanel crearPanelSuperior() {
		JPanel panel = new JPanel();
		JPanel panelLista = new JPanel();
		
		panel.setLayout(new GridLayout(1,2));
		panelLista.setBorder(new TitledBorder(null, "Mis listas.", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		tabla = new JTable(new ModeloTabla());
		tabla.setFillsViewportHeight(true);
		
		lista = new JList<String>(new ModeloLista(controlador.usuarioGetListas()));
		lista.addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent evento) {
						if (!evento.getValueIsAdjusting()) {
							@SuppressWarnings("unchecked")
							JList<String> fuente = (JList<String>)evento.getSource();
							String nombreLista = fuente.getSelectedValue().toString();
							((ModeloTabla)tabla.getModel()).setListaCanciones(controlador.getListaCanciones(nombreLista).getCanciones());
						}
						
					}
				});
		
		panelLista.add(lista);
		panel.add(panelLista);
		JScrollPane scrollPane = new JScrollPane(tabla);
		panel.add(scrollPane);
		
		return panel;
	}
	
	/**
	 * Crea el panel inferior que contendrá el reproductor
	 * @return
	 */
	private JPanel crearPanelInferior() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Reproductor repr = null;
        try{
            repr = new Reproductor();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Error interno.\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        JPanel panelRepr = (JPanel) repr.getPanelReproductor();
        panel.add(panelRepr);

        return panel;
    }
}


