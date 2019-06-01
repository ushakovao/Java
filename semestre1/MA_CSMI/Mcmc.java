package MA_CSMI;

import common.MultiPlotNew;

import java.awt.*;
import java.util.Random;




public class Mcmc {
    int d; int T=1000; double beta ;
    Random r = new Random();  GrilleOx gri = new GrilleOx(d);
    double [][] pi_con; int[][] A;

  GrilleVille gr = new GrilleVille(d);

    public Mcmc() {

        int sum_h = 0;  double Z;
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (i - 1 >= 0) {
                    sum_h +=A[i][j] * A[i - 1][j];
                }
                if (j + 1 < d) {
                    sum_h += A[i][j] * A[i][j + 1];
                }
                if (i + 1 < d) {
                    sum_h += A[i][j] * A[i + 1][j];
                }
                if (j - 1 >= 0) {
                    sum_h += A[i][j] * A[i][j - 1];
                }
            }
        }
        Z = Math.exp(-beta * sum_h);
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                int h=0;
                if (i - 1 >= 0) {
                    h += A[i][j] * A[i - 1][j];
                }
                if (j + 1 < d) {
                    h += A[i][j] * A[i][j + 1];
                }
                if (i + 1 < d) {
                    h += A[i][j] * A[i + 1][j];
                }
                if (j - 1 >= 0) {
                    h += A[i][j] * A[i][j - 1];
                }
                pi_con[i][j]=Math.exp(-beta * h)/Z;
            }
        }
    }

    public int[][] nextSimulation() {

        int xi = r.nextInt(d);
        int xj = r.nextInt(d);
        double p1; double p2;
        int[][] content_o;
        content_o= gr.content;

        for (int t = 1; t <= T; t++) {

            A = content_o; p1 = pi_con[xi][xj];

            int[][] content_n;
            content_n = new int [d][d];
            content_n[xi][xj] = gr.content[xi][xj]*(-1);
            A = content_n; p2 = pi_con[xi][xj];


            double choix = r.nextDouble();
            if (choix < Math.min(p2/p1, 1))
                content_o = content_n;
            if(pi_con[xi][xj]==0.)
                content_o = content_n;
        }
        return content_o;
    }
    public static void main(String[] args) {
        int d = 25;
        GrilleOx maGrille = new GrilleOx(d);
        //Mcmc mcmc = new Mcmc();
        //mcmc.beta = 10000; mcmc.d=d;
        MultiPlotNew mp = new MultiPlotNew();
        mp.addPlotablePoint("Grille", maGrille);
        mp.setColor("Grille", Color.blue);
        mp.plotNow();
    }

}

























































/*
public class Mcmc {
    double f, pi_p;
    double beta = 0.2;
    double z = 0;
    int d;
    int T;
    Grille gr = new Grille(d);
    double[][] pi;
    MyRandom r = new MyRandom();

    public Mcmc(Grille g) {


        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                Grille grn = g;
                g.content[i][j] = grn.content[i][j] * (-1);
                f = Math.exp((-beta) * grn.HamiltonSum(grn));
                z = f + z;
                g.content[i][j] = grn.content[i][j];
            }
        }

        //Notre pi[][] montre la valeur pi d'une grille où on a inverser un élélment à (i,j)
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                Grille grn = gr;
                g.content[i][j] = grn.content[i][j] * (-1);
                pi_p = (Math.exp((-beta) * grn.HamiltonSum(grn)) / z);
                g.content[i][j] = grn.content[i][j];
                pi[i][j] = pi_p;
            }
        }
    }
}

  /*  public int[][] nextSimulation() {
        for (int t = 0; t < T; t++) {

            int row = r.nextInt(d);
            int col = r.nextInt(d);
            Grille grn = gr;

            grn.content[row][col] = gr.content[row][col] * (-1);
            double choix = r.nextDouble();
            if (choix < Math.min((pi.gr[row][col] / gr.pi[row][col]), 1))
                gr = grn;


        }
      return gr.content;

    }


}








   /* ArrayList<Integer> states = new ArrayList<Integer>();
    ArrayList<Integer> element = new ArrayList<Integer>();
    MyRandom r = new MyRandom();
    public int d, T;

    private ArrayList<Double> pi = new ArrayList<Double>();

    private int etatCourant = 0;




    public Mcmc() {

        double z; double beta;
        for (int i = 0; i < d; i++){
            for (int j = 0; j< d; j++) {
                z = Math.exp((-beta) * Hamilton(i, j));
            }
        }

        for (int i = 0; i < nbEtat; i++)
            pi.add((double) (1/z)*Math.exp((-beta) * Hamilton(row, col));
        System.out.println(z);

    }

    public int Hamilton() {
        int res = 0;
        for (int i = 0; i < d - 1; i++) {
            for (int j = 0; j < d - 1; j++) {
                res += states.get(i * d + j) * (states.get((i + 1) * d + j) + states.get(i * d + (j + 1)));
            }

        }
        for (int i = 0; i < d - 1; i++) {
            res += states.get(i * d + (d - 1)) * states.get((i + 1) * d + (d - 1)) + states.get((d - 1) * d + i) * states.get((d - 1) * d + (i + 1));

        }

        System.out.println(res);
        return res;
    }


    public int nextSimulation() {




        for (int t = 1; t <= T; t++) {

            int aleat = 2 * rand.nextInt(2) - 1;
            int xi = ((etatCourant + aleat + nbEtat) % nbEtat);

            double choix = rand.nextDouble();
            if (choix < Math.min(pi.get(xi) / pi.get(etatCourant), 1))
                etatCourant = xi;

        }

        return etatCourant;
    }


    public static void main(String[] args) {

        Mcmc mcmc= new Mcmc();

        ArrayList<Double> donnees = new ArrayList<Double>();

        for (int k = 0; k < 1000; k++) {
            donnees.add((double) mcmc.nextSimulation());
        }

        MultiPlot traceur = new MultiPlot();
        traceur.plotNow();

    }

}
*/