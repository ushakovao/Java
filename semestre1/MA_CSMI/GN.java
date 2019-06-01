package MA_CSMI;

import common.PlotableMA;

import java.util.ArrayList;

/**
 * Created by Oxana on 08/11/2015.
 */
public class GN implements PlotableMA {
    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();
MyRandom rand = new MyRandom ();
    public GN (int taille){
        for (int i=0;i<taille;i++){
            double cumul = 0;
            xs.add(i, rand.nextDouble());
            cumul += xs.get(i);
            ys.add(i, cumul/i);
        }
    }
    public ArrayList<Double> getXs(){
        return xs;
    }
    public ArrayList<Double> getYs(){
        return ys;
    }




}


