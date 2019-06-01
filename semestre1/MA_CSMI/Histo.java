
package MA_CSMI;
import common.PlotableMA;

import java.util.ArrayList;
import java.util.List;




public class Histo implements PlotableMA {

    Integer nbBaton;

    ArrayList<Double> xs = new ArrayList<Double>();
    ArrayList<Double> ys = new ArrayList<Double>();
    double[]  data ;


    public Histo(double[] data) {


        double begin_a = 0;
        nbBaton = 7;
        int nb_int = nbBaton;
        double end_a = begin_a + nb_int;
        double intervalValue = (end_a - begin_a) / nb_int;
        double beginOfTheInterval = begin_a;
        double endOfTheInterval = begin_a + intervalValue;
        List<Double> Nb_Points = new ArrayList<Double>();

        for (int i = 0; i < nb_int; i++) {
            Nb_Points.add(i, 0.0);
            for (int j = 0, counter = 0; j < data.length; j++) {
                if (beginOfTheInterval <= data[j] && data[j] <=
                        endOfTheInterval) {
                    Nb_Points.set(i, (double) ++counter);
                }
            }
            beginOfTheInterval += intervalValue;
            endOfTheInterval += intervalValue;
        }
        double x1=0, x2=0, y1=0, y2=0;
        for (int i =0; i < Nb_Points.size(); i++ ) {
            x1 = intervalValue*(i);
            x2 = intervalValue*(i+1);
            y1 = Nb_Points.get(i);
            y2 = Nb_Points.get(i);


        }
    }



   public ArrayList<Double> getXs(){
        return xs;
    }

   public ArrayList<Double> getYs(){
        return ys;
    }

}