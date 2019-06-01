package MA_CSMI;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Oxana on 10/01/2016.
 */
public class GrilleOx implements PlotablePoint {

    Random r = new Random();
    int rad = 20;
    int beta ;

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();
    private ArrayList<Double> radius = new ArrayList<Double>();

    int[][] content;

    public GrilleOx(int d) {
        content = new int[d][d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                content[i][j] = r.nextInt(2) * 2 - 1;
                if (content[i][j] == 1) {
                    Xs.add((double) i);
                    Ys.add((double) j);
                    radius.add((double) rad);
                }
            }
        }
    }
    public ArrayList<Double> getXs() {
        return Xs;
    }

    public ArrayList<Double> getYs() {
        return Ys;
    }

    public ArrayList<Double> radius() {
        return radius;
    }
}
