package es.um.tds.vista;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PanelNuevaLista extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final int TAM_CUADRO_TEXTO = 10;
	
//TODO No sé si serán necesarios
//	private JPanel panel1;
//	private JPanel panel2;
//	private JPanel panel3;
//	private JPanel panel4;
//	private JPanel panelBtn;
	
	//TODO tiene pinta de que es bastante recomendable hacer un initialize(), preguntar
	//si se hace siempre es más bien para el caso de los JFrame
	/**
	 * Constructor de la clase
	 */
	public PanelNuevaLista() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel panel1 = crearPanel1();
		
		JPanel panelInv = new JPanel();
		panelInv.setLayout(new BorderLayout());
		//panelInv.setVisible(false);
		
		JPanel panel2 = crearPanel2();
		JPanel panel3 = crearPanel3();
		JPanel panel4 = crearPanel4();
		
		panelInv.add(panel2, BorderLayout.NORTH);
		panelInv.add(panel3, BorderLayout.CENTER);
		panelInv.add(panel4, BorderLayout.SOUTH);
		
		this.add(panel1);
		this.add(panelInv);
	}
	
	/**
	 * 
	 * @return
	 */
	private JPanel crearPanel1() {
		JPanel panel = new JPanel();
		JTextField txtSuperior = new JTextField(TAM_CUADRO_TEXTO);
		JButton btnSuperior = new JButton("Crear");
		panel.add(txtSuperior);
		panel.add(btnSuperior);
		return panel;
	}
	
	private JPanel crearPanel2() {
		JPanel panel = new JPanel();
		JTextField txtMedio1 = new JTextField(TAM_CUADRO_TEXTO);
		JTextField txtMedio2 = new JTextField(TAM_CUADRO_TEXTO);
		JTextField txtMedio3 = new JTextField(TAM_CUADRO_TEXTO);
		JButton btnMedio = new JButton("Buscar");
		panel.add(txtMedio1);
		panel.add(txtMedio2);
		panel.add(txtMedio3);
		panel.add(btnMedio);
		return panel;
	}

	private JPanel crearPanel3() {
		JPanel panel = new JPanel();
		VentanaPrincipal.fixedSize(panel, 450, 250);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JTable tablaIzq = new JTable(new ModeloTabla());
		tablaIzq.setPreferredScrollableViewportSize(new Dimension(350,70));
		tablaIzq.setFillsViewportHeight(true);
		
		JScrollPane scrollPaneIzq = new JScrollPane(tablaIzq);
		panel.add(scrollPaneIzq);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		JPanel panelBtn = crearPanelBtn();
		panel.add(panelBtn);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		JPanel panelTablaDer = new JPanel();
		panelTablaDer.setBorder(new TitledBorder(null, "PlayList", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JTable tablaDer = new JTable(new ModeloTabla());
		tablaDer.setPreferredScrollableViewportSize(new Dimension(350,200));
		tablaDer.setFillsViewportHeight(true);
		
		JScrollPane scrollPaneDer = new JScrollPane(tablaDer);
		panelTablaDer.add(scrollPaneDer);
		panel.add(panelTablaDer);
		return panel;
	}
	
	private JPanel crearPanel4() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
		JButton btnAceptar = new JButton("Aceptar");
		JButton btnCancelar = new JButton("Cancelar");
		panel.add(btnAceptar);
		panel.add(btnCancelar);
		return panel;
	}
	
	private JPanel crearPanelBtn() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JButton btnañadir = new JButton(">>");
		JButton btnretirar = new JButton("<<");
		panel.add(btnañadir);
		panel.add(btnretirar);
		return panel;
	}
}
