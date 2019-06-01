package MA_CSMI;

import common.MultiPlotNew;

import java.util.ArrayList;


import java.util.List;


public class Brownien3D implements PlotableColored {

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();
    private ArrayList<Double> colors = new ArrayList<Double>();
    private MyRandom rand = new MyRandom();

    public Brownien3D(double droite, double pas) {
        Xs.add(0.);
        Ys.add(0.);
        colors.add(0.);
        int i = 0;
        while (Xs.get(i) < droite) {
            double g = rand.nextGaussian();
            Xs.add(Xs.get(i) + Math.sqrt(pas) * rand.nextGaussian());
            Ys.add(Ys.get(i) + Math.sqrt(pas) * rand.nextGaussian());
            colors.add(i + Math.sqrt(pas) * rand.nextGaussian());

            i++;
        }

    }
    public List<Double> colors() {
        return colors;
    }
    public List<Double> getXs() {
        return Xs;
    }


    public List<Double> getYs() {
        return Ys;
    }


        public static void main(String[] args) {

            MultiPlotNew multiTraceur = new MultiPlotNew();
            Brownien3D brow;

            for (int i = 0; i < 1; i++) {
                brow = new Brownien3D(2, 0.001);
                multiTraceur.addPlotable("brownien " + i,brow);
            }

            multiTraceur.plotNow();

        }



}



