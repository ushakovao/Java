package tpA;

import common.MultiPlotNew;
import common.uu;

import java.util.ArrayList;

public class Tableau {

    public static void main(String[] args) {


		/*
		 * Déclaration d'une variable (ou pointeur) qui devra pointer sur un tableau d'entier
		 */
        int[] tableau;

		/*
		 * création d'un tableau et affectation de la variable précédente. On doit
		 * impérativement indiquer la taille
		 */
        tableau = new int[3];

		/*
		 * On ne pourra plus jamais modifié la taille de ce tableau. On peut
		 * cependant accéder aux valeurs dans ce tableau
		 */

        uu.oo("en 0: " + tableau[0]);
        uu.oo("en 1: " + tableau[1]);
        uu.oo("en 2: " + tableau[2]);

		/*
		 * On peut modifier ces valeurs
		 */
        tableau[0] = 3;
        tableau[1] = 4;
        tableau[2] = 6;

        uu.oo("après modification: ");
        uu.oo("en 0: " + tableau[0]);
        uu.oo("en 1: " + tableau[1]);
        uu.oo("en 2: " + tableau[2]);

		/*
		 * Voici une façon pratique de déclarer et remplir un tableau
		 */
        int[] tableau2 = { 5, 6, 9, 10 }; // ce tableau est donné par le prof

		/*
		 * Parcourons
		 */
        uu.oo("le tableau2:");
        for (int i = 0; i < tableau2.length; i++) {
            System.out.print(tableau2[i] + ", ");
        }
		/*
		 * Ou bien en mieux avec un forEach
		 */
        uu.oo("\n le même tableau2");
        for (Integer elem : tableau2) {
            System.out.print(elem + ", ");
        }
		/*
		 * Attention, le forEach ne permet pas de modifier les
		 * tableaux
		 */
        for (Integer elem : tableau2) {
            elem=3;
        }
        uu.oo("\n le même tableau2 qu'on croyait avoir modifié, mais en fait non");
        for (Integer elem : tableau2) {
            System.out.print(elem + ", ");
        }


        /**
         * Quel message renvoie l'ordinateur lorsque l'on demande écrit
         * tableau2[5] ? (alors que l'indexe 5 n'existe pas pour tableau2)
         *
         */

        System.out.print(tableau2[5]);

		/*
		 * Beaucoup plus pratique les "ArrayList" : c'est un tableau dont on est
		 * pas obligé de spécifier la taille. Il est aussi plus facile à
		 * afficher Dans ce tableau on y mettra des entier d'où la spécification
		 * <Integer> Pour nous Integer c'est quasi-identique à int.
		 */
        ArrayList<Integer> tableau3 = new ArrayList<Integer>();

		/*
		 * Remplissage
		 */
        tableau3.add(12);
        tableau3.add(13);
        tableau3.add(14);
        tableau3.add(15);
		/*
		 * lecture d'un élément
		 */
        int unInt = tableau3.get(1);
        /**
         * pourquoi y a-t-il un warning à cette ligne ? ne jamais ignorer les
         * warning !
         */

		/*
		 * Modification
		 */
        tableau3.set(2, 123409);
		/*
		 * Affichage
		 */
        uu.oo("\n tout le tableau 3: " + tableau3);
		/*
		 * Suppression d'un element au millier
		 */
        tableau3.remove(2);
        uu.oo("le tableau 3 diminué: " + tableau3);

        /**
         * Quel message renvoie l'ordinateur lorsque l'on déclare une variable,
         * puis que l'on utilise cette variable sans y avoir affecter un objet.
         * Exemple :
         *
         * ArrayList<Integer> tableau4; tableau4.add(12);
         *
         */





    }

}