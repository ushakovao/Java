package MA_CSMI;

/**
 * Created by Oxana on 29/11/2015.
 */
public class Integral1 implements VariableAleatoire {

    public double nextSimulation() {
        MyRandom r = new MyRandom();
        double answer, x, y, a;
        x = r.nextUnif(0, 1);
        y = r.nextUnif(0, 2);
        a = 2.; // max(1/(b_1-a_1),1/(b_2-a_2))
        answer = (a) * (x * x) * Math.pow(y - x, 3);
        return answer;
    }

    public static void main(String[] args) {
        VariableAleatoire va = new Integral1();
        MonteCarlo mc = new MonteCarlo();
        double esp3 = mc.estimeEsperance(va, 0.0000001);
        double var3 = mc.estimeVariance(va, 0.0000001);
        System.out.println("Esp 3.1 = " + esp3);
        System.out.println("Var 3.1 = " + var3);
    }
}