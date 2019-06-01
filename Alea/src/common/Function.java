package common;

import java.util.ArrayList;

/**
 * Created by vigon on 12/05/2016.
 * Here is the most basic and generic implementation of Plotable. The user have to entry both xs and ys
 */
public class Function implements PlotableMA{


    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();


    public Function(ArrayList<Double> xs,ArrayList<Double> ys){
        this.xs=xs;
        this.ys=ys;
    }


    public ArrayList<Double> getXs(){return xs;}
    public ArrayList<Double> getYs(){return ys;}


}