/**
 * Created by Oxana on 02/12/2015.
 */
import java.util.ArrayList;

public class Etat {

    // public String nom;
    public ArrayList<Fleche> sorties = new ArrayList<Fleche>();
    public int Id;

    public Etat(int Id) {
        this.Id = Id;
    }

    public double poidsTotal() {
        double res = 0;
        for (Fleche fle : sorties) {
            res += fle.poids;
        }
        return res;
    }

    @Override
    public String toString() {
        return "Etat " + Id + ", sorties=" + sorties;
    }

}
