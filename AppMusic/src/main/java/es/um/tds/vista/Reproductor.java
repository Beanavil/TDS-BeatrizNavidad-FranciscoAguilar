package es.um.tds.vista;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


import es.um.tds.controlador.AppMusic;
import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.persistencia.DAOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Reproductor {

	  private MediaPlayer mediaPlayer;
	  private ListaCanciones listaActual;
	  private int indiceCancionActual;
	  
	  private AppMusic controlador;

	  private JPanel panelReproductor;

	  private JButton btnPrev;
	  private JButton btnPlay;
	  private JButton btnNext;
	  
	  private static ImageIcon stop;
	  private static ImageIcon play;
	  private static ImageIcon next;
	  private static ImageIcon prev;

	/**
	 * Constructor
	 * @throws DAOException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Reproductor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, DAOException {
		
		JPanel panelBotones = crearPanelBotones();
		panelReproductor = new JPanel(new BorderLayout());
		panelReproductor.add(panelBotones, BorderLayout.CENTER);
		panelReproductor.setVisible(true);
		
		controlador = AppMusic.getUnicaInstancia();

		listaActual = new ListaCanciones(" ");

	    try {
	      com.sun.javafx.application.PlatformImpl.startup(() -> {});
	    } catch (Exception e) {
	      e.printStackTrace();
	      System.out.println("Exception: " + e.getMessage());
	    }
	}

	
	  /**
	   * Crea el panel de botones del reproductor
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
	   * Crea el botón de "canción previa" y su manejador de eventos
	   * @param panelBotones Panel en el que se añade el botón
	   */
	  private void crearBotonPrev(JPanel panelBotones) {
		  btnPrev = new JButton(prev);
		  btnPrev.addActionListener(
				  event -> {
					  if (listaActual.getNumCanciones() > 0) { 
						  indiceCancionActual =
								  indiceCancionActual == 0 ? (listaActual.getNumCanciones() - 1) : (indiceCancionActual - 1);
								  cambiarCancion(listaActual.getCancion(indiceCancionActual));
					  }
				  });
		  panelBotones.add(btnPrev);
	  }


	  /**
	   * Crea el botón de "reproducir/parar canción" y su manejador de eventos
	   * @param panelBotones Panel en el que se añade el botón
	   */
	 private void crearBotonPlay(JPanel panelBotones) {
		btnPlay.addActionListener(
	        aEvent -> {
	          if (listaActual.getNumCanciones() == 0)
	        	  return;
	          else if (mediaPlayer == null) {
	            crearMediaPlayer();
	            mediaPlayer.play();
	            btnPlay.setIcon(stop);
	          } else {
	            switch (mediaPlayer.getStatus()) {
	              case DISPOSED:
	              case READY:
	                crearMediaPlayer();
	              case STOPPED:
	              case PAUSED:
	                btnPlay.setIcon(stop);
	                mediaPlayer.play();
	                break;
	              case PLAYING:
	                btnPlay.setIcon(play);
	                mediaPlayer.pause();
	                break;
	              default:
	                break;
	            }
	          }
	        });
	    panelBotones.add(btnPlay);
	}

	
	 /**
	   * Crea el botón de "canción siguiente" y su manejador de eventos
	   * @param panelBotones Panel en el que se añade el botón
	   */
	private void crearBotonNext(JPanel panelBotones) {
		btnNext = new JButton(next);
		btnNext.addActionListener(
				event -> {
					if (listaActual.getNumCanciones() > 0) { 
						indiceCancionActual = (indiceCancionActual + 1) % listaActual.getNumCanciones();
						cambiarCancion(listaActual.getCancion(indiceCancionActual));
					}
				});
		panelBotones.add(btnNext);
	}
	
	
	  
	  /**
	   * Cambia de canción
	   */
	  private void cambiarCancion(Cancion cancion) {
	    if (mediaPlayer != null) {
	      mediaPlayer.dispose();
	    }
	    btnPlay.setIcon(play);
	  }

	  
	  /**
	   * Inicialización del mediaPlayer
	   */
	  private void crearMediaPlayer() {
		if (listaActual.getNumCanciones() == 0)
			return;
		
	    Cancion cancion = listaActual.getCancion(indiceCancionActual);
	    File f;
	    try {
	      f = rutaToFichero(cancion.getRutaFichero());
	    } catch (IOException e) {
	      JOptionPane.showMessageDialog(
	          panelReproductor,
	          "Error al intentar reproducir la canción",
	          "Canción no disponible",
	          JOptionPane.ERROR_MESSAGE);
	      return;
	    }
	    Media media = new Media(f.toURI().toString());
	    media.getMarkers().put("listened", new Duration(10 * 1000)); // 10 = secs to be listened

	    mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.setOnMarker(
	        mEvent -> {
	        // Añadir a reproducidas recientes y actualizar la lista de más reproducidas
	          controlador.actualizarNumReproducciones(cancion);
	          controlador.addReciente(cancion);
	        });
	    mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime()));
	  }

	  
	  /**
	   * Devuelfe el fichero con la ruta especificada o un fichero temporal si la ruta comienza con http
	   * @param ruta
	   * @return Fichero de la ruta
	   * @throws IOException
	   */
	  private File rutaToFichero(String ruta) throws IOException {
	    if (ruta.startsWith("http")) {
	      URL url = new URL(ruta);
	      Path rutaFicheroMP3 = Files.createTempFile("now-playing", ".mp3");
	      try (InputStream stream = url.openStream()) {
	        Files.copy(stream, rutaFicheroMP3, StandardCopyOption.REPLACE_EXISTING);
	      }
	      return rutaFicheroMP3.toFile();
	    } else {
	      return new File(ruta);
	    }
	  }

	  /**
	   * Reproduce una lista de canciones a partir de una canción en concreto
	   * @param lista Lista de canciones a reproducir
	   * @param index Índice de la canción por la que empezar
	   */
	  public void play(ListaCanciones lista, int index) {
	    if (!panelReproductor.isVisible()) {
	      panelReproductor.setVisible(true);
	    }
	    listaActual = lista;
	    indiceCancionActual = index;
	    cambiarCancion(listaActual.getCancion(index));
	  }

	  /**
	   * Reproduce una lista de canciones desde el principio
	   * @param lista Lista de canciones a reproducir
	   */
	  public void play(ListaCanciones lista) {
	    play(lista, 0);
	  }

	  
	  public JPanel getPanelReproductor() {
		  return panelReproductor;
	  }
	  
	  
	  public void dispose() {
	    if (mediaPlayer != null) {
	      mediaPlayer.dispose();
	    }
	  }
	}