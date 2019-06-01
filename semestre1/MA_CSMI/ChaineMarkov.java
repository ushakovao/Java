package MA_CSMI;

/**
 * Created by Oxana on 02/12/2015.
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/*


public class ChaineMarkov implements PlotableMA {

    private Graphe graphe;
    private Etat pointDepart;
    private Random rand = new Random();
    public int[] tempPasse;

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();

    public ArrayList<Double> getXs() {
        return Xs;
    }

    public ArrayList<Double> getYs() {
        return Ys;
    }

    public ChaineMarkov(Graphe graph, int pointDepar, int n) {
        graphe = graph;
        pointDepart = graphe.etats.get(pointDepar);
        tempPasse = new int[n];
        for(int j=0; j<n; j++){
            tempPasse[j]=0;
        }
    }

    public double[] calculPiNouveau(double[] Pi0){
        double[] res = new double[Pi0.length];
        double tmp=0;

        for(int i=0; i<Pi0.length ; i++){
            tmp=0;
            for(int j=0; j< Pi0.length; j++){
                tmp=tmp+Pi0[j]*graphe.matriceTransition[j][i];
            }
            res[i]=tmp;
        }
        return res;
    }

    public double[] calculPiN(double[] Pi0, int n){
        double[] res = new double[Pi0.length];
        res=Pi0;
        for(int i=0; i<n; i++){
            res=calculPiNouveau(res);
        }
        return res;
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
            tempPasse[etatActuel.Id]++;
        }
    }




    public static void main(String[] args) {
        int n = 6;

        ArrayList<Integer> a = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();
        MyRandom r = new MyRandom();
        int nbvilles = r.nextInt(n);
        Graphe test = new Graphe(nbvilles, nbvilles);
       /* for (int i=0; i<nbvilles; i++){
            int nbra = r.nextInt(n);
            a.add(nbra);
        }
        for (int j=0; j<nbvilles; j++){
            int nbrb = r.nextInt(n);
            b.add(nbrb);
        }
        //les fleches entre toutes les villes
         for (int k=0; k<nbvilles; k++) {
             test.ajouteFleche(a.get(k), b.get(k));
         }

        //les fleches à àleatoire*/

  /*      for (int i=1; i<= n; i++)


        Graphe graphe = new Graphe(3,5);

        graphe.ajouteFleche(0, 1);
        graphe.ajouteFleche(0, 2, 2.);
        graphe.ajouteFleche(1, 2);
        graphe.ajouteFleche(2, 1);










        test.calculProbaDeTransition();
        ChaineMarkov CM = new ChaineMarkov(test, 0, n);

        CM.simuleTrajectoire(5);

        MultiPlotNewMA trac = new MultiPlotNewMA();
        trac.addPlotable("chaine de Markov",CM);
        trac.plotNow();


    }
}
*/