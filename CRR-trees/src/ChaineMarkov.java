
/**
 * Created by Oxana on 02/12/2015.
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;



public class ChaineMarkov implements PlotableMA {

    private Graphe graphe;
    private Etat pointDepart;
    private Random rand = new Random();

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();

    public ArrayList<Double> getXs() {
        return Xs;
    }

    public ArrayList<Double> getYs() {
        return Ys;
    }

    public ChaineMarkov(Graphe graph, int pointDepar) {
        graphe = graph;
        pointDepart = graphe.etats.get(pointDepar);
    }

    private Etat prochainEtat(Etat etatActuel) {

        if (etatActuel.sorties.size() == 0)
            return etatActuel;
        else {
            int i = 0;
            double cumul = etatActuel.sorties.get(0).proba;
            double unReel = rand.nextDouble();

            while (unReel > cumul) {
                i++;
                cumul = cumul + etatActuel.sorties.get(i).proba;
            }
            return etatActuel.sorties.get(i).arrivee;
        }

    }

    public void simuleTrajectoire(int Tmax) {

        Etat etatActuel = pointDepart;
        Xs.add(0.);
        Ys.add(pointDepart.Id * 1.);

        for (int t = 1; t <= Tmax; t++) {
            etatActuel = prochainEtat(etatActuel);
            Xs.add(t * 1.);
            Ys.add(etatActuel.Id * 1.);
            System.out.print(etatActuel.Id + ",");
        }
    }

    public static void main(String[] args) {

        Graphe graph = new Graphe(3);

        graph.ajouteFleche(0, 1);
        graph.ajouteFleche(0, 2);
        graph.ajouteFleche(1, 2);
        graph.ajouteFleche(2, 1);
        graph.ajouteFleche(2, 0);

        graph.calculProbaDeTransition();

        ChaineMarkov CM = new ChaineMarkov(graph, 0);

        CM.simuleTrajectoire(30);

        MultiPlotNew trac = new MultiPlotNew();
        trac.addPlotable("chaine de Markov",CM);
        trac.plotNow();

    }

}