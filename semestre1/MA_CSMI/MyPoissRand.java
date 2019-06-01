package MA_CSMI;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Oxana on 08/10/2015.
 */
public class MyPoissRand extends Random {
        public MyPoissRand() {super();}

        public static int nextPoisson (double lambda) {

        double L = Math.exp(-lambda);

        double p = 1.0;
        int k = 0;

        do {

            p *= Math.random();
            k++;
        } while (p > L);

        return k - 1;
         }

        public static void main(String[] args) {

        MyPoissRand myRand = new MyPoissRand();
        double lambda = 0.2;

        ArrayList<Integer> tirages = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            tirages.add(myRand.nextPoisson(lambda));
        }
        System.out.println(tirages);

    }
}
