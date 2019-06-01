package MA_CSMI;

import common.PlotableMA;

import java.util.ArrayList;

public class Beta implements PlotableMA {



    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();


    public Beta (int taille, double gauche, double droite){
        for (int i=0;i<taille;i++){
            double alpha = 5; double beta = 5;
            double alpha_g = Math.sqrt(2 * Math.PI * (alpha-1)) * Math.pow(((alpha-1) / Math.E), (alpha-1));
            double beta_g = Math.sqrt(2 * Math.PI * (beta-1)) * Math.pow(((beta-1) / Math.E), (beta-1));
            double ab_g = Math.sqrt(2 * Math.PI * (alpha + beta-1)) * Math.pow(((alpha + beta-1) / Math.E), (alpha + beta-1));


            xs.add(i,gauche + (droite-gauche)/taille*i);
            ys.add(i, Math.pow(xs.get(i),alpha-1)*Math.pow(1-xs.get(i),beta-1)* ( ab_g/(alpha_g * beta_g)));

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
