package tpB;

/**
 * Created by ushakova on 03/10/16.
 */
import java.util.ArrayList;
import java.util.Random;

/**
 * Jusqu'à present nous avons utilisé des classes toutes faites comme la classe
 * "Random" Maintenant nous allons créer notre propre classe avec ses propres
 * méthodes. Cette classe sera testée et utilisée dans le programme principal {@link MonPremierMain}
 *
 */
public class MaPremiereClasse {

	/*
	 * En premier lieu:  on indique les "champs" (field) qui sont les variables
	 * globales à cette classe Le mot "private" indique qu' elles ne seront pas
	 * visible à l' extérieure de cette classe. Le mot "public" indique le
	 * contraire. Convention : comme toutes les variables, les champs, les méthodes
	 * commencent par des minuscules.
	 *
	 * Seule le nom des classes commencent par une majuscule
	 */

    public int unEntier;
    private double unReel;
    private ArrayList<Integer> unTableau;

	/*
	 * En second lieu :  on écrit le constructeur de cette classe. C' est une méthode (il
	 * y a des parenthèses après son nom). Cette méthode décrit les opérations
	 * qui seront effectuées lors de la création d' un objet du type
	 * "MaPremiereClasse" c.à.d lors d' une phrase du type
	 *
	 * "MonPremierObjet toto= new MaPremiereClasse()"
	 *
	 * Le nom du constructeurs
	 * doit être le même que le nom de la classe. Il commence donc par une
	 * majuscule (c'est l'exception qui confirme la règle).
	 */

    public MaPremiereClasse() {
        unEntier = 3;
        unReel = 0.5;
        unTableau = new ArrayList<Integer>();// un tableau vide
    }

    /*
     * On peut écrire plusieurs constructeurs avec des arguments différents.
     */
    public MaPremiereClasse(int unEnt) {
        unEntier = unEnt;
        unReel = 0.5;
        unTableau = new ArrayList<Integer>();// un tableau vide
    }

    public MaPremiereClasse(int unEnt, double unRe) {
        unEntier = unEnt;
        unReel = unRe;
        unTableau = new ArrayList<Integer>();// un tableau vide
    }

	/*
	 * En troisième lieu: viennent les méthodes de cette classe. Ces méthodes peuvent être
	 * public ou privée selon si elle pourrons ou non être appelée de
	 * l'extérieure. convention : les nom des méthodes commencent par des
	 * minuscules.
	 */

    /*
     * Des méthodes qui ne renvoient rien ("void"="vide")
     */
    public void ajouteUnEntier(int a) {
        unEntier = unEntier + a;
    }

    private void creeLeTableau(int size) {
        for (int i = 0; i < size; i++) {
            unTableau.add(0);
        }
    }

    public void remplieAleatoirementLeTableau(int size) {
        Random random = new Random();
        creeLeTableau(size);
        for (int i = 0; i < size; i++) {
            unTableau.set(i, random.nextInt());
        }
    }


    public void creeEtRemplieAleatoirementLeTableau(int size) {
        for (int i = 0; i < size; i++) {
            unTableau.add(0);
        }
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            unTableau.set(i, random.nextInt());
        }
    }



    /**
     * Les méthodes privée servent essentiellement à simplifier la présentation du
     * code. Cependant, dans notre cas, la méthode creeLeTableau(int size) est superflue :
     * créez une méthode creeEtRemplieAleatoirementLeTableau(int size) qui ne nécessite pas creeLeTableau(int size)
     */

	/*
	 * Des méthodes qui permettent l' accès à un champs privé (les getter)
	 */

    public double getUnReel() {
        return unReel;
    }

    public int getUnTableau(int index) {
        return unTableau.get(index);
    }

    /*
     * Des méthodes qui permette de modifier un champs privé (les setter)
     */
    public void setUnReel(double re) {
        unReel = re;
    }

    @Override
    public String toString() {
        return "MaPremiereClasse [unEntier=" + unEntier + ", unReel=" + unReel
                + ", unTableau=" + unTableau + "]";
    }

	/*
	 * La méthode suivante permet d' associer une chaîne de caractère (String) à
	 * un objet de la classe en question. C' est très pratique (voir le 'main').
	 * Mais je n' ai pas écrit à la main cette méthode, j' ai fait : clique-droit
	 * > generate >  toString ...
	 */


    public static void main(String[] args) {
        MaPremiereClasse a = new MaPremiereClasse();
        //test de creeEtRemplieAleatoirementLeTableau);
        a.creeEtRemplieAleatoirementLeTableau(3);
        //afficher unEntier, unReel et unTableau crée et rempli
        System.out.println(a);

    }


}