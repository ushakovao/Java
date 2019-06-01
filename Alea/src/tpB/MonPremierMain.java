package tpB;

/**
 * Created by ushakova on 03/10/16.
 */
public class MonPremierMain {

    /**
     * Ne lancer pas tout ce programme d'un coup. Commentez des bouts pour le
     * comprendre étape par étape. (pour commentez, sélectionner puis utilisez
     * "Source>Toggle Comment" Gardez toujours un oeil sur la classe {@link MaPremiereClasse}
     *
     */
    public static void main(String[] args) {



        MaPremiereClasse monPremierObjet = new MaPremiereClasse();

        // Affichage d'un champs
        System.out.println(monPremierObjet.unEntier);
        // Affichage de la chaîne de caractère associée
        System.out.println(monPremierObjet);

        // Modification directe d'un champs public
        monPremierObjet.unEntier = 7;
        // Modification d'un champs privé grace à son 'setter'
        monPremierObjet.setUnReel(0.756);

        System.out.println("monPremierObjet final  " +monPremierObjet);

        // Création d'un second objet de la même classe
        MaPremiereClasse monSecondObjet = new MaPremiereClasse(123456);
        System.out.println(monSecondObjet);

        monSecondObjet.remplieAleatoirementLeTableau(4);
        System.out.println("monSecondObjet final  " + monSecondObjet);

        // Enfin une erreur classique :
        MaPremiereClasse monTroisiemeObjet;
        monTroisiemeObjet = monSecondObjet;
        System.out.println("monTroisiemeObjet final  " + monTroisiemeObjet);

        /**
         * Que se passe-t-il si l'on modifie un champs de monTroisiemeObjet ou
         * bien un champs de monSecondObjet ?
         *
         * Ainsi le pointeur monTroisiemeObjet est bien mal nommé.
         */

        /**
         * Remarquez aussi le warning sur monTroisiemeObjet;
         */

    }

}