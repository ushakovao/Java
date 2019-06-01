package MA_CSMI;

/**
 * Created by Oxana on 09/10/2015.
 */

import java.awt.Color;
        import java.awt.Dimension;
        import java.awt.Graphics;
        import java.awt.Graphics2D;
        import java.util.Random;

        import javax.swing.JFrame;
        import javax.swing.JPanel;

    public class Line extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.red);

        for (int i = 0; i <= 100000; i++) {
            Dimension size = getSize();
            int w1 = size.width ;
            int h1 = size.height;

            Random r = new Random();
            int x = Math.abs(r.nextInt()) % w1;
            int y = Math.abs(r.nextInt()) % h1;
            g2d.drawLine(x, y, x, y);
        }
    }

    public static void main(String[] args) {
        Points points = new Points();
        JFrame frame = new JFrame("Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(points);
        frame.setSize(250, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

