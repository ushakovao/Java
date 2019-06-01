package tpE;

import common.Histo;
import common.MultiPlotA;
import tpB.MyRandom;

import java.util.ArrayList;

/**
 * Created by ushakova on 17/10/16.
 */
public class Testing {
    public static void main(String[] args) {

        ArrayList<Double> donnees = new ArrayList<>();

        double alpha=15;
        double beta=2;

        MyRandom r = new MyRandom();
        int nbTirage = 10000;
        int nbbaton = 100;
        for (int i = 0; i < nbTirage; i++) {
            donnees.add(i, r.nextBeta(alpha, beta));
        }


        Histo H = new Histo(donnees);
        H.setNombreDeBaton(nbbaton);
        H.setIntervalle(0, 1);
        H.dresseHistogramme(true);

        MultiPlotA multiPlot = new MultiPlotA();
      //  multiPlot.addPlotable("Histo avec " +alpha + "  et beta  "+beta, H);
        multiPlot.addPlotable("Courbe", new Beta(100, 0, 1,alpha, beta));
        multiPlot.setStroke("Courbe", MultiPlotA.thick);
        multiPlot.plotNow();

    }
}
