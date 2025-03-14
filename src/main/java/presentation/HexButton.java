package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//https://forums.codeguru.com/showthread.php?478270-Hexagonal-Buttons

/**
 * A six sided toggle button. This is not guaranteed to be a perfect hexagon, it is just
 * guaranteed to have six sides in
 * the form of a hexagon. To be a perfect hexagon the size of this component must have a height
 * to width ratio of
 * 1 to 0.866
 *
 * @author keang
 * @date 5 Jun 2009
 */

//This GUI code was adapted from a CSSE 375 revision of a
// Settlers of Catan Project with instructor permission
public class HexButton extends JButton {
    public static final int BORDER_WIDTH = 8;
    private static final long serialVersionUID = 4865976127980106774L;

    private Polygon hexagon = new Polygon();

    private static final int NUM_POINTS = 6;

    private boolean paint = false;

    /**
     * @param arg0
     */
    public HexButton(String arg0) {

        super(arg0);

        addMouseListener(new HexMouseAdapter());
    }

    @Override
    public boolean contains(Point p) {
        return hexagon.contains(p);
    }

    @Override
    public boolean contains(int x, int y) {
        return hexagon.contains(x, y);
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        calculateCoords();
    }

    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        calculateCoords();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        calculateCoords();
    }

    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        calculateCoords();
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (contains(e.getPoint())) super.processMouseEvent(e);
    }

    private void calculateCoords() {
        int w = getWidth() - 1;
        int h = getHeight() - 1;

        final double heightRatio = 0.25;
        int ratio = (int) (h * heightRatio);

        createHexagon(w, h, ratio);
    }

    private void createHexagon(int w, int h, int ratio) {
        int[] hexX = new int[]{w / 2, w, w, w / 2, 0, 0};
        int[] hexY = new int[]{0, ratio, h - ratio, h, h - ratio, ratio};

        hexagon = new Polygon(hexX, hexY, NUM_POINTS);
    }


    @Override
    protected void paintComponent(Graphics g) {
        setOpaque(false);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(getBackground());
        setBorderPathColor(g);

        g2.setStroke(new BasicStroke(BORDER_WIDTH));
        g.drawPolygon(hexagon);
    }

    private void setBorderPathColor(Graphics g) {
        if (paint) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.black);
        }
    }

    private class HexMouseAdapter extends MouseAdapter{

        public HexMouseAdapter(){
            super();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            paint = true;
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            paint = false;
            repaint();
        }
    }

}