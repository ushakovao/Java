package common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigon on 16/11/2015.
 */
public class IdentityPointed implements PlotablePoint{

    private ArrayList<Double> Xs=new ArrayList<Double>();
    private ArrayList<Double> Ys=new ArrayList<Double>();
    private ArrayList<Double> radii=new ArrayList<Double>();


    public IdentityPointed(double pente){
        for (int i=0;i<10;i++){
            Xs.add(i*1.);
            Ys.add(pente*i*1.);
            radii.add(i*1.);
        }

    }

    @Override
    public List<Double> radius() {
        return radii;
    }

    @Override
    public List<Double> getXs() {
        return Xs;
    }

    @Override
    public List<Double> getYs() {
        return Ys;
    }
}