package MA_CSMI;

import common.MultiPlotNew;
import common.PlotableMA;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Oxana on 18/11/2015.
 */
public class BS implements PlotableMA {

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();
    Random rd = new Random();
    MyRandom mrd = new MyRandom();

    // Black-Scholes formula
    public static double callPrice(double S, double X, double r,double d,  double sigma, double T)
    {
        double d1 = (Math.log(S / X) + (r + sigma * sigma / 2) * T) / (sigma * Math.sqrt(T));
        double d2 = d1 - sigma * Math.sqrt(T);
        return  Math.exp(-d * T)*S * MyRandom.Phi(d1) - X * Math.exp(-r * T) * MyRandom.Phi(d2);
    }

    // estimate by Monte Carlo simulation
    public static double call(double S, double X, double r,double d, double sigma, double T )
    {


        int N = 10000;
        double sum = 0.0;
        for (int i = 0; i < N; i++) {


            Random rd = new Random();
            double B=105;
            double eps = rd.nextGaussian();
            double price = S * Math.exp((r-d) * T - 0.5 * Math.pow(sigma, 2)  * T + sigma * eps * Math.sqrt(T));

            if (price < B){
            double value = Math.max(price - X, 0);
            sum += value;}
        }
        double mean = sum / N;
        return Math.exp(-r * T) * mean;}



    public BS(double droite, double S, double X, double r,double d, double sigma, double T,double l,double k) {

        Xs.add(0.);
        Ys.add(S);

        int i = 0;
        while (Xs.get(i) < droite) {
            Xs.add(Xs.get(i) + T);
          // Ys.add(Ys.get(i) + Ys.get(i) * (r - d - l * k) * T + Ys.get(i) * sigma * Math.sqrt(T) * rd.nextGaussian()+Math.sqrt(T)*mrd.nextPoisson(l));
           Ys.add(Ys.get(i) + Ys.get(i) * (r - d ) * T + Ys.get(i) * sigma * Math.sqrt(T) * rd.nextGaussian());

            i++;
        }
    }

    public ArrayList<Double> getXs() {
        return Xs;
    }

    public ArrayList<Double> getYs() {
        return Ys;
    }


    public static void main(String[] args) {

        double S = 100;
        double X=  110;
        double r =0.03;
        double d =0.02;
        double T =0.5;
        double sigma = 0.1;
        double l = 0.5;
        double k = 0.05;
        double droite = 100;
        System.out.println(callPrice(S, X, r, d, sigma, T));
        System.out.println(call(S, X, r, d, sigma, T));

        MultiPlotNew multiTraceur = new MultiPlotNew();
        BS brow;

        for (int i = 0; i < 5; i++) {
            brow = new BS(droite, S,X, r, d, sigma, T, l, k );
            multiTraceur.addPlotable("brownien " + i,brow);
        }

        multiTraceur.plotNow();


    }
    }
