package MA_CSMI;

import common.PlotableMA;

import java.util.ArrayList;

/**
 * Created by Oxana on 07/11/2015.
 */
public class Weibull implements PlotableMA {



    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();


    public Weibull (int taille, double gauche, double droite, double echelle, double forme){
        for (int i=0;i<taille;i++){


            xs.add(i,gauche + (droite-gauche)/taille*i);
            //ys.add(i, 1-Math.pow(Math.E,Math.pow(-xs.get(i)/echelle,forme)));
            ys.add(i, echelle*(Math.pow((-Math.log(1-xs.get(i))),(1./forme))));

        }
    }


    public ArrayList<Double> getXs(){

        return xs;
    }

    public ArrayList<Double> getYs(){

        return ys;
    }



}

