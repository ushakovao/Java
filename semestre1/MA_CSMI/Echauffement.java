package MA_CSMI;

import common.PlotableMA;

import java.util.ArrayList;


public class Echauffement implements PlotableMA {

    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();

    MyRandom rand = new MyRandom ();

    public Echauffement  (int taille, double gauche, double droite, double l) {



        double nbr; double res;
        double sum = 0;
        for (int i=0;i<taille;i++){
            double cumul = 0;
            xs.add(i,gauche + (droite-gauche)/taille*i);
            nbr = rand.nextExp(l);
            cumul += xs.get(i);
            ys.add(i,  Math.sqrt(2*Math.PI/cumul)*Math.pow((cumul/Math.E),cumul));
        }
    }

        public ArrayList<Double> getXs () {
            return xs;
        }

        public ArrayList<Double> getYs () {
            return ys;
        }


    }