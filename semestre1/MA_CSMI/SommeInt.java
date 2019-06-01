package MA_CSMI;

import java.util.Random;
/**
 * Created by Oxana on 30/11/2015.
 */
public class SommeInt implements VariableAleatoire {

    public double nextSimulation() {
        MyRandom r = new MyRandom();
        double temp, b,  n, answer;
        n = 100;
        temp =0;
        for (int i=0; i<n; i++){
            double a = (r.nextInt(3)-1);
            b = a*a;
            temp = temp + b;
        }
        answer = Math.pow(temp, 0.5);
        return answer;
    }

    public static void main(String[] args) {
        VariableAleatoire va = new SommeInt();
        MonteCarlo mc = new MonteCarlo();
        double esp = mc.estimeEsperance(va, 0.0001);
        double var = mc.estimeVariance(va, 0.0001);
        System.out.println("Esp 6.1 = " + esp);
        System.out.println("Var 6.1 = " + var);

    }
}

