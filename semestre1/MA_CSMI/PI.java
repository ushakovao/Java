package MA_CSMI;

import java.util.Random;

/**
 * Created by Oxana on 30/11/2015.
 */
public class PI {
    public static void main(String[] args) {
        MyRandom rand = new MyRandom();

        double jet = 0;
        double suc = 0;
        int n = 10000000;

        for (int i = 0; i < n; i++) {
            double x = rand.nextUnif(-1,1) ;
            double y = rand.nextUnif(-1,1) ;
            jet = jet + 1;
            if (x * x + y * y <= 1) {
                suc = suc + 1;
            }
        }
    System.out.println("Nb des jets = "+n);
    System.out.println("Pi = "+4*suc/jet);
    }
  }