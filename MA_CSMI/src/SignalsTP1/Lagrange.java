package SignalsTP1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oxana on 08/02/2016.
 */
public class Lagrange {
    public double f (double x)
    {
        return (Math.tan(x));
    }

   public static double Lagrange(double x, ArrayList<Double> points, ArrayList<Double> values){

       double temp = 1;double ans = 0;

       for (int i=0; i< points.size(); i++){
           for (int j=0; j<points.size(); j++){
               if ( i != j){
                   double c= (x-points.get(j))/(points.get(i)-points.get(j));
                   temp = temp*c*values.get(i);
               }
           }
           ans = ans + temp;
       }
       return ans;
   }

   public static void main(String[] args) {

        ArrayList<Double> x_es = new ArrayList<Double>();
        x_es.add(-1.5);
        x_es.add(-0.75);
        x_es.add(0.);
        x_es.add(0.45);
        x_es.add(1.5);

        ArrayList<Double> y_es = new ArrayList<Double>();
        y_es.add(-14.1014);
        y_es.add(-0.931596);
        y_es.add(0.);
        y_es.add(0.931596);
        y_es.add(14.1014);

       double res = Lagrange(1.1, x_es, y_es);


       System.out.println(res);

   }


}


