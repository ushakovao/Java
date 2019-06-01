package MA_CSMI;

/**
 * Created by Oxana on 18/11/2015.
 */
import common.MultiPlotNew;
import common.PlotableMA;

import java.util.ArrayList;


public class Brownien2D implements PlotableMA {

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();
    private ArrayList<Double> colors = new ArrayList<Double>();
    private MyRandom rand = new MyRandom();

    public Brownien2D(double droite, double pas) {

        Xs.add(0.);
        Ys.add(0.);
        int i = 0;
        while (Xs.get(i) < droite) {

            double z = rand.nextPoisson(0.001);

            Xs.add(Xs.get(i) + 0.04*Math.sqrt(pas) * rand.nextGaussian());

            Ys.add(Ys.get(i) + 0.002+ Math.sqrt(pas) * rand.nextGaussian());


        i++;
    }
    }

    public ArrayList<Double> getXs() {
        return Xs;
    }

    public ArrayList<Double> getYs() {
        return Ys;
    }

    public ArrayList<Double> getColor() {
        return Ys;
    }

    public static void main(String[] args) {

        MultiPlotNew multiTraceur = new MultiPlotNew();
        Brownien2D brow;

        for (int i = 0; i < 1; i++) {
            brow = new Brownien2D(2, 0.0001);
            multiTraceur.addPlotable("brownien " + i,brow);
        }

        multiTraceur.plotNow();

    }

}

