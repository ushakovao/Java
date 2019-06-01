package tpB;

/**
 * Created by ushakova on 03/10/16.
 */

import java.util.ArrayList;
import java.util.Random;

public class MyFirstRandom {

    /*
     * Un champs privé
     */
    private Random random;

    /*
     * Le constructeur
     */
    public MyFirstRandom() {
        random = new Random();
    }

    /**
     * Une méthode. Décrivez ce que fait cette méthode
     */
    public int nextDiscrete(double[] probas) {
        int res = 0;

        double unReel = random.nextDouble();
        double cumul = probas[0];
        while (unReel > cumul) {
            res++;
            cumul = cumul + probas[res];
        }
        return res;
    }



    public static double nextUnif(double begin, double end) {
        Random randomdouble = new Random();
        return begin + (end - begin) * randomdouble.nextDouble();
    }

    //Espérance = (droite+gauche)/2

    /*
     * Quand on crée un classe, c'est dans le but de l'utiliser dans un (gros)
     * programme principal (un programme contenant
     * "public static void main..."). C'est ce que nous avons fait précédemment.
     * Cependant, on peut créer des petit "main" à l'interieure de ses classe
     * pour illustrer leur fonctionnement. Exemple :
     */
    public static void main(String[] args) {

        MyFirstRandom myRand = new MyFirstRandom();
        double[] probabilites = { 0.2, 0.2, 0.6 };

        ArrayList<Integer> desTirages = new ArrayList<Integer>();

        for (int i = 0; i < 20; i++) {
            desTirages.add(myRand.nextDiscrete(probabilites));
        }
        System.out.println("Next Discrete  "+desTirages);

        ArrayList<Double> desTiragesD = new ArrayList<Double>();

        for (int i = 0; i < 5; i++) {
            desTiragesD.add(myRand.nextUnif(1.2, 2.2));
        }
        System.out.println("Next Unif  "+desTiragesD);

    }

    /**
     * Créez une méthode nextUnif(double gauche, double droite) qui renvoie une
     * VA réelle uniforme dans l'intervalle gauche droite. Quelle est
     * l'espérance d'une telle VA ?
     */



}