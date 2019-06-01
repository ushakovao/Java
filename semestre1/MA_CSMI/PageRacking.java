package MA_CSMI;

import java.util.ArrayList;

/**
 * Created by Oxana on 03/12/2015.
 */
public class PageRacking {

    public static void main(String[] args) {
        double damping = 0.85;
        double a = 1.;
        double b = 1.;
        double c = 1.;
        double d = 1.;

        int n = 25;
        for (int i = 0; i < n; i++) {
            double at, bt, ct, dt;
            at = a + (1 * b * damping)+(1 *c * damping)+(1 * d * damping);
            bt = b + (0.25 * a * damping);
            ct = c + (0.25 * a * damping);
            dt = d+ (0.25 * a * damping);

            a = at;
            b = bt;
            c = ct;
            d = dt;
        }
        System.out.printf("When n="+n+"  :a="+a+"  b="+b+"  c="+c+"  d="+d);}




          /* double at, bt, ct;
            at = a + (1 * b * damping);
            bt = b + (1 * a * damping)+(1 * c * damping);
            ct = c;

            a = at;
            b = bt;
            c = ct;

        }
        System.out.printf("When n="+n+"  :a="+a+"  b="+b+"  c="+c);
        }*/
           /*double at, bt, ct, dt;
            at = a + (1 * c * damping);
            bt = b + (0.5 * a * damping);
            ct = c + 0.5 * a * damping + 1 * b * damping + 1 * d * damping;
            dt = (d);

            a = at;
            b = bt;
            c = ct;
            d = dt;
        }
        System.out.printf("When n="+n+"  :a="+a+"  b="+b+"  c="+c+"  d="+d);*/





}