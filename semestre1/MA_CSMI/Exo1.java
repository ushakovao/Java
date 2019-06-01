package MA_CSMI;

import common.MultiPlotNew;

import java.util.ArrayList;

/**
 * Created by Oxana on 12/01/2016.
 */
public class Exo1 {

    public static void main(String[] args) {
        ArrayList<Double> koko = new ArrayList<Double>();
        ArrayList<Double> kiki = new ArrayList<Double>();
        MyRandom r = new MyRandom();
        int n = 100;
        double l = 0.5;
        MyRandom rand = new MyRandom();


        double nbr;
        double cumul = 0;
        for (int i = 0; 1 < n; i++) {

            nbr = rand.nextExp(l);
            koko.add(i,nbr);
           // cumul += koko.get(i);
           // kiki.add(koko.get(i));
        }


        Histogramme histi = new Histogramme(koko);
        histi.setNombreDeBaton(150);
        histi.dresseHistogramme(true);
        MultiPlotNew multiPlot = new MultiPlotNew();
        multiPlot.addPlotable("test", histi);
        multiPlot.setStroke("test", MultiPlotNew.normal);
        multiPlot.addPlotable("Gamma", new Echauffement(1000, 0, 1, l));



        multiPlot.plotNow();

    }
}
