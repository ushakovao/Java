package tpD;

import common.PlotableMA;


import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Jusqu'à présent, nos traceurs prenaient en entrée des listes d'abscisses et d'ordonnées
 * entières. Ces entiers représentaient des pixels.
 *
 *  Le but final est de tracer des graphes de fonctions mathématiques.
 *  Mais informatiquement, on stocke une fonction sous la forme de deux tableaux de réel :
 *  Les abscisses sont espacés régulièrement entre deux bornes (la gauche et la droite).
 *  Les ordonnées sont l'évalutation des abscisses par la fonction mathématique.
 *  Dans un autre fichier nous créons la classe Fonction qui sera l'argument de notre
 *  traceur.
 *
 */
public class Traceur3 extends JFrame {

    // Les entrées
    public PlotableMA fonc;

    // autre
    private int abs[];
    private int ord[];
    private int taille;

    // constructeur
    public Traceur3(PlotableMA f) {

        fonc = f;
        taille = fonc.getXs().size();
        setSize(300, 300);
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new Panneau());
        this.setBackground(Color.white);

        setVisible(true);
    }

    /*
     * La seule difficulté est de convertir nos abscisses/ordonnées réelles, en
     * abscisses ordonnées entière représentant des pixels.
     */
    public void convertiEnPixel(int windowW, int windowH) {

        abs = new int[taille];
        ord = new int[taille];

        double maxX = maximum(fonc.getXs());
        double minX = minimum(fonc.getXs());
        double maxY = maximum(fonc.getYs());
        double minY = minimum(fonc.getYs());

        double amplitudeX = maxX - minX;
        double amplitudeY = maxY - minY;

        for (int i = 0; i < taille; i++) {
            abs[i] = (int) ((fonc.getXs().get(i) - minX) / amplitudeX * windowW);
            ord[i] = windowH
                    - (int) ((fonc.getYs().get(i) - minY) / amplitudeY * windowH);
        }

    }

    private class Panneau extends JPanel {
        public void paintComponent(Graphics g) {
            convertiEnPixel(this.getWidth(), this.getHeight());
            g.drawPolyline(abs, ord, abs.length);
        }
    }

    private double maximum(List<Double> tab) {

        double theMax = tab.get(0);
        for (int i = 1; i < tab.size(); i++) {
            if (theMax < tab.get(i)) {
                theMax = tab.get(i);
            }
        }
        return (theMax);
    }

    private double minimum(List<Double> tab) {

        double theMin = tab.get(0);
        for (int i = 1; i < tab.size(); i++) {
            if (theMin > tab.get(i)) {
                theMin = tab.get(i);
            }
        }
        return (theMin);
    }

    public static void main(String[] args) {

        // Deux tracés, chacun dans une fenêtre différentes.
        // Les deux fenêtres sont de même taille et superposées
        		/*
		 * J'aurais pu aussi écrire : FonctionId id=new FonctionId(100,-1,1);
		 *
		 * Le fait qu'un pointeur puisse avoir plusieurs types s'appelle : le
		 * "polymorphisme"
		 */


        //PlotableMA id = new FonctionIdByPoints(100, -1, 1);
        //new Traceur3(id);

        PlotableMA gauss = new CourbeDeGauss(100, -2, 2);
        new Traceur3(gauss);

        PlotableMA idd = new FonctionId(100, -1, 1);
        new Traceur3(idd);

        PlotableMA parabo = new Parabole(100, -2, 2);
        new Traceur3(parabo);


    }

}