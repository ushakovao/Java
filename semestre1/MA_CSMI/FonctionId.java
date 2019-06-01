package MA_CSMI;

import common.PlotableMA;

import java.util.ArrayList;


public class FonctionId implements PlotableMA {


	private ArrayList<Double> xs  = new ArrayList<Double>();
	private ArrayList<Double> ys  = new ArrayList<Double>();

 MyRandom r = new MyRandom ();
	public FonctionId(int taille){
		for (int i=0;i<taille;i++){
		 xs.add(i, 0.0);
		 ys.add(i, r.nextDouble()*1);

		}				
	}	
	
	public ArrayList<Double> getXs(){
		return xs;
	}
	
	public ArrayList<Double> getYs(){
		return ys;
	}
	
	
	
	
	
}
