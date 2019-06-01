package common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigon on 16/11/2015.
 */
public class IdentityColored implements PlotableColored{

    private ArrayList<Double> Xs=new ArrayList<Double>();
    private ArrayList<Double> Ys=new ArrayList<Double>();
    private ArrayList<Double> colors=new ArrayList<Double>();


    public IdentityColored(){
        for (int i=0;i<100;i++){
            Xs.add(i*1.);
            Ys.add(i*1.);
            colors.add(i*1.);
        }

    }

    @Override
    public List<Double> colors() {
        return colors;
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