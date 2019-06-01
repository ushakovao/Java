
/**
 * Created by Oxana on 02/12/2015.
 */

public class Fleche {

    public Etat arrivee;
    public double poids = 0;
    public double proba = 0;

    public Fleche(Etat arr) {
        arrivee = arr;
        poids = 1;
    }

    public Fleche(Etat arr, double poid) {
        arrivee = arr;
        poids = poid;
    }

    @Override
    public String toString() {
        return "(" + arrivee.Id + ", poids=" + poids + ", proba=" + proba + ")";
    }

}
