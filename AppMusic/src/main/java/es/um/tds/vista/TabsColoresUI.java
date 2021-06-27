package es.um.tds.vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;


/**
 * Propio BasicTabbedPaneUI para las pestañas de la ventana principal.
 * 
 * @author Beatriz y Francisco
 */
public class TabsColoresUI extends BasicTabbedPaneUI {
	
	public static Color COLOR_AZUL = new Color(164, 230, 246);
    private Polygon figura;

    @Override
    protected void installDefaults() {
        super.installDefaults();
    }

    /**
     * Sobreescribe el método paintTabBackground para cambiar el color de las pestañas
     */
    @Override
    protected void paintTabBackground(Graphics g, int posicionPestana, int indicePestana, int x, int y, 
    		int w, int h, boolean esSeleccionada) {
    	
    	// Calculamos los vértices de las pestañas, en función del ancho(w) y alto(h) de pestaña
    	int xp[] = new int[]{x, x, x + w, x + w, x};
    	int yp[] = new int[]{y, y + h - 3, y + h - 3, y, y};
    	
    	// Establecemos el color de relleno según los valores anteriores
        Graphics2D g2D = (Graphics2D) g;
        GradientPaint colorRelleno = new GradientPaint(x, y, COLOR_AZUL, x, y + h, COLOR_AZUL);

        // Establecemos la forma de la pestaña y la pintamos según si está seleccionada (colorRelleno) o no (blanco)
        figura = new Polygon(xp, yp, xp.length);
        if (esSeleccionada) {
            g2D.setPaint(colorRelleno);
        } else {
            if (tabPane.isEnabled() && tabPane.isEnabledAt(indicePestana)) {
                GradientPaint gradientShadowTmp = new GradientPaint(0, 0, Color.WHITE, 0, y + h / 2, Color.WHITE);
                g2D.setPaint(gradientShadowTmp);
            } else {
                GradientPaint gradientShadowTmp = new GradientPaint(0, 0, Color.WHITE, 0, y + 15 + h / 2, Color.WHITE);
                g2D.setPaint(gradientShadowTmp);
            }
        }
        g2D.fill(figura);
    }
    
    
    /**
     * Sobreescribe el método layoutLabel para que el título de las pestañas salga alineado a la izquierda
     */
    @Override
    protected void layoutLabel(int posicionPestaña, FontMetrics metrics, int indicePestana, String titulo, Icon icono,
                               Rectangle rectPestana, Rectangle rectIcono, Rectangle rectTexto, boolean esSeleccionada ) {
    	
      rectTexto.x = rectTexto.y = rectIcono.x = rectIcono.y = 0;
      SwingUtilities.layoutCompoundLabel((JComponent) tabPane,
                                         metrics, titulo, icono,
                                         SwingUtilities.CENTER,
                                         SwingUtilities.LEFT, // CENTER antes
                                         SwingUtilities.CENTER,
                                         SwingUtilities.TRAILING,
                                         rectPestana,
                                         rectIcono,
                                         rectTexto,
                                         textIconGap);
    }
}
