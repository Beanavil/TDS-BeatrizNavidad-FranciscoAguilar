package es.um.tds.vista;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import es.um.tds.modelo.Cancion;

/**
 * Modelo para una JTable.
 * 
 * @author Beatriz y Francisco
 */
public class ModeloTabla extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	
	private static final int N_COLUMNAS = 2;
	private static final int COLUMNA_TITULO = 0;
	private static final int COLUMNA_AUTOR = 1;
	private static final String NOMBRE_CT = "Titulo";
	private static final String NOMBRE_CA = "Intérprete";
	
	private List<Cancion> listaCanciones;
	
	/**
	 * Constructor.
	 * @param listaCanciones Lista de canciones asignada al modelo.
	 */
	public ModeloTabla(List<Cancion> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}
	
	/**
	 * Constructor vacío.
	 */
	public ModeloTabla() {
		this(new ArrayList<Cancion>()); 
	}
	 
	/**
	 * Devuelve la lista de canciones asignada al modelo.
	 * @return
	 */
	public List<Cancion> getListaCanciones() {
	  return listaCanciones;
	}

	/**
	 * Establece la lista de canciones asignada al modelo.
	 * @return
	 */
	public void setListaCanciones(List<Cancion> listaCanciones) {
	  this.listaCanciones = listaCanciones;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColumnCount() {
		return N_COLUMNAS;
	}
	  
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowCount() {
		return listaCanciones.size();
	}
	  
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnName (int col) {
		switch (col) {
	    case COLUMNA_TITULO:
	      return NOMBRE_CT;
	    case COLUMNA_AUTOR:
	      return NOMBRE_CA;
	    default:
	      throw new IndexOutOfBoundsException("Columna fuera de rango " + this.getClass().getName());
	  }
	}
	  
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValueAt(int fila, int col) {
		switch (col) {
	    case COLUMNA_TITULO:
	      return listaCanciones.get(fila).getTitulo();
	    case COLUMNA_AUTOR:
	      return listaCanciones.get(fila).getInterprete();
	    default:
	      throw new IndexOutOfBoundsException("Columna fuera de rango " + this.getClass().getName());
	  }
	}	
}
