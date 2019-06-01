package MA_CSMI;

import common.MultiPlotNew;

import java.util.ArrayList;

/**
 * Created by Oxana on 13/11/2015.
 */
public class Lourde2Histo {

    public static void main(String[] args) {
        ArrayList<Double> koko = new ArrayList<Double>();
        MyRandom r = new MyRandom();
        double alpha = 10;
        double beta = 0.1;
        int nbTirages = 100000;

        for (int i = 0; i < nbTirages; i++) {
            if (alpha <= 1 || beta < 0 || beta > 1) {
                System.out.println("Wrong Alpha and/or Beta");
                break;
            } else
                koko.add(r.nextLourde2(alpha, beta));
        }
        Histogramme histi = new Histogramme(koko);
        histi.setNombreDeBaton(200);
        // histi.setIntervalle(0.2, 1);
        histi.dresseHistogramme(true);
        MultiPlotNew multiPlot = new MultiPlotNew();
        multiPlot.addPlotable("test", histi);

        //multiPlot.addPlotable("Beta", new Beta(100000, 0, 1));
        multiPlot.setStroke("test", MultiPlotNew.normal);
        multiPlot.plotNow();

    }
}