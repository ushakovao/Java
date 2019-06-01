package SignalsTP1;

import common.Histogramme;
import common.MultiPlotNew;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oxana on 30/01/2016.
 */
public class Main {
    public static void main(String[] args) {

        Polynomial p1 = new Polynomial(1,1);
        Polynomial p2 = new Polynomial(30, 2);
        Polynomial p3 = new Polynomial(4, 3);
        Polynomial s = p1.addPols(p2);
        Polynomial t = p1.times(p2);
        Polynomial h = p3.per_number(5);


        Polynomial q1 = new Polynomial(1, 0);
        Polynomial q2 = new Polynomial(1, 1);
        Polynomial q3 = new Polynomial(1, 2);
        Polynomial q4 = new Polynomial(1, 3);
        Polynomial q5 = new Polynomial(1, 4);
        Polynomial q6 = new Polynomial(1, 5);
        Polynomial q7 = new Polynomial(1, 6);
        Polynomial q8 = new Polynomial(1, 7);
        Polynomial q9 = new Polynomial(1, 8);

        MultiPlotNew l = new MultiPlotNew();
        p1.toFonction(100, -50, 50);
        p2.toFonction(100, -50, 50);
        t.toFonction(100, -50, 50);
        l.addPlotable("p1=1x^1", p1);
        l.addPlotable("p2=30x^2", p2);
       l.addPlotable("p1 * p2", t);
        l.plotNow();
        TrigFourier hh = new TrigFourier();




        System.out.println("p1(x) =  " + p1);
        System.out.println("p2(x) =  " + p2);
        System.out.println("p1(x) + p2(x) = " + s);
        System.out.println("p1(x) * p2(x) = " + t);
        System.out.println("(4x^3) * 5 = " + h);
        System.out.println("p1 - p2  = " + p1.minus(p2));
        System.out.println("p1(3)  = " + p1.evaluate(3));
        System.out.println("p'(x)  = " + p1.deriv());
        System.out.println("<p1,p2>  = " + p1.dot(p2, -1, 1, 100));



      //  ArrayList s = p1.Legendre();
      //  ArrayList d = p3.Legendre();


        MultiPlotNew m2 = new MultiPlotNew();
        TrigFourier fou = new TrigFourier();
        ArrayList<Double> ss = fou.answer;
        System.out.print("444____" + ss);
       // fou.toFonctionF(100,0,10);

       /* System.out.println(fou);
        System.out.println(ss);

        Lagrange lag =new Lagrange();

        System.out.print(lag);*/

     /*  Polynomial p3 = q1.addPols(q2).addPols(q3).addPols(q4).addPols(q5).addPols(q6).addPols(q7).addPols(q8).addPols(q9);
       ArrayList<Polynomial> d = p3.Legendre();
       MultiPlotNew m1 =new MultiPlotNew();
       for (int i=0; i<d.size();i++){
           d.get(i).toFonction(100,-1,1);
           m1.addPlotable("i=" + i, d.get(i));
           System.out.println(d.get(i));
       }
        m1.plotNow();




       // s.toFonction(100, -10, 10);


        //System.out.println(p3);
        //System.out.println(p3.Legendre());





      /*  MultiPlotNew m1 =new MultiPlotNew();
        MultiPlotNew m2 =new MultiPlotNew();
        MultiPlotNew m3 =new MultiPlotNew();
        p1.toFonction(100, -1, 1);
        p2.toFonction(100, -1, 1);
        p3.toFonction(100, -1, 1);
        m1.addPlotable("1", p1);
        m2.addPlotable("2",p2 );
        m3.addPlotable("3", p3);

        m1.plotNow();
        m2.plotNow();
        m3.plotNow();
    }
 */
    }
}





