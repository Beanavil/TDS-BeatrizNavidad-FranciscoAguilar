package es.um.tds.vista;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import es.um.tds.controlador.AppMusic;
import es.um.tds.excepciones.BDException;
import es.um.tds.excepciones.DAOException;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.vista.paneles.PanelMasReproducidas;
import es.um.tds.vista.paneles.PanelRecientes;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Reproductor de canciones.
 * 
 * @author Beatriz y Francisco
 */
public class Reproductor {

	private AppMusic controlador;
	  private MediaPlayer mediaPlayer;
	  
	  private ListaCanciones listaActual;
	  private int indiceCancionActual;
	  private final String tempPath = "./temp";
	  
	  private JPanel panelReproductor;

	  private JButton btnPrev;
	  private JButton btnPlay;
	  private JButton btnNext;
	  
	  private static ImageIcon stop;
	  private static ImageIcon play;
	  private static ImageIcon next;
	  private static ImageIcon prev;

	/**
	 * Constructor.
	 * @throws BDException
	 * @throws DAOException 
	 */
	public Reproductor() throws BDException, DAOException {
		controlador = AppMusic.getUnicaInstancia();
		inicialize();
	}
	
	/**
	 * Inicializa los componentes del reproductor.
	 */
	public void inicialize() {
		JPanel panelBotones = crearPanelBotones();
		panelReproductor = new JPanel(new BorderLayout());
		panelReproductor.add(panelBotones, BorderLayout.CENTER);
		panelReproductor.setVisible(true);

		listaActual = new ListaCanciones(" ");
	}
	
    /**
     * Crea el panel de botones del reproductor.
     * @return
     */
    private JPanel crearPanelBotones() {
		BufferedImage iconPrev = null;
		BufferedImage iconPlay = null;
		BufferedImage iconNext = null;
		BufferedImage iconStop = null;
		
		try {
			iconPrev = ImageIO.read(new File("./resources/previous-song-button-icon.png")); 
		iconPlay = ImageIO.read(new File("./resources/play-song-button-icon.png")); 
		iconNext = ImageIO.read(new File("./resources/next-song-button-icon.png")); 
		iconStop = ImageIO.read(new File("./resources/stop-song-button-icon.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		panelBotones.add(Box.createHorizontalGlue());
		
		// Botón "canción anterior" reproduce la canción anterior en la lista o la última
		// si la que está en reproducción es la primera
		prev = new ImageIcon(iconPrev);
		Image image = prev.getImage();
		Image scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		prev = new ImageIcon(scaledimage);
		crearBotonPrev(panelBotones);
		
		// Botón play/stop
		play = new ImageIcon(iconPlay);
		image = play.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		play = new ImageIcon(scaledimage);
		btnPlay = new JButton(play);
		
		stop = new ImageIcon(iconStop);
		image = stop.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		stop = new ImageIcon(scaledimage);
		
		crearBotonPlay(panelBotones);
		
		// Botón "canción siguiente" reproduce la canción siguiente en la lista o la primera
		// si la que está en reproducción es la última
		next = new ImageIcon(iconNext);
		image = next.getImage();
		scaledimage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		next = new ImageIcon(scaledimage);
		crearBotonNext(panelBotones);
		panelBotones.add(Box.createHorizontalGlue());
		return panelBotones;
    }
	  
	 /**
	  * Crea el botón de "canción previa" y su manejador de eventos.
      * @param panelBotones Panel en el que se añade el botón
      */
     private void crearBotonPrev(JPanel panelBotones) {
		 btnPrev = new JButton(prev);
		 btnPrev.addActionListener(
				 event -> {
					 if (listaActual.getNumCanciones() > 0) { 
						 indiceCancionActual =
								 indiceCancionActual == 0 ? (listaActual.getNumCanciones() - 1) : (indiceCancionActual - 1);
								 disposePlayer();
								 reproducirCancion();
					 }
				 });
		 panelBotones.add(btnPrev);
	 }

    /**
     * Crea el botón de "reproducir/parar canción" y su manejador de eventos.
     * @param panelBotones Panel en el que se añade el botón
     */
	private void crearBotonPlay(JPanel panelBotones) {
		btnPlay.addActionListener(
	        aEvent -> {
	          if (listaActual.getNumCanciones() == 0)
	        	  return;
	          else if (mediaPlayer == null) {
	            reproducirCancion();
	            btnPlay.setIcon(stop);
	          } else {
	            switch (mediaPlayer.getStatus()) {
	              case DISPOSED:
	              case READY:
	                reproducirCancion();
	                btnPlay.setIcon(stop);
	              case STOPPED:
	              case PAUSED:
	                reanudarCancion();
	                btnPlay.setIcon(stop);
	                break;
	              case PLAYING:
	                pausarCancion();
	                btnPlay.setIcon(play);
	                break;
	              default:
	                break;
	            }
	          }
	        });
	    panelBotones.add(btnPlay);
	}
	
	/**
	  * Crea el botón de "canción siguiente" y su manejador de eventos.
	  * @param panelBotones Panel en el que se añade el botón
	  */
	private void crearBotonNext(JPanel panelBotones) {
		btnNext = new JButton(next);
		btnNext.addActionListener(
				event -> {
					if (listaActual.getNumCanciones() > 0) { 
						indiceCancionActual = (indiceCancionActual + 1) % listaActual.getNumCanciones();
						disposePlayer();
						reproducirCancion();
					}
				});
		panelBotones.add(btnNext);
	}
	  
   /**
    * Libera todos los recursos asignados al player anterior (dispose)
    * y establece el icono del botón play/stop que corresponde.
    */
   private void disposePlayer() {
     if (mediaPlayer != null) {
       mediaPlayer.dispose();
     }
     btnPlay.setIcon(play);
   }

	  
  /**
   * Reproduce canción
   * @throws IOException 
   */
  private void reproducirCancion() {
	if (listaActual.getNumCanciones() == 0)
		return;
	
	if(mediaPlayer != null) pararCancion();
	Cancion cancion = listaActual.getCancion(indiceCancionActual);
	URL uri = null;
	try {
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
        uri = new URL(cancion.getRutaFichero());
        
        System.setProperty("java.io.tmpdir", tempPath);
        Path mp3 = Files.createTempFile("now-playing", ".mp3");
        
        try (InputStream stream = uri.openStream()) {
          Files.copy(stream, mp3, StandardCopyOption.REPLACE_EXISTING);
        }
        
	    Media media = new Media(mp3.toFile().toURI().toString());
	    controlador.actualizarNumReproducciones(cancion);
	    controlador.addReciente(cancion);
	    PanelRecientes.refrescar();
	    PanelMasReproducidas.refrescar();
	    
	    mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.play(); // esto falla en ubuntu

	    
	} catch (MalformedURLException e) {
		JOptionPane.showMessageDialog(panelReproductor, "Error al cargar canción.\n",
				"Error", JOptionPane.ERROR_MESSAGE);
		
	} catch (IOException e) {
		JOptionPane.showMessageDialog(panelReproductor, "Error al cargar canción.\n",
				"Error", JOptionPane.ERROR_MESSAGE);
	}
  }
	  
  /**
   * Finaliza la reproducción de una canción y 
   * elimina los archivos temporales descargados para la reproducción.
   */
  public void pararCancion() {
        if (mediaPlayer != null) mediaPlayer.stop();
        File directorio = new File(tempPath);
        
        String[] files = directorio.list();
        for (String archivo : files) {
        	File fichero = new File(tempPath + File.separator + archivo);
        	fichero.delete();
        }
        mediaPlayer = null;
    }
  
  /**
   * Reanuda la reproducción de una canción.
   */
  public void reanudarCancion() {
        if(mediaPlayer != null)
            mediaPlayer.play();
    }
  
  /**
   * Pausa la reproducción de una canción.
   */
  public void pausarCancion() {
        if(mediaPlayer != null)
            mediaPlayer.pause();
    }
  
  /**
   * Establece una lista de canciones a reproducir a partir de un número de 
   * canción en concreto.
   * @param lista Lista de canciones a reproducir
   * @param index Índice de la canción por la que empezar
   */
  public void setListaReproduccion(ListaCanciones lista, int index) {
    if (!panelReproductor.isVisible()) {
      panelReproductor.setVisible(true);
    }
    listaActual = lista;
    indiceCancionActual = index;
    disposePlayer();
  }

  /**
   * Establece una lista de canciones a reproducir desde el principio.
   * @param lista Lista de canciones a reproducir
   */
  public void setListaReproduccion(ListaCanciones lista) {
    setListaReproduccion(lista, 0);
  }

  /**
   * Devuelve el panel de botones del reproductor.
   * @return
   */
  public JPanel getPanelReproductor() {
	  return panelReproductor;
  }
  
  /**
   * Elimina el objeto MediaPlayer
   */
  public void dispose() {
    if (mediaPlayer != null) {
      mediaPlayer.dispose();
    }
  }
}