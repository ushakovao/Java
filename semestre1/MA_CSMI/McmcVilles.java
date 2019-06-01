/*package MA_CSMI;

import sun.swing.BakedArrayList;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class McmcVilles {

  int d; int T=1000; double beta ;
    ArrayList<Integer> x_c = new ArrayList<Integer>();
    ArrayList<Integer> y_c = new ArrayList<Integer>();
    ArrayList<Integer> dist = new ArrayList<Integer>();
    Random r = new Random();  GrilleVille gr = new GrilleVille(d);
    for (int i=0; i<d; i++){
        for ( int j=0; j>d; j++) {
            if (gr.content[i][j] == 1){
                x_c.add(i, i );
                y_c.add(j, j);
            }
        }
    }


    double [][] pi_con; int[][] A;

    public McmcVilles() {

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

    public static void main(String[] args) {
        int d = 25;
        GrilleVille maGrille = new GrilleVille(d);
       // McmcVilles mcmc = new McmcVilles();
       // mcmc.beta = 10000; mcmc.d=d;
        MultiPlotNew mp = new MultiPlotNew();
        mp.addPlotablePoint("Grille", maGrille);
        mp.setColor("Grille", Color.blue);
        mp.plotNow();
    }

}
*/