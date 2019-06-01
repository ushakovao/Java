package MA_CSMI;

import common.MultiPlotNew;

import java.util.ArrayList;

/**
 * Created by Oxana on 08/11/2015.
 */
public class Lourde1Histo {

    public static void main(String[] args) {


        ArrayList<Double> koko = new ArrayList<Double>();
        int nbTirages = 50000;
        MyRandom rand = new MyRandom();
        double nbr;

        for (int i = 0; i < nbTirages; i++) {
            nbr = rand.nextLourde1(5);
            koko.add(i, nbr);
        }




        Histogramme histi = new Histogramme(koko);
        histi.setNombreDeBaton(150);
        // histi.setIntervalle(0.2, 1);
        histi.dresseHistogramme(true);
        MultiPlotNew multiPlot = new MultiPlotNew();
        multiPlot.addPlotable("test", histi);

        //multiPlot.addPlotable("Beta", new Beta(100000, 0, 1));
        multiPlot.setStroke("test", MultiPlotNew.normal);
        multiPlot.plotNow();



    }
}



