package MA_CSMI;

import common.PlotableMA;

import java.util.ArrayList;

/**
 * Created by Oxana on 13/11/2015.
 */
public class Exp implements PlotableMA {



    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();
    MyRandom r = new MyRandom();

    public Exp (int taille, double gauche, double droite){
        for (int i=0;i<taille;i++){
            double lambda = 1;
            xs.add(i,gauche + (droite-gauche)/taille*i);
            ys.add(i, lambda*Math.exp(-lambda*xs.get(i)));

            //ys.add(i, MyRandom.foncBeta(i,alpha, beta)); ask about
        }
    }


    public ArrayList<Double> getXs(){

        return xs;
    }

    public ArrayList<Double> getYs(){

        return ys;
    }



}


