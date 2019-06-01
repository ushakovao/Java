package SignalsTP1;

import common.PlotableMA;

import java.util.ArrayList;

/**
 * Created by Oxana on 08/02/2016.
 */
public class TrigFourier implements PlotableMA {

    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();

    public double f (double x)
    {
        return( 1/(Math.sqrt(2)) *  Math.cos(x));
    }

    public double f0 (double x)
    {
        return( 1/(Math.sqrt(2)) *  f(x));
    }

    public double f_an (double x)
    {
        return( Math.cos(x)*  f(x));
    }

    public double f_bn (double x)
    {
        return( Math.sin(x)*  f(x));
    }

    ArrayList<Double> answer = new ArrayList<>();

     public ArrayList TrigFourier ( double T, int steps, int n, int down, double up){
         double m = (up - down);
         double h = m/steps;
         double sum_a0 = h * (f0(up) + f0(down));
         double sum_an = h * (f_an(up * n) + f_an(down * n));
         double sum_bn = h * (f_bn(up * n) + f_bn(down * n));
         ArrayList<Double> res = new ArrayList<>();


         for (int k =0; k<steps; k++){
             double x = down + h * k;
             sum_a0 = sum_a0 + f0(x);
         }

         for (int i = 0; i < n; i++) {
            for (int j = 0; j < steps; j++) {
                double x = down + h * i;
                sum_an = ((sum_an + f_an(x * i))*h)* (2/T);
                sum_bn = ((sum_bn + f_bn(x * i))*h)* (2/T);
            }
           res.add(n, sum_an+sum_bn);
         }
         sum_a0 = sum_a0 * h * (2/T);
         sum_an = sum_an * h * (2/T);
         sum_bn = sum_bn * h * (2/T);

        double res1 = 0;

         for (int k =0; k<=n; k++){
             double temp = sum_a0/2;
             res1 = res.get(k)+sum_a0;
             answer.add(k,  res1);
         }
        return answer;
     }


    public void toFonctionF (int taille, double gauche, double droite){
        for (int i=0;i<taille;i++){
            xs.add(i, gauche + (droite - gauche) / taille *i);
            ys.add( i, answer.get(i));
        }
    }



    public ArrayList<Double> getXs(){return xs;}
    public ArrayList<Double> getYs(){return ys;}

}
