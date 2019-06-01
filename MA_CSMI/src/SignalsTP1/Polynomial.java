package SignalsTP1;


import common.PlotableMA;

import java.util.ArrayList;

/**
 * Created by Oxana on 30/01/2016.
 */
public class Polynomial implements PlotableMA {


    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();




    private double[] coef;  // coefficients
    private int pow;     // degree of polynomial (0 for the zero polynomial)

    public Polynomial(double a, int b) {
        coef = new double[b+1];
        coef[b] = a;
        pow = degree();
    }

    // return the degree of this polynomial (0 for the zero polynomial)
    public int degree() {
        int d = 0;
        for (int i = 0; i < coef.length; i++)
            if (coef[i] != 0) d = i;
        return d;
    }



    public String toString() {
        if (pow ==  0) return "" + coef[0];
        if (pow ==  1) return coef[1] + "x + " + coef[0];
        String s = coef[pow] + "x^" + pow;
        for (int i = pow-1; i >= 0; i--) {
            if      (coef[i] == 0) continue;
            else if (coef[i]  > 0) s = s + " + " + ( coef[i]);
            else if (coef[i]  < 0) s = s + " - " + (-coef[i]);
            if      (i == 1) s = s + "x";
            else if (i >  1) s = s + "x^" + i;
        }
        return s;
    }


    // return c = a + b
    public Polynomial addPols(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, Math.max(a.pow, b.pow));
        for (int i = 0; i <= a.pow; i++) c.coef[i] += a.coef[i];
        for (int i = 0; i <= b.pow; i++) c.coef[i] += b.coef[i];
        c.pow = c.degree();
        return c;
    }

    // return (a - b)
    public Polynomial minus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, Math.max(a.pow, b.pow));
        for (int i = 0; i <= a.pow; i++) {
            c.coef[i] += a.coef[i];
        }
        for (int i = 0; i <= b.pow; i++){
            c.coef[i] -= b.coef[i];
        }
        c.pow = c.degree();
        return c;
    }


    public Polynomial times(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, a.pow + b.pow);
        for (int i = 0; i <= a.pow; i++)
            for (int j = 0; j <= b.pow; j++)
                c.coef[i+j] += (a.coef[i] * b.coef[j]);
        c.pow = c.degree();
        return c;
    }

  /*  public Polynomial divide (Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, Math.abs(a.pow - b.pow));
        for (int i = 0; i <= a.pow; i++)
            for (int j = 0; j <= b.pow; j++)
                c.coef[i+j] += (a.coef[i]/ b.coef[j]);
        c.pow = c.degree();
        return c;
    }*/


    public Polynomial per_number(double nb) {
        Polynomial a = this;
        Polynomial pol = new Polynomial(0,0);
        for (int i = pow; i >= 0; i--) {
            coef[i] = coef[i] * nb;
            Polynomial term = new Polynomial(coef[i], i);
            if (coef[i] != 0){
                pol = pol.addPols(term);
            }

        }
        return pol;
    }

    public Polynomial per_number_div(double nb) {
        Polynomial a = this;
        Polynomial pol = new Polynomial(0,0);
        Polynomial f = new Polynomial(0,0);
       // System.out.print("ee__"+f+"____");
        for (int i = pow; i >= 0; i--) {
            coef[i] = coef[i] / nb;
            Polynomial term = new Polynomial(coef[i], i);
            if (coef[i] != 0){
                pol = pol.addPols(term);
            }
        }
        return pol;
    }





    //integrate
    public double integrate ( int a, int b, int n){
        double c= (b - a);
        double h = c/n;
        double sum = h * (evaluate(b) + evaluate(a));
        for (int i = 1; i < n; i++) {
            double x = a + h * i;
            sum = sum + evaluate(x);
        }
        return sum * h;
    }

    public double dot(Polynomial b, int down, int up, int n ) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, a.pow + b.pow);
        for (int i = 0; i <= a.pow; i++) {
            for (int j = 0; j <= b.pow; j++) {
                c.coef[i + j] += (a.coef[i] * b.coef[j]);
                c.pow = c.degree();
            }
        }
        double m = (up - down);
        double h = m / n;
        double sum = h * (c.evaluate(up) + evaluate(down));

        for (int i = 1; i < n; i++) {
            double x = down + h * i;
            sum = sum + evaluate(x);
        }
        return sum * h;
    }


    public double f (double x, int n){
        return Math.pow(x, n);
    }

    public double g(double x){
        return Math.exp(-x);
    }

    public double fg (double x, int n ){
        return (f(x,n)*g(x));
    }


    public double integrate_func (int a, int b, int step, int n){
        double c = (b - a);
        double h = c/step;
        double sum = 0.5 * (fg(b,n) + fg(a, n));
        for (int i = 1; i < step; i++) {
            double x = a + h * i;
            sum = sum + fg(x, n);
        }

        return sum * h;
    }

    //dot product



    public double evaluate(double x) {
        double b = 0;
        for (int i = pow; i >= 0; i--)
            b = coef[i] + (x * b);
        return b;
    }

    public  ArrayList  Legendre ()
    {
        Polynomial legendre ;
        Polynomial X = new Polynomial(1, 1);
        Polynomial temp;
        ArrayList<Polynomial> polys = new ArrayList<>();
        for (int j=0; j <= pow; j++) {

            if (j == 0) {
                legendre = new Polynomial(1, 0);
                polys.add(j, legendre);
            }
            else if (j == 1) {
                legendre = new Polynomial(1, 1);

                 polys.add(j, legendre);
            }
            else {
                    Polynomial q1 = polys.get(j - 1).times(X).per_number(2 * j - 1);
                    Polynomial q2 = polys.get(j - 2).per_number(j - 1);
                    temp = q1.minus(q2);
                    legendre = temp.per_number_div(j);
                    polys.add(j, legendre);
            }
        }
        return polys;
    }



    public Polynomial deriv() {
        if (pow == 0) return new Polynomial(0,0);
        Polynomial deriv = new Polynomial(0, pow - 1);
        deriv.pow = pow - 1;
        for (int i = 0; i < pow; i++)
            deriv.coef[i] = (i + 1) * coef[i + 1];
        return deriv;
    }



    public void toFonction (int taille, double gauche, double droite){
        for (int i=0;i<taille;i++){
            xs.add(i, gauche + (droite-gauche)/taille*i);
            ys.add( i, (evaluate(xs.get(i))));
        }
    }

    public ArrayList<Double> getXs(){return xs;}
    public ArrayList<Double> getYs(){return ys;}


  /*  public static void main(String[] args) {
        Polynomial zero = new Polynomial(0, 0);


        Polynomial p1 = new Polynomial(1, 2);

        Polynomial p2   = new Polynomial(1, 3);

        Polynomial q1   = new Polynomial(-1, 1);
        Polynomial q2   = new Polynomial(1, 0);
        Polynomial q3 = new Polynomial(2, 4);

        Polynomial p3    = q1.add(q2).add(q3);

        System.out.println("p1(x) =  " + p1);
        System.out.println("p2(x) =  " + p2);
        System.out.println("p3(x) =  " + p3);
        System.out.println("p3'(x) =  " +p3.differentiate());

        System.out.println("Evaluate p1 in -4:  "+p1.evaluate(-4));
        System.out.println("Evaluate p3 in  2:  " +p3.evaluate(2));

        }

*/


/*
        Polynomial r    = p.add(q);
        Polynomial s    = p.times(q);
        Polynomial t    = p.compose(q);*/

       /* ArrayList<Double> sca = new ArrayList<Double>();
        for (int i=0; i< 5; i++) {
            sca.add(i, (double) s.evaluate(i));
        }*/
/*
        System.out.println("Oxana000" + p.integrate_func(-1, 1, 10,3));
        System.out.println("Oxana" + p.integrate(s, -1, 1, 10));
        System.out.println("zero(x) =     " + zero);
        System.out.println("p(x) =        " + p);
        System.out.println("q(x) =        " + q);
        System.out.println("p(x) + q(x) = " + r);
        System.out.println("p(x) * q(x) = " + s);
        System.out.println("Horner : p(q(x))     = " + t);
        System.out.println("0 - p(x)    = " + zero.minus(p));
        System.out.println("p(3)        = " + p.evaluate(3));
        System.out.println("p'(x)       = " + p.differentiate());
        System.out.println("p''(x)      = " + p.differentiate().differentiate());

*/


    }

/*
    // return a(b(x))  - compute using Horner's method
    public Polynomial compose(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(0, 0);
        for (int i = a.pow; i >= 0; i--) {
            Polynomial term = new Polynomial(a.coef[i], 0);
            c = term.add(b.times(c));
        }
        return c;
    }
*/
