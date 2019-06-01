package MA_CSMI;

import common.PlotableMA;

import java.util.ArrayList;

/**
 * Created by Oxana on 14/12/2015.
 */
public class Brd implements PlotableMA {


    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();


    public Brd(double T, double etatCourant) {

        Xs.add(0.);
        Ys.add(0.);
        int i = 0;
        while (Xs.get(i) < T) {
            Xs.add(i,(double) etatCourant);

            Ys.add(Ys.get(i)+ 0.1);
            i++;
        }
    }

    public ArrayList<Double> getXs() {
        return Xs;
    }

    public ArrayList<Double> getYs() {
        return Ys;
    }
}
