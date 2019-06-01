package tpA;

import java.util.ArrayList;
import java.util.Random;

public class Frequence {

    public static void main(String[] args) {

		/*
		 * De nouveau on crée un objet de type Random() (on dit aussi
		 * "appartenant à la classe Random")
		 */
        Random rand = new Random();

		/*
		 * On crée un pointeur auquel on affecte une "ArrayList" :
		 */
        ArrayList<Integer> occurences = new ArrayList<Integer>();

		/*
		 * Je remplis mon tableau avec 10 zeros consécutifs. Remarquez au
		 * passage la syntaxe de la boucle 'for'
		 */

        int taille = 10;
        for (int i = 0; i < taille; i++) {
            occurences.add(0);
        }

		/*
		 * Affichons ce tableau
		 */
        System.out.println(occurences);

        /**
         * Analysez la suite
         */
        int nbTirage = 10000;
        int unEntier;

        for (int i = 0; i < nbTirage; i++) {
            unEntier = rand.nextInt(taille);
            for (int k = 0; k < taille; k++) {
                if (unEntier == k) {
					/*
					 * incrémentation de la k-ième valeur du tableau. Remarquez
					 * l'utilisation de get() et set()
					 */
                    int old = occurences.get(k);
                    occurences.set(k, old + 1);
                }
            }
        }




		/*
		 * Affichons ce tableau de nouveau
		 */
        System.out.println(occurences);


        int somme =0;
        for (int i=0; i<taille; i++){
            somme += i*occurences.get(i);
        }
        double moyenne = (double) somme/nbTirage;

        System.out.println("Pour nbTirage = "+ nbTirage +"  la moyenne = " + moyenne);



        //initialisation and value assigement of parameters
        double[] data = {1., 2.1, 3.2, 3.3, 3.4, 5., 5.9};
        double droite = 6;
        double gauche = 0;
        int nbBaton = 1;
        double intervalLength = (droite - gauche) / nbBaton;
        double beginOfTheInterval = gauche;
        double endOfTheInterval = gauche+intervalLength;

        //initialisation and value assigement to ArrayList
        ArrayList<Double> tailleBatons = new ArrayList<Double>();
        for (int i=0; i<nbBaton; i++) tailleBatons.add(0.);

        //algoritmics and output
        for (int i=0; i<nbBaton; i++) {

            for (int j = 0; j < data.length; j++){

                if (beginOfTheInterval <= data[j] && data[j] <= endOfTheInterval){
                    Double old = tailleBatons.get(i);
                    tailleBatons.set(i, old + 1);
                }
            }
            beginOfTheInterval += intervalLength;
            endOfTheInterval += intervalLength;
        }
        System.out.println("Nb des batons = " +nbBaton +"; Taille des batons " + tailleBatons);
    }

}










