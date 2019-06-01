package MA_CSMI;

/**
 * Created by Oxana on 29/11/2015.
 */
public class Integral3 implements VariableAleatoire {

    public double nextSimulation() {
        MyRandom r = new MyRandom();
        double res, x,  n,  densite_inv;
        n = 1000;
        densite_inv = 2;
        res =0;
            for (int i=0; i<n; i++){
            double a;
            a = r.nextUnif(-1, 1);
            x = a*a;
            res = res + x;
            }
        return (densite_inv)*Math.pow(res, 0.5);
    }

    public static void main(String[] args) {
        VariableAleatoire va = new Integral3();
        MonteCarlo mc = new MonteCarlo();
        double esp = mc.estimeEsperance(va, 0.0001);
        double var = mc.estimeVariance(va, 0.0001);
        System.out.println("Esp 5.1 = " + esp);
        System.out.println("Var 5.1 = " + var);
    }
}

