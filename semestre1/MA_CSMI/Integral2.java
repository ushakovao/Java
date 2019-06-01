package MA_CSMI;

/**
 * Created by Oxana on 29/11/2015.
 */
public class Integral2 implements VariableAleatoire {

    public double nextSimulation() {
        MyRandom r = new MyRandom();
        double answer, x, y, alpha, densite_inv;
        x = r.nextUnif(-10, 10);
      //  y = r.nextUnif(0, 2);
        alpha = -10.;
        densite_inv = 20;
        answer = (densite_inv*x*x) / Math.pow((1 + Math.abs(x)), alpha);
        return answer;
    }

    public static void main(String[] args) {
        VariableAleatoire va = new Integral2();
        MonteCarlo mc = new MonteCarlo();
        double esp = mc.estimeEsperance(va, 0.0001);
        double var = mc.estimeVariance(va, 0.0001);
        System.out.println("Esp 4.1 = " + esp);
        System.out.println("Var 4.1 = " + var);
    }
}
