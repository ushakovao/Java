package MA_CSMI;

/**
 * Created by Oxana on 29/11/2015.
 */
import java.util.Random;

public class VaCarreUnif implements VariableAleatoire {

    Random rand = new Random();

    public double nextSimulation() {

        return Math.pow(rand.nextDouble(), 2);

    }

}
