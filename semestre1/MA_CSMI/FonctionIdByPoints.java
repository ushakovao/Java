package MA_CSMI;
import java.util.ArrayList;


public class FonctionIdByPoints implements PlotablePoint {
		
	
	private ArrayList<Double> xs  = new ArrayList<Double>();
	private ArrayList<Double> ys  = new ArrayList<Double>();
	private ArrayList<Double> radius  = new ArrayList<Double>();

	
	public FonctionIdByPoints(int taille, double gauche, double droite){
		for (int i=0;i<taille;i++){
		 xs.add(gauche + (droite-gauche)/taille*i);
		 ys.add( xs.get(i));
			radius.add(Math.min(i,100.));

		}				
	}	
	
	public ArrayList<Double> radius(){return radius;}
	public ArrayList<Double> getXs(){
		return xs;
	}
	
	public ArrayList<Double> getYs(){
		return ys;
	}
	
	
	
	
	
}
