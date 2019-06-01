package MA_CSMI;

import common.MultiPlotNew;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Oxana on 08/11/2015.
 */
public class GNc {

    public static void main(String[] args) {


        ArrayList<Double> koko = new ArrayList<Double>();
        int nbTirages = 200000;
        MyRandom rand = new MyRandom();
        double echelle=5; double forme=2;


        double mu; double var;
        mu = echelle*rand.st_gamma(1+1./forme);
        var = (echelle*echelle)*rand.st_gamma(1+2./forme)-(mu*mu);


        double nbr;
        for (int i = 0; i < nbTirages; i++) {
            nbr = rand.GN(500,mu, var);
            koko.add(i, nbr);
        }


        double res=0;
        for (int i = 0; i < koko.size(); i++) {
                res += koko.get(i);
        }
        System.out.print(res / koko.size());
        System.out.println("666666666" + mu);



        Histogramme histi = new Histogramme(koko);
        histi.setNombreDeBaton(150);
        // histi.setIntervalle(0.2, 1);
        histi.dresseHistogramme(true);
        MultiPlotNew multiPlot = new MultiPlotNew();

        multiPlot.addPlotable("W(5,2) - tirages=200000 - n=500", histi);

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
