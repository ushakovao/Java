package MA_CSMI;

import common.MultiPlotNew;
import common.PlotableMA;

import java.util.ArrayList;

/**
 * Created by Oxana on 11/11/2015.
 */

public class MA implements PlotableMA {

        private ArrayList<Double> Xs = new ArrayList<Double>();
        private ArrayList<Double> Ys = new ArrayList<Double>();

        private MyRandom rand = new MyRandom();

        public MA(int nb) {

            Xs.add(0.);
            Ys.add(0.);

            double alpha = 50;
            for (int i = 0; i < nb; i++) {
               // Xs.add(i * 1. / nb);

                Xs.add(i* 1. / nb);
                Ys.add(rand.nextPoisson(0.2) / Math.sqrt(nb));


            }
            }


    public ArrayList<Double> getXs() {
            return Xs;
        }

        public ArrayList<Double> getYs() {
            return Ys;
        }





        public static void main(String[] args) {

            MultiPlotNew multiTraceur = new MultiPlotNew();
            MA marcheAleatoire;

            for (int i = 0; i < 3; i++) {
                marcheAleatoire = new MA(50);
                multiTraceur.addPlotable("2 dim à 5000 " + i,marcheAleatoire);
            }

           multiTraceur.imposeRectangle(-1, -1, 1, 1);

            multiTraceur.plotNow();

        }

    }


