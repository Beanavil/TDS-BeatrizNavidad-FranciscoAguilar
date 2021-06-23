package es.um.tds.vista;

import javax.swing.table.AbstractTableModel;
//TODO quitar se ha añadido para la prueba
import java.util.ArrayList;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;

public class ModeloDefinido extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	  
	private static final int N_COLUMNAS = 2;
	private static final int COLUMNA_TITULO = 0;
	private static final int COLUMNA_AUTOR = 1;
	private static final String NOMBRE_CT = "Titulo";
	private static final String NOMBRE_CA = "Artista";

	private ListaCanciones playlist;
	
	public ModeloDefinido(ListaCanciones playlist) {
	  this.playlist = playlist;
	}
	
	//TODO Esto se debería de poder hacer llamando al constructor previo pero me da error
//	public ModeloDefinido() {
//		this.playlist = new ListaCanciones(NOMBRE_LC_DEFECTO);
//	}

	//TODO prueba de funcionamiento
	public ModeloDefinido() {
		Cancion cancion = new Cancion( "IWannaKillPaco", "Bea", "SatanicRitual", "SoFucked", 10);
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();
		canciones.add(cancion);
		ListaCanciones playList = new ListaCanciones("Prueba", canciones); 
		this.playlist = playList;
	}
	public ListaCanciones getListaCanciones() {
	  return playlist;
	}

	public void setPlaylist(ListaCanciones playlist) {
	  this.playlist = playlist;
	}

	@Override
	public int getColumnCount() {
		return N_COLUMNAS;
	}
	  
	@Override
	public int getRowCount() {
		return playlist.getNumCanciones();
	}
	  
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
	  
	@Override
	public Object getValueAt(int fila, int col) {
		switch (col) {
	    case COLUMNA_TITULO:
	      return playlist.getCancion(fila).getTitulo();
	    case COLUMNA_AUTOR:
	      return playlist.getCancion(fila).getInterprete();
	    default:
	      throw new IndexOutOfBoundsException("Columna fuera de rango " + this.getClass().getName());
	  }
	}	
}
