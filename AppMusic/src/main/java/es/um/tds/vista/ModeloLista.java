package es.um.tds.vista;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import es.um.tds.modelo.ListaCanciones;


/**
 * Modelo para una JList.
 * 
 * @author Beatriz y Francisco
 */
public class ModeloLista extends AbstractListModel<String> {

	private static final long serialVersionUID = 1L;
	private List<ListaCanciones> playlists;

	  /**
	   * Constructor.
	   * @param listaCanciones Lista de canciones asignada al modelo
	   */
	  public ModeloLista(List<ListaCanciones> listaCanciones) {
		  super();
		  this.playlists = listaCanciones;
	  }
	
	  /**
	   * Constructor vacío.
	   */
	  public ModeloLista() {
		this(new ArrayList<ListaCanciones>());
	  }
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public int getSize() {
	    return playlists.size();
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public String getElementAt(int index) {
	    return playlists.get(index).getNombre();
	  }

	  /**
	   * Devuelve la lista de listas de canciones del modelo.
	   * @return
	   */
	  public List<ListaCanciones> getListaPlaylists() {
	    return playlists;
	  }

	  /**
	   * Establece la lista de listas de canciones del modelo.
	   * @return
	   */
	  public void setListaPlaylists(List<ListaCanciones> listaPlaylists) {
	    this.playlists = listaPlaylists;
	  }
}
