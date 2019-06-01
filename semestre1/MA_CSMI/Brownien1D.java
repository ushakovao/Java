package MA_CSMI;

/**
 * Created by Oxana on 18/11/2015.
 */
import common.MultiPlotNew;
import common.PlotableMA;

import java.util.ArrayList;



public class Brownien1D implements PlotableMA {

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();
    private MyRandom rand = new MyRandom();

    public Brownien1D(double droite, double pas) {

        Xs.add(0.);
        Ys.add(0.);
        int i = 0;
        while (Xs.get(i) < droite) {
            Xs.add(Xs.get(i) + pas);

            Ys.add(Ys.get(i) + Math.sqrt(pas) * rand.nextGaussian());
            i++;
        }
    }

    public ArrayList<Double> getXs() {
        return Xs;
    }

    public ArrayList<Double> getYs() {
        return Ys;
    }

    public static void main(String[] args) {

        MultiPlotNew multiTraceur = new MultiPlotNew();
        Brownien1D brow;

        for (int i = 0; i < 7; i++) {
            brow = new Brownien1D(2, 0.001);
            multiTraceur.addPlotable("brownien " + i,brow);
        }

        multiTraceur.plotNow();

    }

}
