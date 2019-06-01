package FFT;

import TP2.ImportWav;
import TP2.VincentException;
import TP2.WavJava;
import TP3_fft_son.TestFFT;
import common.PlotableMA;

import java.util.ArrayList;


public class Spectrum implements PlotableMA {

    private ArrayList<Double> Xs = new ArrayList<Double>();
    private ArrayList<Double> Ys = new ArrayList<Double>();

    String filePath = TestFFT.filePath;

   public int n =0;

    WavJava wj = new WavJava ();

    public Spectrum(WavJava wj) {


    }



    double drawnStep = (wj.lastTimeDrawn - wj.firstTimeDrawn) / wj.nbPointDrawn;
    double integerDrawnStep = (drawnStep * wj.frequency);

    public WavJava Cut(String highoebass, double percent) {

    if (highoebass == "low")  {
        for (int i = 0; i < wj.nbPointDrawn; i++)

            if ((i * integerDrawnStep) > (percent * (wj.frequency )) && (i * integerDrawnStep)  == 0) {
                wj.samples[i] = wj.samples[i];
            }
            else {
                wj.samples[i] = 0;
            }
        return wj;
    }
    else if (highoebass == "high"){
        for (int i = 0; i < wj.nbPointDrawn+1; i++)

            if ((i * integerDrawnStep) < (percent * (wj.frequency ))) {
                wj.samples[i] = wj.samples[i];
            }
            else {
                System.out.println(wj+"totototo"+wj.samples);
                wj.samples[i] = 0;
            }
        return wj;
    }
    else if  (highoebass == "both")  {
        for (int i = 0; i < wj.nbPointDrawn; i++)

            if ((i * integerDrawnStep) < (percent * (wj.frequency )) || (i * integerDrawnStep) > (percent * (wj.frequency ))  && (i * integerDrawnStep)  == 0) {
                wj.samples[i] = wj.samples[i];
            }
            else {
                wj.samples[i] = 0;
            }
        return wj;
    }

    else throw new VincentException("For 'cut' need only : high, low, both");
    }


    public WavJava BassFilter (double hz ){  //Hz et pas kHz!!

        for (int i = 0; i < wj.nbPointDrawn; i++)

            if ((i * integerDrawnStep) < (hz) ) {
                    wj.samples[i] = wj.samples[i];
            }
            else {
                    wj.samples[i] = 0;
            }
        return wj;


    }


    @Override
    public ArrayList<Double> getXs() {
        ArrayList<Double> Xs = new ArrayList<Double>(wj.nbPointDrawn);
        for (int i = 0; i < wj.nbPointDrawn; i++)
            Xs.add(i * drawnStep);
        return Xs;
    }

    @Override
    public ArrayList<Double> getYs() {

        ArrayList<Double> Ys = new ArrayList<Double>(wj.nbPointDrawn);
        for (int i = 0; i < wj.nbPointDrawn; i++) {
            Ys.add((double) wj.samples[(int) (i * integerDrawnStep)]);
        }

        return Ys;

    }
}


