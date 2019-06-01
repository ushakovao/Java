package tpD;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Le même programme que Traceur1, mais nous passons en paramêtre
 * les listes des abscisses et des ordonnées au moyen de deux champs publics.
 *
 */
public class Traceur2 extends JFrame {

    public int abs[];
    public int ord[];

    /*
     * Le constructeur prend comme argument les abscisses et les ordonnées
     */


//On cherche le max parmi touts les Abs et Ords
    public int findMax (int[] liste){
        int max = liste[0];
        for (int i=0; i< liste.length; i++){
            if (liste[i] > max) {
                max = liste[i];
            }
        }
        return max;
    }

    public Traceur2(int[] x, int[] y) {

        abs = x;
        ord = y;

        this.setSize(findMax(x)+20, findMax(y)+20);
        //this.setSize(300, 200);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new Panneau());
        this.setVisible(true);
    }


    private class Panneau extends JPanel {

        public void paintComponent(Graphics g) {
            g.drawPolyline(abs, ord, abs.length);
        }
    }

    /*
     * Programme test
     */
    public static void main(String[] args) {

        int[] x = { 80, 10, 200, 200 };
        int[] y = { 100, 0, 100, 50 };

        new Traceur2(x, y);
    }

}