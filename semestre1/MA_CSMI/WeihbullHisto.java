package MA_CSMI;

import common.MultiPlotNew;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Oxana on 07/11/2015.
 */
public class WeihbullHisto {

    public static void main(String[] args) {


        ArrayList<Double> koko = new ArrayList<Double>();
        ArrayList<Double> kiki = new ArrayList<Double>();
        int nbTirages = 1000; double l = 1.;
        MyRandom rand = new MyRandom();
        double nbr; double cumul = 0;

        for (int i = 0; i < nbTirages; i++) {

            nbr = rand.nextExp(l);
            koko.add(i,nbr);
            cumul += koko.get(i);
            kiki.add(i,cumul);

        }




        Histogramme histi = new Histogramme(koko);
   //     Histogramme histi2 = new Histogramme(kiki);
        histi.setNombreDeBaton(150);
        histi.dresseHistogramme(true);
        //histi2.dresseHistogramme(true);
        MultiPlotNew multiPlot = new MultiPlotNew();
        multiPlot.addPlotable("Exp", histi);
      //  multiPlot.addPlotable("Gamma", histi2);
        multiPlot.addPlotable("Gammad", new Echauffement(2000, -10, 10, l));
       // histi.setIntervalle(0, 1);
        multiPlot.setColor("Exp", Color.red);
      //  multiPlot.setColor("Gamma", Color.blue);
        multiPlot.plotNow();

    }
}