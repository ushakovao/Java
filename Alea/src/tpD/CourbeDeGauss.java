package tpD;

import common.PlotableMA;

import java.util.ArrayList;

public class CourbeDeGauss implements PlotableMA {



    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();


    public CourbeDeGauss(int taille, double gauche, double droite){
        for (int i=0;i<taille;i++){
            xs.add(i,gauche + (droite-gauche)/taille*i);
            //ys.add(i, Math.exp(-Math.pow(xs.get(i),2)));
            ys.add(i, Math.exp(-0.5*Math.pow(xs.get(i),2))/Math.sqrt(2*Math.PI));
        }
    }


    public ArrayList<Double> getXs(){
        return xs;
    }
    public ArrayList<Double> getYs(){
        return ys;
    }



}