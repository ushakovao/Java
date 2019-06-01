/**
 * Created by Oxana on 02/12/2015.
 */
import java.util.ArrayList;

public class Graphe {

    public ArrayList<Etat> etats = new ArrayList<Etat>();

    public Graphe(int nbEtat) {
        for (int i = 0; i < nbEtat; i++) {
            etats.add(new Etat(i));
        }
    }

    public void ajouteFleche(int dep, int arr) {
        Etat etatDep = etats.get(dep);
        etatDep.sorties.add(new Fleche(etats.get(arr)));
    }

    public void ajouteFleche(int dep, int arr, double poids) {
        Etat etatDep = etats.get(dep);
        etatDep.sorties.add(new Fleche(etats.get(arr), poids));
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (Etat et : etats) {
            res.append(et).append("\n");
        }
        return res.toString();
    }

    public void calculProbaDeTransition() {
        for (Etat eta : etats) {
            double poidsTotal = eta.poidsTotal();
            for (Fleche fl : eta.sorties) {
                fl.proba = fl.poids / poidsTotal;
            }
        }
    }

    public static void main(String[] args) {

        Graphe graphe = new Graphe(3);

        graphe.ajouteFleche(0, 1);
        graphe.ajouteFleche(0, 2, 2.);
        graphe.ajouteFleche(1, 2);
        graphe.ajouteFleche(2, 1);

        graphe.calculProbaDeTransition();

        System.out.println(graphe);

    }

}