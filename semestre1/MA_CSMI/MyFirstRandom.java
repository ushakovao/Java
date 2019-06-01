
package MA_CSMI;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

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
	public int nextDiscrete(double[] probabilities) {
		int res = 0;

			double unReel = random.nextDouble();
			double cumul = probabilities[0];
		while (unReel > cumul) {
			res++;cumul = cumul + probabilities[res];
		      }
		   return res;

	}

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

		ArrayList<Integer> tirages = new ArrayList<Integer>();

		for (int i = 0; i < 20; i++) {
			tirages.add(myRand.nextDiscrete(probabilites));
		}

		System.out.println(tirages);

	}


	  //Créez une méthode nextUnif(double gauche, double droite) qui renvoie une
	  //VA réelle uniforme dans l'intervalle gauche droite. Quelle est
	 //l'espérance d'une telle VA ?


	public static double nextUnif(double begin, double end) {
		Random randomdouble = new Random();
		return begin + (end - begin) * randomdouble.nextDouble();
	}

	 public double  nextUnif1(double a, double b) {
	 double longeur = b-a;
		 double res1 = 0;

		 double unReel = random.nextDouble();
		 		if
			 (unReel < b & unReel > a)
			 res1 = unReel;
			 else random.nextDouble();


		 return res1;
	 }

	public double nextGeo(double p) {
		double unReel = random.nextDouble();
		double res = 0;
		double q = 1 - p;
		res = Math.floor(Math.log10(unReel)/Math.log(q));
		return res;
	}


	public int nextPoisson (double lambda) {
		double L = Math.exp(-lambda);
		double p = 1.0;
		int k = 0;

		do {

			p *= Math.random();
			k++;
		} while (p > L);

		return k - 1;
	}
}








