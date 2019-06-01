package MA_CSMI;

import common.MultiPlotNew;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Oxana on 10/01/2016.
 */

public class McmcGrille implements PlotablePoint {
    public double[] pi;
    public int T = 100000;
    public int tailleGrille = 5;
    public int tailleRadius = 20;
    public double beta;
    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();
    private ArrayList<Double> radius = new ArrayList<Double>();
    private int[][] grille;
    private int[][] grille_vieille;
    Random rand = new Random();
    public ArrayList<Double> getXs() { return Xs; }
    public ArrayList<Double> getYs() { return Ys; }
    public ArrayList<Double> radius() { return radius; }

    public McmcGrille(double b) {
        grille = new int[tailleGrille][tailleGrille];
        grille_vieille = new int[tailleGrille][tailleGrille];
        pi = new double[2];
        pi[0] = 0;
        pi[1] = 0;
        for(int i=0; i<tailleGrille ; i++){
            for(int j=0; j<tailleGrille ; j++){
                grille[i][j]=rand.nextInt(2)*2 -1;
                if(grille[i][j]==1){
                    Xs.add((double)i);
                    Ys.add((double)j);
                    radius.add((double)tailleRadius);
                }
            }
        }
        nextSimulationDebut();
        beta=b;
    }
    private double pi ( int h, double beta ) { return Math.exp(-beta*(double)h);}

    public void remplirXsYsRadius(){
        Xs.clear();
        Ys.clear();
        radius.clear();
        for(int i=0; i<tailleGrille ; i++){
            for(int j=0; j<tailleGrille ; j++){
                if(grille[i][j]==1){
                    Xs.add((double)i);
                    Ys.add((double)j);
                    radius.add((double)tailleRadius);
                }
            }
        }
    }



    public void copyAtoB ( int[][] A, int[][] B){
        for(int i=0; i<tailleGrille ; i++){
            for(int j=0; j<tailleGrille ; j++){
                B[i][j]=A[i][j];
            }
        }
    }


    public void nextSimulationDebut(){
        int hamiltonien = 0;
        for(int i=0; i<tailleGrille ; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                if( i-1>=0 ){
                    hamiltonien+=grille[i][j]*grille[i-1][j];
                }
                if( j+1<tailleGrille ){
                    hamiltonien+=grille[i][j]*grille[i][j+1];
                }
                if( i+1<tailleGrille ){
                    hamiltonien+=grille[i][j]*grille[i+1][j];
                }
                if( j-1>=0 ){
                    hamiltonien+=grille[i][j]*grille[i][j-1];
                }
            }
        }
        pi[0]=pi(hamiltonien, beta);
        //pi[0]=hamiltonien;
        return;
    }

    public void nextSimulation() {
        int xi=rand.nextInt(tailleGrille);
        int xj=rand.nextInt(tailleGrille);
        grille[xi][xj]*=-1;   //Si on a 1, il vaudra alors -1, et sinon on a bien l'inverse.
        // xi=rand.nextInt(tailleGrille);
        // xj=rand.nextInt(tailleGrille);
        // grille[xi][xj]*=-1;
        int hamiltonien = 0;
        // grille_vieille = grille;
        copyAtoB(grille, grille_vieille);
        for(int i=0; i<tailleGrille ; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                if( i-1>=0 ){
                    hamiltonien+=grille[i][j]*grille[i-1][j];
                }
                if( j+1<tailleGrille ){
                    hamiltonien+=grille[i][j]*grille[i][j+1];
                }
                if( i+1<tailleGrille ){
                    hamiltonien+=grille[i][j]*grille[i+1][j];
                }
                if( j-1>=0 ){
                    hamiltonien+=grille[i][j]*grille[i][j-1];
                }
            }
        }
        if(pi(hamiltonien, beta)<pi[0]){
            // if(hamiltonien<pi[0]){
            pi[1]=pi[0];
            pi[0]=pi(hamiltonien, beta);
            // pi[0]=hamiltonien;
        }
        else{
            copyAtoB(grille_vieille, grille);
        }
        // System.out.println(pi[0]);
        return;
    }

    public static void main(String[] args) {
        McmcGrille maGrille = new McmcGrille(200);
        System.out.print("debut : " + maGrille.pi[0] + "\n");
        for (int k = 0; k < maGrille.T; k++) {
            maGrille.nextSimulation();
            // System.out.println(k);
        }
        System.out.print("fin : " + maGrille.pi[0] + ", ");


        maGrille.remplirXsYsRadius();
        MultiPlotNew traceur = new MultiPlotNew();

        maGrille.remplirXsYsRadius();
        traceur.addPlotablePoint("fin", maGrille);
        traceur.setColor("fin", Color.red);
       //traceur.setDim(100+maGrille.tailleGrille*(int)(maGrille.tailleRadius), 100+maGrille.tailleGrille*(int)(maGrille.tailleRadius));
        traceur.plotNow();
    }








}


