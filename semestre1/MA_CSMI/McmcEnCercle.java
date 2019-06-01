package MA_CSMI;

/**
 * Created by Oxana on 14/12/2015.
 */


import common.MultiPlotNew;

import java.util.ArrayList;
import java.util.Random;

public class McmcEnCercle {

    private ArrayList<Double> pi = new ArrayList<Double>();

    private int etatCourant = 0;

    private int T = 1000;
    private int nbEtat = 8;
    Random rand = new Random();



    public McmcEnCercle() {

        int z=0;
        for (int h =0; h < nbEtat; h++)
            z = z + (h+1)*(h+1);
        for (int i = 0; i < nbEtat; i++)
            pi.add((double) (((i + 1)*(i+1)))/z);
        System.out.println(z);

    }



    public int nextSimulation() {

        etatCourant = rand.nextInt(nbEtat);


        for (int t = 1; t <= T; t++) {

            int aleat = 2 * rand.nextInt(2) - 1;
            int xi = ((etatCourant + aleat + nbEtat) % nbEtat);

            double choix = rand.nextDouble();
            if (choix < Math.min(pi.get(xi) / pi.get(etatCourant), 1))
                etatCourant = xi;
            if(pi.get(etatCourant)==0.)
                etatCourant = xi;
        }
        return etatCourant;
    }


    public static void main(String[] args) {

        McmcEnCercle mcmcEnCercle = new McmcEnCercle();

        ArrayList<Double> donnees = new ArrayList<Double>();

        for (int k = 0; k < 1000; k++) {
            donnees.add((double) mcmcEnCercle.nextSimulation());
        }
        Histogramme hist = new Histogramme(donnees);
        //hist.setUnBatonParentier();
        hist.dresseHistogramme(true);

        MultiPlotNew traceur = new MultiPlotNew();
        traceur.addPlotable("histogramme ",hist);

        traceur.plotNow();

    }

}
