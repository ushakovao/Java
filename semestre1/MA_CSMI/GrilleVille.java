package MA_CSMI;

import java.util.ArrayList;
import java.util.Random;



public class  GrilleVille implements PlotablePoint {

    Random r = new Random();
    int rad = 20;

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();
    private ArrayList<Double> radius = new ArrayList<Double>();

   ArrayList<Double> a = new ArrayList<Double>();
   ArrayList<Double> b = new ArrayList<Double>();

    int[][] content;
    int villes = r.nextInt(20);
    public GrilleVille(int d) {
        int villes = r.nextInt(20);
        content = new int[d][d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                content[i][j] = -1;
                for (int k=0; k <villes; k++){
                    int xi = r.nextInt(d);
                    int xj = r.nextInt(d);
                    content[xi][xj] = 1;
                }

                if (content[i][j] == 1) {
                    Xs.add((double) i);
                    Ys.add((double) j);
                    radius.add((double) rad);
                }
            }
        }
    }

    public ArrayList<Double> getXs()  {
        return Xs;
    }

    public ArrayList<Double> getYs() {
        return Ys;
    }

    public ArrayList<Double> radius() {
        return radius;
    }
}