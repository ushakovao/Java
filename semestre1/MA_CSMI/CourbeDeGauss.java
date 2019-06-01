package MA_CSMI;
import common.PlotableMA;

import java.util.ArrayList;

public class CourbeDeGauss implements PlotableMA {

	
	
	private ArrayList<Double> xs  = new ArrayList<Double>();
	private ArrayList<Double> ys  = new ArrayList<Double>();
	MyRandom r = new MyRandom();

	
	public CourbeDeGauss(int taille, double gauche, double droite, double mu, double var){
		for (int i=0;i<taille;i++){
		 xs.add(i,gauche + (droite-gauche)/taille*i);
		 ys.add(i, (Math.pow(Math.pow(var,0.5)*(Math.pow(2*Math.PI,0.5)), -1))* Math.exp(-0.5*(Math.pow(xs.get(i)-mu,2)/var)));

		}

	}	
	
	
	public ArrayList<Double> getXs(){

		return xs;
	}
	
	public ArrayList<Double> getYs(){

		return ys;
	}
	
	

}
