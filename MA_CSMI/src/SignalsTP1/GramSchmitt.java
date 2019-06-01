package SignalsTP1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oxana on 31/01/2016.
 */
public class GramSchmitt {

    public double sca(double[] a, double[] b) {
        double[] f;
        double d = 0;
        f = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            f[i] = a[i] * b[i];
            d = d + f[i];
        }
        return d;
    }



    public double[] multycoef(double[] a, double b) {
        double[] f;
        f = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            f[i] = a[i] * b;
        }
        return f;
    }


    public static void main(String[] args) {

        GramSchmitt gs = new GramSchmitt();

       /* double[] u1 = {0, 1, 1};
        double[] u2 = {1, 1, 0};
        double[] u3 = {1, 0, 1};

        if ((int) gs.sca(u1, u2) != 0 || (int) gs.sca(u1, u3) != 0 || (int) gs.sca(u2, u3) != 0) {
            System.out.println("Need to be ortogonalized as");
            System.out.println((int) gs.sca(u1, u2));
            System.out.println((int) gs.sca(u1, u3));
            System.out.println((int) gs.sca(u2, u3));

        } else {
            System.out.println("The vectors are already orthogonal");
        }


        double[] v1 = u1;
        double[] v2 = gs.minus(u2, gs.multycoef(v1, gs.sca(u2, v1) / (gs.sca(v1, v1))));
        double[] v3 = gs.minus(gs.minus(u3, gs.multycoef(v2, gs.sca(u3, v2) / gs.sca(v2, v2))), gs.multycoef(v1, gs.sca(u3, v1) / gs.sca(v1, v1)));


        System.out.println(Arrays.toString(v1));
        System.out.println(Arrays.toString(v2));
        System.out.println(Arrays.toString(v3));

        if ((int) gs.sca(v1, v2) != 0 || (int) gs.sca(v1, v3) != 0 || (int) gs.sca(v1, v3) != 0) {
            System.out.println("Error!!");
            System.out.println((int) gs.sca(v1, v2));
            System.out.println((int) gs.sca(v1, v3));
            System.out.println((int) gs.sca(v1, v3));

        } else {
            System.out.println("v1 , v2 and v3 are ortho - ok:");
            System.out.println((int) gs.sca(v1, v2));
            System.out.println((int) gs.sca(v1, v3));
            System.out.println((int) gs.sca(v1, v3));
        }*/

        Polynomial q1 = new Polynomial(1, 0);
        Polynomial q2 = new Polynomial(1, 1);
        Polynomial q3 = new Polynomial(1, 2);
        ArrayList<Polynomial> liste = new ArrayList<>();
        liste.add(0, q1);
        liste.add(1, q2);
        liste.add(2, q3);
        System.out.println("initrail:    "+liste);

        ArrayList<Polynomial> v = new ArrayList<>();
        for (int i=0; i<= liste.size(); i++){
            Polynomial res2=liste.get(i);
            if (i==0){
                v.add(i,liste.get(i));
                res2 = liste.get(i);
            }

            else {
                for (int k = 1; k <= i; k++) {
                    double fraction;
                    Polynomial res;

                    fraction = liste.get(i).dot(v.get(k - 1),0,2,100) / v.get(k - 1).dot(v.get(k - 1),0,2,100);
                    res = v.get(k-1).per_number(fraction);
                    res2 = res2.minus(res);
                }
                v.add(i, res2);
            }
            System.out.println ("result at i= "+i+"=>   "+res2);
        }
    }
}

