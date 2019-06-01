
package MA_CSMI;

import common.PlotableMA;

import java.util.ArrayList;





public class FonctionConstante implements PlotableMA {


	private ArrayList<Double> xs  = new ArrayList<Double>();
	private ArrayList<Double> ys  = new ArrayList<Double>();


	public FonctionConstante(int taille, double gauche, double droite,double value){
		for (int i=0;i<taille;i++){
		 xs.add(gauche + (droite-gauche)/taille*i);
		 ys.add( value);

		}				
	}	
	
	public ArrayList<Double> getXs(){
		return xs;
	}
	
	public ArrayList<Double> getYs(){
		return ys;
	}
	
	
	
	
	
}
