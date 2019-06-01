
package MA_CSMI;

/**
 * Created by Oxana on 16/10/2015.
 */
import common.PlotableMA;

import java.util.ArrayList;

    /**
     * Created by ushakova on 13/10/2015.
     */
    public class Parabo implements PlotableMA {

        private ArrayList<Double> xs  = new ArrayList<Double>();
        private ArrayList<Double> ys  = new ArrayList<Double>();

        public Parabo (int taille, double gauche, double droite){
            for (int i=0;i<taille;i++){
                xs.add(gauche + (droite-gauche)/taille*i);
                ys.add( (xs.get(i)) *(xs.get(i)) );
            }
        }


        public ArrayList<Double> getXs(){
            return xs;
        }

        public ArrayList<Double> getYs(){return ys; }

    }
