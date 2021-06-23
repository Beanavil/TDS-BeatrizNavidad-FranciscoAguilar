package es.um.tds.vista;

import javax.swing.table.AbstractTableModel;
//TODO quitar se ha añadido para la prueba
import java.util.ArrayList;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.Estilo;
import es.um.tds.modelo.ListaCanciones;

public class ModeloDefinido extends AbstractTableModel{
  private static final long serialVersionUID = 1L;
    
  private static final int N_COLUMNAS = 2;
  private static final int COLUMNA_TITULO = 0;
  private static final int COLUMNA_AUTOR = 1;
  private static final String NOMBRE_CT = "Titulo";
  private static final String NOMBRE_CA = "Intérprete";
  private static final String NOMBRE_LC_DEFECTO = "Lista";

  private ListaCanciones playlist;
  
  public ModeloDefinido(ListaCanciones playlist) {
    this.playlist = playlist;
  }
  
  public ModeloDefinido() {
    this.playlist = new ListaCanciones(NOMBRE_LC_DEFECTO);
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