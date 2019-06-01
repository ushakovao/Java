package MA_CSMI;

import common.MultiPlotNew;

/**
 * Created by Oxana on 22/11/2015.
 */
public class B3d {
    public static void main(String[] args) {

        MultiPlotNew multiTraceur = new MultiPlotNew();
        Brownien3D brow;

        for (int i = 0; i < 1; i++) {
            brow = new Brownien3D(2, 0.001);
            multiTraceur.addPlotable("brownien " + i,brow);
        }

        multiTraceur.plotNow();

    }

}


