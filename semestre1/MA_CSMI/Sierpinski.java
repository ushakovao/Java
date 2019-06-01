package MA_CSMI;

import common.MultiPlotNew;
import common.PlotableMA;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jonathan on 26/10/2015.
 */
public class Sierpinski implements PlotableMA {


    private ArrayList<Double> xs  = new ArrayList<Double>();
    private ArrayList<Double> ys  = new ArrayList<Double>();

    public ArrayList<Double> getXs(){
        return xs;
    }

    public ArrayList<Double> getYs(){
        return ys;
    }

    public Sierpinski(int cotes, int nombreDePoints){
        Random rand = new Random();
        double t=2.2;
        if(cotes!=4){
            double somme=0;
            for(int h=0;h<=(cotes/4);h++){
                somme+=Math.cos(2*h*Math.PI/(double) cotes);}
            t=2*somme;}
        ArrayList<Double> xPoints  = new ArrayList<>();
        ArrayList<Double> yPoints  = new ArrayList<>();
        for(int j=0;j<cotes;j++){
            xPoints.add(Math.cos(Math.PI * (3. / 2. + (2 * j - 1.) / (double) cotes)));
            yPoints.add(Math.sin(Math.PI * (3. / 2. +(2*j-1) / (double) cotes)));
        }
        double x=xPoints.get(0),y=yPoints.get(0);
        xs.add(x);
        ys.add(y);
        int k;
        for (int i=0;i<nombreDePoints;i++){
           k=rand.nextInt(cotes);
            x=(x+(t-1)*xPoints.get(k))/t;
            y=(y+(t-1)*yPoints.get(k))/t;
            xs.add(x);
            ys.add(y);
            }}


    public void afficheSierpinski() {

        MultiPlotNew multi = new MultiPlotNew();
        multi.addPlotable("triangle de Sierpinski", this);
        multi.setJoinType("triangle de Sierpinski", MultiPlotNew.JoinType.bullet,2);
       // multi.setDimFenetre(1400,850);
        multi.plotNow();
    }



    public static void main(String[] args) {
        Sierpinski sierp=new Sierpinski(5,300000);
        sierp.afficheSierpinski();
}}
