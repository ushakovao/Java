package MA_CSMI;

/**
 * Created by Oxana on 29/11/2015.
 */
import java.util.Random;

public class VaUnifDansDisque implements VariableAleatoire {

    Random rand = new Random();

    public double nextSimulation() {

        double x = rand.nextDouble() * 2 - 1;
        double y = rand.nextDouble() * 2 - 1;

        if (x * x + y * y < 1)
            return 1.;
        else
            return 0.;

    }
}

