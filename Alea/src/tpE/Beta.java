package tpE;

import common.PlotableMA;
import tpB.MyRandom;

import java.util.ArrayList;

/**
 * Created by ushakova on 17/10/16.
 */
public class Beta implements PlotableMA {



    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();

    MyRandom r=new MyRandom();
    public Beta(int taille, double gauche, double droite, double alpha, double beta){
        for (int i=0;i<taille;i++){
        double alpha_g = Math.sqrt(2 * Math.PI * (alpha-1)) * Math.pow(((alpha-1) / Math.E), (alpha-1));
        double beta_g = Math.sqrt(2 * Math.PI * (beta-1)) * Math.pow(((beta-1) / Math.E), (beta-1));
        double ab_g = Math.sqrt(2 * Math.PI * (alpha + beta-1)) * Math.pow(((alpha + beta-1) / Math.E), (alpha + beta-1));


        xs.add(i,gauche + (droite-gauche)/taille*i);
        ys.add(i, Math.pow(xs.get(i),alpha-1)*Math.pow(1-xs.get(i),beta-1)* ( ab_g/(alpha_g * beta_g)));


        }
    }












    public ArrayList<Double> getXs(){
        return xs;
    }
    public ArrayList<Double> getYs(){
        return ys;
    }

}