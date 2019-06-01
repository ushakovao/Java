package MA_CSMI;
/*package MA_CSMI;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Oxana on 19/12/2015.

public class Grille implements PlotablePoint {
    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();
    private ArrayList<Double> radius = new ArrayList<Double>();

    int [][] content;
    MyRandom m = new MyRandom();
    double r = m.nextDouble();
    int d; int ham;
    public Grille (int d) {
        content = new int[d][d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                ham =
                if (r > 0.5)
                    content[i][j] = +1;
                else
                    content[i][j] = -1;
                if ( i -j = 1 && i-j = -1)
                    ham =
            }
        }
    }

    public Ham ( content ) {

    }
int n;
   public ArrayList getXs () {
        return n%d;}

    public ArrayList<Double> getRadius() {
        return radius;
    }

    c getYs(int n) {
        return n/d;}
    radius(int n){
            if (content [n%d][n/d] == 1)
                return = 1
            else return 0;

        }

}

}
*/

import MA_CSMI.MyRandom;
import MA_CSMI.PlotablePoint;
/*
import java.util.ArrayList;


public class Grille {
    ArrayList<Integer> states = new ArrayList<Integer>();
    ArrayList<Integer> element = new ArrayList<Integer>();
    MyRandom r = new MyRandom();
    public int d, T;
    int [][] content;
    //double [][] pi ;

//Initialisation de la grille

  public Grille (int d) {
            content = new int[d][d];
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < d; j++) {
                    int x = r.nextInt(2) * 2 - 1;
                    content[i][j] = x;
                }
            }
        }

  public  int HamiltonSum( Grille g) {
        int res = 0;
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (i == 0) {
                    if (j == 0) {
                        res += content[i][j] * (content[i + 1][j] + content[i][j + 1]);
                    } else if (j == d - 1) {
                        res += content[i][j] * (content[i + 1][j] + content[i][j - 1]);
                    } else {
                        res += content[i][j] * (content[i + 1][j] + content[i][j - 1] + content[i][j + 1]);
                    }
                } else if (i == d - 1) {
                    if (j == 0) {
                        res += content[i][j] * (content[i - 1][j] + content[i][j + 1]);
                    } else if (j == d - 1) {
                        res += content[i][j] * (content[i - 1][j] + content[i][j - 1]);
                    } else {
                        res += content[i][j] * (content[i - 1][j] + content[i][j - 1] + content[i][j + 1]);
                    }
                } else if (j == 0) {
                    if (i == 0) {
                        res += content[i][j] * (content[i + 1][j] + content[i][j + 1]);
                    } else if (i == d - 1) {
                        res += content[i][j] * (content[i - 1][j] + content[i][j + 1]);
                    } else {
                        res += content[i][j] * (content[i + 1][j] + content[i - 1][j] + content[i][j + 1]);
                    }
                } else if (j == d - 1) {
                    if (i == 0) {
                        res += content[i][j] * (content[i + 1][j] + content[i][j - 1]);
                    } else if (i == d - 1) {
                        res += content[i][j] * (content[i - 1][j] + content[i][j - 1]);
                    } else {
                        res += content[i][j] * (content[i - 1][j] + content[i + 1][j] + content[i][j - 1]);
                    }
                } else {

                    res += content[i][j] * (content[i + 1][j] + content[i][j + 1] + content[i - 1][j] + content[i][j - 1]);
                }

            }


        } System.out.println(res);
        return res;
    }

  /*public int Hamilton (int rowh, int colh){
       int resh=0;
       if (rowh == 0) {
           if (colh == 0) {
               resh += content[rowh][colh] * (content[rowh + 1][colh] + content[rowh][colh + 1]);
           } else if (colh == d - 1) {
               resh += content[rowh][colh] * (content[rowh + 1][colh] + content[rowh][colh - 1]);
           } else {
               resh += content[rowh][colh] * (content[rowh + 1][colh] + content[rowh][colh - 1] + content[rowh][colh + 1]);
           }
       } else if (rowh == d - 1) {
           if (colh == 0) {
               resh += content[rowh][colh] * (content[rowh - 1][colh] + content[rowh][colh + 1]);
           } else if (colh == d - 1) {
               resh += content[rowh][colh] * (content[rowh - 1][colh] + content[rowh][colh - 1]);
           } else {
               resh += content[rowh][colh] * (content[rowh - 1][colh] + content[rowh][colh - 1] + content[rowh][colh + 1]);
           }
       } else if (colh == 0) {
           if (rowh == 0) {
               resh += content[rowh][colh] * (content[rowh + 1][colh] + content[rowh][colh + 1]);
           } else if (rowh == d - 1) {
               resh += content[rowh][colh] * (content[rowh - 1][colh] + content[rowh][colh + 1]);
           } else {
               resh += content[rowh][colh] * (content[rowh + 1][colh] + content[rowh - 1][colh] + content[rowh][colh + 1]);
           }
       } else if (colh == d - 1) {
           if (rowh == 0) {
               resh += content[rowh][colh] * (content[rowh + 1][colh] + content[rowh][colh - 1]);
           } else if (rowh == d - 1) {
               resh += content[rowh][colh] * (content[rowh - 1][colh] + content[rowh][colh - 1]);
           } else {
               resh += content[rowh][colh] * (content[rowh - 1][colh] + content[rowh + 1][colh] + content[rowh][colh - 1]);
           }
       } else {

           resh += content[rowh][colh] * (content[rowh + 1][colh] + content[rowh][colh + 1] + content[rowh - 1][colh] + content[rowh][colh - 1]);
       }
       System.out.println(resh);
       return resh;

   }

*/



/*
    Grille gr = new Grille(d);



    public int[][] nextSimulation() {
        for (int t = 0; t < T; t++) {

            int row = r.nextInt(d);
            int col = r.nextInt(d);

            Grille grn = gr;
            grn.content[row][col] = gr.content[row][col] * (-1);
            double choix = r.nextDouble();
            if (choix < Math.min((Mcmc.grn.pi[row][col] / gr.pi[row][col]), 1))
                gr = grn;


        }
        return gr.content;

    }

  /*  public Grille nextSimulation() {
        for ( int t=0; t<T; t++){

            int row = r.nextInt(d);
            int col = r.nextInt(d);

            Grille grn = gr;
            grn.content[row][col]= gr.content[row][col]*(-1);
            double choix = r.nextDouble();
            if (choix < Math.min((grn.pi[row][col]/gr.pi[row][col]),1))
                gr=grn;


        }
        return gr;
    }




    public static void main(String[] args) {

        Mcmc mcmc = new Mcmc();

        ArrayList<Double> donnees = new ArrayList<Double>();

        for (int k = 0; k < 1000; k++) {
            donnees.add((double) mcmc.nextSimulation());
        }


        MultiPlot traceur = new MultiPlot();
        traceur.addPlotable("histogramme ",hist);



        traceur.plotNow();

    }

}
}*/