package common;

import tpD.CourbeDeGauss;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Histo implements PlotableMA {

    private ArrayList<Double> xs = new ArrayList<Double>();
    private ArrayList<Double> ys = new ArrayList<Double>();
    private ArrayList<Double> donnees = new ArrayList<Double>();

    public  ArrayList<Double> getXs(){
        return xs;
    }

    public  ArrayList<Double> getYs(){
        return ys;
    }

    private int nbBaton;
    private double gauche;
    private double droite;
    public ArrayList<Double> tailleBatons = new ArrayList<Double>();

    public Histo(ArrayList<Double> donnees) {
        this.donnees = donnees;
    }

    public Histo(double[] donnees) {
        this.donnees = new ArrayList<Double>();
        for (double d : donnees) {
            this.donnees.add(d);
        }
    }

    public void setNombreDeBaton(int nombre) {
        nbBaton = nombre;
    }

    public void setIntervalle(double gauche, double droite) {
        this.gauche = gauche;
        this.droite = droite;
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

    public void dresseHistogramme(boolean normalize) {
            for (int i = 0; i < nbBaton; i++) {
            tailleBatons.add(0.);
        }

        for (double d : donnees) {
            int indice = (int) ((d - gauche) / (droite - gauche) * nbBaton);
            if (indice >= 0 && indice < nbBaton) {
                tailleBatons.set(indice, tailleBatons.get(indice) + 1);
            } else System.out.println( d + " est censurée, car en dehors de l'intervalle");
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



    public static void main(String[] args){

        ArrayList<Double> donnees = new ArrayList<>();

        Random r = new Random();
        int nbTirage = 1000000;
        int nbbaton = 1000;
        for (int i=0; i<nbTirage; i++){
            donnees.add(i,r.nextGaussian());
        }


        Histogramme H=new Histogramme(donnees);
        H.setNombreDeBaton(nbbaton);
        H.setIntervalle(-5, 5);
        H.dresseHistogramme(true);

        MultiPlotA multiPlot = new MultiPlotA();
        multiPlot.addPlotable("Histo - nbTirage: " + nbTirage + " nbBatons: " + nbbaton , H);
        multiPlot.setStroke("Histo - nbTirage: " + nbTirage + " nbBatons: " + nbbaton, MultiPlotA.normal);
        multiPlot.setColor("Histo - nbTirage: " + nbTirage + " nbBatons: " + nbbaton, Color.black);
        multiPlot.addPlotable("Courbe de Gauss - normalizé", new CourbeDeGauss(100, -5, 5));
        multiPlot.setColor("Courbe de Gauss - normalizé", Color.red);
        multiPlot.setStroke("Courbe de Gauss - normalizé", MultiPlotA.thick);


        double[] donneesA = { 1., 2., 3., 4., 4., 4., 5.,8.,8.,9.};
        Histogramme HA=new Histogramme(donneesA);
        HA.setNombreDeBaton(10);
        HA.setIntervalle(0, 10);
        HA.dresseHistogramme(true);

        MultiPlotA multiPlotA = new MultiPlotA();
        multiPlotA.addPlotable("Test", HA);



        multiPlot.plotNow();

    }




}
