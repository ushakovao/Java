package tpA;

import java.util.Random;

public class GenerateurAleatoire {

    public static void main(String[] args) {

		/*
		 * Déclare un pointeur du nom de "rand" et de type "Random". Pour
		 * l'intant, ce pointeur ne pointe vers rien du tout.
		 */
        Random rand;

		/*
		 * "new Random()" crée un objet de type "Random" le symbol "=" permet
		 * d'affecter ce nouvel objet é notre pointeur "ran".
		 */
        rand = new Random();

		/*
		 * Les deux opérations précédentes peuvent aussi s'écrire : Radom
		 * rand=new Random();
		 */

		/*
		 * On crée un objet unEntier de type int (c'est un type 'primitif') et
		 * on lui affecte la valeur 0
		 */
        int unEntier = 0;

		/*
		 * J' utilise la méthode "nextInt()" de notre objet pour tirer
		 * aléatoirement un entier entre 0 et 9. On affecte cet entier.
		 */
        unEntier = rand.nextInt(10);

		/*
		 * Affichage
		 */

        System.out.println("l' entier aléatoire est " + unEntier);


		/*
		 * Tirons maintenant des réels aléatoires. En java, par défaut, un réel
		 * est objet de type "double"
		 */
        double unReel = 0.0;

        unReel = rand.nextDouble();

        System.out.println("un réel aléatoire entre zero et un: " + unReel);

        /**
         * Reprendre les questions précédentes, mais avec les tirages de réel.
         */

    }

}