
package common;
import common.MultiPlotNew;
import common.PlotableMA;
import tpD.CourbeDeGauss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Histogramme implements PlotableMA {

	private ArrayList<Double> xs = new ArrayList<Double>();
	private ArrayList<Double> ys = new ArrayList<Double>();
	private List<Double> donnees = new ArrayList<Double>();

	/**
	 * ces valeurs peuvent être fixées par des méthodes set. Si elles ne le sont pas, des valeurs par défaut seront calculées
	 */
	private Integer nbBaton = null;
	private Double gauche = null;
	private Double droite = null;
	private boolean unBatonParEntier = false;

	public ArrayList<Double> tailleBatons = new ArrayList<Double>();


	public Histogramme(ArrayList<Double> donnees) {
		this.donnees = donnees;
	}

	public Histogramme(double[] donnees) {
		this.donnees = new ArrayList<Double>();
		for (double d : donnees) { ///chto eto!!!
			this.donnees.add(d);
		}
	}


	@Override
	public ArrayList<Double> getXs() {
		return xs;
	}

	@Override
	public ArrayList<Double> getYs() {
		return ys;
	}


	public void setNombreDeBaton(int nombre) {
		if (nbBaton != null) throw new RuntimeException("vous avez déjà fixé le nombre de battons");
		if (unBatonParEntier)
			throw new RuntimeException("vous avez décidé de mettre un baton par entier, vous ne pouvez pas appeler la méthode setNombreDeBaton");
		nbBaton = nombre;
	}


	public void setUnBatonParEntier() {
		if (nbBaton != null)
			throw new RuntimeException("vous avez déjà fixé le nombre de battons. Nous ne pouvez pas appeler la méthode setUnBatonParEntier");
		if (unBatonParEntier) throw new RuntimeException("vous avez déjà décidé de mettre un baton par entier");
		unBatonParEntier = true;
	}


	public void setIntervalle(double gauche, double droite) {
		this.gauche = gauche;
		this.droite = droite;
	}


	public void dresseHistogramme(boolean normalize) {

		if (gauche == null) {
			gauche = minimum(donnees);
			droite = maximum(donnees) * 1.0001; //important
		}

		if (nbBaton == null && unBatonParEntier) {
			nbBaton = (int) (droite - gauche) + 1;
		} else if (nbBaton == null) nbBaton = donnees.size() / 100 + 5;


		for (int i = 0; i < nbBaton; i++) {
			tailleBatons.add(0.);
		}

		for (double d : donnees) {
			/** attention, il faut *nbBaton et pas *(nbBaton-1) comme c'était fait avant. Par contre, pour d=droite cela crée un dépassement de tableau, il faut que l'extréminté droite soit légèrement supérieure à la valeur maximale (par défaut), ou alors il faut censurer la valeur d=droite  */
			int indice = (int) ((d - gauche) / (droite - gauche) * nbBaton);
			if (indice >= 0 && indice < nbBaton) {
				tailleBatons.set(indice, tailleBatons.get(indice) + 1);
			} else System.out.println("la donnée:" + d + " est censurée, car en dehors de l'intervalle");
		}

		xs.add(gauche);
		xs.add(gauche);

		for (int i = 1; i < nbBaton; i++) {
			double x = gauche + (droite - gauche) / nbBaton * i;
			xs.add(x);
			xs.add(x);
			xs.add(x);
		}
		xs.add(droite);
		xs.add(droite);

		ys.add(0.);
		ys.add(tailleBatons.get(0));
		for (int i = 0; i < nbBaton - 1; i++) {
			ys.add(tailleBatons.get(i));
			ys.add(0.);
			ys.add(tailleBatons.get(i + 1));
		}
		ys.add(tailleBatons.get(nbBaton - 1));
		ys.add(0.);

		if (normalize) {
			double largeurBaton = (droite - gauche) / nbBaton;

			for (int i = 0; i < ys.size(); i++) {
				ys.set(i, ys.get(i) / (donnees.size() * largeurBaton));
			}
		}

	}


	private double maximum(List<Double> tab) {
		double theMax = tab.get(0);
		for (int i = 1; i < tab.size(); i++) {
			if (theMax < tab.get(i)) {
				theMax = tab.get(i);
			}
		}
		return (theMax);
	}

	private double minimum(List<Double> tab) {

		double theMin = tab.get(0);
		for (int i = 1; i < tab.size(); i++) {
			if (theMin > tab.get(i)) {
				theMin = tab.get(i);
			}
		}
		return (theMin);
	}

	//public static void main(String[] args) {

	//double[] donnees = { 1., 2., 3., 4., 4., 4., 10., 10.,10, 2., 10,10,10, 2., 2., 3.,
	//5., 4., 4., 13, 2,2,1,0,-1,-1,-1,-3,-3 };

	//Histogramme hist = new Histogramme(donnees);
	//hist.setUnBatonParEntier();
	//hist.dresseHistogramme(true);

	//MultiPlot multiPlot = new MultiPlot();
	//multiPlot.addPlotable("test",hist);
	//multiPlot.setStroke("test",MultiPlot.thick);
	//multiPlot.plotNow();

	//ArrayList<Double> koko=new ArrayList<Double>();
	//int nbTirages=100;
	//Random rand=new Random();
	//double nbr;
	//for (int i=0;i<nbTirages;i++){
	//	nbr=rand.nextGaussian();
	//	koko.add(i, nbr);
	//}




}




