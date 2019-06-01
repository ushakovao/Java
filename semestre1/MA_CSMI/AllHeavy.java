package MA_CSMI;

import common.MultiPlotNew;

import java.util.ArrayList;

/**
 * Created by Oxana on 13/11/2015.
 */
public class AllHeavy {


    public static void main(String[] args) {


        ArrayList<Double> koko = new ArrayList<Double>();
        int nbTirages = 50000;
        MyRandom rand = new MyRandom();
        double nbr; double echelle=2; double forme=2;

        for (int i = 0; i < nbTirages; i++) {
            nbr = rand.nextWeibull(echelle,forme);
            koko.add(i, nbr);
        }
        double c1=1; double c2 = 1/forme;
        for ( int i=1; i <= forme; i++)
            c2 = 1/forme;
            c1=c1*c2;

        double mu; double var;
        mu = echelle*rand.st_gamma(1+1/forme);
        var = (echelle*echelle)*rand.st_gamma(1+2/forme)-(mu*mu);




        Histogramme histi = new Histogramme(koko);
        histi.setNombreDeBaton(150);
        // histi.setIntervalle(0.2, 1);
        histi.dresseHistogramme(true);
        MultiPlotNew multiPlot = new MultiPlotNew();
        multiPlot.addPlotable("test", histi);
        //multiPlot.addPlotable("Weibull", new Beta(100000, 0, 1));
        multiPlot.addPlotable("Gauss", new CourbeDeGauss(100000, -2, 6, mu, var));
        multiPlot.setStroke("test", MultiPlotNew.normal);
        multiPlot.plotNow();



    }
}
