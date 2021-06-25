package es.um.tds.vista;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import es.um.tds.modelo.ListaCanciones;

//TODO Muchas cosas que parece que no se usan
public class ModeloLista extends AbstractListModel<String> {

	private static final long serialVersionUID = 1L;
	private List<ListaCanciones> playlists;

	  public ModeloLista(List<ListaCanciones> listaCanciones) {
		  super();
		  this.playlists = listaCanciones;
	  }
	
	  public ModeloLista() {
		this(new ArrayList<ListaCanciones>());
	  }
	  
	  @Override
	  public int getSize() {
	    return playlists.size();
	  }

	  @Override
	  public String getElementAt(int index) {
	    return playlists.get(index).getNombre();
	  }

	  public List<ListaCanciones> getListaPlaylists() {
	    return playlists;
	  }

	  public void setListaPlaylists(List<ListaCanciones> listaPlaylists) {
	    this.playlists = listaPlaylists;
	  }
}
