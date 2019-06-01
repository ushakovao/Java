package TP3_fft_son;
import FFT.*;
import TP2.ExportWav;
import TP2.ImportWav;
import TP2.WavJava;
import common.MultiPlotNew;
import common.PlotableMA;
import java.util.Random;

import java.util.ArrayList;

/**
 * Created by ushakova on 23/02/16.
 */
public class TestFFT {

    public static String filePath = "assets/sons/la.wav";
    public static String filePath2 = "assets/sons/out/la-filtred.wav";
    public static void main(String[] args) {


            /** number of samples*/
            int m=5;
            double x[]  = new  double[2*m] ;
            for (int i =0;i< m;i++) x[2*i]=1;

            System.out.println("\n original signal");
            for (int i=0;i<x.length;i++) System.out.printf("%8.1f ",x[i]);

            ComplexDoubleFFT fft=new ComplexDoubleFFT(m);

            /**ft = fourier transform*/
            fft.ft(x);
            System.out.println("\n transformed signal");
            for (int i=0;i<x.length;i++) System.out.printf("%8.1f ",x[i]);

            /**bt = back transform*/
            fft.bt(x);
            System.out.println("\n back-transformed signal");
            for (int i=0;i<x.length;i++) System.out.printf("%8.1f ",x[i]);

            /** why don't we get exactly the same signal that the original one ? */

            WavJava  w = new WavJava( );
            ImportWav impowav =  new ImportWav(filePath, w );




       /*     MultiPlotNew mp1 = new MultiPlotNew();
            w.lastTimeDrawn = 0.5;
            mp1.addPlotable("Wav initial form", w);
            mp1.plotNow();


            double [] w_new = new double[2*n];
            for (int i=0; i<n; i++){
                w_new[2*i] = w.samples[i];
                w.samples[i] = 1;
            }

            ComplexDoubleFFT fft = new ComplexDoubleFFT(n);

            /**ft = fourier transform*/
         /*   fft.ft(w_new);
            System.out.println("\n transformed signal");
            for (int i =0; i<w_new.length; i++ ){
                    w_new[i] *= 1./(w_new.length/2);
            }


           for (int i=0;i<w_new.length;i++) System.out.printf("%8.1f ",w_new[i]);

            MultiPlotNew mp2 = new MultiPlotNew();
            w.lastTimeDrawn = 0.5;
            mp2.addPlotable("Wav after FFT", w);
            mp2.plotNow();



            MultiPlotNew mp3 = new MultiPlotNew();
            w.lastTimeDrawn = 0.5;
            mp3.addPlotable("Wav after FFT and filter", w);
            mp3.plotNow();

            /**bt = back transform*/
           /* fft.bt(w_new);
            System.out.println("\n back-transformed signal");
            for (int i=0;i<w_new.length;i++) System.out.printf("%8.1f ",w_new[i]);

            MultiPlotNew mp4 = new MultiPlotNew();
            w.lastTimeDrawn = 0.5;
            mp4.addPlotable("Wav exported", w);
            mp4.plotNow();


            ExportWav ew = new ExportWav(filePath2, w);*/

           // System.out.println(ew);

    }

}

