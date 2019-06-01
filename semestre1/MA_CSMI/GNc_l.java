package MA_CSMI;

import common.MultiPlotNew;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Oxana on 14/11/2015.
 */
public class GNc_l {

    public static void main(String[] args) {


        ArrayList<Double> koko = new ArrayList<Double>();
        int nbTirages = 200000;
        MyRandom rand = new MyRandom();



        double nbr;
        for (int i = 0; i < nbTirages; i++) {
            nbr = rand.GN_l(500);
            koko.add(i, nbr);
        }



        Histogramme histi = new Histogramme(koko);
        histi.setNombreDeBaton(150);
        // histi.setIntervalle(0.2, 1);
        histi.dresseHistogramme(true);
        MultiPlotNew multiPlot = new MultiPlotNew();

        multiPlot.addPlotable("Lourde(1,0.5) - tirages=200000 - n=500", histi);
        multiPlot.addPlotable("Gauss", new CourbeDeGauss(1000, -4, 4, 0, 1));

        multiPlot.setColor("W(5,2) - tirages=200000 - n=500", Color.blue);
        // multiPlot.addPlotable("Exp", new Exp(1000, -4, 4));
        multiPlot.setColor("Gauss", Color.red);


        //multiPlot.setColor("Identité", Color.blue);
        // multiPlot.addPlotable("Beta", new Beta(100000, 0, 1));
        // multiPlot.setStroke("test", MultiPlot.normal);
        multiPlot.plotNow();

    }




}
